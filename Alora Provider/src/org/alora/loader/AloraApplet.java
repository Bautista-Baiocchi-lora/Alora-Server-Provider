package org.alora.loader;

import java.applet.Applet;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

/**
 * Created by Ethan on 2/26/2018.
 */
public class AloraApplet extends Applet {
    private static final long serialVersionUID = -6988977112927509734L;
    public Properties properties = new Properties();

    @Override
    public final String getParameter(String string) {
        String ret = (String) properties.get(string);
        System.out.println("looking for: " + string + " || Found: " + ret);
        return ret;
    }

    @Override
    public final URL getDocumentBase() {
        return this.getCodeBase();
    }

    @Override
    public final URL getCodeBase() {
        try {
            return new URL("http://93.158.238.42");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
