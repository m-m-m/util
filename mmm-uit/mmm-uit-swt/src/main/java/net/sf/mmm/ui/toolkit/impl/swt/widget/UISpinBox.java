/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swt.widget;

import org.eclipse.swt.SWT;

import net.sf.mmm.ui.toolkit.api.model.UIListModelIF;
import net.sf.mmm.ui.toolkit.api.widget.UISpinBoxIF;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactory;
import net.sf.mmm.ui.toolkit.impl.swt.UISwtNode;
import net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncControlAccess;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncMySpinnerAccess;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UISpinBoxIF} interface using SWT as
 * the UI toolkit.
 * 
 * @param <E>
 *        is the templated type of the list-elements.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UISpinBox<E> extends UIWidget implements UISpinBoxIF<E> {

    /** the native SWT widget */
    private final SyncMySpinnerAccess syncAccess;

    /** the model */
    private UIListModelIF<E> model;
    
    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the UIFactory instance.
     * @param parentObject
     *        is the parent of this object (may be <code>null</code>).
     * @param listModel
     *        is the model for the elements to select.
     */
    public UISpinBox(UIFactory uiFactory, UISwtNode parentObject, UIListModelIF<E> listModel) {

        super(uiFactory, parentObject);
        this.syncAccess = new SyncMySpinnerAccess(uiFactory, SWT.NONE, listModel);
        this.model = listModel;
        // this.spinBox.setMinimum(this.model.getMinWidthimumValue());
        // this.spinBox.setMaximum(this.model.getMaximumValue());
        // this.spinBox.setSelection(this.model.getMinimumValue());
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swt.UIComponent#getSyncAccess()
     * 
     */
    @Override
    public AbstractSyncControlAccess getSyncAccess() {
    
        return this.syncAccess;
    }
    
    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#getType()
     * 
     */
    public String getType() {

        return TYPE;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.widget.UISpinBoxIF#getModel()
     * 
     */
    public UIListModelIF<E> getModel() {

        return this.model;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionIndexIF#setSelectedIndex(int)
     * 
     */
    public void setSelectedIndex(int newIndex) {

        this.syncAccess.setSelectedIndex(newIndex);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadSelectionIndexIF#getSelectedIndex()
     * 
     */
    public int getSelectedIndex() {

        return this.syncAccess.getSelectedIndex();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionValueIF#setSelectedValue(Object)
     * 
     */
    public void setSelectedValue(E newValue) {

        int index = this.model.getIndexOf(newValue);
        if (index > -1) {
            setSelectedIndex(index);
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadSelectionValueIF#getSelectedValue()
     * 
     */
    public E getSelectedValue() {

        int i = getSelectedIndex();
        if (i >= 0) {
            return this.model.getElement(i);
        }
        return null;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.widget.UIListWidgetIF#setModel(net.sf.mmm.ui.toolkit.api.model.UIListModelIF)
     * 
     */
    public void setModel(UIListModelIF<E> newModel) {

        this.syncAccess.setModel(newModel);
        this.model = newModel;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteEditableIF#isEditable()
     * 
     */
    public boolean isEditable() {

        return this.syncAccess.isEditable();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteEditableIF#setEditable(boolean)
     * 
     */
    public void setEditable(boolean editableFlag) {

        this.syncAccess.setEditable(editableFlag);
    }

}
