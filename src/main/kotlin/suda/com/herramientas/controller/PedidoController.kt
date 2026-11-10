package suda.com.herramientas.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import suda.com.herramientas.dto.PedidoRequest
import suda.com.herramientas.dto.RecibirRequest
import suda.com.herramientas.model.Pedido
import suda.com.herramientas.service.PedidoService

@RestController
@RequestMapping("/api/pedidos")
class PedidoController(private val pedidoService: PedidoService) {

    @PostMapping
    fun crear(@RequestBody request: PedidoRequest): ResponseEntity<List<Pedido>> {
        val pedidos = pedidoService.crear(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidos)
    }

    @PutMapping("/{numeroPedido}/enviar")
    fun enviar(@PathVariable numeroPedido: Int): List<Pedido> =
        pedidoService.enviar(numeroPedido)

    @PutMapping("/{numeroPedido}/recibir")
    fun recibir(
        @PathVariable numeroPedido: Int,
        @RequestBody request: RecibirRequest
    ): List<Pedido> = pedidoService.recibir(numeroPedido, request.codigo)

    @GetMapping("/{numeroPedido}")
    fun consultar(@PathVariable numeroPedido: Int): List<Pedido> =
        pedidoService.consultar(numeroPedido)
}