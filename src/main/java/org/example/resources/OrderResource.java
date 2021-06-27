package org.example.resources;

import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.IntParam;
import org.example.api.KafkaProducerService;
import org.example.db.dao.OrderDAO;
import org.example.db.entity.Order;
import org.example.request.OrderRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {
    /**
     * The DAO object to manipulate employees.
     */
    private OrderDAO orderDAO;

    /**
     * Constructor.
     *
     * @param orderDAO object to manipulate orders.
     */
    public OrderResource(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }


    @GET
    @UnitOfWork
    public Response findAll(){
        List<Order> orders = orderDAO.findAll();
        return Response.accepted(orders)
                .status(200).build();
    }


    @GET
    @Path("/{oid}")
    @UnitOfWork
    public Optional<Order> findById(@PathParam("oid") IntParam oid) {
        return orderDAO.findById(oid.get());
    }

    /**
     * This is used to create a new Order object
     * @param orderRequest This is the order object we need to add
     * @return Response with appropriate status*/
    @POST
    @UnitOfWork
    public Response createOrder(OrderRequest orderRequest) {
        Optional<Order> order = orderRequest.build();
        if(!order.isPresent())
            return Response.accepted("Required field Not present")
                    .status(422).build();

        Order orderCreated = orderDAO.create(order.get());

        //**
        KafkaProducerService kafkaProducerService = new KafkaProducerService();
        String status = kafkaProducerService.sendMessageToTopic("Orders", orderCreated.toString());
        //**

        return Response.ok(orderCreated)
                .status(200).build();
    }

    @Path("/{uid}")
    @PUT
    @UnitOfWork
    public Response updateOrder(@PathParam("uid") int uid, OrderRequest orderRequest){
        Optional<Order> oldOrder = orderDAO.findById(uid);
        if(!oldOrder.isPresent()){
            return Response.notModified("No such Order Exist")
                    .status(404).build();
        }
        oldOrder = orderDAO.updateOrder(oldOrder.get(), orderRequest);
        if(oldOrder.isPresent()){
            return Response.accepted(oldOrder.get())
                    .status(200).build();
        }else {
            return Response.notModified("Data Provided is not correct")
                    .status(422).build();
        }

    }

    @Path("/{uid}")
    @DELETE
    @UnitOfWork
    public Response deleteOrder(@PathParam("uid") int uid){
        Optional<Order> order = orderDAO.findById(uid);
        System.out.println("don't know");
        if(!order.isPresent())
            return Response.notModified("No such Order Exist")
                    .status(404).build();
        System.out.println("is present");
        orderDAO.deleteOrder(order.get());
        return Response.accepted(order.get()).status(200).build();
    }

}
