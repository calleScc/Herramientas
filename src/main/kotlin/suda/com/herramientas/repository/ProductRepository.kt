package suda.com.herramientas.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import suda.com.herramientas.model.Product

@Repository
interface ProductRepository : JpaRepository<Product, Int>