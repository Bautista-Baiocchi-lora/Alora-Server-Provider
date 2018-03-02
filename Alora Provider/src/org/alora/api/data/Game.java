package org.alora.api.data;

import org.bot.Engine;

/**
 * Created by Ethan on 2/27/2018.
 */
public class Game {

    public static final int STATE_LOGGED_IN = 30;
    public static final int STATE_LOG_IN_SCREEN = 10;
    public static final int CONNECTING = 20;

    public static int getClientCycle() {
        return (int) Engine.getReflectionEngine().getFieldValue("YF", "Z", null);
    }

    public static int getBaseX() {
        return (int) Engine.getReflectionEngine().getFieldValue("VI", "I", null);
    }

    public static int getBaseY() {
        return (int) Engine.getReflectionEngine().getFieldValue("CS", "C", null);
    }

    public static int getMapScale() {
        return (int) Engine.getReflectionEngine().getFieldValue("TD", "H", null);
    }

    public static int getMapScaleDelta() {
        return (int) Engine.getReflectionEngine().getFieldValue("SC", "F", null);
    }

    public static int getMapAngle() {
        return (int) Engine.getReflectionEngine().getFieldValue("AH", "Z", null);
    }


    public static int getID() {
        return (int) Engine.getReflectionEngine().getFieldValue("DE", "D", null);
    }

    public static int getLoginStage() {
        return (int) Engine.getReflectionEngine().getFieldValue("ID", "H", null);

    }
    public static int getPlane() {
        return (int) Engine.getReflectionEngine().getFieldValue("VC", "I", null);
    }


    public static boolean isLoggedIn() {
        return getLoginStage() == STATE_LOGGED_IN;
    }
}
