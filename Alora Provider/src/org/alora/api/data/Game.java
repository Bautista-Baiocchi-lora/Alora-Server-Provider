package org.alora.api.data;

import org.bot.Engine;

/**
 * Created by Ethan on 2/27/2018.
 */
public class Game {

    public static final int STATE_LOGGED_IN = 30;
    public static final int STATE_LOG_IN_SCREEN = 10;
    public static final int CONNECTING = 20;

    public static int getBaseX() {
        return (int) Engine.getReflectionEngine().getFieldValue("VI", "I", null);
    }

    public static int getBaseY() {
        return (int) Engine.getReflectionEngine().getFieldValue("CS", "C", null);
    }

    public static int getID() {
        return (int) Engine.getReflectionEngine().getFieldValue("DE", "D", null);
    }

    public static int getPlane() {
        return 0;
    }

    public static int getGameCycle() {
        return (int) Engine.getReflectionEngine().getFieldHookValue("LoopCycle", null);
    }

}
