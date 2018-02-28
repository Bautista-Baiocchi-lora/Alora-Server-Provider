package org.alora.overlays;

import org.alora.api.interactive.Players;
import org.alora.api.wrappers.Player;
import org.bot.Engine;
import org.bot.component.screen.ScreenOverlay;
import org.bot.ui.screens.clientframe.menu.logger.Logger;

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
        return Engine.getServerProvider().isDebugPlayers();
    }

    @Override
    public void render(Graphics2D graphics) {
        if (!b) {
            for (Player p : elements()) {
                if (p != null && p.isValid()) {
                    Logger.log(p.getName() + " : " + p.getLocation());
                }
            }
            Logger.log("ME: " + Players.getLocal().getName() + " : " + Players.getLocal().getLocation());
            b = true;
        }
    }
}