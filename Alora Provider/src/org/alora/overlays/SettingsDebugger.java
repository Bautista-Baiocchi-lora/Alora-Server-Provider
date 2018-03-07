package org.alora.overlays;

/**
 * Created by Ethan on 3/2/2018.
 */


import org.alora.api.data.Game;
import org.alora.api.data.Settings;
import org.ubot.bot.component.screen.ScreenOverlay;
import org.ubot.client.ui.logger.Logger;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class SettingsDebugger extends ScreenOverlay<String> {

    private Map<Integer, Integer> settingCache = new LinkedHashMap<>();
    private boolean b = false;
    public SettingsDebugger() {
        super("Settings");
    }

    @Override
    public String[] elements() {
        return new String[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean activate() {
        return super.activate();
    }

    @Override
    public void render(Graphics2D graphics) {
        if (!Game.isLoggedIn())
            return;
        if (settingCache.isEmpty()) {
            for (int i = 0; i < Settings.getAll().length; i++) {
                settingCache.put(i, Settings.get(i));
            }
            System.out.println("Settings Cache Set");
        }
        for (Map.Entry<Integer, Integer> entry : settingCache.entrySet()) {
            int cacheValue = entry.getValue();
            int curValue = Settings.get(entry.getKey());
            if (cacheValue != curValue) {
                Logger.log("Index: " + entry.getKey() + " - " + cacheValue + " -> " + curValue);
                settingCache.remove(entry.getKey());
                settingCache.put(entry.getKey(), curValue);
            }
        }
    }
}