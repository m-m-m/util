/* $Id: UIWidget.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.impl.swing.widget;

import net.sf.mmm.ui.toolkit.api.UINodeIF;
import net.sf.mmm.ui.toolkit.api.composite.UICompositeIF;
import net.sf.mmm.ui.toolkit.api.widget.UIWidgetIF;
import net.sf.mmm.ui.toolkit.impl.swing.UIComponent;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactory;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UIWidgetIF} interface using Swing as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class UIWidget extends UIComponent implements UIWidgetIF {

    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the UIFactory instance.
     * @param parentObject
     *        is the parent of this object (may be <code>null</code>).
     */
    public UIWidget(UIFactory uiFactory, UINodeIF parentObject) {

        super(uiFactory, parentObject);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#isWidget()
     * {@inheritDoc}
     */
    @Override
    public boolean isWidget() {

        return true;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UINodeIF#getParent()
     * {@inheritDoc}
     */
    @Override
    public UICompositeIF getParent() {
    
        return (UICompositeIF) super.getParent();
    }
    
}
