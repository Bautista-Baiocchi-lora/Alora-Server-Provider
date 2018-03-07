package org.alora.api.interfaces;

/**
 * Created by Ethan on 2/27/2018.
 */

import org.alora.api.wrappers.Tile;

public interface Locatable {

    int distanceTo();

    int distanceTo(Locatable locatable);

    int distanceTo(Tile tile);

    Tile getLocation();

    boolean canReach();

}
