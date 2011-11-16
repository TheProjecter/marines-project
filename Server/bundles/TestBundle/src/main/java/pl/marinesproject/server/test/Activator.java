/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.marinesproject.server.test;

import java.util.Hashtable;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 *
 * @author jblew
 */
public class Activator implements BundleActivator {
    @Override
    public void start(BundleContext context) throws Exception {
        String userName = context.getProperty("user.name");
        System.out.println("Maven Hello Service: Started OSGi bundle");
        System.out.println("User Name: " + userName);

        context.registerService("pl.marinesproject.server.test.Hello", new Hello() {
            @Override
            public void hello() {
                System.out.println("Oh, hi!");
            }
        }, new Hashtable());
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("Maven Hello Service: Stopped OSGi bundle");
    }
}
