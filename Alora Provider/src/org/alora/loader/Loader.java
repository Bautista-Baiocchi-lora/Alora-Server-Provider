package org.alora.loader;

import org.alora.overlays.BasicInfoDebugger;
import org.alora.overlays.GameObjectInfo;
import org.alora.overlays.NPCInfoOverlay;
import org.alora.overlays.PlayerInfoOverlay;
import org.bot.Engine;
import org.bot.component.screen.ScreenOverlay;
import org.bot.provider.loader.ServerLoader;
import org.bot.provider.manifest.HookType;
import org.bot.provider.manifest.Revision;
import org.bot.provider.manifest.ServerManifest;
import org.bot.util.injection.Injector;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;
import org.objenesis.instantiator.ObjectInstantiator;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ethan on 7/6/2017.
 */
@ServerManifest(author = "Ethan", serverName = "Alora", info = "Testing Applet", type = Applet.class, version = 0.1D, revision = Revision.OSRS, hookType = HookType.XML)
public class Loader extends ServerLoader<Applet> {
    private JPanel panel;

    public Loader() throws IOException {
        super("http://brookmanbucks.com/landing/FuckAlora.jar", "https://pastebin.com/raw/bNgt0f4Y", "Alora");
    }

    @Override
    public List<Injector> getInjectables() {
        List<Injector> injectors = new ArrayList<>();
        return injectors;
    }

    @Override
    public List<ScreenOverlay> getOverlays() {
        List<ScreenOverlay> overlays = super.getOverlays();
        overlays.add(new PlayerInfoOverlay());
        overlays.add(new GameObjectInfo());
        overlays.add(new BasicInfoDebugger());
        overlays.add(new NPCInfoOverlay());
        return overlays;
    }

    @Override
    protected Applet loadComponent() throws IllegalArgumentException, IllegalAccessException {
        panel = new JPanel();
        try {
            panel.setLayout(new BorderLayout());
            panel.setPreferredSize(new Dimension(765, 503));
            final AloraApplet aloraApplet = new AloraApplet();
            Class<?> clientLoader = Engine.getReflectionEngine().getClass("ClientLoader").getRespresentedClass();
            Objenesis objenesis = new ObjenesisStd();
            ObjectInstantiator instantiator = objenesis.getInstantiatorOf(clientLoader);
            Object clientLoaderInstance = instantiator.newInstance();
            Engine.getReflectionEngine().setFieldValue("ClientLoader", "addWindowListener", clientLoaderInstance);
            populateProperties(aloraApplet);
            Engine.getReflectionEngine().setFieldValue("ClientLoader", "BLACK", aloraApplet.properties, clientLoaderInstance);
            Engine.getReflectionEngine().setFieldValue("ClientLoader", "add", new JFrame(), clientLoaderInstance);
            Class<?> threadClass = Engine.getReflectionEngine().getClass("CH").getRespresentedClass();
            Object aloraThread = threadClass.getConstructor(Applet.class, int.class, String.class, int.class).newInstance(aloraApplet, 32, "runescape", 26);
            Engine.getReflectionEngine().setFieldValue("CB", "D", aloraThread);
            Engine.getReflectionEngine().setFieldValue("GB", "L", aloraThread);
            Object clientInstance = Engine.getReflectionEngine().getClass("MS").getNewInstance();
            Engine.getReflectionEngine().setFieldValue("RD", "M", null);
            Applet clientApplet = (Applet) clientInstance;
            invokeSettings();
            clientApplet.init();
            return aloraApplet;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void invokeSettings() throws Exception {
        Class<?> settings = Engine.getReflectionEngine().getClass("Settings").getRespresentedClass();
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
