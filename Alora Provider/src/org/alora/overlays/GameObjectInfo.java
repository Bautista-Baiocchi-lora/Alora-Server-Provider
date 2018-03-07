package org.alora.overlays;


import org.alora.api.interactive.GameObjects;
import org.alora.api.wrappers.GameObject;
import org.ubot.bot.component.screen.ScreenOverlay;

import java.awt.*;

/**
 * Created by Ethan on 2/27/2018.
 */
public class GameObjectInfo extends ScreenOverlay<GameObject> {
    private boolean b = false;

    public GameObjectInfo() {
        super("Objects");
    }

    @Override
    public GameObject[] elements() {
        return GameObjects.getAll();
    }


    @Override
    public void render(Graphics2D graphics) {
        if (!b) {
            b = true;
        }
    }
}