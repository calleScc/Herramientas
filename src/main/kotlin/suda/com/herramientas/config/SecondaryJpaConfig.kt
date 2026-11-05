package suda.com.herramientas.config

import jakarta.persistence.EntityManagerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager

@Configuration
@EnableJpaRepositories(
    basePackages = ["suda.com.herramientas.secondary.repository"],
    entityManagerFactoryRef = "secondaryEntityManagerFactory",
    transactionManagerRef = "secondaryTransactionManager"
)
class SecondaryJpaConfig {

    @Bean("secondaryEntityManagerFactory")
    fun secondaryEntityManagerFactory(
        secondary: SecondaryDatabase
    ): LocalContainerEntityManagerFactoryBean {
        val emf = LocalContainerEntityManagerFactoryBean()
        emf.dataSource = secondary.dataSource
        emf.setPackagesToScan("suda.com.herramientas.secondary.model")
        emf.jpaVendorAdapter = HibernateJpaVendorAdapter()
        emf.setPersistenceUnitName("secondary")
        emf.setJpaPropertyMap(
            mapOf(
                // "update" crea la tabla sola en herramientas2 (esa base está vacía)
                "hibernate.hbm2ddl.auto" to "update",
                "hibernate.show_sql" to "true",
                "hibernate.format_sql" to "true",
                "hibernate.physical_naming_strategy" to
                        "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy"
            )
        )
        return emf
    }

    @Bean("secondaryTransactionManager")
    fun secondaryTransactionManager(
        @Qualifier("secondaryEntityManagerFactory") emf: EntityManagerFactory
    ): PlatformTransactionManager = JpaTransactionManager(emf)
}