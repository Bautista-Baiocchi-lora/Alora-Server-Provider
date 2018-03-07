package org.alora.api.data;

import org.alora.api.interactive.Widgets;
import org.alora.api.wrappers.WidgetChild;

import java.util.Arrays;

/**
 * Created by Ethan on 3/7/2018.
 */
public enum Prayers {


    PROTECT_ITEM(25, 91, 25), PROTECT_FROM_MAGIC(37, 95, 37), PROTECT_FROM_RANGE(39, 96, 40),
    PROTECT_FROM_MELEE(41, 97, 43), EAGLE_EYE(43, 866, 44), MYSTIC_MIGHT(45, 867, 45),
    PIETY(55, 1053, 70);

    private final int PRAYER_TAB = 271;
    private int index;
    private int varp;
    private int reqLvl;

    Prayers(int index, int varp, int reqLvl) {
        this.index = index;
        this.varp = varp;
        this.reqLvl = reqLvl;
    }

    public static boolean usingQuickPrayer() {
        return Settings.get(429) == 1;
    }

    public static void toggleQuickPrayer() {
        if (!Game.isLoggedIn())
            return;
        if (usingQuickPrayer()) {
            WidgetChild wc = Widgets.getWidgetWithAction("Activate Quick-prayers");
            if (wc != null) {
                Menu.sendWidgetInteraction("", wc.getHash());
            }
        }
    }

    private static void printAllPrayers() {
        int i = 0;
        for (WidgetChild wc : Widgets.get(271).getChildren()) {
            System.out.println(i + " : " + wc.getHash() + " : " + wc.getText() + " : " + Arrays.toString(wc.getActions()));
            i++;
        }
    }

    public void activate() {
        if (!Game.isLoggedIn())
            return;
        if (!isActive()) {
            Menu.sendWidgetInteraction("", getHash());
        } else {
            deactivate();
        }
    }

    public void deactivate() {
        if (!Game.isLoggedIn())
            return;
        if (isActive()) {
            Menu.sendWidgetInteraction("", getHash());
        }
    }

    private WidgetChild getPrayer() {
        return Widgets.get(PRAYER_TAB).getChildren()[index];
    }

    private int getHash() {
        if (!Game.isLoggedIn())
            return -1;
        return getPrayer().getHash();
    }

    public boolean isActive() {
        if (!Game.isLoggedIn())
            return false;
        return Settings.get(varp) != 0;
    }


    public int getInterface() {
        return this.index;
    }

    public int getRequiredLvl() {
        return this.reqLvl;
    }


}
