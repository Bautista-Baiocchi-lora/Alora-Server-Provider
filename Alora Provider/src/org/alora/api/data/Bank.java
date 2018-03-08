package org.alora.api.data;

import org.alora.api.interactive.Widgets;
import org.alora.api.wrappers.Item;
import org.alora.api.wrappers.WidgetChild;
import org.ubot.util.Filter;
import org.ubot.util.Utilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ethan on 3/8/2018.
 */
public class Bank {
    private static final int BANK_INTERFACE = 674, BANK_ITEMS_PAD = 28;
    private static final String[] BANK_NAMES = {"Bank Booth"};

    public static Item[] getAllItems(Filter<Item> filter) {
        List<Item> list = new ArrayList<>();
        if (!Game.isLoggedIn() || !isOpen())
            return list.toArray(new Item[list.size()]);

        WidgetChild parent = Widgets.get(BANK_INTERFACE, BANK_ITEMS_PAD);
        if (parent.isVisible()) {
            WidgetChild[] children = parent.getChildren();
            if (children != null) {
                for (int BankI = 0; BankI < children.length; BankI++) {
                    WidgetChild widgetChild = children[BankI];
                    Item item = new Item(widgetChild.getItemID(),
                            widgetChild.getItemStackSize(), BankI,
                            null, widgetChild.getHash());
                    if (item.isValid()
                            && (filter == null || filter.accept(item))) {
                        list.add(item);
                    }
                }
            }
        }
        return list.toArray(new Item[list.size()]);
    }

    public static Item[] getAllItems() {
        return getAllItems(null);
    }

    public static boolean isOpen() {
        return Widgets.getOpenInterfaces().contains(674);
    }

    public static Item nil() {
        return new Item(-1, -1, -1, null, -1);
    }

    public static Item getItem(Filter<Item> filter) {
        Item[] allItems = getAllItems(filter);
        return allItems.length > 0 ? allItems[0] : nil();
    }

    public static Item getItem(final int... ids) {
        if (ids == null)
            return nil();
        return getItem(item -> item.isValid() && Utilities.inArray(item.getId(), ids));
    }

    public static Item getItem(final String... names) {
        if (names == null)
            return nil();
        return getItem(item -> item.isValid() && item.getName() != null
                && Utilities.inArray(item.getName(), names));
    }

    public static boolean contains(final Filter<Item> filter) {
        return getItem(filter).isValid();
    }

    public static boolean contains(final String... names) {
        return getItem(names).isValid();
    }

    public static boolean contains(final int... ids) {
        return getItem(ids).isValid();
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

    public static int getCount(Filter<Item> filter) {
        final Item item = getItem(filter);
        return item.isValid() ? item.getStackSize() : 0;
    }

    public static int getCount() {
        Item[] items = getAllItems(null);
        if (items == null)
            return 0;
        int count = 0;
        for (Item item : items) {
            count = count + item.getStackSize();
        }
        return count;
    }

    public static int getCount(final int... ids) {
        Item[] items = getAllItems(item -> item.isValid() && Utilities.inArray(item.getId(), ids));
        if (items == null)
            return 0;
        int count = 0;
        for (Item item : items) {
            count = count + item.getStackSize();
        }
        return count;
    }

    public static int getCount(final String... names) {
        Item[] items = getAllItems(item -> item.isValid() && item.getName() != null
                && Utilities.inArray(item.getName(), names));
        if (items == null)
            return 0;
        int count = 0;
        for (Item item : items) {
            count = count + item.getStackSize();
        }
        return count;
    }
}
