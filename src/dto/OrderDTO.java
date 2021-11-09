package dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDTO {
    private String orderId;
    private LocalDate orderDate;
    private String custId;
    private double total;
    private ArrayList<OrderDetailsDTO> orderDetail;

    public OrderDTO() {
    }

    public OrderDTO(String orderId, LocalDate orderDate, String custId, double total) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.custId = custId;
        this.total = total;
    }
    public OrderDTO(String orderId, LocalDate orderDate, String custId, double total,ArrayList<OrderDetailsDTO> orderDetail) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.custId = custId;
        this.total = total;
        this.orderDetail=orderDetail;
    }

    public OrderDTO(String orderId, LocalDate orderDate, String customerId, List<OrderDetailsDTO> orderDetail) {
    }


    public List<OrderDetailsDTO> getOrderDetail() {
        return orderDetail;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", orderDate=" + orderDate +
                ", custId='" + custId + '\'' +
                ", total=" + total +
                '}';
    }
}
