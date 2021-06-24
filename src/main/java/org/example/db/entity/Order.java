package org.example.db.entity;

import javax.persistence.*;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "orders")
@NamedQueries({
        @NamedQuery(name = "org.example.db.entity.Order.findAll",
                query = "select e from Order e"),
})
public class Order {

    /**
     * Entity's unique identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int oid;

    /**
     * User Id
     */
    @Column(name = "uid")
    private int uid;

    /**
     * Product Id
     */
    @Column(name = "pid")
    private int pid;

    /**
     * Quantity
     */
    @Column(name = "quantity")
    private int quantity;

    public Order() {
    }

    public Order(int oid, int uid, int pid, int quantity) {
        this.oid = oid;
        this.uid = uid;
        this.pid = pid;
        this.quantity = quantity;
    }

    public int getOid() {
        return oid;
    }

    public int getUid() {
        return uid;
    }

    public int getPid() {
        return pid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return oid == order.oid && uid == order.uid && pid == order.pid && quantity == order.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(oid, uid, pid, quantity);
    }

}
