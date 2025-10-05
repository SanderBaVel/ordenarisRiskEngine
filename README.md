"# ordenarisRiskEngine" 
## Cómo ejecutar el proyecto

1. **Clonar el repositorio:**
   ```bash
   git clone https://github.com/SanderBaVel/ordenarisRiskEngine.git
   ```

2. importa crear bd y importar script sql: db/Dump20251005.sql
   ```
   CREATE DATABASE motor_riesgos;
   USE motor_riesgos;

   ```

3. **Configurar credenciales en `src/main/resources/application.properties`:**
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/exampledatabase
   spring.datasource.username=root
   spring.datasource.password=
   spring.jpa.hibernate.ddl-auto=none
   spring.jpa.show-sql=true
   spring.sql.init.mode=never
   ```

4. **Ejecutar el proyecto Spring Boot:**
   ```bash
   mvn spring-boot:run
   ```
   o desde tu IDE (Eclipse / IntelliJ / VSCode).

---

## Supuestos y limitaciones

- El motor evalúa **6 reglas** definidas en `OrdenarisRiskEngineService.java`.
- No incluye autenticación ni control de usuarios (prueba técnica).
- Conectado a una base de datos **MySQL local**.
- No se manejan transacciones ni logs en esta versión.

---

## Reglas de evaluación

1. **Deuda Activa:** empresa con deuda vencida > 90 días → `RECHAZADO`
2. **Alta Solicitud vs Ventas:** monto > 8x promedio mensual → `ALTO`
3. **Empresa Nueva:** antigüedad < 2 años → `MEDIO`
4. **Demanda Legal Abierta:** juicio activo → `ALTO`
5. **Historial Excelente:** últimos 12 pagos sin retrasos → reduce riesgo
6. **Producto Estricto:** arrendamiento financiero → aumenta riesgo

## Endpoint principal

**POST** `/api/evaluar`

### Ejemplo de cuerpo JSON:
```json
{
  "empresaId": "2",
  "montoSolicitado": 50000.00,
  "productoFinanciero": "LINEA_OPERATIVA",
  "fechaSolicitud": "2025-10-04"
}

## Postman Collection

Importa la colección `postman/peticiones-ordenaris.postman_collection.json` para probar los distintos escenarios de evaluación.