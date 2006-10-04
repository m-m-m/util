/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swing.composite;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.ui.toolkit.api.UIComponentIF;
import net.sf.mmm.ui.toolkit.api.UINodeIF;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactory;

/**
 * This is the abstract base implementation of a
 * {@link net.sf.mmm.ui.toolkit.api.composite.UICompositeIF} that can contain
 * any number of components.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class UIMultiComposite extends UIComposite {

    /** the component list */
    protected final List<UIComponentIF> components;

    /**
     * The constructor.
     * 
     * @param uiFactory
     * @param parentObject
     */
    public UIMultiComposite(UIFactory uiFactory, UINodeIF parentObject) {

        super(uiFactory, parentObject);
        this.components = new ArrayList<UIComponentIF>();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UICompositeIF#getComponentCount()
     * 
     */
    public int getComponentCount() {

        return this.components.size();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UICompositeIF#getComponent(int)
     * 
     */
    public UIComponentIF getComponent(int index) {

        return this.components.get(index);
    }

}
