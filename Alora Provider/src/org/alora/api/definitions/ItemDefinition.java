package org.alora.api.definitions;

import org.bot.Engine;

import java.lang.reflect.Method;

/**
 * Created by Ethan on 3/2/2018.
 */
public class ItemDefinition {

    private Object raw;
    private String name;
    private String[] options;

    public ItemDefinition(int id) {
        raw = getMethodValue(id);
        name = (String) Engine.getReflectionEngine().getFieldValue("SE", "K", raw);
//            options = (String[]) Engine.getReflectionEngine().getFieldValue("SE", "h", null);
    }

    public String getName() {
        return name;
    }

    public boolean isValid() {
        return name != null;
    }

    public String[] getOptions() {
        return options;
    }

    private Object getMethodValue(int id) {
        try {
            Class<?> clazz = Engine.getReflectionEngine().getClass("SE").getRespresentedClass();
            for (Method m : clazz.getDeclaredMethods()) {
                if (m.getName().equals("B")) {
                    if (m.getParameterCount() == 1) {
                        if (m.getReturnType().toGenericString().equals("final class SE")) {
                            m.setAccessible(true);
                            return m.invoke(null, id);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}