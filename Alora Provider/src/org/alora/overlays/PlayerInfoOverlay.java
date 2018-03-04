package org.alora.overlays;

import org.alora.api.interactive.Players;
import org.alora.api.wrappers.Player;
import org.ubot.component.screen.ScreenOverlay;

import java.awt.*;

/**
 * Created by Ethan on 2/27/2018.
 */
public class PlayerInfoOverlay extends ScreenOverlay<Player> {
    private boolean b;

    @Override
    public Player[] elements() {
        return Players.getAll();
    }

    @Override
    public boolean activate() {
        return false;
    }

    @Override
    public void render(Graphics2D graphics) {
        if (!b) {
            for (Player p : elements()) {
                if (p != null && p.isValid()) {

                }
            }

            b = true;
        }
    }
}