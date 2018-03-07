package org.alora.overlays;

import org.alora.api.data.Game;
import org.alora.api.interactive.Players;
import org.alora.api.wrappers.Player;
import org.ubot.bot.component.screen.ScreenOverlay;

import java.awt.*;

/**
 * Created by Ethan on 2/27/2018.
 */
public class PlayerInfoOverlay extends ScreenOverlay<Player> {
    private boolean b;

    public PlayerInfoOverlay() {
        super("Players");
    }

    @Override
    public Player[] elements() {
        return Players.getAll();
    }

    @Override
    public void render(Graphics2D graphics) {
        if (!Game.isLoggedIn())
            return;
        if (!b) {
            for (Player p : elements()) {
                if (p != null && p.isValid()) {

                }
            }

            b = true;
        }
    }
}