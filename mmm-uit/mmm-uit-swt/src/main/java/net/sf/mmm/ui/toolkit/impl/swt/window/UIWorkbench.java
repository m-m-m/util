/* $Id: UIWorkbench.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.impl.swt.window;

import net.sf.mmm.ui.toolkit.api.window.UIWorkbenchIF;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactory;

/**
 * This is the interface of the
 * {@link net.sf.mmm.ui.toolkit.api.window.UIWorkbenchIF} interface using SWT as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIWorkbench extends UIFrame implements UIWorkbenchIF {

    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the
     *        {@link net.sf.mmm.ui.toolkit.api.UIObjectIF#getFactory() factory}
     *        instance.
     */
    public UIWorkbench(UIFactory uiFactory) {

        super(uiFactory, null, true);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#getType()
     * {@inheritDoc}
     */
    public String getType() {

        return UIWorkbenchIF.TYPE;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.window.UIFrameIF#createFrame(java.lang.String, boolean)
     * {@inheritDoc}
     */
    public UIFrame createFrame(String title, boolean resizeable) {

        //TODO
        return super.createFrame(title, resizeable);
    }

}
