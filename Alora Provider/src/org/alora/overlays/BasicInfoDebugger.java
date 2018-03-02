package org.alora.overlays;


import org.alora.api.data.Game;
import org.alora.api.data.Menu;
import org.alora.api.interactive.Players;
import org.alora.api.wrappers.NPC;
import org.bot.Engine;
import org.bot.component.screen.ScreenOverlay;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ethan on 7/10/2017.
 */
public class BasicInfoDebugger extends ScreenOverlay<String> {
    private final List<String> debuggedList = new ArrayList<>();

    @Override
    public String[] elements() {
        debuggedList.clear();

        drawText("We Got Canvas going.");
        drawText("Int One: " + Menu.intValueOne());
        drawText("Int Two: " + Menu.intValueTwo());
        drawText("Int Three: " + Menu.intValueThree());
        drawText("Long One: " + Menu.longValue());
        drawText("String One: " + Menu.stringValueOne());
        drawText("String Two: " + Menu.stringValueTwo());
        drawText("BaseX: " + Game.getBaseX());
        drawText("BaseY: " + Game.getBaseY());
        drawText("Location: " + Players.getLocal().getLocation());
        drawText("Plane: " + Game.getPlane());
        drawText("Logged In: " + Game.isLoggedIn() + " : " + Game.getLoginStage());
        drawText("Cycle: " + Game.getClientCycle());
        if (Players.getLocal().getInteracting() != null) {
            if (Players.getLocal().getInteracting() instanceof NPC) {
                String name = ((NPC) Players.getLocal().getInteracting()).getName();
                drawText("Interacting:  " + name);
            }
        }

        return debuggedList.toArray(new String[debuggedList.size()]);
    }

    @Override
    public boolean activate() {
        return Engine.getServerProvider().isDebugGameInfo();
    }

    @Override
    public void render(Graphics2D graphics) {
        graphics.setColor(Color.orange);
        int yOff = 30;

        for (String str : elements()) {
            graphics.drawString(str, 15, yOff);
            yOff += 15;
        }
    }

    private void drawText(String debug) {
        debuggedList.add(debug);
    }

}