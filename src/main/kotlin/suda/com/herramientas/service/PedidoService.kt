package suda.com.herramientas.service

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import suda.com.herramientas.dto.PedidoRequest
import suda.com.herramientas.model.Pedido
import suda.com.herramientas.repository.PedidoRepository
import suda.com.herramientas.repository.ProductRepository
import java.security.SecureRandom

private const val PENDIENTE = "PENDIENTE"
private const val ENVIADO = "ENVIADO"
private const val RECIBIDO = "RECIBIDO"

@Service
class PedidoService(
    private val pedidoRepository: PedidoRepository,
    private val productRepository: ProductRepository
) {

    private val random = SecureRandom()

    @Transactional
    fun crear(request: PedidoRequest): List<Pedido> {
        val cliente = request.cliente.trim()
        if (cliente.isEmpty()) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre del cliente es obligatorio")
        }
        if (request.items.isEmpty()) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "El pedido debe tener al menos un producto")
        }

        // Regla: un cliente no puede tener otro pedido pendiente
        if (pedidoRepository.existsByClienteIgnoreCaseAndEstado(cliente, PENDIENTE)) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "El cliente ya tiene un pedido pendiente")
        }

        val numeroPedido = pedidoRepository.ultimoNumeroPedido() + 1
        val codigo = generarCodigo()
        val pedidos = mutableListOf<Pedido>()

        for (item in request.items) {
            if (item.cantidad <= 0) {
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, "La cantidad debe ser mayor a 0")
            }

            // Regla: el producto debe existir
            val producto = productRepository.findFirstByNameIgnoreCase(item.producto.trim())
                ?: throw ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "El producto '${item.producto}' no existe"
                )

            // Regla: debe haber stock suficiente
            if (producto.stock < item.cantidad) {
                throw ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No hay stock suficiente de '${producto.name}'. Disponible: ${producto.stock}"
                )
            }

            // Regla: al hacer el pedido, el stock baja
            producto.stock -= item.cantidad
            productRepository.save(producto)

            pedidos.add(
                Pedido(
                    numeroPedido = numeroPedido,
                    cliente = cliente,
                    nombreProducto = producto.name,
                    cantidad = item.cantidad,
                    estado = PENDIENTE,
                    codigoVerificacion = codigo
                )
            )
        }

        return pedidoRepository.saveAll(pedidos)
    }

    @Transactional
    fun enviar(numeroPedido: Int): List<Pedido> {
        val pedidos = buscar(numeroPedido)

        if (pedidos.first().estado != PENDIENTE) {
            throw ResponseStatusException(
                HttpStatus.CONFLICT,
                "Solo se puede enviar un pedido PENDIENTE. Estado actual: ${pedidos.first().estado}"
            )
        }

        pedidos.forEach { it.estado = ENVIADO }
        return pedidoRepository.saveAll(pedidos)
    }

    @Transactional
    fun recibir(numeroPedido: Int, codigo: String): List<Pedido> {
        val pedidos = buscar(numeroPedido)

        // Regla: antes de recibirlo, tiene que estar ENVIADO
        if (pedidos.first().estado != ENVIADO) {
            throw ResponseStatusException(
                HttpStatus.CONFLICT,
                "El pedido debe estar ENVIADO antes de recibirlo. Estado actual: ${pedidos.first().estado}"
            )
        }

        // Regla: solo se recibe con el codigo de verificacion correcto
        if (pedidos.first().codigoVerificacion != codigo.trim()) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "El codigo de verificacion es incorrecto")
        }

        pedidos.forEach { it.estado = RECIBIDO }
        return pedidoRepository.saveAll(pedidos)
    }

    fun consultar(numeroPedido: Int): List<Pedido> {
        return buscar(numeroPedido)
    }

    private fun buscar(numeroPedido: Int): List<Pedido> {
        val pedidos = pedidoRepository.findByNumeroPedido(numeroPedido)
        if (pedidos.isEmpty()) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "El pedido $numeroPedido no existe")
        }
        return pedidos
    }

    private fun generarCodigo(): String {
        return random.nextInt(1_000_000).toString().padStart(6, '0')
    }
}