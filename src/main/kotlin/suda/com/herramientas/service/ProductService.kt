package suda.com.herramientas.service

import org.springframework.stereotype.Service
import suda.com.herramientas.model.Product
import suda.com.herramientas.repository.ProductRepository

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun saludoService(): String {
        return "Hola desde el Service"
    }

    fun listar(): List<Product> {
        return productRepository.findAll()
    }

    fun buscarPorId(id: Int): Product? {
        return productRepository.findById(id).orElse(null)
    }

    fun crear(product: Product): Product {
        product.id = null
        return productRepository.save(product)
    }

    fun actualizar(id: Int, datos: Product): Product? {
        val existente = productRepository.findById(id).orElse(null) ?: return null
        existente.description = datos.description
        existente.price = datos.price
        existente.stock = datos.stock
        return productRepository.save(existente)
    }

    fun eliminar(id: Int): Boolean {
        if (!productRepository.existsById(id)) return false
        productRepository.deleteById(id)
        return true
    }
}