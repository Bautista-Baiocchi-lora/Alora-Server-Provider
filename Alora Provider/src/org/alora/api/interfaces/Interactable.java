package org.alora.api.interfaces;


/**
 * Created by Ethan on 2/27/2018.
 */
public interface Interactable {

    boolean interact(final int menuIndex);

    boolean interact(final int menuIndex, String entityName);

}