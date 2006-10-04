/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swt;

import org.eclipse.swt.widgets.Listener;

import net.sf.mmm.ui.toolkit.base.UIAbstractNode;

/**
 * This is the abstract base implementation for all SWT
 * {@link net.sf.mmm.ui.toolkit.api.UINodeIF ui-nodes}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class UISwtNode extends UIAbstractNode {

    /** the ui factory */
    private final UIFactory factory;
    
    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the UIFactory instance.
     * @param parentObject
     *        is the parent of this object (may be <code>null</code>).
     */
    public UISwtNode(UIFactory uiFactory, UIAbstractNode parentObject) {

        super(uiFactory, parentObject);
        this.factory = uiFactory;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#getFactory()
     * 
     */
    @Override
    public UIFactory getFactory() {
    
        return this.factory;
    }
    
    /**
     * This method creates a new SWT listener that adapts the events.
     * 
     * @return the listener adapter.
     */
    protected Listener createSwtListener() {

        return new SwtListenerAdapter(this);
    }

}
