package org.alora.api.wrappers;

import org.alora.api.data.Calculations;
import org.alora.api.data.Game;
import org.alora.api.interfaces.Interactable;
import org.alora.api.interfaces.Locatable;
import org.bot.Engine;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.Arrays;

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
        return (int) Engine.getReflectionEngine().getFieldValue("WS", "G", raw);
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

    public int getHealth() {
        return (int) Engine.getReflectionEngine().getFieldValue("WS", "M", raw);

    }

    public int getMaxHealth() {
        return (int) Engine.getReflectionEngine().getFieldValue("WS", "N", raw);

    }

    public int getInteractingIndex() {
        return (int) Engine.getReflectionEngine().getFieldValue("WS", "H", raw);
    }
    public boolean isMoving() {
        if (!isValid())
            return false;
        return getQueueSize() > 0;
    }

    public boolean isInCombat() {
        if (raw == null)
            return false;
        int loopCycleStatus = Game.getClientCycle() - 130;
        int[] hitCycles = (int[]) Engine.getReflectionEngine().getFieldValue("WS", "E", raw);
        for (final int loopCycle : hitCycles) {
            if (loopCycle > loopCycleStatus) {
                return true;
            }
        }
        return false;
    }

    public void getIntValues() {
        Class<?> c = Engine.getReflectionEngine().getClass("WS").getRespresentedClass();
        for (Field f : c.getDeclaredFields()) {
            if (f.getGenericType().getTypeName().equals("int[]")) {
                if (!f.toGenericString().contains("static")) {
                    int[] i = (int[]) Engine.getReflectionEngine().getFieldValue("WS", f.getName(), raw);
                    System.out.println(Arrays.toString(i) + " : " + f.getName());

                }
            }
        }
    }

    public Actor getInteracting() {
        if (raw == null)
            return null;
        int interactingIndex = getInteractingIndex();
        if (interactingIndex == -1)
            return new Actor(null);
        if (interactingIndex < 32768) {
            Object[] localNpcs = (Object[]) Engine.getReflectionEngine().getFieldValue("YB", "H", null);
            if (localNpcs.length > interactingIndex)
                return new NPC(interactingIndex, localNpcs[interactingIndex]);
        } else {
            interactingIndex -= 32768;
        }
        return new Actor(null);
    }

    @Override
    public boolean interact(String action) {
        if (this instanceof NPC) {
            long indexHash = ((NPC) this).getHash();
            org.alora.api.data.Menu.sendNPCInteraction(indexHash, action);
            return true;
        }
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


    @Override
    public boolean equals(Object a) {
        if (a != null && a instanceof Actor) {
            Actor t = (Actor) a;
            boolean x = this.getLocation().equals(t.getLocation()) && this.getAnimation() == t.getAnimation() && this.getMaxHealth() == this.getMaxHealth();
            if (t instanceof Player && this instanceof Player) {
                Player j = (Player) t;
                return x & j.getName().equals(((Player) this).getName());
            } else if (t instanceof NPC && this instanceof NPC) {
                NPC j = (NPC) t;
                return x & j.getId() == (((NPC) this).getId());
            }
            return false;
        }
        return false;
    }

}
