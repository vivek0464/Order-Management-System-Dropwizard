package org.example;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.example.db.dao.OrderDAO;
import org.example.db.entity.Order;
import org.example.resources.OrderResource;

public class HelloFiveApplication extends Application<HelloFiveConfiguration> {

    /**
     * Hibernate bundle.
     */
    private final HibernateBundle<HelloFiveConfiguration> hibernateBundle
            = new HibernateBundle<HelloFiveConfiguration>(
            Order.class
    ) {

        @Override
        public DataSourceFactory getDataSourceFactory(
                HelloFiveConfiguration configuration
        ) {
            return configuration.getDataSourceFactory();
        }

    };

    public static void main(final String[] args) throws Exception {
        System.out.println("hello");
        new HelloFiveApplication().run(args);
    }

    @Override
    public String getName() {
        return "HelloFive";
    }

    @Override
    public void initialize(
            final Bootstrap<HelloFiveConfiguration> bootstrap) {
        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public void run(final HelloFiveConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
        final OrderDAO orderDAO
                = new OrderDAO(hibernateBundle.getSessionFactory());

        environment.jersey().register(new OrderResource(orderDAO));
    }

}
