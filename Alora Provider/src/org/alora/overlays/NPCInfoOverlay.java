package org.alora.overlays;

import org.alora.api.interactive.Npcs;
import org.alora.api.wrappers.NPC;
import org.bot.Engine;
import org.bot.component.screen.ScreenOverlay;

import java.awt.*;

/**
 * Created by Ethan on 2/27/2018.
 */
public class NPCInfoOverlay extends ScreenOverlay<NPC> {
    private boolean b;

    @Override
    public NPC[] elements() {
        return Npcs.getAll();
    }

    @Override
    public boolean activate() {
        return Engine.getServerProvider().isDebugNPCs();
    }

    @Override
    public void render(Graphics2D graphics) {
        if (!b) {
            for (NPC p : refresh()) {
                if (p != null && p.isValid()) {
                    if (p.getName().equals("Ali Morrisane")) {
                        p.interact(2);
                    }
                }
            }
            b = true;
        }
      /*  final FontMetrics metrics = graphics.getFontMetrics();

        for (NPC p : refresh()) {
            if (p != null && p.isValid()) {

                Point point = p.getPointOnScreen();

                graphics.setColor(Color.BLUE);
                graphics.fillRect(point.x, point.y, 5, 5);
                graphics.setColor(Color.black);
                String name = "[" + p.getName() + "]";
                graphics.drawString(name, point.x - (metrics.stringWidth(name) / 2), point.y - 5);
                p.getLocation().draw(graphics);

            }

        }
*/
    }
}