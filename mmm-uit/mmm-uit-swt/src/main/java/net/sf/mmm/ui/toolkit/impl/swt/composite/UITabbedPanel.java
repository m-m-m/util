/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swt.composite;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;

import net.sf.mmm.ui.toolkit.api.UIComponentIF;
import net.sf.mmm.ui.toolkit.api.composite.UITabbedPanelIF;
import net.sf.mmm.ui.toolkit.impl.swt.UIComponent;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactory;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncTabFolderAccess;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncTabItemAccess;


/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.composite.UITabbedPanelIF} interface using
 * SWT as the UI toolkit.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UITabbedPanel extends UIMultiComposite implements UITabbedPanelIF {

    /** the synchron access to the {@link org.eclipse.swt.widgets.TabFolder} */
    private final SyncTabFolderAccess syncAccess;
    
    /** the list of the tab-items */
    private final List<SyncTabItemAccess> tabItems;
    
    /**
     * The constructor.
     *
     * @param uiFactory
     *        is the UIFactory instance.
     * @param parentObject
     *        is the parent of this object (may be <code>null</code>).
     */
    public UITabbedPanel(UIFactory uiFactory, UIComponent parentObject) {

        super(uiFactory, parentObject, null);
        this.syncAccess = new SyncTabFolderAccess(uiFactory, SWT.DEFAULT);
        this.tabItems = new ArrayList<SyncTabItemAccess>();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UITabbedPanelIF#addComponent(net.sf.mmm.ui.toolkit.api.UIComponentIF, java.lang.String)
     * {@inheritDoc}
     */
    public void addComponent(UIComponentIF component, String title) {

        UIComponent c = (UIComponent) component;
        c.setParent(this);
        SyncTabItemAccess itemAccess = new SyncTabItemAccess(getFactory(), SWT.NONE);
        itemAccess.setText(title);
        itemAccess.setParentAccess(this.syncAccess);
        Control control = c.getSyncAccess().getSwtObject();
        if (control != null) {
            itemAccess.setControl(control);            
        }
        this.tabItems.add(itemAccess);
        this.components.add(c);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UITabbedPanelIF#addComponent(net.sf.mmm.ui.toolkit.api.UIComponentIF, java.lang.String, int)
     * {@inheritDoc}
     */
    public void addComponent(UIComponentIF component, String title, int position) {

        UIComponent c = (UIComponent) component;
        c.setParent(this);
        SyncTabItemAccess itemAccess = new SyncTabItemAccess(getFactory(), SWT.NONE, position);
        itemAccess.setText(title);
        itemAccess.setParentAccess(this.syncAccess);
        Control control = c.getSyncAccess().getSwtObject();
        if (control != null) {
            itemAccess.setControl(control);            
        }
        this.tabItems.add(position, itemAccess);
        this.components.add(position, c);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UITabbedPanelIF#removeComponent(net.sf.mmm.ui.toolkit.api.UIComponentIF)
     * {@inheritDoc}
     */
    public void removeComponent(UIComponentIF component) {

        int index = this.components.indexOf(component);
        if (index >= 0) {
            removeComponent(index);
        }        
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UITabbedPanelIF#removeComponent(int)
     * {@inheritDoc}
     */
    public void removeComponent(int position) {

        UIComponent component = this.components.remove(position);
        SyncTabItemAccess itemAccess = this.tabItems.remove(position);
        component.setParent(null);
        itemAccess.dispose();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#getType()
     * {@inheritDoc}
     */
    public String getType() {

        return TYPE;
    }
    
    /**
     * @see net.sf.mmm.ui.toolkit.impl.swt.composite.UIComposite#createChildren()
     * {@inheritDoc}
     */
    @Override
    protected void createChildren() {
    
        super.createChildren();
        int len = this.tabItems.size();
        for (int i = 0; i < len; i++) {
            UIComponent component = this.components.get(i);
            Control control = component.getSyncAccess().getSwtObject();
            SyncTabItemAccess itemAccess = this.tabItems.get(i);
            itemAccess.setControl(control);
            itemAccess.create();
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swt.UIComponent#getActiveSyncAccess()
     * {@inheritDoc}
     */
    @Override
    public SyncTabFolderAccess getActiveSyncAccess() {
    
        return this.syncAccess;
    }
    
}
