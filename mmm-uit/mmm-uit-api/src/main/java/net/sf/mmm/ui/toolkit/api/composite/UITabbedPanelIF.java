/* $Id: UITabbedPanelIF.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.api.composite;

import net.sf.mmm.ui.toolkit.api.UIComponentIF;

/**
 * TODO This type ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UITabbedPanelIF extends UICompositeIF {

    /** the type of this object */
    String TYPE = "TabbedPanel";

    /**
     * This method adds the given component as new tab on the ride of all
     * existing tabs.
     * 
     * @param component
     *        is the component to add. The given component instance must be
     *        created by the same factory.
     * @param title
     *        is the title that will be displayed in the tab.
     */
    void addComponent(UIComponentIF component, String title);

    /**
     * This method adds the given component as new tab on the ride of all
     * existing tabs.
     * 
     * @param component
     *        is the component to add. The given component instance must be
     *        created by the same factory.
     * @param title
     *        is the title that will be displayed in the tab.
     * @param position
     *        is the index position where the given component will be inserted.
     */
    void addComponent(UIComponentIF component, String title, int position);

    /**
     * This method removes the given component. It will have no effect, if the
     * component has not been added to this panel.
     * 
     * @param component
     *        is the component to remove.
     */
    void removeComponent(UIComponentIF component);

    /**
     * This method removes the component at the given position.
     * 
     * @see UICompositeIF#getComponent(int)
     * 
     * @param position
     *        is the index position of the component to remove.
     */
    void removeComponent(int position);

}
