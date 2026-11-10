package suda.com.herramientas.dto

class ItemRequest(
    var producto: String = "",
    var cantidad: Int = 0
)

class PedidoRequest(
    var cliente: String = "",
    var items: List<ItemRequest> = emptyList()
)

class RecibirRequest(
    var codigo: String = ""
)