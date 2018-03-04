package org.alora.api.interactive;

import org.alora.api.wrappers.Widget;
import org.alora.api.wrappers.WidgetChild;
import org.alora.loader.Loader;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Ethan on 2/28/2018.
 */
public class Widgets {
    public static List<String> getOpenInterfaces() {
        return (List<String>) Loader.getReflectionEngine().getFieldValue("org.alora.api.callbacks.InterfacesCallback", "loadedInterfaces");
    }
    public static Widget[] get() {
        Object[][] widgets = (Object[][]) Loader.getReflectionEngine().getFieldValue("IH", "sZ", null);
        if (widgets == null)
            return new Widget[0];
        Widget[] children = new Widget[widgets.length];
        for (int i = 0; i < widgets.length; i++) {
            children[i] = new Widget(widgets[i], i);
        }
        return children;
    }

    public static WidgetChild get(int parent, int child) {
        Widget widgets = get(parent);
        if (widgets == null)
            return null;
        return widgets.getChild(child);
    }

    public static Widget get(int parent) {
        Object[][] widgets = (Object[][]) Loader.getReflectionEngine().getFieldValue("IH", "sZ", null);
        if (widgets.length == 0 || (widgets.length - 1) < parent || parent < 0)
            return new Widget(null, parent);
        return new Widget(widgets[parent], parent);
    }


    public static WidgetChild getWidgetWithText(String text) {
        Object[][] widgets = (Object[][]) Loader.getReflectionEngine().getFieldValue("IH", "sZ", null);
        for (int parentIndex = 0; parentIndex < widgets.length; parentIndex++) {
            Object[] children = widgets[parentIndex];
            if (children == null)
                continue;
            for (int childIndex = 0; childIndex < children.length; childIndex++) {
                Object child = children[childIndex];
                if (child != null) {
                    String widgetText = (String) Loader.getReflectionEngine().getFieldValue("IK", "XI", child);
                    if (widgetText != null && widgetText.contains(text) && getOpenInterfaces().contains(parentIndex)) {
                        return new WidgetChild(child, childIndex, parentIndex);
                    }
                }
            }
        }
        return null;
    }

    public static WidgetChild getWidgetWithAction(String action) {
        Object[][] widgets = (Object[][]) Loader.getReflectionEngine().getFieldValue("IH", "sZ", null);
        for (int parentIndex = 0; parentIndex < widgets.length; parentIndex++) {
            Object[] children = widgets[parentIndex];
            if (children == null)
                continue;
            for (int childIndex = 0; childIndex < children.length; childIndex++) {
                Object child = children[childIndex];
                if (child != null) {
                    String[] widgetText = (String[]) Loader.getReflectionEngine().getFieldValue("IK", "N", child);
                    if (widgetText != null && widgetText.length > 0 && getOpenInterfaces().contains(parentIndex)) {
                        for (String s : widgetText) {
                            if (s != null && s.contains(action)) {
                                return new WidgetChild(child, childIndex, parentIndex);
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public static boolean hasSpecialInteract(String str, int widget) {
        WidgetChild[] a1 = get(widget).getChildren();//219
        for (WidgetChild c : a1) {
            if (c != null && c.getChildren().length > 0) {
                for (WidgetChild c1 : c.getChildren()) {
                    if (c1 != null && c1.getText().length() > 0) {
                        if (c1.getText().contains(str)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static void getWidgetWithTexts() {
        Object[][] widgets = (Object[][]) Loader.getReflectionEngine().getFieldValue("IH", "sZ", null);

        for (int parentIndex = 0; parentIndex < widgets.length; parentIndex++) {
            Object[] children = widgets[parentIndex];
            if (children == null)
                continue;
            for (int childIndex = 0; childIndex < children.length; childIndex++) {
                Object child = children[childIndex];
                if (child != null) {
                    Class<?> c = Loader.getReflectionEngine().getClass("IK").getRespresentedClass();
                    for (Field f : c.getDeclaredFields()) {
                        if (f.getGenericType().getTypeName().equals("java.lang.String[]")) {
                            if (!f.toGenericString().contains("static")) {
                                String[] i = (String[]) Loader.getReflectionEngine().getFieldValue("IK", f.getName(), child);
                                System.out.println(Arrays.toString(i) + " : " + f.getName());
                            }
                        }
                    }

                }
            }
        }
    }

}
