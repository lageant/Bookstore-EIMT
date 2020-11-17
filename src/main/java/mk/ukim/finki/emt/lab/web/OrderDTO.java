package mk.ukim.finki.emt.lab.web;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class OrderDTO {
    private Long product_id;
    private String username;
    @NotNull
    @Min(1)
    private int quantity;

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
