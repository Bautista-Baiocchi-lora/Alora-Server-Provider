package org.alora.api.data;


import org.alora.loader.Loader;

/**
 * Created by Ethan on 2/26/2018.
 */
public class Menu {
    public static void sendInteraction(int i1, int i2, int i3, long indexHash, String action, String option) {
        if (!Game.isLoggedIn())
            return;
        Loader.getReflectionEngine().getMethodValue("HG", "I", 8, "void", null, i1, i2, i3, indexHash, action, option, 0, 0);
    }

    public static void sendNPCInteraction(long indexHash, String action) {
        if (!Game.isLoggedIn())
            return;
        switch (action) {
            case "Talk-to":
                sendInteraction(0, 0, 17, indexHash, "", "");
                break;
            case "Trade":
                sendInteraction(0, 0, 4, indexHash, "", "");
                break;
            case "Heal":
                sendInteraction(0, 0, 4, indexHash, "", "");
                break;
            case "Assignment":
                sendInteraction(0, 0, 4, indexHash, "", "");
                break;
            case "Previous":
                sendInteraction(0, 0, 4, indexHash, "", "");
                break;
            case "Attack":
                sendInteraction(0, 0, 16, indexHash, "", "");
                break;
            case "Pickpocket":
                sendInteraction(0, 0, 4, indexHash, "", "");
                break;
            default:
                sendInteraction(0, 0, 17, indexHash, "", "");
                break;
        }
    }

    public static void sendNPCInteraction(long indexHash, int menuIndex) {
        if (!Game.isLoggedIn())
            return;
        sendInteraction(0, 0, menuIndex, indexHash, "", "");
    }

    public static void sendGroundItemInteraction(int localOne, int localTwo, int itemID) {
        if (!Game.isLoggedIn())
            return;
        sendInteraction(localOne, localTwo, 18, (long) itemID, "", "");
    }
    public static void sendItemInteraction(int slot, int widgetHash, String action, int itemID) {
        if (!Game.isLoggedIn())
            return;
        for (ItemOptions option : ItemOptions.values()) {
            if (option.getName().trim().equals(action.toLowerCase().trim())) {
                sendInteraction(slot, widgetHash, option.getIndex(), (long) itemID, "", "");
            }
        }
    }

    public static void sendWalkingInteraction(int x, int y) {
        Menu.sendInteraction(x, y, 60, 1, "", "Walk here");
    }
    public static void sendWidgetInteraction(String option, int hash) {
        if (!Game.isLoggedIn())
            return;
        switch (option.toLowerCase()) {
            case "continue":
                sendInteraction(-1, hash, 41, 0, "", "");
                break;
            default:
                sendInteraction(-1, hash, 9, 1, "", "");
                break;
        }
    }

    public static long longValue() {
        if (!Game.isLoggedIn())
            return -1;
        return (long) Loader.getReflectionEngine().getFieldValue("VE", "B", getMenuInstance());
    }

    public static int intValueThree() {
        if (!Game.isLoggedIn())
            return -1;
        return (int) Loader.getReflectionEngine().getFieldValue("VE", "I", getMenuInstance());
    }

    public static int intValueOne() {
        if (!Game.isLoggedIn())
            return -1;
        return (int) Loader.getReflectionEngine().getFieldValue("VE", "D", getMenuInstance());
    }

    public static int intValueTwo() {
        if (!Game.isLoggedIn())
            return -1;
        return (int) Loader.getReflectionEngine().getFieldValue("VE", "F", getMenuInstance());
    }

    public static String stringValueOne() {
        if (!Game.isLoggedIn())
            return null;
        return (String) Loader.getReflectionEngine().getFieldValue("VE", "Z", getMenuInstance());
    }

    public static String stringValueTwo() {
        if (!Game.isLoggedIn())
            return null;
        return (String) Loader.getReflectionEngine().getFieldValue("VE", "C", getMenuInstance());
    }

    public static int getIndexForAction(String[] actions, String action) {
        if (!Game.isLoggedIn())
            return -1;
        for (int i = 0; i <= 4; i++) {
            if (stringIsValid(actions[i]) && actions[i].equalsIgnoreCase(action)) {
                if (i == 0) {
                    return 42;
                }
                if (i == 1) {
                    return 50;
                }
                if (i == 2) {
                    return 49;
                }
                if (i == 3) {
                    return 46;
                }
                if (i == 4) {
                    return 1001;
                }
            }
        }
        return -1;
    }

    private static boolean stringIsValid(String string) {
        return string != null && string.length() > 0 && !string.isEmpty() && !string.equals("null");
    }

    private static Object getMenuInstance() {
        if (!Game.isLoggedIn())
            return null;
        return Loader.getReflectionEngine().getMethodValue("MS", "T", 0, "class CG", null);
    }

}
