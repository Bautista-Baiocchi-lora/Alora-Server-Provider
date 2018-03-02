package org.alora.api.wrappers;

import org.alora.api.data.Menu;
import org.alora.api.definitions.ItemDefinition;
import org.alora.api.interfaces.Identifiable;
import org.alora.api.interfaces.Interactable;
import org.alora.api.interfaces.Nameable;

import java.awt.*;


/**
 * Created by Ethan on 2/28/2018.
 */
public class Item implements Identifiable, Nameable, Interactable {
    private int index;
    private int id;
    private int stackSize;
    private Rectangle area;
    private int hash = 0;
    private ItemDefinition itemDefinition;

    public Item(int id, int stackSize, int index, Rectangle area, int hash) {
        this.id = id;
        this.stackSize = stackSize;
        this.area = area;
        this.index = index;
        this.hash = hash;
    }

    public int getIndex() {
        return index;
    }


    public int getStackSize() {
        return stackSize;
    }

    public Rectangle getArea() {
        return area;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public long getHash() {
        return hash;
    }

    @Override
    public String getName() {
        if (itemDefinition == null)
            itemDefinition = new ItemDefinition(id);
        return itemDefinition.getName();
    }

    @Override
    public boolean interact(String action) {
        Menu.sendItemInteraction(index, hash, action, getId());
        return true;
    }

    public boolean isValid() {
        return id > 0 && stackSize > 0;
    }

    public Point getCentralPoint() {
        return new Point((int) getArea().getCenterX(), (int) getArea().getCenterY());
    }
}