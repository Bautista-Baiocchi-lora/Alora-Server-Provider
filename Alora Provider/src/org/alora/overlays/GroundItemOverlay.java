package org.alora.overlays;

import org.alora.api.data.Game;
import org.alora.api.interactive.GroundItems;
import org.alora.api.wrappers.GroundItem;
import org.ubot.bot.component.screen.ScreenOverlay;

import java.awt.*;

/**
 * Created by Ethan on 2/27/2018.
 */
public class GroundItemOverlay extends ScreenOverlay<GroundItem> {

    public GroundItemOverlay() {
        super("GroundItems");
    }

    @Override
    public GroundItem[] elements() {
        return GroundItems.getAll();
    }

    @Override
    public void render(Graphics2D graphics) {
        if (!Game.isLoggedIn())
            return;
        for (GroundItem p : refresh()) {
            if (p != null && p.isValid()) {
                System.out.println(p.getId() + " : " + p.getName() + " : " + p.distanceTo());
            }
        }
    }
}