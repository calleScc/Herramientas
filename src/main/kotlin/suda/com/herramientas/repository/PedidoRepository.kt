package suda.com.herramientas.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import suda.com.herramientas.model.Pedido

@Repository
interface PedidoRepository : JpaRepository<Pedido, Int> {

    fun existsByClienteIgnoreCaseAndEstado(cliente: String, estado: String): Boolean

    fun findByNumeroPedido(numeroPedido: Int): List<Pedido>

    @Query("select coalesce(max(p.numeroPedido), 0) from Pedido p")
    fun ultimoNumeroPedido(): Int
}