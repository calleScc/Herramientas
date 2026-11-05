package suda.com.herramientas.config

import jakarta.persistence.EntityManagerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories(
    basePackages = ["suda.com.herramientas.repository"],
    entityManagerFactoryRef = "entityManagerFactory",
    transactionManagerRef = "transactionManager"
)
class PrimaryJpaConfig {

    @Bean
    @Primary
    fun entityManagerFactory(
        dataSource: DataSource,
        @Value("\${spring.jpa.hibernate.ddl-auto:validate}") ddlAuto: String,
        @Value("\${spring.jpa.show-sql:true}") showSql: String
    ): LocalContainerEntityManagerFactoryBean {
        val emf = LocalContainerEntityManagerFactoryBean()
        emf.dataSource = dataSource
        emf.setPackagesToScan("suda.com.herramientas.model")
        emf.jpaVendorAdapter = HibernateJpaVendorAdapter()
        emf.setPersistenceUnitName("default")
        emf.setJpaPropertyMap(
            mapOf(
                "hibernate.hbm2ddl.auto" to ddlAuto,
                "hibernate.show_sql" to showSql,
                "hibernate.format_sql" to "true",
                "hibernate.physical_naming_strategy" to
                        "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy"
            )
        )
        return emf
    }

    @Bean
    @Primary
    fun transactionManager(
        @Qualifier("entityManagerFactory") emf: EntityManagerFactory
    ): PlatformTransactionManager = JpaTransactionManager(emf)
}