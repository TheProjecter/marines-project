/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.marinesproject.server.webserver.templates;

import com.google.common.io.Resources;
import java.io.IOException;
import java.nio.charset.Charset;
import net.sf.jtpl.Template;

/**
 *
 * @author jblew
 */
public class Templates {
    private Templates() {
    }

    public static Template get(String name) {
        try {
            Template t = new Template(Resources.toString(Resources.getResource(Templates.class, name + ".html"), Charset.forName("UTF-8")));
            t.assign("pageTitle", "MarinesMUD");
            t.assign("scripts", "");
            t.assign("pageContent", "<p>Sorry, page content is empty.</p>");
            return t;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
