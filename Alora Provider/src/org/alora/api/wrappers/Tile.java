package org.alora.api.wrappers;

/**
 * Created by Ethan on 2/27/2018.
 */


import org.alora.api.data.Calculations;
import org.alora.api.data.Game;
import org.alora.api.interfaces.Interactable;
import org.alora.api.interfaces.Locatable;

import java.awt.*;

public class Tile implements Locatable, Interactable {
    int x;
    int y;
    int z;

    public Tile(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        this.z = Game.getPlane();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    @Override
    public boolean interact(String action) {
        return false;
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
        return Calculations.distanceTo(this);
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
        return this;
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
    public String toString() {
        return "Tile: [ X: " + getX() + ", Y: " + getY() + ", Z: " + getZ() + "]";
    }
}
