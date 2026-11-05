package suda.com.herramientas.secondary.controller

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
import suda.com.herramientas.secondary.model.Supplier
import suda.com.herramientas.secondary.service.SupplierService

@RestController
@RequestMapping("/api/suppliers")
class SupplierController(private val supplierService: SupplierService) {

    @GetMapping
    fun listar(): List<Supplier> = supplierService.listar()

    @GetMapping("/{id}")
    fun buscar(@PathVariable id: Int): ResponseEntity<Supplier> {
        val supplier = supplierService.buscarPorId(id)
        return if (supplier != null) ResponseEntity.ok(supplier)
        else ResponseEntity.notFound().build()
    }

    @PostMapping
    fun crear(@RequestBody supplier: Supplier): ResponseEntity<Supplier> =
        ResponseEntity.status(HttpStatus.CREATED).body(supplierService.crear(supplier))

    @PutMapping("/{id}")
    fun actualizar(
        @PathVariable id: Int,
        @RequestBody supplier: Supplier
    ): ResponseEntity<Supplier> {
        val actualizado = supplierService.actualizar(id, supplier)
        return if (actualizado != null) ResponseEntity.ok(actualizado)
        else ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{id}")
    fun eliminar(@PathVariable id: Int): ResponseEntity<Void> =
        if (supplierService.eliminar(id)) ResponseEntity.noContent().build()
        else ResponseEntity.notFound().build()
}