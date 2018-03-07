package org.alora.api.wrappers;

import org.alora.api.data.Calculations;
import org.alora.api.data.Menu;
import org.alora.api.data.Walking;
import org.alora.api.interfaces.Identifiable;
import org.alora.api.interfaces.Interactable;
import org.alora.api.interfaces.Locatable;
import org.alora.api.interfaces.Nameable;
import org.alora.loader.Loader;


/**
 * Created by Ethan on 2/27/2018.
 */

public class GameObject implements Identifiable, Nameable, Locatable, Interactable {

    private Object raw;
    private Tile tile;
    private int id;


    public GameObject(Object raw, int x, int y, int z) {
        this.raw = raw;
        this.tile = new Tile(x, y, z);
    }

    @Override
    public long getHash() {
        return (long) Loader.getReflectionEngine().getFieldValue("UC", "P", raw);
    }

    @Override
    public int getId() {
        if (raw == null)
            return 0;
        if (id == 0) {
            id = (int) (getHash() >>> 32) & Integer.MAX_VALUE;
            return id;
        }
        return -1;
    }

    public int getLocalX() {
        final int x = (int) getHash() & 0x7F;
        return x;
    }

    public int getLocalY() {
        final int y = 0x7F & (int) getHash() >> 7;
        return y;
    }

    private String getRealName() {
        if (objectDefInstance() != null) {
            return (String) Loader.getReflectionEngine().getFieldValue("JG", "X", objectDefInstance());
        }
        return "null";
    }

    public boolean walkTo() {
        return Walking.walkTo(this);
    }
    public String[] getActions() {
        if (objectDefInstance() != null) {
            return (String[]) Loader.getReflectionEngine().getFieldValue("JG", "R", objectDefInstance());
        }
        return null;
    }

    private Object objectDefInstance() {
        int id2 = (int) (getHash() >>> 32) & Integer.MAX_VALUE;
        try {
            Object objectDef = Loader.getReflectionEngine().getMethodValue("JG", "C", 1, "final class JG", null, id2);
            return objectDef;

        } catch (Exception e) {
        }
        return null;
    }


    public int getX() {
        return tile.getX();
    }

    public int getY() {
        return tile.getY();
    }

    public boolean isValid() {
        return raw != null;
    }


    @Override
    public int distanceTo() {
        return Calculations.distanceTo(this.getLocation());
    }

    @Override
    public int distanceTo(Locatable locatable) {
        return Calculations.distanceBetween(tile, locatable.getLocation());
    }

    @Override
    public int distanceTo(Tile tile) {
        return Calculations.distanceBetween(tile, getLocation());
    }

    public Tile getLocation() {
        return tile;
    }

    @Override
    public boolean canReach() {
        return false;
    }

/*
    private int getMenuIndex(String action) {
        Logger.log("Searching for index");
        final String[] actions = getActions();
        Logger.log("Found actions");
        final Object instance = objectDefInstance();
        if (instance != null) {
            Logger.log("found instance");
        }
        for (int i = 0; i <= 4; i++) {
            Logger.log("looping");
            if (actions != null) {
                if (actions[i].toLowerCase().equalsIgnoreCase(action)) {
                    Logger.log("One of out actions hit");
                    if (i == 0) {
                        return 42;
                    }
                    if (i == 1) {
                        return 50;
                    }
                    if (i == 2) {
                        return 49;
                    }
             */
/*       if ((int) Loader.getReflectionEngine().getFieldValue("JG", "L", instance) == i) {
                        return (int) Loader.getReflectionEngine().getFieldValue("JG", "e", instance);
                    }*//*

                    if (i == 3) {
                        return 46;
                    }
                */
/*    if ((int) Loader.getReflectionEngine().getFieldValue("JG", "L", instance) == i) {
                        return (int) Loader.getReflectionEngine().getFieldValue("JG", "e", instance);
                    }*//*

                    if (i == 4) {
                        return 1001;
                    }
                }
            }
        }
        return -1;
    }
*/

    @Override
    public boolean interact(String action) {
        String color = "<col=00ffff>";
        final int index = Menu.getIndexForAction(getActions(), action);
        Menu.sendInteraction(getLocalX(), getLocalY(), index, getHash(), color + getName(), action);
        return true;
    }

    @Override
    public String getName() {
        return getRealName();
    }
}
