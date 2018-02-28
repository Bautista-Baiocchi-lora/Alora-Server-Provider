package org.alora.api.wrappers;

import org.alora.api.interfaces.Nameable;
import org.bot.Engine;

/**
 * Created by Ethan on 2/27/2018.
 */
public class Player extends Actor implements Nameable {

    public Player(Object raw) {
        super(raw);
    }

    @Override
    public String getName() {
        if (getRaw() == null)
            return null;
        return (String) Engine.getReflectionEngine().getFieldValue("OG", "BZ", getRaw());
    }
}