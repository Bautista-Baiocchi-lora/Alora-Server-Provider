package org.alora.api.wrappers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ethan on 2/28/2018.
 */

public class Widget {

    private Object[] raw;
    private int index;

    public Widget(Object[] raw, int index) {
        this.raw = raw;
        this.index = index;
    }

    public WidgetChild[] getChildren() {
        List<WidgetChild> list = new ArrayList<>();
        if (raw == null)
            return list.toArray(new WidgetChild[list.size()]);
        for (int i = 0; i < raw.length; i++) {
            list.add(new WidgetChild(raw[i], i, index));
        }
        return list.toArray(new WidgetChild[list.size()]);
    }

    public WidgetChild getChild(int index) {
        if (raw == null || raw.length <= index) {
            return new WidgetChild(null, index, index);
        }
        return new WidgetChild(raw[index], index, index);
    }

    public boolean isValid() {
        return raw != null;
    }

    public int getIndex() {
        return index;
    }
}