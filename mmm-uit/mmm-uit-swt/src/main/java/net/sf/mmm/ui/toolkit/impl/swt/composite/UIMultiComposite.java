/* $Id: UIMultiComposite.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.impl.swt.composite;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.ui.toolkit.api.UINodeIF;
import net.sf.mmm.ui.toolkit.impl.swt.UIComponent;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactory;
import net.sf.mmm.ui.toolkit.impl.swt.UISwtNode;

/**
 * This class is an abstract base implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.composite.UICompositeIF} interface using SWT
 * as the UI toolkit. It is used for composites that have a list of multiple
 * child-components.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class UIMultiComposite extends UIComposite {

    /** the component list */
    protected final List<UIComponent> components;

    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the UIFactory instance.
     * @param parentObject
     *        is the parent of this object (may be <code>null</code>).
     * @param borderTitle
     *        is the title of the border or <code>null</code> for NO border.
     */
    public UIMultiComposite(UIFactory uiFactory, UISwtNode parentObject, String borderTitle) {

        super(uiFactory, parentObject, borderTitle);
        this.components = new ArrayList<UIComponent>();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UICompositeIF#getComponentCount()
     * {@inheritDoc}
     */
    public int getComponentCount() {

        return this.components.size();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UICompositeIF#getComponent(int)
     * {@inheritDoc}
     */
    public UIComponent getComponent(int index) {

        return this.components.get(index);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.base.UIAbstractNode#setParent(net.sf.mmm.ui.toolkit.api.UINodeIF)
     * {@inheritDoc}
     */
    @Override
    public void setParent(UINodeIF newParent) {
    
        super.setParent(newParent);
    }
    
}
