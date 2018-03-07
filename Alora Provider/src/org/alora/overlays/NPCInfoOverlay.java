package org.alora.overlays;

import org.alora.api.data.Game;
import org.alora.api.interactive.Npcs;
import org.alora.api.wrappers.NPC;
import org.ubot.bot.component.screen.ScreenOverlay;

import java.awt.*;

/**
 * Created by Ethan on 2/27/2018.
 */
public class NPCInfoOverlay extends ScreenOverlay<NPC> {
    private boolean b;

    public NPCInfoOverlay() {
        super("NPCs");
    }

    @Override
    public NPC[] elements() {
        return Npcs.getAll();
    }

    @Override
    public void render(Graphics2D graphics) {
        if (!Game.isLoggedIn())
            return;
        if (!b) {
            for (NPC p : refresh()) {
                if (p != null && p.isValid()) {
                    if (p.getName().equals("Ali Morrisane")) {
                        p.interact("Trade");
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