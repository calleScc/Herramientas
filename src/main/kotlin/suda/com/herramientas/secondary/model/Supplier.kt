package suda.com.herramientas.secondary.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "supplier")
class Supplier(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column(name = "name", length = 100)
    var name: String = "",

    @Column(name = "phone", length = 30)
    var phone: String = "",

    @Column(name = "email", length = 100)
    var email: String = ""
)