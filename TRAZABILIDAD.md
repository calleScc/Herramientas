# Registro de trazabilidad: 22 de octubre de 2026

Este registro resume las consultas hechas a Claude durante el desarrollo del CRUD de `Product` el 22 de octubre de 2026.  El commit de ese día es ("Agrego CRUD de Product (controller, model, repository, service)").

| N | Consulta (prompt exacto) | Objetivo | Información y palabras clave obtenidas | Cómo llevó a la siguiente |
|---|---|---|---|---|
| 1 | "Ayudame a hacer lo del controller el model el repository y el service es un proyecto nuevo | Generar las 4 capas del proyecto | Kotlin, Spring Boot, JPA, Flyway, `ddl-auto: validate`, tabla `projects`, `Product`, `JpaRepository`, `@RestController`, `/api/products`. Se usó `Int` para el id y `Double` para el precio | Con el código escrito, faltaba ejecutarlo |
| 2 | "ayudame a corregir y dime porque esta mal" *[captura del error de arranque]* | Interpretar el error y buscar la solucion| `Schema validation: missing table [projects]`. Se supuso que Flyway se saltó la migración (`baselineOnMigrate`) y se sugirió consultar `flyway_schema_history` | La consulta mostró que esa suposición era incorrecta |
| 3 | "ya migre y mira lo q me sale" *[captura de pgAdmin con `flyway_schema_history`]* | Ver qué migraciones corrieron | V1 (`init`) y V2 (`repair`) sí se ejecutaron. En la base existe la tabla `product`, no `projects`. Se cambió a `@Table(name = "product")` | Faltaba confirmar si las columnas coincidían |
| 4 | "supongo q si esta bien, has una comprobacion" *[captura con las columnas de `product`]* | Comparar tabla contra modelo | `id` integer, `description` varchar(100), `price` varchar(100), `stock` integer. Se reescribió `Product` y la función `actualizar` del service | Con el modelo corregido, se volvió a ejecutar |
| 5 | "Funciona" *[captura de la consola]* | Confirmar que no aiga ningun error| `Successfully validated 2 migrations`, `Tomcat started on port 8081`, `Started HerramientasApplicationKt` | La app ya corría, así que se pasó a subir el código |

## Este es el commit del 22 de octubre

- `Product.kt`, `ProductRepository.kt`, `ProductService.kt` y `ProductController.kt`.
- Modelo ajustado a la tabla `product` (`id`, `description`, `price`, `stock`).
- Aplicación validada y arrancando en el puerto 8081.
