package org.alora.api.interactive;

import org.alora.api.data.Game;
import org.alora.api.wrappers.GroundItem;
import org.alora.api.wrappers.Tile;
import org.alora.loader.Loader;
import org.ubot.util.Filter;
import org.ubot.util.Utilities;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ethan on 3/2/2018.
 */
public class GroundItems {

    public static GroundItem[] getAll(Filter<GroundItem> filter) {
        List<GroundItem> groundItems = new ArrayList<>();

        Object[][][] groundArrayObjects = (Object[][][]) Loader.getReflectionEngine().getFieldValue("VB", "L");

        int z = Game.getPlane();
        for (int x = 0; x < 104; x++) {
            for (int y = 0; y < 104; y++) {
                Object nl = groundArrayObjects[z][x][y];
                if (nl != null) {
                    for (Object obj = getMethodValue("D", nl); obj != null; obj = getMethodValue("B", nl)) {
                        GroundItem groundItem = new GroundItem(obj, new Tile(Game.getBaseX() + x, Game.getBaseY() + y, Game.getPlane()));
                        if (filter == null || filter.accept(groundItem)) {
                            groundItems.add(groundItem);
                        }
                    }
                }
            }
        }
        return groundItems.toArray(new GroundItem[groundItems.size()]);
    }

    private static Object getMethodValue(String field, Object obj) {
        try {
            Class<?> clazz = Loader.getReflectionEngine().getClass("TS").getRespresentedClass();
            for (Method m : clazz.getDeclaredMethods()) {
                if (m.getName().equals(field)) {
                    if (m.getParameterCount() == 0) {
                        m.setAccessible(true);
                        return m.invoke(obj);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static GroundItem[] getAll(final String... names) {
        return getAll(groundItem -> groundItem.isValid() && groundItem.getName() != null && Utilities.inArray(groundItem.getName(), names));
    }

    public static GroundItem[] getAll(final int... ids) {
        return getAll(groundItem -> groundItem.isValid() && Utilities.inArray(groundItem.getId(), ids));
    }

    public static GroundItem[] getAll() {
        return getAll(groundItem -> true);
    }

    public static GroundItem getNearest(Filter<GroundItem> filter) {
        return getNearest(Players.getLocal().getLocation(), filter);
    }

    public static GroundItem getNearest(Tile start, Filter<GroundItem> filter) {
        GroundItem closet = new GroundItem(null, null);
        int distance = 16;
        for (GroundItem groundItem : getAll(filter)) {
            if (groundItem.isValid() && distance > groundItem.distanceTo(start)) {
                closet = groundItem;
                distance = groundItem.distanceTo(start);
            }
        }
        return closet;
    }

    public static GroundItem getNearest(final int... ids) {
        return getNearest(Players.getLocal().getLocation(), groundItem -> groundItem.isValid() && Utilities.inArray(groundItem.getId(), ids));
    }

    public static GroundItem getNearest(final String... names) {
        return getNearest(Players.getLocal().getLocation(), groundItem -> groundItem.isValid() && Utilities.inArray(groundItem.getName(), names));
    }

    public static GroundItem getAt(final Tile tile) {
        return getNearest(Players.getLocal().getLocation(), obj -> obj != null && tile.equals(obj.getLocation()));
    }


    public static GroundItem nil() {
        return new GroundItem(null, null);
    }

}
