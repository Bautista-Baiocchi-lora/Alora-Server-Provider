package org.alora.api.data;

import org.alora.api.wrappers.Tile;
import org.bot.ui.screens.clientframe.menu.logger.Logger;

import java.awt.*;

/**
 * Created by Ethan on 3/2/2018.
 */
public class Walking {
    private static final int localXPacket = 46;
    private static final int localYPacket = 53;
    private static final int localX = 648;
    private static final int localY = 83;
    private static boolean addX = false;
    private static boolean addY = false;

    public static void walkTo(Tile t) {

    }

    private static Point getPacketPoint(Point point) {
        int x;
        int y;
        if (localXPacket > point.x) {
            x = localXPacket - point.x;
            addX = false;
        } else {
            x = point.x - localXPacket;
            addX = true;
        }
        if (localYPacket > point.y) {
            y = localYPacket - point.y;
            addY = false;
        } else {
            y = point.y - localYPacket;
            addY = true;
        }
        Logger.log(x + " : " + y);
        return new Point(x, y);
    }
}
