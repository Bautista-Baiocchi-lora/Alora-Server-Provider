package org.alora.api.wrappers;

import org.alora.api.data.Menu;
import org.alora.api.interactive.Widgets;
import org.alora.api.interfaces.Interactable;
import org.alora.loader.Loader;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ethan on 2/28/2018.
 */
public class WidgetChild implements Interactable {

    private Object raw;
    private int index;
    private int parent;

    public WidgetChild(Object raw, int index, int parent) {
        this.raw = raw;
        this.index = index;
        this.parent = parent;
    }

    public int getHash() {
        return (int) Loader.getReflectionEngine().getFieldValue("IK", "RZ", raw);
    }

    public int getItemStackSize() {
        return (int) Loader.getReflectionEngine().getFieldValue("IK", "HZ", raw);
    }

    public int getItemID() {
        return (int) Loader.getReflectionEngine().getFieldValue("IK", "g", raw);
    }


    public String getText() {
        return (String) Loader.getReflectionEngine().getFieldValue("IK", "XI", raw);
    }

    public int[] getSlotContentIds() {
        return (int[]) Loader.getReflectionEngine().getFieldValue("IK", "tI", raw);
    }

    public int[] getStackSizes() {
        return (int[]) Loader.getReflectionEngine().getFieldValue("IK", "ZC", raw);
    }

    public String[] getActions() {
        return (String[]) Loader.getReflectionEngine().getFieldValue("IK", "N", raw);
    }

    public boolean isVisible() {
        return Widgets.getOpenInterfaces().contains(parent);
    }

    public void getIntValues() {
        Class<?> c = Loader.getReflectionEngine().getClass("IK").getRespresentedClass();
        for (Field f : c.getDeclaredFields()) {
            if (f.getGenericType().getTypeName().equals("int")) {
                if (!f.toGenericString().contains("static")) {
                    int i = (int) Loader.getReflectionEngine().getFieldValue("IK", f.getName(), raw);
                    System.out.println(i + " : " + f.getName());
                }
            }
        }
    }

    public WidgetChild[] getChildren() {
        List<WidgetChild> list = new ArrayList<>();
        Object[] children = (Object[]) Loader.getReflectionEngine().getFieldValue("IK", "CZ", raw);
        if (children == null) {
            return list.toArray(new WidgetChild[list.size()]);
        }
        for (int i = 0; i < children.length; i++) {
            list.add(new WidgetChild(children[i], i, parent));
        }
        return list.toArray(new WidgetChild[list.size()]);
    }

    public int getIndex() {
        return index;
    }

    public int getParent() {
        return parent;
    }

    @Override
    public boolean interact(String action) {
        Menu.sendWidgetInteraction(action, getHash());
        return true;
    }

}
