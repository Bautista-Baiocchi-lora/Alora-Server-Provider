package org.alora.loader;

import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;
import org.objenesis.instantiator.ObjectInstantiator;
import org.ubot.client.provider.loader.ServerLoader;
import org.ubot.client.provider.manifest.ServerManifest;
import org.ubot.component.screen.ScreenOverlay;
import org.ubot.util.injection.Injector;
import org.ubot.util.reflection.ReflectionEngine;

import javax.swing.*;
import java.applet.Applet;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Ethan on 3/2/2018.
 */
@ServerManifest(author = "Ethan", serverName = "Alora", info = "A server provider for the server Alora.", version = 0.1D)
public class Loader extends ServerLoader {
    private static ReflectionEngine reflectionEngine;

    public Loader() {
        super("http://brookmanbucks.com/landing/FuckAlora.jar", "https://pastebin.com/raw/bNgt0f4Y", "Alora");
    }

    public static ReflectionEngine getReflectionEngine() {
        return reflectionEngine;
    }

    @Override
    public List<ScreenOverlay> getOverlays() {
        List<ScreenOverlay> overlays = super.getOverlays();
        return overlays;
    }

    @Override
    protected List<Injector> getInjectables() {
        return null;
    }

    @Override
    protected Applet loadApplet(ReflectionEngine reflectionEngine) throws IllegalAccessException {
        Loader.reflectionEngine = reflectionEngine;
        Applet applet = null;
        try {
            final AloraApplet aloraApplet = new AloraApplet();
            Class<?> clientLoader = reflectionEngine.getClass("ClientLoader").getRespresentedClass();
            Objenesis objenesis = new ObjenesisStd();
            ObjectInstantiator instantiator = objenesis.getInstantiatorOf(clientLoader);
            Object clientLoaderInstance = instantiator.newInstance();
            reflectionEngine.setFieldValue("ClientLoader", "addWindowListener", clientLoaderInstance);
            populateProperties(aloraApplet);
            reflectionEngine.setFieldValue("ClientLoader", "BLACK", aloraApplet.properties, clientLoaderInstance);
            reflectionEngine.setFieldValue("ClientLoader", "add", new JFrame(), clientLoaderInstance);
            Class<?> threadClass = reflectionEngine.getClass("CH").getRespresentedClass();
            Object aloraThread = threadClass.getConstructor(Applet.class, int.class, String.class, int.class).newInstance(aloraApplet, 32, "runescape", 26);
            reflectionEngine.setFieldValue("CB", "D", aloraThread);
            reflectionEngine.setFieldValue("GB", "L", aloraThread);
            Object clientInstance = reflectionEngine.getClass("MS").getNewInstance();
            reflectionEngine.setFieldValue("RD", "M", null);
            Applet clientApplet = (Applet) clientInstance;
            invokeSettings(reflectionEngine);
            clientApplet.init();
            applet = aloraApplet;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return applet;
    }

    private void invokeSettings(ReflectionEngine reflectionEngine) throws Exception {
        Class<?> settings = reflectionEngine.getClass("Settings").getRespresentedClass();
        for (Method m : settings.getDeclaredMethods()) {
            if (m.getName().equals("I")) {
                if (m.getParameterCount() == 0) {
                    m.setAccessible(true);
                    m.invoke(null);
                }
            }
        }
    }

    private void populateProperties(AloraApplet applet) {
        applet.properties.put("worldid", "1");
        applet.properties.put("members", "1");
        applet.properties.put("modewhat", "0");
        applet.properties.put("modewhere", "0");
        applet.properties.put("safemode", "0");
        applet.properties.put("game", "0");
        applet.properties.put("js", "1");
        applet.properties.put("lang", "0");
        applet.properties.put("affid", "0");
        applet.properties.put("lowmem", "1");
        applet.properties.put("settings", "kKmok3kJqOeN6D3mDdihco3oPeYN2KFy6W5--vZUbNA");

    }
}
