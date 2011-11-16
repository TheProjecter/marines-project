/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.marinesproject.server.webserver;

/**
 *
 * @author jblew
 *
public class WebServerImpl implements WebServer {
    private final Server server;
    private InetSocketAddress addr;

    public WebServerImpl() {
        server = new Server();
    }

    @Override
    public InetSocketAddress getAddress() {
        return addr;
    }

    public void bind(InetSocketAddress addr, String resourceBase) {
        this.addr = addr;
        SocketConnector connector = new SocketConnector();

        // Set some timeout options to make debugging easier.
        //connector.setMaxIdleTime(1000 * 60 * 60);
        //connector.setSoLingerTime(-1);
        connector.setPort(addr.getPort());
        connector.setHost(addr.getHostName());

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setResourceBase(resourceBase);

        ContextHandler resourceContext = new ContextHandler();
        resourceContext.setContextPath("/resources");
        resourceContext.setResourceBase(".");
        resourceContext.setClassLoader(Thread.currentThread().getContextClassLoader());
        resourceContext.setHandler(resource_handler);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.setContextPath("/");
        server.setHandler(context);
        for (String path : Servlets.servlets.keySet()) {
            context.addServlet(Servlets.servlets.get(path), path);
        }

        HandlerList handlers = new HandlerList();

        handlers.setHandlers(new Handler[]{resourceContext, context});
        server.setHandler(handlers);

        server.setConnectors(new Connector[]{connector});
        Logger.getLogger("WebServer").log(Level.INFO, "Binded webServer to port {0}", addr.getPort());
    }

    public void start() {
        try {
            server.start();
        } catch (Exception ex) {
            Logger.getLogger("WebServer").log(Level.SEVERE, null, ex);
        }
    }

    public void destroy() {
        try {
            server.stop();
        } catch (Exception ex) {
            Logger.getLogger("WebServer").log(Level.SEVERE, null, ex);
        }
    }
}
*/