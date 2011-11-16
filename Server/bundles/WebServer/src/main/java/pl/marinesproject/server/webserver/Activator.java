/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.marinesproject.server.webserver;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import pl.marinesproject.server.webserver.servlets.HelloServlet;

/**
 *
 * @author jblew
 */
public class Activator implements BundleActivator {
    //private final WebServerImpl webServer = new WebServerImpl();
    @Override
    public void start(BundleContext bc) throws Exception {
        System.out.println("Starting WebServer...");
        int port = 8080;
        String resourcesBase = "./resources/";
        if (bc.getProperty("pl.marinesproject.server.webserver.port") != null) {
            port = Integer.parseInt(bc.getProperty("pl.marinesproject.server.webserver.port"));
        }
        if (bc.getProperty("pl.marinesproject.server.webserver.resourcesBase") != null) {
            resourcesBase = bc.getProperty("pl.marinesproject.server.webserver.resourcesBase");
        }

        ServiceReference sRef = bc.getServiceReference(HttpService.class.getName());
        if (sRef != null) {
            HttpService service = (HttpService) bc.getService(sRef);
            service.registerServlet("/hello", new HelloServlet(), null, null);
            System.out.println("Started WebServer.");
        }
 else System.out.println("HTTP SERVICE IS NULL");

        //webServer.bind(new InetSocketAddress("0.0.0.0", port), resourcesBase);
        //webServer.start();

        //Dictionary<String, String> props = new Hashtable<String, String>();
        //bc.registerService(WebServer.class.getName(), webServer, props);
        //System.out.println("Started WebServer.");
    }

    @Override
    public void stop(BundleContext bc) throws Exception {
        ServiceReference sRef = bc.getServiceReference(HttpService.class.getName());
        if (sRef != null) {
            HttpService service = (HttpService) bc.getService(sRef);
            service.unregister("/hello");
        }
        System.out.println("Destroyed WebServer.");
    }
}
