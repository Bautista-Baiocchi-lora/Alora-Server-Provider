package org.alora.overlays;

import org.alora.api.interactive.Inventory;
import org.alora.api.wrappers.Item;
import org.ubot.bot.component.screen.ScreenOverlay;

import java.awt.*;

/**
 * Created by Ethan on 3/1/2018.
 */
public class InventoryOverlay extends ScreenOverlay<Item> {

    public InventoryOverlay() {
        super("Inventory");
    }

    @Override
    public Item[] elements() {
        return Inventory.getAllItems(i -> i.isValid() && i != null);
    }


    @Override
    public void render(Graphics2D graphics) {
        final FontMetrics metrics = graphics.getFontMetrics();

        for (Item i : refresh()) {
            if (i.isValid()) {
                graphics.setColor(Color.BLUE.darker());
                Point point = i.getCentralPoint();
                String id = i.getId() + "";
                graphics.drawString(id,
                        point.x - (metrics.stringWidth(String.valueOf(i.getId())) / 2), point.y + 5);
                graphics.drawRect(i.getArea().x, i.getArea().y, i.getArea().width, i.getArea().height);
            }
        }
    }

}