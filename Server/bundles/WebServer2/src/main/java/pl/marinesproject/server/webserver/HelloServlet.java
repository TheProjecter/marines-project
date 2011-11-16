/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.marinesproject.server.webserver;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jtpl.Template;
import pl.marinesproject.server.webserver.templates.Templates;

/**
 *
 * @author jblew
 */
class HelloServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter out = res.getWriter();
        try {
            out.println(generatePage());
        } catch (Exception ex) {
            ex.printStackTrace(out);
        }
        out.close();
    }

    private String generatePage() throws Exception {
        Template tpl = Templates.get("admin");
        tpl.assign("pageTitle", "Welcome to MARINESMUD");
        tpl.assign("scripts", "");

        tpl.assign("pageContent", "<h2>Welcome to MARINESMUD!</h2><p>This sogtware is still in development phase. "
                + "If you are interested in it please visit <a href=\"http://code.google.com/p/marines-mud/\">our page on Google Code</a>. If you want to contribute, write to jblew[at]blew.pl.</p>"
                + "<ul>"
                + "   <li><a href=\"/play\">Play via Telnet</a></li>"
                + "   <li><a href=\"/admin\">Go to administration panel</a></li>"
                + "</ul>");
        tpl.parse("main");
        return (tpl.out());
    }
}
