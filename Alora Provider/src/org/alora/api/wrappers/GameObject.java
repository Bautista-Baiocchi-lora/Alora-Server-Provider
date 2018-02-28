package org.alora.api.wrappers;

import org.alora.api.data.Calculations;
import org.alora.api.data.Game;
import org.alora.api.interfaces.Identifiable;
import org.alora.api.interfaces.Interactable;
import org.alora.api.interfaces.Locatable;
import org.alora.api.interfaces.Nameable;
import org.bot.Engine;

import java.awt.*;
import java.lang.reflect.Field;

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
        return (long) Engine.getReflectionEngine().getFieldValue("UC", "P", raw);
    }

    @Override
    public int getId() {
        if (raw == null)
            return 0;
        if (id == 0) {
            id = ((int) Engine.getReflectionEngine().getFieldValue("UC", "J", raw));
            return id;
        }
        return -1;
    }

    public int getLocalX() {
        int x = tile.x - Game.getBaseX();
        return x;
    }

    public int getLocalY() {
        int y = tile.y - Game.getBaseY();
        return y;
    }

    public void intValues() {
        try {
            Class<?> c = Engine.getReflectionEngine().getClass("UC").getRespresentedClass();
            for (Field f : c.getDeclaredFields()) {
                if (f.getGenericType().getTypeName().equals("int")) {
                    if (!f.toGenericString().contains("static")) {
                        if (String.valueOf(getHash()).startsWith("20939")) {
                            int i = (int) Engine.getReflectionEngine().getFieldValue("UC", f.getName(), raw);
                            System.out.println(getHash() + " : " + i + " : " + f.getName());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public boolean isOnScreen() {
        return false;
    }

    @Override
    public Point getPointOnScreen() {
        return null;
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

    @Override
    public boolean turnTo() {
        return false;
    }

    public Tile getLocation() {
        return tile;
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

    @Override
    public boolean interact(int menuIndex, String objectName) {
        org.alora.api.data.Menu.sendInteraction(getLocalX(), getLocalY(), menuIndex, getHash(), objectName, "");
        return true;
    }

    @Override
    public boolean interact(int menuIndex) {
        System.out.println(getLocalX() + " : " + getLocalY());
        org.alora.api.data.Menu.sendInteraction(getLocalX(), getLocalY(), menuIndex, getHash(), "", "");
        return true;
    }

    @Override
    public String getName() {
        return null;
    }
}
