package org.alora.api.data;

import org.bot.Engine;
import org.bot.ui.screens.clientframe.menu.logger.Logger;


/**
 * Created by Ethan on 2/26/2018.
 */
public class Menu {
    public static void sendInteraction(int i1, int i2, int i3, long indexHash, String action, String option) {
        Engine.getReflectionEngine().getMethodValue("HG", "I", 8, "void", null, i1, i2, i3, indexHash, action, option, 0, 0);
    }

    public static void sendNPCInteraction(long indexHash, int menuIndex) {
        Logger.log("Sending NPC Interaction");
        switch (menuIndex) {
            case 1:
                sendInteraction(0, 0, 17, indexHash, "", "");
                break;
            case 2:
                sendInteraction(0, 0, 4, indexHash, "", "");
                break;
            case 3:
                sendInteraction(0, 0, 19, indexHash, "", "");
                break;
            case 4:
                sendInteraction(0, 0, 2, indexHash, "", "");
                break;
            default:
                sendInteraction(0, 0, 17, indexHash, "", "");
                break;
        }
    }

    public static void sendItemInteraction(int slot, int menuIndex, int itemID) {
        switch (menuIndex) {
            case 1:
                sendInteraction(slot, 69694, 25, itemID, "", "");
                break;
            case 2:
                sendInteraction(slot, 69694, 13, itemID, "", "");
                break;
            default:
                sendInteraction(slot, 69694, 13, itemID, "", "");
                break;
        }
    }

    public static void sendWidgetInteraction(int widgetHash, int menuIndex) {
        switch (menuIndex) {
            case 1:
                sendInteraction(-1, widgetHash, 9, 1, "", "");
                break;
            case 2:
                sendInteraction(-1, widgetHash, 9, 1, "", "");
                break;
            default:
                sendInteraction(-1, widgetHash, 9, 1, "", "");
                break;
        }
    }

    public static long longValue() {
        return (long) Engine.getReflectionEngine().getFieldValue("VE", "B", getMenuInstance());
    }

    public static int intValueThree() {
        return (int) Engine.getReflectionEngine().getFieldValue("VE", "I", getMenuInstance());
    }

    public static int intValueOne() {
        return (int) Engine.getReflectionEngine().getFieldValue("VE", "D", getMenuInstance());
    }

    public static int intValueTwo() {
        return (int) Engine.getReflectionEngine().getFieldValue("VE", "F", getMenuInstance());
    }

    public static String stringValueOne() {
        return (String) Engine.getReflectionEngine().getFieldValue("VE", "Z", getMenuInstance());
    }

    public static String stringValueTwo() {
        return (String) Engine.getReflectionEngine().getFieldValue("VE", "C", getMenuInstance());
    }

    public static Object getMenuInstance() {
        return Engine.getReflectionEngine().getMethodValue("MS", "T", 0, "class CG", null);
    }

}
