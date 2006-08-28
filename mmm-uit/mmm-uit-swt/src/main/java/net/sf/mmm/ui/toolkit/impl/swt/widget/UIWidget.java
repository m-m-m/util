/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swt.widget;

import net.sf.mmm.ui.toolkit.api.widget.UIWidgetIF;
import net.sf.mmm.ui.toolkit.impl.swt.UIComponent;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactory;
import net.sf.mmm.ui.toolkit.impl.swt.UISwtNode;
import net.sf.mmm.ui.toolkit.impl.swt.composite.UIComposite;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UIWidgetIF} interface using SWT as
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
    public UIWidget(UIFactory uiFactory, UISwtNode parentObject) {

        super(uiFactory, parentObject);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.base.UIAbstractObject#isWidget()
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
    public UIComposite getParent() {
    
        return (UIComposite) super.getParent();
    }
    
}
