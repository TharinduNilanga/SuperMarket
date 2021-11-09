package view.tm;

import java.time.LocalDate;

public class CartTM {
        private String custId;
        private String itemId;
        private String description;
        private double unitPrice;
        private int discount;
        private int Qty;
        private double total;

    public CartTM() {
    }

    public CartTM(String custId, String itemId, String description, double unitPrice, int discount, int qty, double total) {
        this.custId = custId;
        this.itemId = itemId;
        this.description = description;
        this.unitPrice = unitPrice;
        this.discount = discount;
        this.Qty = qty;
        this.total = total;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "CartTM{" +
                "custId='" + custId + '\'' +
                ", itemId='" + itemId + '\'' +
                ", description='" + description + '\'' +
                ", unitPrice=" + unitPrice +
                ", discount=" + discount +
                ", Qty=" + Qty +
                ", total=" + total +
                '}';
    }
}
