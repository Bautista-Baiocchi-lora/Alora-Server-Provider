package org.alora.api.wrappers;

import org.alora.api.data.Calculations;
import org.alora.api.data.Game;
import org.alora.api.interfaces.Interactable;
import org.alora.api.interfaces.Locatable;
import org.bot.Engine;

import java.awt.*;

/**
 * Created by Ethan on 2/27/2018.
 */
public class Actor implements Locatable, Interactable {

    private final Object raw;

    public Actor(Object raw) {
        this.raw = raw;
    }

    public int getLocalX() {
        if (!isValid())
            return -1;
        return (int) Engine.getReflectionEngine().getFieldValue("WS", "x", raw);
    }

    public int getLocalY() {
        if (!isValid())
            return -1;
        return (int) Engine.getReflectionEngine().getFieldValue("WS", "y", raw);
    }

    public int getX() {
        if (!isValid())
            return -1;
        return ((((int) Engine.getReflectionEngine().getFieldValue("WS", "x", raw)) >> 7) + Game.getBaseX());
    }

    public int getY() {
        if (!isValid())
            return -1;
        return ((((int) Engine.getReflectionEngine().getFieldValue("WS", "y", raw)) >> 7) + Game.getBaseY());
    }

    public int getAnimation() {
        if (raw == null)
            return -1;
        return (int) Engine.getReflectionEngine().getFieldHookValue("Animation", raw);
    }

    public int getOrientation() {
        if (!isValid())
            return -1;
        return (int) Engine.getReflectionEngine().getFieldValue("WS", "q", raw);
    }

    public int getQueueSize() {
        if (!isValid())
            return -1;
        return (int) Engine.getReflectionEngine().getFieldValue("WS", "BI", raw);
    }

    public boolean isMoving() {
        if (!isValid())
            return false;
        return getQueueSize() > 0;
    }

    @Override
    public boolean interact(int menuIndex) {
        if (this instanceof NPC) {
            long indexHash = ((NPC) this).getHash();
            org.alora.api.data.Menu.sendNPCInteraction(indexHash, menuIndex);
            return true;
        }
        return false;
    }

    @Override
    public boolean interact(int menuIndex, String entityName) {
        return false;
    }


    @Override
    public boolean isOnScreen() {
        return false;
    }

    @Override
    public Point getPointOnScreen() {
        return getLocation().getPointOnScreen();
    }


    @Override
    public int distanceTo() {
        return Calculations.distanceTo(getLocation());
    }

    @Override
    public int distanceTo(Locatable locatable) {
        return Calculations.distanceBetween(getLocation(), locatable.getLocation());
    }

    @Override
    public int distanceTo(Tile tile) {
        return Calculations.distanceBetween(getLocation(), tile);
    }

    @Override
    public boolean turnTo() {
        return false;
    }

    @Override
    public Tile getLocation() {
        return new Tile(getX(), getY());
    }

    @Override
    public void draw(Graphics2D g, Color color) {

    }

    @Override
    public void draw(Graphics2D g) {

    }

    @Override
    public boolean canReach() {
        return false;
    }

    protected Object getRaw() {
        return raw;
    }

    public boolean isValid() {
        return getRaw() != null;
    }
}
