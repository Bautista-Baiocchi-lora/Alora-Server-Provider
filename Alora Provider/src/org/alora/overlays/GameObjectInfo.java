package org.alora.overlays;


import org.alora.api.data.Game;
import org.alora.api.interactive.GameObjects;
import org.alora.api.interactive.GroundItems;
import org.alora.api.interactive.Players;
import org.alora.api.wrappers.GameObject;
import org.alora.api.wrappers.GroundItem;
import org.ubot.bot.component.screen.ScreenOverlay;

import java.awt.*;

/**
 * Created by Ethan on 2/27/2018.
 */
public class GameObjectInfo extends ScreenOverlay<GameObject> {
    private boolean b = false;

    public GameObjectInfo() {
        super("Objects");
    }

    @Override
    public GameObject[] elements() {
        return GameObjects.getAll();
    }


    @Override
    public void render(Graphics2D graphics) {
        if (!Game.isLoggedIn())
            return;
        if (!b) {
   /*         for(Widget w : Widgets.get()) {
                if(w != null && w.isValid()) {
                    WidgetChild[] wc = w.getChildren();
                    if(wc.length > 0) {
                    for(WidgetChild child : wc) {
                        if (child != null) {
                            if (child.getHash() == 44171292) {
                                System.out.println(child.getParent() + " : "+child.getIndex());
                                break;
                            }
                        }
                        if(child.getChildren().length > 0) {
                            for (WidgetChild child2 : child.getChildren()) {
                                if (child2 != null) {
                                    if (child2.getHash() == 44171292) {
                                        System.out.println("AY2: ");
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    }
                }
            }*/
            for (GroundItem g : GroundItems.getAll()) {
                System.out.println(g.getLocation() + " : " + g.distanceTo(Players.getLocal()));
            }
            b = true;
        }
    }
}