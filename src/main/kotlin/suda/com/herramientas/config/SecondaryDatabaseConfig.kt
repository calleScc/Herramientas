package suda.com.herramientas.config

import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate

class SecondaryDatabase(val dataSource: HikariDataSource) : AutoCloseable {
    val jdbcTemplate = JdbcTemplate(dataSource)
    override fun close() = dataSource.close()
}

@Configuration
class SecondaryDatabaseConfig {

    @Bean
    fun secondaryDatabase(
        @Value("\${app.secondary-datasource.url}") url: String,
        @Value("\${app.secondary-datasource.username}") user: String,
        @Value("\${app.secondary-datasource.password}") pass: String,
        @Value("\${app.secondary-datasource.driver-class-name}") driver: String
    ): SecondaryDatabase {
        val ds = HikariDataSource().apply {
            poolName = "herramientas2-pool"
            jdbcUrl = url
            username = user
            password = pass
            driverClassName = driver
        }
        return SecondaryDatabase(ds)
    }
}