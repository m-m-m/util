/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swt.widget;

import org.eclipse.swt.SWT;

import net.sf.mmm.ui.toolkit.api.composite.Orientation;
import net.sf.mmm.ui.toolkit.api.model.UIListModelIF;
import net.sf.mmm.ui.toolkit.api.widget.UISlideBarIF;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactory;
import net.sf.mmm.ui.toolkit.impl.swt.UISwtNode;
import net.sf.mmm.ui.toolkit.impl.swt.model.SlideBarModelAdapter;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncSliderAccess;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UISlideBarIF} interface using SWT as
 * the UI toolkit.
 * 
 * @param <E>
 *        is the templated type of the elements that can be selected with this
 *        widget.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UISlideBar<E> extends UIWidget implements UISlideBarIF<E> {

    /** the synchron access to the slider */
    private final SyncSliderAccess syncAccess;

    /** the model adapter */
    private final SlideBarModelAdapter modelAdapter;
    
    /** the orientation of the slider */
    private final Orientation orientation;

    /** the model of the slider */
    private UIListModelIF<E> model;
    
    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the UIFactory instance.
     * @param parentObject
     *        is the parent of this object (may be <code>null</code>).
     * @param sliderOrientation
     *        is the orientation of the slide-bar.
     * @param sliderModel
     *        is the list model containing the data. See
     *        {@link net.sf.mmm.ui.toolkit.base.model.UINumericRangeModel}.
     */
    public UISlideBar(UIFactory uiFactory, UISwtNode parentObject, Orientation sliderOrientation,
            UIListModelIF<E> sliderModel) {

        super(uiFactory, parentObject);
        this.orientation = sliderOrientation;
        this.model = sliderModel;
        int style = UIFactory.convertOrientation(sliderOrientation);
        this.syncAccess = new SyncSliderAccess(uiFactory, style);
        this.syncAccess.setMaximum(this.model.getElementCount());
        this.modelAdapter = new SlideBarModelAdapter(this.syncAccess);
        this.modelAdapter.setModel(this.model);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swt.UIComponent#getSyncAccess()
     * {@inheritDoc}
     */
    @Override
    public SyncSliderAccess getSyncAccess() {

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
     * @see net.sf.mmm.ui.toolkit.base.UIAbstractNode#doInitializeListener()
     * {@inheritDoc}
     */
    @Override
    protected boolean doInitializeListener() {

        this.syncAccess.addListener(SWT.Selection, createSwtListener());
        return true;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#getType()
     * {@inheritDoc}
     */
    public String getType() {

        return TYPE;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadOrientationIF#getOrientation()
     * {@inheritDoc}
     */
    public Orientation getOrientation() {

        return this.orientation;
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
     * @see net.sf.mmm.ui.toolkit.api.widget.UIListWidgetIF#getModel()
     * {@inheritDoc}
     */
    public UIListModelIF<E> getModel() {

        return this.model;
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

}
