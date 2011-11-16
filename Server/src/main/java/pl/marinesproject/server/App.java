package pl.marinesproject.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;
import org.apache.felix.main.AutoProcessor;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.Constants;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;
import pl.marinesproject.server.prv.BuildData;
/**
 * Hello world!
 *
 */
public class App {
    public final Framework framework;
    public App() throws Exception {
        // (1) Check for command line arguments and verify usage.
        String bundleDir = "bundles/bin";
        String cacheDir = "cache";
        boolean expectBundleDir = false;

        // (3) Read configuration properties.
        Map<String, String> configProps = new HashMap<String, String>();
        configProps.put("felix.auto.deploy.action", "install,start");
        configProps.put("felix.log.level", "1");
        configProps.put("org.osgi.framework.storage.clean", "org.osgi.framework.storage.clean");
        configProps.put("felix.shutdown.hook", "true");
        configProps.put("org.apache.felix.http.jettyEnabled", "true");
        configProps.put("org.osgi.service.http.port", "8080");
        configProps.put("obr.repository.url", "http://felix.apache.org/obr/releases.xml");

        // (5) Use the specified auto-deploy directory over default.
        if (bundleDir != null) {
            configProps.put(AutoProcessor.AUTO_DEPLOY_DIR_PROPERY, bundleDir);
        }

        // (6) Use the specified bundle cache directory over default.
        if (cacheDir != null) {
            configProps.put(Constants.FRAMEWORK_STORAGE, cacheDir);
        }

        // (7) Add a shutdown hook to clean stop the framework.
        String enableHook = configProps.get("felix.shutdown.hook");
        if ((enableHook == null) || !enableHook.equalsIgnoreCase("false")) {
            Runtime.getRuntime().addShutdownHook(new Thread("Felix Shutdown Hook") {
                @Override
                public void run() {
                    try {
                        if (framework != null) {
                            framework.stop();
                            framework.waitForStop(0);
                        }
                    } catch (Exception ex) {
                        System.err.println("Error stopping framework: " + ex);
                    }
                }
            });
        }

            // (8) Create an instance and initialize the framework.
            FrameworkFactory factory = getFrameworkFactory();
            framework = factory.newFramework(configProps);
            framework.init();
            // (9) Use the system bundle context to process the auto-deploy
            // and auto-install/auto-start properties.
            AutoProcessor.process(configProps, framework.getBundleContext());
            // (10) Start the framework.
            framework.start();
    }

    private static FrameworkFactory getFrameworkFactory() throws Exception
    {
        URL url = App.class.getClassLoader().getResource(
            "META-INF/services/org.osgi.framework.launch.FrameworkFactory");
        if (url != null)
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            try
            {
                for (String s = br.readLine(); s != null; s = br.readLine())
                {
                    s = s.trim();
                    // Try to load first non-empty, non-commented line.
                    if ((s.length() > 0) && (s.charAt(0) != '#'))
                    {
                        return (FrameworkFactory) Class.forName(s).newInstance();
                    }
                }
            }
            finally
            {
                if (br != null) br.close();
            }
        }

        throw new Exception("Could not find framework factory.");
    }

    public static void main(String[] args) {
        System.out.println("");
        System.out.println("  +----------------------------+");
        System.out.println("  | The Marines Project Server |");
        System.out.println("  +----------------------------+");
        System.out.println("  |  +Version: " + BuildData.version + StringUtils.repeat(" ", 16 - BuildData.version.length()) + "|");
        System.out.println("  |  +By JBLew                 |");
        System.out.println("  |     (http://www.jblew.pl/) |");
        System.out.println("  +----------------------------+");
        //System.out.println("   BUILD NUM: "+BuildData.buildNumber);
        System.out.println("");
        System.out.println("  Based on The MarinesMUD5. The Marines Project"
                + " is continuation of The MarinesMUD,"
                + " which is no longer developed.");
        System.out.println("");
        System.out.println("  Uses:");
        System.out.println("   +Apache Felix   (http://felix.apache.org/)");
        System.out.println("   +Maven2         (http://maven.apache.org/)");
        System.out.println("   +Apache Commons (http://commons.apache.org/)");
        System.out.println("");
        try {
            new App();
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
    }
}
