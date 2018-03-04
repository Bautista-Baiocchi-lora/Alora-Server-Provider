package org.alora.api.interactive;

import org.alora.api.wrappers.Item;
import org.alora.api.wrappers.WidgetChild;
import org.ubot.util.Filter;
import org.ubot.util.Utilities;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Ethan on 2/28/2018.
 */
public class Inventory {

    private static final int WIDGET_INVENTORY_SLOTS = 0;
    private static int WIDGET_INVENTORY_INDEX = 149;

    public static Item[] getAllItems(Filter<Item> filter) {
        java.util.List<Item> list = new ArrayList<>();
        if (Widgets.getOpenInterfaces().contains(300)) {
            WIDGET_INVENTORY_INDEX = 301;
        } else if (Widgets.getOpenInterfaces().contains(15)) {
            WIDGET_INVENTORY_INDEX = 674;
        } else {
            WIDGET_INVENTORY_INDEX = 149;
        }
        final WidgetChild child = Widgets.get(WIDGET_INVENTORY_INDEX, WIDGET_INVENTORY_SLOTS);

        final int[] contentIds = child.getSlotContentIds();
        final int[] stackSizes = child.getStackSizes();
        if (contentIds == null || stackSizes == null)
            return list.toArray(new Item[list.size()]);
        for (int itemIndex = 0; itemIndex < contentIds.length; itemIndex++) {
            Item item = new Item(contentIds[itemIndex] - 1, stackSizes[itemIndex], itemIndex,
                    getSlotArea(itemIndex), child.getHash());
            if (item.isValid() && (filter == null || filter.accept(item))) {
                list.add(item);
            }
        }
        return list.toArray(new Item[list.size()]);
    }

    public static Item[] getAllItems() {
        return getAllItems(item -> true);
    }

    public static Item getItem(Filter<Item> filter) {
        Item[] items = getAllItems(filter);

        if (items == null || items.length == 0)
            return nil();

        return items[0];
    }

    public static Item getItem(final String... names) {
        return getItem(item -> item.isValid() && item.getName() != null && Utilities.inArray(item.getName(), names));
    }

    public static int getUsedSpace() {
        return getAllItems().length;
    }

    public static int getFreeSpace() {
        return 28 - getAllItems().length;
    }

    public static boolean isFull() {
        return getUsedSpace() == 28;
    }

    public static boolean isEmpty() {
        return getUsedSpace() == 0;
    }

    public static int getCount(boolean countStackSize, Filter<Item> filter) {
        int count = 0;
        for (Item item : getAllItems(filter)) {
            count = count + (countStackSize ? item.getStackSize() : 1);
        }
        return count;
    }

    public static int getCount(boolean countStackSize, final String... names) {
        if (names == null)
            return 0;
        return getCount(countStackSize, item -> item.isValid() && item.getName() != null && Utilities.inArray(item.getName(), names));
    }

    public static int getCount(boolean countStackSize) {
        int count = 0;
        for (Item i : getAllItems()) {
            if (i.isValid()) {
                count = count + (countStackSize ? i.getStackSize() : 1);
            }
        }
        return count;
    }

    public static int getCount(boolean countStackSize, final int... ids) {
        if (ids == null)
            return 0;
        return getCount(countStackSize, item -> item.isValid() && Utilities.inArray(item.getId(), ids));
    }

    public static int getCount(final int... ids) {
        return getCount(false, ids);
    }

    public static int getCount(final String... names) {
        return getCount(false, names);
    }

    public static int getCount(Filter<Item> filter) {
        return getCount(false, filter);
    }

    public static boolean contains(Filter<Item> filter) {
        return getItem(filter).isValid();
    }

    public static boolean contains(int... ids) {
        return getItem(ids).isValid();
    }

    public static boolean contains(String... names) {
        return getItem(names).isValid();
    }

    public static boolean containsAll(final int... ids) {
        if (ids == null)
            return false;
        for (int id : ids) {
            if (!contains(id))
                return false;
        }
        return true;
    }

    public static boolean containsAll(final String... names) {
        if (names == null)
            return false;

        for (String name : names) {
            if (!contains(name))
                return false;
        }

        return true;
    }

    public static Item getItemAt(final int index) {
        return getItem(item -> item.getIndex() == index);
    }

    public static Item getItem(final int... ids) {
        return getItem(item -> item.isValid() && Utilities.inArray(item.getId(), ids));
    }

    public static Rectangle getSlotArea(int slot) {
        int x = 563 + ((slot % 4) * 42), y = 213 + ((int) Math.floor((float) slot / (float) 4) * 36);
        return new Rectangle(x, y, 31, 31);
    }

    private static Item nil() {
        return new Item(-1, -1, -1, null, -1);
    }

}