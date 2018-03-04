package org.alora.api.data;


import org.alora.loader.Loader;

/**
 * Created by Ethan on 2/26/2018.
 */
public class Menu {
    public static void sendInteraction(int i1, int i2, int i3, long indexHash, String action, String option) {
        Loader.getReflectionEngine().getMethodValue("HG", "I", 8, "void", null, i1, i2, i3, indexHash, action, option, 0, 0);
    }

    public static void sendNPCInteraction(long indexHash, String action) {
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
        sendInteraction(0, 0, menuIndex, indexHash, "", "");
    }

    public static void sendGroundItemInteraction(int localOne, int localTwo, int itemID) {
        sendInteraction(localOne, localTwo, 18, (long) itemID, "", "");
    }
    public static void sendItemInteraction(int slot, int widgetHash, String action, int itemID) {
        for (ItemOptions option : ItemOptions.values()) {
            if (option.getName().trim().equals(action.toLowerCase().trim())) {
                sendInteraction(slot, widgetHash, option.getIndex(), (long) itemID, "", "");
            }
        }
    }

    public static void sendWidgetInteraction(String option, int hash) {
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
        return (long) Loader.getReflectionEngine().getFieldValue("VE", "B", getMenuInstance());
    }

    public static int intValueThree() {
        return (int) Loader.getReflectionEngine().getFieldValue("VE", "I", getMenuInstance());
    }

    public static int intValueOne() {
        return (int) Loader.getReflectionEngine().getFieldValue("VE", "D", getMenuInstance());
    }

    public static int intValueTwo() {
        return (int) Loader.getReflectionEngine().getFieldValue("VE", "F", getMenuInstance());
    }

    public static String stringValueOne() {
        return (String) Loader.getReflectionEngine().getFieldValue("VE", "Z", getMenuInstance());
    }

    public static String stringValueTwo() {
        return (String) Loader.getReflectionEngine().getFieldValue("VE", "C", getMenuInstance());
    }

    public static int getIndexForAction(String[] actions, String action) {
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
        return Loader.getReflectionEngine().getMethodValue("MS", "T", 0, "class CG", null);
    }

}
