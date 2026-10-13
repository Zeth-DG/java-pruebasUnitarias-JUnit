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
| `calculateSubtotal` | `price = 999.99, quantity = 1` | `999.99` | `999.99` | **Caso borde:** Verificar cálculo en la frontera previa al umbral de descuento. | 
| `calculateSubtotal` | `price = 0, quantity = 3` | `0.0` | `0.0` | Validar comportamiento cuando el precio es cero. | 
| `calculateSubtotal` | `price = 100, quantity = 0` | `0.0` | `0.0` | Validar comportamiento cuando la cantidad es cero. | 
| `calculateSubtotal` | `price = -100, quantity = 5` | `0.0` | `0.0` | Verificar manejo de seguridad ante precios negativos. | 
| `isaValidPurchase` | `price = 0.01, quantity = 2` | `true` | `true` | **Caso borde:** Validar aceptación con el precio mínimo mayor a cero. | 
| `isaValidPurchase` | `price = 1500, quantity = 1` | `true` | `true` | Comprobar aceptación de compra válida estándar con cantidad mínima (`1`). | 
| `isaValidPurchase` | `price = 100, quantity = 3` | `true` | `true` | Comprobar aceptación de compra estándar sin descuento. | 
| `isaValidPurchase` | `price = 0, quantity = 5` | `false` | `false` | Rechazar compra con precio igual a cero. | 
| `isaValidPurchase` | `price = 100, quantity = 0` | `false` | `false` | Rechazar compra con cantidad cero. | 
| `isaValidPurchase` | `price = -100, quantity = 5` | `false` | `false` | Rechazar compra con precio negativo. | 
| `calculateDiscount` | `subTotal = 600` | `0.0` | `0.0` | Verificar que subtotales menores a 1000 no generan descuento. | 
| `calculateDiscount` | `subTotal = 999.99` | `0.0` | `0.0` | **Caso borde:** Confirmar que $999.99$ no recibe descuento (límite estricto). | 
| `calculateDiscount` | `subTotal = 1000` | `100.0` | `100.0` | **Caso borde:** Verificar aplicación exacta en el umbral exacto ($1000$). | 
| `calculateDiscount` | `subTotal = 1000.01` | `100.001` | `100.001` | **Caso borde:** Verificar aplicación inmediata del descuento tras superar el umbral. | 
| `calculateDiscount` | `subTotal = 5000` | `500.0` | `500.0` | Verificar cálculo correcto del descuento para subtotales altos. | 
| `calculateFinalPrice` | `subTotal = 300` | `300.0` | `300.0` | Comprobar que el precio final coincide con el subtotal si no hay descuento. | 
| `calculateFinalPrice` | `subTotal = 1000` | `900.0` | `900.0` | Comprobar resta correcta del descuento en el límite ($1000 - 100$). | 
| `calculateFinalPrice` | `subTotal = 5000` | `4500.0` | `4500.0` | Comprobar resta correcta del descuento en montos mayores ($5000 - 500$). | 

## 4. Alcance y futuros casos límite 

Se han incorporado exitosamente las pruebas de valores frontera (como `999.99`, `1000.01` y `0.01`). Para garantizar aún más la máxima solidez en un entorno de producción real, se identifican las siguientes oportunidades de mejora:

1. **Manejo de cantidades negativas en el subtotal:**
   * **Inconsistencia de insumos (`price = 100`, `quantity = -5`):** La validación actual en `calculateSubtotal` solo verifica si `price < 0`. Sería recomendable agregar una comprobación para `quantity < 0` para prevenir subtotales negativos si se llama al método directamente.

2. **Desbordamiento de memoria (Numeric Overflow):**
   * **Valores numéricos extremos (`price = Double.MAX_VALUE`, `quantity = Integer.MAX_VALUE`):** Evaluar el comportamiento ante valores masivos para prevenir resultados del tipo `Infinity` o `NaN`.

3. **Pruebas de aserción con tolerancia (`Delta` Assertions):**
   * En cálculos flotantes que involucren centavos (como el retorno de `100.001`), se sugiere adoptar de manera explícita la firma con delta: `assertEquals(expected, actual, 0.0001)` para prevenir imprecisiones de representación IEEE 754.

## 5. Resultados de ejecución

Las pruebas actualizadas se ejecutaron utilizando **JUnit 6** en **IntelliJ IDEA**, obteniendo los siguientes resultados reales:

* **Pruebas totales ejecutadas:** 18

* **Pruebas exitosas (Passed):** 18

* **Pruebas fallidas (Failed):** 0

* **Estado del proceso:** Finalizado con éxito (`exit code 0`).

## 6. Errores que se pueden prevenir con la realización de pruebas unitarias

El diseño de pruebas unitarias permite detectar de manera anticipada fallos comunes de programación como:

1. **Errores de Frontera / "Off-by-one":** Al probar valores como `999.99` y `1000.00`, garantizamos que la condición de negocio sea exactamente `subTotal >= 1000` y no `subTotal > 1000`, evitando aplicar descuentos indebidos o negarlos cuando el cliente alcanza el monto exacto.

2. **Falta de manejo de valores inválidos (Robustez):** Si el método de subtotal no contemplara la validación para precios negativos (`price < 0`), una multiplicación arrojaría un subtotal negativo, corrompiendo el cálculo en la caja. Las pruebas garantizan que la aplicación sea resiliente ante datos de entrada anómalos.

## 7. Conclusión

Las pruebas unitarias son un pilar fundamental en el desarrollo de software para comercio electrónico. La inclusión de pruebas en los límites de decisión (valores borde) fortalece el sistema frente a comportamientos inesperados en las transacciones reales. Permiten aislar y verificar metódicamente cada componente de la lógica de negocio, asegurando que los cambios futuros no rompan las reglas financieras del sistema.
