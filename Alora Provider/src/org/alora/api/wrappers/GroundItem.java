package org.alora.api.wrappers;

import org.alora.api.data.Calculations;
import org.alora.api.data.Game;
import org.alora.api.data.Menu;
import org.alora.api.data.Walking;
import org.alora.api.definitions.ItemDefinition;
import org.alora.api.interfaces.Identifiable;
import org.alora.api.interfaces.Interactable;
import org.alora.api.interfaces.Locatable;
import org.alora.api.interfaces.Nameable;
import org.alora.loader.Loader;


/**
 * Created by Ethan on 3/2/2018.
 */
public class GroundItem implements Locatable, Identifiable, Nameable, Interactable {
    private Object raw;
    private Object itemInstance;
    private ItemDefinition itemDefinition;
    private int id;
    private int x;
    private int y;

    public GroundItem(Object raw, int x, int y) {
        this.raw = raw;
        this.x = x;
        this.y = y;
        this.itemInstance = getItemInstance();
        this.id = getId();
    }

    public int getLocalX() {
        return x;
    }

    public int getLocalY() {
        return y;
    }

    private Object getItemInstance() {
        if (raw == null)
            return null;
        return Loader.getReflectionEngine().getFieldValue("HA", "B", raw);
    }

    @Override
    public String getName() {
        if (itemDefinition == null)
            itemDefinition = new ItemDefinition(id);
        return itemDefinition.getName();
    }

    @Override
    public int getId() {
        if (itemInstance == null)
            return -1;
        return (int) Loader.getReflectionEngine().getFieldValue("AE", "F", itemInstance);
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
    public int distanceTo() {
        return Calculations.distanceTo(getLocation());
    }

    @Override
    public int distanceTo(Locatable locatable) {
        return Calculations.distanceBetween(getLocation(), locatable.getLocation());
    }

    @Override
    public int distanceTo(Tile tile) {
        return Calculations.distanceBetween(getLocation(), getLocation());
    }

    @Override
    public Tile getLocation() {
        return new Tile(Game.getBaseX() + getLocalX(), Game.getBaseY() + getLocalY(), Game.getPlane());
    }

    public boolean walkTo() {
        return Walking.walkTo(this);
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
