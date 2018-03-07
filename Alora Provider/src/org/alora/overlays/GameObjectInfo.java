package org.alora.overlays;


import org.alora.api.data.Prayers;
import org.alora.api.interactive.GameObjects;
import org.alora.api.wrappers.GameObject;
import org.ubot.bot.component.screen.ScreenOverlay;
import org.ubot.util.Condition;

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
            System.out.println(Prayers.PIETY.isActive());
            System.out.println();
            Prayers.PIETY.activate();
            Condition.sleep(1000);
        }
    }
}