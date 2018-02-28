package org.alora.api.interactive;

import org.alora.api.data.Game;
import org.alora.api.wrappers.GameObject;
import org.alora.api.wrappers.Tile;
import org.bot.Engine;
import org.bot.util.Filter;
import org.bot.util.Utilities;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Ethan on 2/27/2018.
 */
public class GameObjects {

    public static GameObject[] getAll() {
        return getAll(new Filter<GameObject>() {
            @Override
            public boolean accept(GameObject gameObject) {
                return true;
            }
        });
    }

    public static GameObject[] getAll(final String... names) {
        return getAll(new Filter<GameObject>() {
            @Override
            public boolean accept(GameObject gameObject) {
                return gameObject.isValid() && gameObject.getName() != null
                        && Utilities.inArray(gameObject.getName(), names);
            }
        });
    }

    public static GameObject[] getAll(final int... ids) {
        return getAll(new Filter<GameObject>() {
            @Override
            public boolean accept(GameObject gameObject) {
                return gameObject.isValid() && Utilities.inArray(gameObject.getId(), ids);
            }
        });
    }

    public static GameObject[] getAll(Filter<GameObject> filter) {
        Set<GameObject> objects = new LinkedHashSet<>();
        Object[][][] tiles = (Object[][][]) Engine.getReflectionEngine().getFieldValue("TJ", "E", null);
        if (tiles == null) {
            System.out.println("tiles null");
            return objects.toArray(new GameObject[objects.size()]);
        }
        int z = Game.getPlane();
        int baseX = Game.getBaseX(), baseY = Game.getBaseY();

        for (int x = 0; x < 104; x++) {
            for (int y = 0; y < 104; y++) {
                Object groundTile = tiles[z][x][y];
                if (groundTile != null) {

                    Object[] gameObjects = (Object[]) Engine.getReflectionEngine().getFieldValue("KA", "B", groundTile);
                    if (gameObjects != null) {
                        for (Object j : gameObjects) {
                            if (j != null) {
                                GameObject obj = new GameObject(j, x + baseX, y + baseY,
                                        z);
                                if (obj != null && (filter == null || filter.accept(obj)))
                                    objects.add(obj);
                            }
                        }
                    }

                }

            }
        }
        return objects.toArray(new GameObject[objects.size()]);
    }

    public static GameObject getNearest(Filter<GameObject> filter) {
        return getNearest(Players.getLocal().getLocation(), filter);
    }

    public static GameObject getNearest(Tile start, Filter<GameObject> filter) {
        GameObject closet = new GameObject(null, -1, -1, -1);
        int distance = 255;
        for (GameObject gameObject : getAll(filter)) {
            if (gameObject.isValid() && distance > gameObject.distanceTo(start)) {
                closet = gameObject;
                distance = gameObject.distanceTo(start);
            }
        }
        return closet;
    }

    public static GameObject getNearest(final int... ids) {
        return getNearest(Players.getLocal().getLocation(), new Filter<GameObject>() {
            @Override
            public boolean accept(GameObject gameObject) {
                return gameObject.isValid() && Utilities.inArray(gameObject.getId(), ids);
            }
        });
    }

    public static GameObject getNearest(final String... names) {
        return getNearest(Players.getLocal().getLocation(), new Filter<GameObject>() {
            @Override
            public boolean accept(GameObject groundItem) {
                return groundItem.isValid() && Utilities.inArray(groundItem.getName(), names);
            }
        });
    }


    public static GameObject nil() {
        return new GameObject(null, -1, -1, -1);
    }
}