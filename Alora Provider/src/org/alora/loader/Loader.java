package org.alora.loader;

import org.alora.overlays.*;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;
import org.objenesis.instantiator.ObjectInstantiator;
import org.ubot.bot.component.screen.ScreenOverlay;
import org.ubot.client.provider.loader.ServerLoader;
import org.ubot.client.provider.manifest.ServerManifest;
import org.ubot.util.Condition;
import org.ubot.util.injection.Injector;
import org.ubot.util.reflection.ReflectionEngine;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Ethan on 3/2/2018.
 */
@ServerManifest(author = "Ethan", serverName = "Alora", info = "A server provider for the server Alora.", version = 0.1D)
public class Loader extends ServerLoader {
    private static Applet applet;
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
        overlays.add(new BasicInfoDebugger());
        overlays.add(new GameObjectInfo());
        overlays.add(new InventoryOverlay());
        overlays.add(new NPCInfoOverlay());
        overlays.add(new PlayerInfoOverlay());
        overlays.add(new SettingsDebugger());
        return overlays;
    }

    @Override
    protected List<Injector> getInjectables() {
        return null;
    }

    @Override
    protected Applet loadApplet(ReflectionEngine reflectionEngine) throws IllegalAccessException {
        Loader.reflectionEngine = reflectionEngine;
        SwingUtilities.invokeLater(() -> {
            try {
                Applet applet;
                JFrame fakeFrame = new JFrame();
                fakeFrame.setPreferredSize(new Dimension(765, 503));
                try {
                    final AloraApplet aloraApplet = new AloraApplet();
                    Class<?> clientLoader = reflectionEngine.getClass("ClientLoader").getRespresentedClass();
                    Objenesis objenesis = new ObjenesisStd();
                    ObjectInstantiator instantiator = objenesis.getInstantiatorOf(clientLoader);
                    Object clientLoaderInstance = instantiator.newInstance();
                    reflectionEngine.setFieldValue("ClientLoader", "addWindowListener", clientLoaderInstance);
                    populateProperties(aloraApplet);
                    reflectionEngine.setFieldValue("ClientLoader", "BLACK", aloraApplet.properties, clientLoaderInstance);
                    reflectionEngine.setFieldValue("ClientLoader", "add", fakeFrame, clientLoaderInstance);
                    Class<?> threadClass = reflectionEngine.getClass("CH").getRespresentedClass();
                    Object aloraThread = threadClass.getConstructor(Applet.class, int.class, String.class, int.class).newInstance(aloraApplet, 32, "runescape", 26);
                    reflectionEngine.setFieldValue("CB", "D", aloraThread);
                    reflectionEngine.setFieldValue("GB", "L", aloraThread);
                    Object clientInstance = reflectionEngine.getClass("MS").getRespresentedClass().newInstance();
                    Applet clientApplet = (Applet) clientInstance;
                    invokeSettings(reflectionEngine);
                    clientApplet.init();
                    clientApplet.start();
                    applet = aloraApplet;
                    clientApplet.setPreferredSize(new Dimension(765, 503));
                    applet.setPreferredSize(new Dimension(765, 503));
                    Loader.applet = applet;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                System.out.println("Substance Graphite failed to initialize");
            }
        });
        while (applet == null) {
            Condition.sleep(100);
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
