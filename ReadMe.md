# Sistema para calcular compras (PurchaseCalculator)

Este repositorio contiene la implementación y las pruebas unitarias en Java para un sistema automatizado de cálculo de compras de comercio electrónico, enfocado en asegurar la precisión de subtotales, validaciones de compra, descuentos y precios finales.

## 1. Descripción del problema

El sistema gestiona la lógica financiera básica de una plataforma de comercio electrónico. Realiza tres cálculos principales:

* **Subtotal:** Multiplica el precio unitario de un producto por la cantidad solicitada.

* **Descuento:** Evalúa si el subtotal califica para una rebaja promocional del $10\%$, es decir, si es igual o mayor a \$1000.00 MXN.

* **Precio Final:** Determina el monto total a pagar restando el descuento aplicado al subtotal.

**¿Por qué es importante verificarlos?**
En cualquier plataforma de comercio electrónico, los errores en los cálculos monetarios pueden generar pérdidas financieras directas para el negocio (si se aplican descuentos erróneos o precios menores) o insatisfacción y pérdida de confianza en los clientes (si se les cobra de más). Las pruebas garantizan que las reglas financieras se cumplan bajo cualquier escenario.

## 2. Reglas de Negocio

Las reglas de operación implementadas en el sistema son las siguientes:

1. **Cálculo del subtotal (`calculateSubtotal`):** Se obtiene multiplicando $\text{precio} \times \text{cantidad}$. Si el precio ingresado es negativo, el sistema por seguridad retorna un subtotal de `0`.

2. **Validación de compra (`isaValidPurchase`):** Una compra se considera válida únicamente si tanto el precio como la cantidad son strictly mayores a `0` ($\text{precio} > 0$ y $\text{quantity} > 0$). Si alguno es cero o negativo, se rechaza la compra y se emite un mensaje de error por consola.

3. **Aplicación del descuento (`calculateDiscount` y `calculateFinalPrice`):** Se otorga un descuento del $10\%$ ($0.10$) **si y solo si** el subtotal es mayor o igual a $1000$ MXN ($\text{subTotal} \ge 1000$). De lo contrario, el descuento es `0`.

## 3. Casos de Prueba Implementados

A continuación se detallan los casos de prueba ejecutados en la suite de JUnit:

| Método Probado | Datos de Entrada | Resultado Esperado | Resultado Obtenido | Propósito del Caso | 
| ----- | ----- | ----- | ----- | ----- | 
| `calculateSubtotal` | `price = 1500, quantity = 1` | `1500.0` | `1500.0` | Verificar multiplicación con valores positivos estándar. | 
| `calculateSubtotal` | `price = 100, quantity = 3` | `300.0` | `300.0` | Verificar multiplicación para compras regulares sin descuento. | 
| `calculateSubtotal` | `price = 0, quantity = 3` | `0.0` | `0.0` | Validar comportamiento cuando el precio es cero. | 
| `calculateSubtotal` | `price = 100, quantity = 0` | `0.0` | `0.0` | Validar comportamiento cuando la cantidad es cero. | 
| `calculateSubtotal` | `price = -100, quantity = 5` | `0.0` | `0.0` | Verificar manejo de seguridad ante precios negativos. | 
| `isaValidPurchase` | `price = 1500, quantity = 1` | `true` | `true` | Comprobar aceptación de compra válida estándar. | 
| `isaValidPurchase` | `price = 100, quantity = 3` | `true` | `true` | Comprobar aceptación de compra estándar sin descuento. | 
| `isaValidPurchase` | `price = 0, quantity = 5` | `false` | `false` | Rechazar compra con precio igual a cero. | 
| `isaValidPurchase` | `price = 100, quantity = 0` | `false` | `false` | Rechazar compra con cantidad cero. | 
| `isaValidPurchase` | `price = -100, quantity = 5` | `false` | `false` | Rechazar compra con precio negativo. | 
| `calculateDiscount` | `subTotal = 600` | `0.0` | `0.0` | Verificar que subtotales menores a 1000 no generan descuento. | 
| `calculateDiscount` | `subTotal = 1000` | `100.0` | `100.0` | Verificar aplicación exacta del descuento en el límite inferior ($1000$). | 
| `calculateDiscount` | `subTotal = 5000` | `500.0` | `500.0` | Verificar cálculo correcto del descuento para subtotales altos. | 
| `calculateFinalPrice` | `subTotal = 300` | `300.0` | `300.0` | Comprobar que el precio final coincide con el subtotal si no hay descuento. | 
| `calculateFinalPrice` | `subTotal = 1000` | `900.0` | `900.0` | Comprobar resta correcta del descuento en el límite ($1000 - 100$). | 
| `calculateFinalPrice` | `subTotal = 5000` | `4500.0` | `4500.0` | Comprobar resta correcta del descuento en montos mayores ($5000 - 500$). | 

