/* $Id$ */
package net.sf.mmm.ui.toolkit.base;

import net.sf.mmm.ui.toolkit.api.UIFactoryIF;
import net.sf.mmm.ui.toolkit.api.UIObjectIF;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.UIObjectIF} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class UIAbstractObject implements UIObjectIF {

    /** the factory instance */
    private UIFactoryIF factory;

    /** the {@link #getId() id} of the object */
    private String id;

    /** the static counter */
    private static int staticCounter = 0;

    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the
     *        {@link net.sf.mmm.ui.toolkit.api.UIObjectIF#getFactory() factory}
     *        instance.
     */
    public UIAbstractObject(UIFactoryIF uiFactory) {

        super();
        this.factory = uiFactory;
        this.id = "obj" + staticCounter++;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#getFactory()
     * {@inheritDoc}
     */
    public UIFactoryIF getFactory() {

        return this.factory;
    }

    /**
     * Override this method if you implement a window object.
     * 
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#isWindow()
     */
    public boolean isWindow() {

        return false;
    }

    /**
     * Override this method if you implement a widget object.
     * 
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#isWidget()
     */
    public boolean isWidget() {

        return false;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#getId()
     * {@inheritDoc}
     */
    public String getId() {

        return this.id;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#setId(java.lang.String)
     * {@inheritDoc}
     */
    public void setId(String newId) {

        this.id = newId;
    }

}