/* $Id: UICompositeIF.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.api.composite;

import net.sf.mmm.ui.toolkit.api.UIComponentIF;
import net.sf.mmm.ui.toolkit.api.state.UIReadBorderTitleIF;

/**
 * This is the interface of a UI component that contains other UI components.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UICompositeIF extends UIComponentIF, UIReadBorderTitleIF {

    /**
     * This method gets the number of sub-components in this composite
     * component.
     * 
     * @return the component count.
     */
    int getComponentCount();

    /**
     * This method gets the sub-component at the given index.
     * 
     * @param index is the position of the requested sub-component.
     * @return the component at the given index.
     */
    UIComponentIF getComponent(int index);
    
}