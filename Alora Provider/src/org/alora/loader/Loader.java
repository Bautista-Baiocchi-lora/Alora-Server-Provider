package org.alora.loader;

import org.alora.overlays.*;
import org.ubot.bot.component.screen.ScreenOverlay;
import org.ubot.client.provider.loader.ServerLoader;
import org.ubot.client.provider.manifest.ServerManifest;
import org.ubot.util.Condition;
import org.ubot.util.injection.Injector;
import org.ubot.util.reflection.ReflectionEngine;

import javax.swing.*;
import java.applet.Applet;
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
                JFrame fakeFrame = new JFrame();
                    Class<?> clientLoader = reflectionEngine.getClass("ClientLoader").getRespresentedClass();
                reflectionEngine.setFieldValue("ClientLoader", "add", fakeFrame);
                Object ist = clientLoader.getConstructor(String.class, boolean.class).newInstance("1", true);
                reflectionEngine.setFieldValue("ClientLoader", "addWindowListener", ist);
                Object clientLoaderInstance = reflectionEngine.getFieldValue("ClientLoader", "addWindowListener");
                Loader.applet = (Applet) clientLoaderInstance;
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        while (applet == null) {
            Condition.sleep(100);
        }
        return applet;
    }

}
