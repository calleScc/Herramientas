package suda.com.herramientas.secondary.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import suda.com.herramientas.secondary.model.Supplier
import suda.com.herramientas.secondary.repository.SupplierRepository

@Service
@Transactional("secondaryTransactionManager")
class SupplierService(private val supplierRepository: SupplierRepository) {

    fun listar(): List<Supplier> = supplierRepository.findAll()

    fun buscarPorId(id: Int): Supplier? =
        supplierRepository.findById(id).orElse(null)

    fun crear(supplier: Supplier): Supplier {
        supplier.id = null
        return supplierRepository.save(supplier)
    }

    fun actualizar(id: Int, datos: Supplier): Supplier? {
        val existente = supplierRepository.findById(id).orElse(null) ?: return null
        existente.name = datos.name
        existente.phone = datos.phone
        existente.email = datos.email
        return supplierRepository.save(existente)
    }

    fun eliminar(id: Int): Boolean {
        if (!supplierRepository.existsById(id)) return false
        supplierRepository.deleteById(id)
        return true
    }
}