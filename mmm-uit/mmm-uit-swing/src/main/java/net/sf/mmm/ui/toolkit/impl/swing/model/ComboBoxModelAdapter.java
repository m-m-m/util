/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swing.model;

import javax.swing.MutableComboBoxModel;

import net.sf.mmm.ui.toolkit.api.model.UIListModelIF;

/**
 * This class adapts a {@link net.sf.mmm.ui.toolkit.api.model.UIListModelIF} to
 * a swing {@link javax.swing.ComboBoxModel}.
 * 
 * @param <E>
 *        is the templated type of the elements that can be selected.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ComboBoxModelAdapter<E> extends ListModelAdapter<E> implements MutableComboBoxModel {

    /** uid for serialization */
    private static final long serialVersionUID = 7187832628689261207L;
    
    /** the selected item */
    private Object selectedItem;
    
    /**
     * The constructor.
     * 
     * @param listModel
     */
    public ComboBoxModelAdapter(UIListModelIF<E> listModel) {

        super(listModel);
    }

    /**
     * @see javax.swing.ComboBoxModel#setSelectedItem(java.lang.Object)
     * 
     */
    public void setSelectedItem(Object anItem) {

        this.selectedItem = anItem;
    }

    /**
     * @see javax.swing.ComboBoxModel#getSelectedItem()
     * 
     */
    public Object getSelectedItem() {

        return this.selectedItem;
    }

    /**
     * @see javax.swing.MutableComboBoxModel#addElement(java.lang.Object)
     * 
     */
    public void addElement(Object obj) {

        // TODO Auto-generated method stub

    }

    /**
     * @see javax.swing.MutableComboBoxModel#removeElement(java.lang.Object)
     * 
     */
    public void removeElement(Object obj) {

        // TODO Auto-generated method stub
        
    }

    /**
     * @see javax.swing.MutableComboBoxModel#insertElementAt(java.lang.Object, int)
     * 
     */
    public void insertElementAt(Object obj, int index) {

        // TODO Auto-generated method stub
        
    }

    /**
     * @see javax.swing.MutableComboBoxModel#removeElementAt(int)
     * 
     */
    public void removeElementAt(int index) {

        // TODO Auto-generated method stub
        
    }

}
