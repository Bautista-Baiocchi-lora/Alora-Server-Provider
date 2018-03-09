package org.alora.overlays;


import org.alora.api.data.Game;
import org.alora.api.data.Menu;
import org.alora.api.interactive.Players;
import org.alora.api.interactive.Widgets;
import org.ubot.bot.component.screen.ScreenOverlay;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ethan on 7/10/2017.
 */
public class BasicInfoDebugger extends ScreenOverlay<String> {
    private final List<String> debuggedList = new ArrayList<>();

    public BasicInfoDebugger() {
        super("Game");
    }

    @Override
    public String[] elements() {
        if (!Game.isLoggedIn())
            return new String[0];
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
        drawText("Logged In: " + Game.isLoggedIn() + " : " + Game.getGameState());
        drawText("test: " + Game.test1());
        drawText("test1: " + Game.test2());

        //192 - mage book
        return debuggedList.toArray(new String[debuggedList.size()]);
    }

    @Override
    public void render(Graphics2D graphics) {
        if (!Game.isLoggedIn())
            return;
        for (int i : Widgets.getOpenInterfaces()) {
            //System.out.println(i);
        }
        // System.out.println();
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