package org.alora.api.data;


import org.alora.loader.Loader;

/**
 * Created by Ethan on 2/27/2018.
 */
public class Game {

    public static final int STATE_LOGGED_IN = 30;
    public static final int STATE_LOG_IN_SCREEN = 10;
    public static final int CONNECTING = 20;

    public static int getClientCycle() {
        return (int) Loader.getReflectionEngine().getFieldValue("YF", "Z", null);
    }

    public static int getBaseX() {
        return (int) Loader.getReflectionEngine().getFieldValue("VI", "I", null);
    }

    public static int getBaseY() {
        return (int) Loader.getReflectionEngine().getFieldValue("CS", "C", null);
    }

    public static int getMapScale() {
        return (int) Loader.getReflectionEngine().getFieldValue("TD", "H", null);
    }

    public static int getMapScaleDelta() {
        return (int) Loader.getReflectionEngine().getFieldValue("SC", "F", null);
    }

    public static int getMapAngle() {
        return (int) Loader.getReflectionEngine().getFieldValue("AH", "Z", null);
    }


    public static int getID() {
        return (int) Loader.getReflectionEngine().getFieldValue("DE", "D", null);
    }

    public static int getGameState() {
        return (int) Loader.getReflectionEngine().getFieldValue("MS", "LI", null);

    }
    public static int getPlane() {
        return (int) Loader.getReflectionEngine().getFieldValue("VC", "I", null);
    }

    public static boolean usingQuickPrayer() {
        return Settings.get(375) == 1;
    }

    public static boolean isUsingSpecialAttack() {
        return Settings.get(301) == 1;
    }

    public static int getSpecialAttackPercent() {
        if (Settings.get(300) > 0) {
            return Settings.get(300) / 10;
        }
        return 0;
    }

    public static boolean isRunning() {
        return Settings.get(173) > 0;
    }
    public static boolean isLoggedIn() {
        return getGameState() == STATE_LOGGED_IN;
    }
}
