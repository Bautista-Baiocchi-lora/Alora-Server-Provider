package org.alora.overlays;

/**
 * Created by Ethan on 3/2/2018.
 */


import org.ubot.component.screen.ScreenOverlay;

import java.awt.*;

public class SettingsDebugger extends ScreenOverlay<String> {

    private int[] cache = null;

    @Override
    public String[] elements() {
        return new String[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean activate() {
        return false;
    }

    @Override
    public void render(Graphics2D graphics) {

    }
}