## 4. Alcance y futuros casos límite (Scope)

Para garantizar la máxima solidez en un entorno de producción real, se identifican los siguientes **casos límite y escenarios extendidos** recomendados para futuras iteraciones del proyecto:

1. **Fronteras con decimales exactos (Boundary testing):**

   * **Subtotal justo debajo del umbral (`subTotal = 999.99`):** Comprobar que no se aplique descuento erróneamente por un redondeo involuntario.

   * **Subtotal inmediatamente superior al umbral (`subTotal = 1000.01`):** Verificar la aplicación del descuento en el valor flotante contiguo al límite.

2. **Cantidades y precios mínimos permitidos:**

   * **Unidad monetaria mínima (`price = 0.01`, `quantity = 1`):** Confirmar que el sistema procesa transacciones con los montos mínimos válidos en centavos.

3. **Desbordamiento de Memoria (Numeric Overflow):**

   * **Valores numéricos extremos (`price = Double.MAX_VALUE`, `quantity = Integer.MAX_VALUE`):** Probar el comportamiento del sistema ante valores masivos para prevenir desbordamientos o resultados del tipo `Infinity` / `NaN`.

4. **Pruebas de aserción con tolerancia (`Delta` Assertions):**

   * **Redondeo monetario:** Implementar `assertEquals(expected, actual, 0.001)` en las aserciones de JUnit para manejar imprecisiones inherentes al punto flotante de `double`.

## 5. Resultados de Ejecución

Las pruebas se ejecutaron utilizando **JUnit 6** en **IntelliJ IDEA**, obteniendo los siguientes resultados reales:

* **Pruebas Totales Ejecutadas:** 14

* **Pruebas Exitosas (Passed):** 14

* **Pruebas Fallidas (Failed):** 0

* **Estado del Proceso:** Finalizado con éxito (`exit code 0`).

## 6. Errores que se pueden prevenir

El diseño de pruebas unitarias permite detectar de manera anticipada fallos comunes de programación como:

1. **Errores en límites de condiciones (`Off-by-one` / fronteras):** Si por error la regla de descuento se hubiera escrito como `subTotal > 1000` (excluyendo el 1000), la prueba unitaria con subtotal de $1000$ habría fallado al retornar $0$ de descuento en lugar de $100$.

2. **Falta de manejo de valores inválidos (Robustez):** Si el método de subtotal no contemplara el filtro para precios negativos (`price < 0`), una multiplicación de un precio negativo por una cantidad positiva arrojaría un subtotal negativo, corrompiendo todo el flujo financiero de la compra. Las pruebas aseguran que se devuelva `0` en estos casos anómalos.

## 7. Conclusión

Las pruebas unitarias son un pilar fundamental en el desarrollo de software para comercio electrónico. Permiten aislar y verificar de forma metódica cada componente de la lógica de negocio (subtotales, validaciones, descuentos y totales). Contribuyen directamente a la confiabilidad de la aplicación al garantizar que cualquier cambio futuro en el código no rompa las reglas financieras esenciales, protegiendo tanto los ingresos del negocio como la experiencia de compra del usuario final.