package suda.com.herramientas.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import suda.com.herramientas.model.Product
import suda.com.herramientas.service.ProductService

@RestController
@RequestMapping("/api/products")
class ProductController(private val productService: ProductService) {

    @GetMapping("/hola")
    fun saludo(): String = "Hola mundo"

    @GetMapping("/adios")
    fun despedida(): String = "Adios mundo"

    @GetMapping("/saludoService")
    fun saludoService(): String = productService.saludoService()

    @GetMapping
    fun listar(): List<Product> = productService.listar()

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Int): ResponseEntity<Product> {
        val product = productService.buscarPorId(id)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(product)
    }

    @PostMapping
    fun crear(@RequestBody product: Product): ResponseEntity<Product> {
        val creado = productService.crear(product)
        return ResponseEntity.status(HttpStatus.CREATED).body(creado)
    }

    @PutMapping("/{id}")
    fun actualizar(@PathVariable id: Int, @RequestBody product: Product): ResponseEntity<Product> {
        val actualizado = productService.actualizar(id, product)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(actualizado)
    }

    @DeleteMapping("/{id}")
    fun eliminar(@PathVariable id: Int): ResponseEntity<Void> {
        return if (productService.eliminar(id)) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}