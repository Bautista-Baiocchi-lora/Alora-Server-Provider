package org.alora.api.wrappers;

import org.alora.api.data.Calculations;
import org.alora.api.data.Game;
import org.alora.api.data.Menu;
import org.alora.api.definitions.ItemDefinition;
import org.alora.api.interfaces.Identifiable;
import org.alora.api.interfaces.Interactable;
import org.alora.api.interfaces.Locatable;
import org.alora.api.interfaces.Nameable;
import org.bot.Engine;

import java.awt.*;


/**
 * Created by Ethan on 3/2/2018.
 */
public class GroundItem implements Locatable, Identifiable, Nameable, Interactable {
    private Tile location;
    private Object raw;
    private Object itemInstance;
    private ItemDefinition itemDefinition;
    private int id;

    public GroundItem(Object raw, Tile tile) {
        this.raw = raw;
        this.location = tile;
        this.itemInstance = getItemInstance();
        this.id = getId();
    }

    private int getLocalX() {
        return location.getX() - Game.getBaseX();
    }

    private int getLocalY() {
        return location.getY() - Game.getBaseY();
    }

    private Object getItemInstance() {
        return Engine.getReflectionEngine().getFieldValue("HA", "B", raw);
    }

    @Override
    public String getName() {
        if (itemDefinition == null)
            itemDefinition = new ItemDefinition(id);
        return itemDefinition.getName();
    }

    @Override
    public int getId() {
        return (int) Engine.getReflectionEngine().getFieldValue("AE", "F", itemInstance);
    }

    public String[] getOptions() {
        if (itemDefinition == null)
            itemDefinition = new ItemDefinition(id);
        return itemDefinition.getOptions();
    }

    @Override
    public long getHash() {
        return 0;
    }

    @Override
    public boolean interact(String action) {
        Menu.sendGroundItemInteraction(getLocalX(), getLocalY(), getId());
        return true;
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
        return Calculations.distanceTo(location);
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
        return location;
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

    public boolean isValid() {
        return (id) != -1;
    }

    public int getStackSize() {
        return 0;
    }

}
