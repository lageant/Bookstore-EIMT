package mk.ukim.finki.emt.lab.web;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class OrderIdDTO {
    private Long order_id;
    @NotNull
    @Min(1)
    private int quantity;

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
