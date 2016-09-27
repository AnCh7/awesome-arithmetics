package org.anch.arithmetics.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class App {

    private static final Logger logger = LogManager.getLogger(App.class.getName());

    public static void main(String[] args) {

        ServletHolder servlet = new ServletHolder(new ServletContainer(config()));
        String contextPath = "/*";
        Server server = new Server(5050);
        ServletContextHandler context = new ServletContextHandler(server, contextPath);
        context.addServlet(servlet, contextPath);

        try {
            logger.info("Starting server");
            server.start();
            server.join();
            logger.info("Server started");
        } catch (Exception e) {
            logger.error("Cannot start server", e);
        } finally {
            logger.info("Destroying server");
            server.destroy();
        }
    }

    private static ResourceConfig config() {
        return new ArithmeticsApplication();
    }
}
