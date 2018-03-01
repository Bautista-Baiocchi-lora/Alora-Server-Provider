package org.alora.overlays;

import org.alora.api.interactive.Inventory;
import org.alora.api.wrappers.Item;
import org.bot.Engine;
import org.bot.component.screen.ScreenOverlay;
import org.bot.util.Filter;

import java.awt.*;

/**
 * Created by Ethan on 3/1/2018.
 */
public class InventoryOverlay extends ScreenOverlay<Item> {

    @Override
    public Item[] elements() {
        return Inventory.getAllItems(new Filter<Item>() {
            @Override
            public boolean accept(Item i) {
                return i.isValid() && i != null;
            }
        });
    }

    @Override
    public boolean activate() {
        return Engine.getServerProvider().isDebugInventory();
    }

    @Override
    public void render(Graphics2D graphics) {
        final FontMetrics metrics = graphics.getFontMetrics();

        for (Item i : refresh()) {
            if (i.isValid()) {
                graphics.setColor(Color.GREEN);
                Point point = i.getCentralPoint();
                String id = "" + i.getHash();
                graphics.drawString(id,
                        point.x - (metrics.stringWidth(String.valueOf(i.getId())) / 2), point.y + 5);
                graphics.drawRect(i.getArea().x, i.getArea().y, i.getArea().width, i.getArea().height);
            }
        }
    }

}