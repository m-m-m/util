/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swing.widget;

import javax.swing.JComponent;
import javax.swing.JSpinner;

import net.sf.mmm.ui.toolkit.api.UINodeIF;
import net.sf.mmm.ui.toolkit.api.model.UIListModelIF;
import net.sf.mmm.ui.toolkit.api.widget.UISpinBoxIF;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactory;
import net.sf.mmm.ui.toolkit.impl.swing.model.SpinnerModelAdapter;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UISpinBoxIF} interface using Swing as
 * the UI toolkit.
 * 
 * @param <E>
 *        is the templated type of the elements that can be selected with this
 *        widget.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UISpinBox<E> extends UIWidget implements UISpinBoxIF<E> {

    /** the native Swing widget */
    private final JSpinner spinBox;

    /** the model used */
    private final SpinnerModelAdapter<E> modelAdapter;

    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the UIFactory instance.
     * @param parentObject
     *        is the parent of this object (may be <code>null</code>).
     * @param model
     *        is the model for this spin-box.
     */
    public UISpinBox(UIFactory uiFactory, UINodeIF parentObject, UIListModelIF<E> model) {

        super(uiFactory, parentObject);
        this.modelAdapter = new SpinnerModelAdapter<E>(model);
        this.spinBox = new JSpinner(this.modelAdapter);

    }

    /**
     * @see net.sf.mmm.ui.toolkit.base.UIAbstractNode#doInitializeListener()
     * 
     */
    @Override
    protected boolean doInitializeListener() {

        this.spinBox.addChangeListener(createChangeListener());
        return true;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swing.UIComponent#getSwingComponent()
     * 
     */
    public @Override
    JComponent getSwingComponent() {

        return this.spinBox;
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

        return this.modelAdapter.getModel();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionIndexIF#setSelectedIndex(int)
     * 
     */
    public void setSelectedIndex(int newIndex) {

        this.modelAdapter.setSelectedIndex(newIndex);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadSelectionIndexIF#getSelectedIndex()
     * 
     */
    public int getSelectedIndex() {

        return this.modelAdapter.getSelectedIndex();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionValueIF#setSelectedValue(Object)
     * 
     */
    public void setSelectedValue(E newValue) {

        this.modelAdapter.setSelectedValue(newValue);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadSelectionValueIF#getSelectedValue()
     * 
     */
    public E getSelectedValue() {

        return this.modelAdapter.getSelectedValue();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteEditableIF#isEditable()
     * 
     */
    public boolean isEditable() {

        // TODO Auto-generated method stub
        return false;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteEditableIF#setEditable(boolean)
     * 
     */
    public void setEditable(boolean editableFlag) {

        //this.spinBox.getEditor().set
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.widget.UIListWidgetIF#setModel(net.sf.mmm.ui.toolkit.api.model.UIListModelIF)
     * 
     */
    public void setModel(UIListModelIF<E> newModel) {

        this.modelAdapter.setModel(newModel);
    }

}
