/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swing.widget;

import javax.swing.JComboBox;
import javax.swing.JComponent;

import net.sf.mmm.ui.toolkit.api.UINodeIF;
import net.sf.mmm.ui.toolkit.api.model.UIListModelIF;
import net.sf.mmm.ui.toolkit.api.widget.UIComboBoxIF;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactory;
import net.sf.mmm.ui.toolkit.impl.swing.model.ComboBoxModelAdapter;

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
public class UIComboBox<E> extends UIWidget implements UIComboBoxIF<E> {

    /** the swing combo-box */
    private final JComboBox comboBox;

    /** the combo-box model adapter */
    private ComboBoxModelAdapter<E> modelAdapter;

    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the UIFactory instance.
     * @param parentObject
     *        is the parent of this object (may be <code>null</code>).
     */
    public UIComboBox(UIFactory uiFactory, UINodeIF parentObject) {

        super(uiFactory, parentObject);
        this.comboBox = new JComboBox();
        this.modelAdapter = null;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swing.UIComponent#getSwingComponent()
     * {@inheritDoc}
     */
    @Override
    public JComponent getSwingComponent() {

        return this.comboBox;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.widget.UIListWidgetIF#getModel()
     * {@inheritDoc}
     */
    public UIListModelIF<E> getModel() {

        return this.modelAdapter.getModel();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.widget.UIListWidgetIF#setModel(net.sf.mmm.ui.toolkit.api.model.UIListModelIF)
     * {@inheritDoc}
     */
    public void setModel(UIListModelIF<E> newModel) {

        if (this.modelAdapter != null) {
            this.modelAdapter.dispose();
        }
        this.modelAdapter = new ComboBoxModelAdapter<E>(newModel);
        this.comboBox.setModel(this.modelAdapter);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadSelectionIndexIF#getSelectedIndex()
     * {@inheritDoc}
     */
    public int getSelectedIndex() {

        return this.comboBox.getSelectedIndex();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionIndexIF#setSelectedIndex(int)
     * {@inheritDoc}
     */
    public void setSelectedIndex(int newIndex) {

        this.comboBox.setSelectedIndex(newIndex);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#getType()
     * {@inheritDoc}
     */
    public String getType() {

        return TYPE;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteEditableIF#isEditable()
     * {@inheritDoc}
     */
    public boolean isEditable() {

        return this.comboBox.isEditable();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteEditableIF#setEditable(boolean)
     * {@inheritDoc}
     */
    public void setEditable(boolean editableFlag) {

        this.comboBox.setEditable(editableFlag);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadTextIF#getText()
     * {@inheritDoc}
     */
    public String getText() {

        return (String) this.comboBox.getSelectedItem();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteTextIF#setText(java.lang.String)
     * {@inheritDoc}
     */
    public void setText(String text) {

        this.comboBox.setSelectedItem(text);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionValueIF#setSelectedValue(Object)
     * {@inheritDoc}
     */
    public void setSelectedValue(E newValue) {

        int newIndex = this.modelAdapter.getModel().getIndexOf(newValue);
        if (newIndex != -1) {
            this.comboBox.setSelectedIndex(newIndex);
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadSelectionValueIF#getSelectedValue()
     * {@inheritDoc}
     */
    public E getSelectedValue() {

        int index = this.comboBox.getSelectedIndex();
        if (index == -1) {
            return null;
        }
        return this.modelAdapter.getModel().getElement(index);
    }

}
