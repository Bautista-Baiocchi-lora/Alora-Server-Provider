package org.alora.api.data;

import org.alora.api.interactive.Inventory;
import org.alora.api.interactive.Widgets;
import org.alora.api.wrappers.Item;
import org.alora.api.wrappers.WidgetChild;
import org.ubot.util.Condition;
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
        WidgetChild parent = Widgets.get(BANK_INTERFACE, BANK_ITEMS_PAD);
        if (Widgets.getOpenInterfaces().contains(15)) {
            WidgetChild[] children = parent.getChildren();
            if (children != null) {
                for (int i = 0; i < children.length; i++) {
                    WidgetChild widgetChild = children[i];
                    Item item = new Item(widgetChild.getItemID(),
                            widgetChild.getItemStackSize(), i,
                            null, widgetChild.getHash());
                    if (item.isValid() && (filter == null || filter.accept(item))) {
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
        return Widgets.getOpenInterfaces().contains(15);
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

    public static boolean deposit(final int[] ids, final int amount) {
        return deposit(item -> item.isValid() && Utilities.inArray(item.getId(), ids), amount);
    }

    public static boolean deposit(final String[] names, final int amount) {
        return deposit(item -> item.isValid() && item.getName() != null
                && Utilities.inArray(item.getName(), names), amount);
    }

    public static boolean deposit(final String name, final int amount) {
        return deposit(item -> item.isValid() && item.getName() != null
                && item.getName().equalsIgnoreCase(name), amount);
    }

    public static boolean deposit(Filter<Item> filter, int amount) {
        Item[] items = getAllItems(filter);
        if (items == null || items.length == 0)
            return false;
        for (Item item : items) {
            if (item.isValid()) {
                deposit(item.getId(), amount);
            }
        }
        return true;
    }

    public static boolean deposit(final int id, final int amount) {
        if (!isOpen())
            return false;
        Item item = Inventory.getItem(id);
        if (!item.isValid())
            return false;
        final int invAmount = Inventory.getCount(id);
        String action = "store x";
        if (amount >= invAmount) {
            action = "store all";
        } else {
            switch (invAmount) {
                case 1:
                    action = "store 1";
                    break;
                case 5:
                    action = "store 5";
                    break;
                case 10:
                    action = "store 10";
                    break;
            }
        }
        item.interact(action);
        if (action.contains("x")) {
            Condition.sleep(2500);
            //Gotta do that keybaord
        }
        Condition.wait(new Condition.Check() {
            public boolean poll() {
                return Inventory.getCount(id) != invAmount;
            }
        }, 100, 30);
        return false;
    }

    public static boolean withdraw(final int id, final int amount) {
        if (!isOpen())
            return false;
        Item item = Bank.getItem(id);
        if (!item.isValid())
            return false;
        System.out.println("ID: " + item.getHash() + " : ");
        final int amountBeforeWithdraw = Bank.getCount(id);
        String action;
        int index;
        switch (amount) {
            case 1:
                action = "withdraw-1";
                index = 1;
                break;
            case 5:
                action = "withdraw-5";
                index = 2;
                break;
            case 10:
                action = "withdraw-10";
                index = 3;
                break;
            default:
                action = "withdraw-all";
                index = 6;
                break;
        }
        Menu.sendBankInteraction(item.getIndex(), (int) item.getHash(), action, index);
        if (action.equals("withdraw-x")) {
            Condition.sleep(2500);
            //Gotta do that keybaord
        }
        Condition.wait(new Condition.Check() {
            public boolean poll() {
                return Bank.getCount(id) != amountBeforeWithdraw;
            }
        }, 100, 30);
        return true;
    }

    public static boolean withdraw(final int[] ids, final int amount) {
        return withdraw(item -> item.isValid() && Utilities.inArray(item.getId(), ids), amount);
    }

    public static boolean withdraw(final String[] names, final int amount) {
        return withdraw(item -> item.isValid() && item.getName() != null
                && Utilities.inArray(item.getName(), names), amount);
    }

    public static boolean withdraw(final String name, final int amount) {
        return withdraw(item -> item.isValid() && item.getName() != null
                && item.getName().equalsIgnoreCase(name), amount);
    }

    public static boolean withdraw(Filter<Item> filter, int amount) {
        Item[] items = getAllItems(filter);
        if (items == null)
            return false;
        for (Item item : items) {
            if (item.isValid()) {
                withdraw(item.getId(), amount);
            }
        }
        return true;
    }

    public static boolean depositAll() {
        return depositAllExcept(item -> false);
    }

    public static boolean depositAllExcept(final String... names) {
        return depositAllExcept(item -> item.isValid() && item.getName() != null
                && Utilities.inArray(item.getName(), names));
    }

    public static boolean depositAllExcept(final int... ids) {
        return depositAllExcept(item -> item.isValid() && Utilities.inArray(item.getId(), ids));
    }

    public static boolean depositAllExcept(Filter<Item> filter) {
        boolean deposit = false;
        for (Item item : Inventory.getAllItems()) {
            if (item.isValid() && (filter == null || !filter.accept(item))) {
                deposit(item.getId(), 9999999);
                deposit = true;
            }
        }
        return deposit;
    }
}
