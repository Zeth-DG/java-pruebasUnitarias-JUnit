import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PurchaseCalculatorTest {
    PurchaseCalculator purchaseCalculator = new PurchaseCalculator();
    @Test
    @DisplayName("Test to: calculate subTotal")
    void calculateSubtotal(){
        assertEquals(1500, purchaseCalculator.calculateSubtotal(1500, 1)); //positivos
        assertEquals(300, purchaseCalculator.calculateSubtotal(100, 3)); // sin descuento
        assertEquals(0, purchaseCalculator.calculateSubtotal(0, 3)); //precio = 0
        assertEquals(0, purchaseCalculator.calculateSubtotal(100, 0)); //cantidad 0
        assertEquals(0, purchaseCalculator.calculateSubtotal(-100, 5)); //precio negativo
    }//calculate subTotal

    @Test
    @DisplayName("Test to: is a valid purchase?")
    void isaValidPurchase(){
        assertTrue(purchaseCalculator.isaValidPurchase(1500, 1)); //positivos
        assertTrue(purchaseCalculator.isaValidPurchase(100, 3)); //positivos
        assertFalse(purchaseCalculator.isaValidPurchase(0, 5)); //precio igual a 0
        assertFalse(purchaseCalculator.isaValidPurchase(100, 0)); //cantidad igual a 0
        assertFalse(purchaseCalculator.isaValidPurchase(-100, 5)); //precio negativo
    }//is a valid purchase?

    @Test
    @DisplayName("Test to: calculate discount")
    void calculateDiscount(){
        assertEquals(0,purchaseCalculator.calculateDiscount(600)); //menor a 1000
        assertEquals(100,purchaseCalculator.calculateDiscount(1000)); //justo 1000
        assertEquals(500,purchaseCalculator.calculateDiscount(5000)); //mayor a 1000
    }//calculate discount

    @Test
    @DisplayName("Test to: calculate final price")
    void calculateFinalPrice(){
        assertEquals(300, purchaseCalculator.calculateFinalPrice(300)); //sin descuento
        assertEquals(900, purchaseCalculator.calculateFinalPrice(1000)); //con descuento, subtotal = 1000
        assertEquals(4500, purchaseCalculator.calculateFinalPrice(5000));//con descuento, subtotal mayor a 1000
    }//calculate total

}//class
