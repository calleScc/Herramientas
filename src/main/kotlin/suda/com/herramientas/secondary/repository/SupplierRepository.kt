package suda.com.herramientas.secondary.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import suda.com.herramientas.secondary.model.Supplier

@Repository
interface SupplierRepository : JpaRepository<Supplier, Int>