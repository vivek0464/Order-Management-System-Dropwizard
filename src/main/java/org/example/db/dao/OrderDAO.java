package org.example.db.dao;

import io.dropwizard.hibernate.AbstractDAO;
import org.example.db.entity.Order;
import org.example.request.OrderRequest;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

import static java.sql.Types.NULL;

public class OrderDAO extends AbstractDAO<Order> {

    public OrderDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    /**
     * Method returns all orders stored in the database.
     *
     * @return list of all orders stored in the database
     */
    public List<Order> findAll() {
        return list(namedQuery("org.example.db.entity.Order.findAll"));
    }

    /**
     * Method looks for an order by its id.
     *
     * @param oid the id of an employee we are looking for.
     * @return Optional containing the found employee or an empty Optional
     * otherwise.
     */
    public Optional<Order> findById(int oid) {
        return Optional.ofNullable(get(oid));
    }

    public Order create(Order order){
        return persist(order);
    }
    public Optional<Order> updateOrder(Order oldOrder, OrderRequest orderRequest){
        if(orderRequest.getUid() != NULL){
            oldOrder.setUid(orderRequest.getUid());
        }
        if(orderRequest.getPid() != NULL){
            oldOrder.setPid(orderRequest.getPid());
        }
        if(orderRequest.getQuantity() != NULL){
            oldOrder.setQuantity(orderRequest.getQuantity());
        }
        currentSession().update(oldOrder);
        return Optional.ofNullable(oldOrder);
    }

    public void deleteOrder(Order order){
        currentSession().delete(order);
    }
}
