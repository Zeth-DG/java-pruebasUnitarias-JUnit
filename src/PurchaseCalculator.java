public class PurchaseCalculator {
    public double calculateSubtotal (double price, int quantity){
        if (price < 0 ){
            return 0;
        }
        double subTotal = price * quantity;
        return subTotal;
    }//method calculateSubtotal

    public boolean isaValidPurchase(double price, int quantity){
        if (price <= 0 || quantity <= 0){
            System.out.println("Error input price or quantity");
            return false;
        }//if
        return true;
    }//method isAValidPurchase

    public double calculateDiscount(double subTotal){
        double discount = 0;
        double percentageD = 0.10;
        if (subTotal >= 1000){
            discount = subTotal * percentageD;
            return discount;
        }
        return discount;
    }//method calculate discount

    public double calculateFinalPrice (double subTotal){
        double discount = calculateDiscount(subTotal);

        if (discount > 0){
            double total = subTotal - discount;
            return total;
        } return subTotal;
    }//method final price
}//class
