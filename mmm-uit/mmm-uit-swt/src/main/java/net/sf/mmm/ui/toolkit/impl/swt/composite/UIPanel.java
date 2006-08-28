/* $Id: UIPanel.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.impl.swt.composite;

import org.eclipse.swt.SWT;

import net.sf.mmm.ui.toolkit.api.UIComponentIF;
import net.sf.mmm.ui.toolkit.api.composite.LayoutConstraints;
import net.sf.mmm.ui.toolkit.api.composite.Orientation;
import net.sf.mmm.ui.toolkit.api.composite.UIPanelIF;
import net.sf.mmm.ui.toolkit.impl.swt.UIComponent;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactory;
import net.sf.mmm.ui.toolkit.impl.swt.UISwtNode;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncCompositeAccess;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.composite.UIPanelIF} interface using SWT as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIPanel extends UIMultiComposite implements UIPanelIF {

    /** the synchron access to the {@link org.eclipse.swt.widgets.Composite} */
    private final SyncCompositeAccess syncAccess;

    /** the layout manager for the panel */
    private final LayoutManager layoutManager;

    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the UIFactory instance.
     * @param parentObject
     *        is the parent of this object (may be <code>null</code>).
     * @param borderTitle
     *        is the title of the border or <code>null</code> for NO border.
     * @param orientation
     *        is the orientation for the layout of the panel.
     */
    public UIPanel(UIFactory uiFactory, UISwtNode parentObject, String borderTitle,
            Orientation orientation) {

        super(uiFactory, parentObject, borderTitle);
        this.layoutManager = new LayoutManager();
        this.layoutManager.setOrientation(orientation);
        this.syncAccess = new SyncCompositeAccess(uiFactory, SWT.NORMAL);
        this.syncAccess.setLayout(this.layoutManager);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UIPanelIF#addComponent(net.sf.mmm.ui.toolkit.api.UIComponentIF)
     * {@inheritDoc}
     */
    public void addComponent(UIComponentIF component) {

        addComponent(component, LayoutConstraints.DEFAULT);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UIPanelIF#addComponent(net.sf.mmm.ui.toolkit.api.UIComponentIF,
     *      net.sf.mmm.ui.toolkit.api.composite.LayoutConstraints)
     * {@inheritDoc}
     */
    public void addComponent(UIComponentIF component, LayoutConstraints constraints) {

        UIComponent c = (UIComponent) component;
        // c.getSyncAccess().setParentAccess(this.syncAccess);
        c.getSyncAccess().setLayoutData(constraints);
        c.setParent(this);
        this.components.add(c);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UIPanelIF#addComponent(net.sf.mmm.ui.toolkit.api.UIComponentIF,
     *      net.sf.mmm.ui.toolkit.api.composite.LayoutConstraints, int)
     * {@inheritDoc}
     */
    public void addComponent(UIComponentIF component, LayoutConstraints constraints, int position) {

        UIComponent c = (UIComponent) component;
        // c.getSyncAccess().setParentAccess(this.syncAccess);
        c.getSyncAccess().setLayoutData(constraints);
        c.setParent(this);
        this.components.add(position, c);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UIPanelIF#removeComponent(net.sf.mmm.ui.toolkit.api.UIComponentIF)
     * {@inheritDoc}
     */
    public void removeComponent(UIComponentIF component) {

        int index = this.components.indexOf(component);
        if (index >= 0) {
            removeComponent(index);
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UIPanelIF#removeComponent(int)
     * {@inheritDoc}
     */
    public void removeComponent(int index) {

        UIComponent c = this.components.remove(index);
        c.setParent(null);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#getType()
     * {@inheritDoc}
     */
    public String getType() {

        return TYPE;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swt.UIComponent#getActiveSyncAccess()
     * {@inheritDoc}
     */
    @Override
    public SyncCompositeAccess getActiveSyncAccess() {

        return this.syncAccess;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UIPanelIF#setOrientation(net.sf.mmm.ui.toolkit.api.composite.Orientation)
     * {@inheritDoc}
     */
    public void setOrientation(Orientation orientation) {

        this.layoutManager.setOrientation(orientation);
        this.syncAccess.layout();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UIPanelIF#getOrientation()
     * {@inheritDoc}
     */
    public Orientation getOrientation() {

        return this.layoutManager.getOrientation();
    }

}
