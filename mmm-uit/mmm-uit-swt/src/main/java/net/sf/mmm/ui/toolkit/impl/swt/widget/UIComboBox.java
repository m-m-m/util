/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swt.widget;

import org.eclipse.swt.SWT;

import net.sf.mmm.ui.toolkit.api.model.UIListModelIF;
import net.sf.mmm.ui.toolkit.api.widget.UIComboBoxIF;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactory;
import net.sf.mmm.ui.toolkit.impl.swt.UISwtNode;
import net.sf.mmm.ui.toolkit.impl.swt.model.ComboBoxModelAdapter;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncComboAccess;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UIComboBoxIF} interface using SWT as
 * the UI toolkit.
 * 
 * @param <E>
 *        is the templated type of the elements that can be selected with this
 *        widget.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIComboBox<E> extends UIWidget implements UIComboBoxIF<E> {
    
    /** the synchron access to the combo */    
    private final SyncComboAccess syncAccess;

    /** the listener */
    private final ComboBoxModelAdapter modelAdapter;

    /** the model */
    private UIListModelIF<E> model;
    
    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the UIFactory instance.
     * @param parentObject
     *        is the parent of this object (may be <code>null</code>).
     * @param editableFlag
     *        is the (initial) value of the
     *        {@link net.sf.mmm.ui.toolkit.api.state.UIWriteEditableIF#isEditable() editable-flag}.
     * @param listModel
     *        is the model defining the the selectable elements.
     */
    public UIComboBox(UIFactory uiFactory, UISwtNode parentObject, boolean editableFlag,
            UIListModelIF<E> listModel) {

        super(uiFactory, parentObject);
        int style = SWT.DEFAULT;
        if (editableFlag) {
            style = style ^ SWT.READ_ONLY;
        }
        this.syncAccess = new SyncComboAccess(uiFactory, style);
        this.model = listModel;
        this.modelAdapter = new ComboBoxModelAdapter(this.syncAccess, this.model);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swt.UIComponent#getSyncAccess()
     * {@inheritDoc}
     */
    @Override
    public SyncComboAccess getSyncAccess() {
    
        return this.syncAccess;
    }
    
    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#getType()
     * {@inheritDoc}
     */
    public String getType() {

        return TYPE;
    }


    /**
     * @see net.sf.mmm.ui.toolkit.impl.swt.UIComponent#create()
     * {@inheritDoc}
     */
    @Override
    public void create() {
    
        super.create();
        this.modelAdapter.initialize();
    }
    
    /**
     * @see net.sf.mmm.ui.toolkit.api.widget.UIListWidgetIF#getModel()
     * {@inheritDoc}
     */
    public UIListModelIF<E> getModel() {

        return this.model;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadSelectionIndexIF#getSelectedIndex()
     * {@inheritDoc}
     */
    public int getSelectedIndex() {

        return this.syncAccess.getSelection();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionIndexIF#setSelectedIndex(int)
     * {@inheritDoc}
     */
    public void setSelectedIndex(int newIndex) {

        this.syncAccess.setText(this.modelAdapter.getModel().getElementAsString(newIndex));
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.widget.UIListWidgetIF#setModel(net.sf.mmm.ui.toolkit.api.model.UIListModelIF)
     * {@inheritDoc}
     */
    public void setModel(UIListModelIF<E> newModel) {

        this.modelAdapter.setModel(newModel);
        this.model = newModel;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteEditableIF#isEditable()
     * {@inheritDoc}
     */
    public boolean isEditable() {

        return this.syncAccess.hasStyle(SWT.READ_ONLY);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteEditableIF#setEditable(boolean)
     * {@inheritDoc}
     */
    public void setEditable(boolean editableFlag) {

    // not supported - maybe throw away comboBox and create new-one?
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadTextIF#getText()
     * {@inheritDoc}
     */
    public String getText() {

        return this.syncAccess.getText();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteTextIF#setText(java.lang.String)
     * {@inheritDoc}
     */
    public void setText(String text) {

        this.syncAccess.setText(text);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionValueIF#setSelectedValue(Object)
     * {@inheritDoc}
     */
    public void setSelectedValue(E newValue) {

        int index = this.model.getIndexOf(newValue);
        if (index >= 0) {
            setSelectedIndex(index);
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadSelectionValueIF#getSelectedValue()
     * {@inheritDoc}
     */
    public E getSelectedValue() {

        int index = getSelectedIndex();
        if (index >= 0) {
            return this.model.getElement(index);
        }
        return null;
    }

}
