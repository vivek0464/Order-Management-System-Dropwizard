package org.example.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.db.entity.Order;


import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

import static java.sql.Types.NULL;

public class OrderRequest {
    @JsonProperty("uid")
    @Nullable
    private Integer uid;

    @JsonProperty("pid")
    @Nullable
    private Integer pid;

    @JsonProperty("quantity")
    @Nullable
    private Integer quantity;

    public int getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderRequest that = (OrderRequest) o;
        return uid == that.uid && pid == that.pid && quantity == that.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, pid, quantity);
    }

    public Optional<Order> build(){
        if(uid == NULL || pid == NULL || quantity == NULL){
            return Optional.empty();
        }
        Order order = new Order();
        order.setUid(uid);
        order.setPid(pid);
        order.setQuantity(quantity);

        return Optional.ofNullable(order);

    }

}
