/* $Id: UIListWidgetIF.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.api.widget;

import net.sf.mmm.ui.toolkit.api.model.UIListModelIF;
import net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionIndexIF;
import net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionValueIF;

/**
 * This is the abstract interface for a UI list widget. Such a widget presents a
 * list. The way the list is presented is not further specified here.
 * @see net.sf.mmm.ui.toolkit.api.widget.UIListIF
 * @see net.sf.mmm.ui.toolkit.api.widget.UIComboBoxIF
 * @see net.sf.mmm.ui.toolkit.api.widget.UISlideBarIF
 * 
 * @param <E>
 *        is the templated type of the elements that can be selected with this
 *        widget.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIListWidgetIF<E> extends UIWidgetIF, UIWriteSelectionIndexIF, UIWriteSelectionValueIF<E> {

    /**
     * This method gets the model of this list.
     * 
     * @return the list model or <code>null</code> if the model is not set.
     */
    UIListModelIF<E> getModel();

    /**
     * This method sets the model of this list.
     * 
     * @param newModel
     *        is the new list model to set.
     */
    void setModel(UIListModelIF<E> newModel);

}
