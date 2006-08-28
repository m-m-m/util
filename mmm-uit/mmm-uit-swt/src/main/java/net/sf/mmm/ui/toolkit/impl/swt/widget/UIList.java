/* $Id: UIList.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.impl.swt.widget;

import net.sf.mmm.ui.toolkit.api.model.UIListModelIF;
import net.sf.mmm.ui.toolkit.api.widget.UIListIF;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactory;
import net.sf.mmm.ui.toolkit.impl.swt.UISwtNode;
import net.sf.mmm.ui.toolkit.impl.swt.model.ListModelAdapter;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncListAccess;

import org.eclipse.swt.SWT;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UIListIF} interface using SWT as the
 * UI toolkit.
 * 
 * @param <E>
 *        is the templated type of the elements that can be selected with this
 *        widget.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIList<E> extends UIWidget implements UIListIF<E> {

    /** the model adapter */
    private final ListModelAdapter<E> modelAdapter;

    /** the sync access to the SWT list */
    private final SyncListAccess syncAccess;
    
    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the UIFactory instance.
     * @param parentObject
     *        is the parent of this object (may be <code>null</code>).
     * @param multiSelection -
     *        if <code>true</code> the user can select multiple, else ony one.
     * @param model
     *        is the model.
     */
    public UIList(UIFactory uiFactory, UISwtNode parentObject, boolean multiSelection,
            UIListModelIF<E> model) {

        super(uiFactory, parentObject);
        int style = SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL;
        if (multiSelection) {
            style |= SWT.MULTI;
        } else {
            style |= SWT.SINGLE;
        }
        this.syncAccess = new SyncListAccess(uiFactory, style);
        this.modelAdapter = new ListModelAdapter<E>(this.syncAccess, model);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#getType()
     * {@inheritDoc}
     */
    public String getType() {

        return TYPE;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swt.UIComponent#getSyncAccess()
     * {@inheritDoc}
     */
    @Override
    public SyncListAccess getSyncAccess() {
    
        return this.syncAccess;
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
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadMultiSelectionFlagIF#isMultiSelection()
     * {@inheritDoc}
     */
    public boolean isMultiSelection() {

        return this.syncAccess.hasStyle(SWT.MULTI);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.widget.UIListIF#getModel()
     * {@inheritDoc}
     */
    public UIListModelIF<E> getModel() {

        return this.modelAdapter.getModel();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.widget.UIListIF#setModel(net.sf.mmm.ui.toolkit.api.model.UIListModelIF)
     * {@inheritDoc}
     */
    public void setModel(UIListModelIF<E> newModel) {

        this.modelAdapter.setModel(newModel);
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

        this.syncAccess.setSelection(newIndex);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.widget.UIListIF#getSelectedIndices()
     * {@inheritDoc}
     */
    public int[] getSelectedIndices() {

        return this.syncAccess.getSelections();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionValueIF#setSelectedValue(Object)
     * {@inheritDoc}
     */
    public void setSelectedValue(E newValue) {

        int index = this.modelAdapter.getModel().getIndexOf(newValue);
        if (index != -1) {
            this.syncAccess.setSelection(index);
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadSelectionValueIF#getSelectedValue()
     * {@inheritDoc}
     */
    public E getSelectedValue() {

        int index = this.syncAccess.getSelection();
        if (index == -1) {
            return null;
        }
        return this.modelAdapter.getModel().getElement(index);
    }

}
