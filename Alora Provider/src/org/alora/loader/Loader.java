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
import org.ubot.util.reflection.Modifiers;
import org.ubot.util.reflection.ReflectedMethod;
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
                JFrame fakeFrame = new JFrame(); //we gotta create a fake jFrame, because they check if a frame != null in client somewheere
                fakeFrame.setPreferredSize(new Dimension(765, 503));
                try {
                    final AloraApplet aloraApplet = new AloraApplet(); //this isn't really a applet, but a place we store parameters, and something to pass to CH
                    Class<?> clientLoader = reflectionEngine.getClass("ClientLoader").getRespresentedClass();
                    Objenesis objenesis = new ObjenesisStd();
                    ObjectInstantiator instantiator = objenesis.getInstantiatorOf(clientLoader);
                    Object clientLoaderInstance = instantiator.newInstance(); //creating the blank constructor instance
                    reflectionEngine.setFieldValue("ClientLoader", "addWindowListener", clientLoaderInstance); //this is the ClientLoader.class instance
                    populateProperties(aloraApplet); // populating the properties
                    reflectionEngine.setFieldValue("ClientLoader", "BLACK", aloraApplet.properties); //BLACK == properties
                    reflectionEngine.setFieldValue("ClientLoader", "add", fakeFrame, clientLoaderInstance); // add == the JFrame they check if null
                    Class<?> threadClass = reflectionEngine.getClass("CH").getRespresentedClass(); //CH.class, they applet thread
                    Object aloraThread = threadClass.getConstructor(Applet.class, int.class, String.class, int.class).newInstance(aloraApplet, 32, "runescape", 26); //creating a new instance using standard reflection
                    reflectionEngine.setFieldValue("CB", "D", aloraThread); //MS.I(localCH) - sets 2 fields to the CH instnace in ClientLoader, I set both here
                    reflectionEngine.setFieldValue("GB", "L", aloraThread);//and here
                    Object clientInstance = reflectionEngine.getClass("MS").getRespresentedClass().newInstance(); //MS = Client.class, create a new isntance of it
                    Applet clientApplet = (Applet) clientInstance; //cast that instacne to applet
                    invokeSettings(reflectionEngine);//Settings.I(), in Alora's client, we invoke it like they do.
                    clientApplet.init(); //Client.class applet, init start
                    clientApplet.start();
                    applet = aloraApplet;//setting the applet we return to the uBot
                    clientApplet.setPreferredSize(new Dimension(765, 503));
                    applet.setPreferredSize(new Dimension(765, 503));
                    Loader.applet = applet;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                fakeFrame.dispose();
                System.out.println("Showing:"+fakeFrame.isShowing());
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
    	ReflectedMethod settings = reflectionEngine.getClass("Settings").getMethod(new Modifiers.ModifierBuilder().name("I").parameterCount(0).build());
        settings.invoke(null);
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
