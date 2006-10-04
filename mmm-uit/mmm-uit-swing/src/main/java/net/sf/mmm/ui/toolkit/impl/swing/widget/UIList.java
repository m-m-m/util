/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swing.widget;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import net.sf.mmm.ui.toolkit.api.UINodeIF;
import net.sf.mmm.ui.toolkit.api.model.UIListModelIF;
import net.sf.mmm.ui.toolkit.api.widget.UIListIF;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactory;
import net.sf.mmm.ui.toolkit.impl.swing.model.ListModelAdapter;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UIListIF} interface using Swing as
 * the UI toolkit.
 * 
 * @param <E>
 *        is the templated type of the elements that can be selected with this
 *        widget.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIList<E> extends UIWidget implements UIListIF<E> {

    /** the swing scroll pane */
    private JScrollPane scrollPanel;

    /** the swing list */
    private JList list;

    /** the list model adapter */
    private ListModelAdapter<E> modelAdapter;

    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the UIFactory instance.
     * @param parentObject
     *        is the parent of this object (may be <code>null</code>).
     */
    public UIList(UIFactory uiFactory, UINodeIF parentObject) {

        super(uiFactory, parentObject);
        this.list = new JList();
        this.scrollPanel = new JScrollPane(this.list);
        this.scrollPanel.setMinimumSize(new Dimension(40, 40));
        this.modelAdapter = null;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#getType()
     * 
     */
    public String getType() {

        return TYPE;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadMultiSelectionFlagIF#isMultiSelection()
     * 
     */
    public boolean isMultiSelection() {

        int mode = this.list.getSelectionMode();
        return (mode != ListSelectionModel.SINGLE_SELECTION);
    }

    /**
     * This method sets the selection mode of this list.
     * 
     * @param multiSelection -
     *        if <code>true</code> the user can select multiple items, else
     *        ony one.
     */
    public void setMultiSelection(boolean multiSelection) {

        int mode = ListSelectionModel.SINGLE_SELECTION;
        if (multiSelection) {
            mode = ListSelectionModel.MULTIPLE_INTERVAL_SELECTION;
        }
        this.list.setSelectionMode(mode);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.widget.UIListIF#getModel()
     * 
     */
    public UIListModelIF<E> getModel() {

        if (this.modelAdapter == null) {
            return null;
        } else {
            return this.modelAdapter.getModel();
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.widget.UIListIF#setModel(net.sf.mmm.ui.toolkit.api.model.UIListModelIF)
     * 
     */
    public void setModel(UIListModelIF<E> newModel) {

        if (this.modelAdapter != null) {
            this.modelAdapter.dispose();
        }
        this.modelAdapter = new ListModelAdapter<E>(newModel);
        this.list.setModel(this.modelAdapter);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadSelectionIndexIF#getSelectedIndex()
     * 
     */
    public int getSelectedIndex() {

        return this.list.getSelectedIndex();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionIndexIF#setSelectedIndex(int)
     * 
     */
    public void setSelectedIndex(int newIndex) {
    
        this.list.setSelectedIndex(newIndex);
    }
    
    /**
     * @see net.sf.mmm.ui.toolkit.api.widget.UIListIF#getSelectedIndices()
     * 
     */
    public int[] getSelectedIndices() {

        return this.list.getSelectedIndices();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swing.UIComponent#getSwingComponent()
     * 
     */
    public JComponent getSwingComponent() {

        return this.scrollPanel;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swing.UIComponent#getActiveSwingComponent()
     * 
     */
    protected JComponent getActiveSwingComponent() {

        return this.list;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionValueIF#setSelectedValue(Object)
     * 
     */
    public void setSelectedValue(E newValue) {

        int newIndex = this.modelAdapter.getModel().getIndexOf(newValue);
        if (newIndex != -1) {
            setSelectedIndex(newIndex);
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadSelectionValueIF#getSelectedValue()
     * 
     */
    public E getSelectedValue() {
        
        int index = this.list.getSelectedIndex();
        if (index == -1) {
            return null;
        }
        return this.modelAdapter.getModel().getElement(index);
    }

}