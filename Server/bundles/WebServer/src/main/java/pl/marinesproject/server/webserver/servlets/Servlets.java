/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.marinesproject.server.webserver.servlets;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.Servlet;

/**
 *
 * @author jblew
 */
public class Servlets {
    public static final Map<String, Class<? extends Servlet>> servlets = new HashMap<String, Class<? extends Servlet>>();
    static {
        servlets.put("/play", PlayServlet.class);
        servlets.put("/admin", AdminServlet.class);
        servlets.put("/admin/*", AdminServlet.class);
        servlets.put("/auth", AuthServlet.class);
        servlets.put("/who", WhoServlet.class);
        servlets.put("/editor", EditorServlet.class);
        servlets.put("/test", TestServlet.class);
        servlets.put("/", HelloServlet.class);
    }

    private Servlets() {
    }
 }
