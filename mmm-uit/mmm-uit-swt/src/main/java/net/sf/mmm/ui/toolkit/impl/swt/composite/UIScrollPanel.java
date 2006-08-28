/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swt.composite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;

import net.sf.mmm.ui.toolkit.api.composite.UICompositeIF;
import net.sf.mmm.ui.toolkit.api.composite.UIScrollPanelIF;
import net.sf.mmm.ui.toolkit.impl.swt.UIComponent;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactory;
import net.sf.mmm.ui.toolkit.impl.swt.UISwtNode;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncScrolledCompositeAccess;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.composite.UIScrollPanelIF} interface using
 * SWT as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIScrollPanel extends UIComposite implements UIScrollPanelIF {

    /** the synchron access to the scrolled composite */
    private final SyncScrolledCompositeAccess syncAccess;

    /** the child component */
    private UIComponent childComponent;

    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the UIFactory instance.
     * @param parentObject
     *        is the parent of this object (may be <code>null</code>).
     */
    public UIScrollPanel(UIFactory uiFactory, UISwtNode parentObject) {

        super(uiFactory, parentObject, null);
        int style = SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER;
        this.syncAccess = new SyncScrolledCompositeAccess(uiFactory, style);
        this.childComponent = null;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UIScrollPanelIF#setComponent(net.sf.mmm.ui.toolkit.api.composite.UICompositeIF)
     * {@inheritDoc}
     */
    public void setComponent(UICompositeIF child) {

        if (this.childComponent != null) {
            this.childComponent.setParent(null);
        }
        this.childComponent = (UIComponent) child;
        if (this.childComponent != null) {
            this.childComponent.setParent(this);
            // this.childComponent.getSwtControl().setParent(this.scrollPanel);
            Control childControl = this.childComponent.getSyncAccess().getSwtObject();
            if (childControl != null) {
                this.syncAccess.setContent(this.childComponent.getSyncAccess().getSwtObject());
                update();
            }
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swt.UIComponent#create()
     * {@inheritDoc}
     */
    @Override
    public void create() {

        super.create();
        if (this.childComponent != null) {
            this.syncAccess.setContent(this.childComponent.getSyncAccess().getSwtObject());
            update();
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UICompositeIF#getComponentCount()
     * {@inheritDoc}
     */
    public int getComponentCount() {

        return 1;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UICompositeIF#getComponent(int)
     * {@inheritDoc}
     */
    public UIComponent getComponent(int index) {

        if (index == 0) {
            return this.childComponent;
        }
        throw new ArrayIndexOutOfBoundsException(index);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#getType()
     * {@inheritDoc}
     */
    public String getType() {

        return TYPE;
    }

    /**
     * This method updates the required size.
     */
    public void update() {

        if ((this.childComponent != null) && (this.childComponent.getSyncAccess().getSwtObject() != null)) {
            Point size = this.childComponent.getSyncAccess().computeSize(SWT.DEFAULT, SWT.DEFAULT);
            this.syncAccess.setMinimumSize(size);
        }
        super.update();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swt.composite.UIComposite#getActiveSyncAccess()
     * {@inheritDoc}
     */
    @Override
    public SyncScrolledCompositeAccess getActiveSyncAccess() {

        return this.syncAccess;
    }
}
