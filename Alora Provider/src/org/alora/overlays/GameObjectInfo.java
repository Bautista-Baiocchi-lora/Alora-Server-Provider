package org.alora.overlays;

import org.alora.api.interactive.GameObjects;
import org.alora.api.interactive.Inventory;
import org.alora.api.wrappers.GameObject;
import org.bot.Engine;
import org.bot.component.screen.ScreenOverlay;

import java.awt.*;

/**
 * Created by Ethan on 2/27/2018.
 */
public class GameObjectInfo extends ScreenOverlay<GameObject> {
    private boolean b = false;

    @Override
    public GameObject[] elements() {
        return GameObjects.getAll();
    }

    @Override
    public boolean activate() {
        if (!Engine.getServerProvider().isDebugObjects()) {
            b = false;
        } else {
            return true;
        }
        return false;
    }

    @Override
    public void render(Graphics2D graphics) {
        if (!b) {
            b = true;
            Inventory.getAllItems();

        }
    }
}