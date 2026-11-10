package suda.com.herramientas.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "pedidos")
class Pedido(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column(name = "numero_pedido")
    var numeroPedido: Int = 0,

    @Column(name = "cliente", length = 100)
    var cliente: String = "",

    @Column(name = "nombre_producto", length = 100)
    var nombreProducto: String = "",

    @Column(name = "cantidad")
    var cantidad: Int = 0,

    @Column(name = "estado", length = 20)
    var estado: String = "PENDIENTE",

    @Column(name = "codigo_verificacion", length = 6)
    var codigoVerificacion: String = ""
)