/* $Id: UIWidgetIF.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.api.widget;

import net.sf.mmm.ui.toolkit.api.UIComponentIF;
import net.sf.mmm.ui.toolkit.api.composite.UICompositeIF;

/**
 * This is the abstract interface for a UI widget. Such a widget is an atomic
 * componet such as button, label, tree, table, etc.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIWidgetIF extends UIComponentIF {

    /**
     * The parent of a widget must be a {@link UICompositeIF composite}.
     * 
     * @see net.sf.mmm.ui.toolkit.api.UINodeIF#getParent()
     */
    public UICompositeIF getParent();
    
}