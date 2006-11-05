/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swt.widget;

import net.sf.mmm.ui.toolkit.api.widget.UIWidget;
import net.sf.mmm.ui.toolkit.impl.swt.AbstractUIComponent;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.UISwtNode;
import net.sf.mmm.ui.toolkit.impl.swt.composite.AbstractUIComposite;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UIWidget} interface using SWT as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractUIWidget extends AbstractUIComponent implements UIWidget {

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the UIFactorySwt instance.
   * @param parentObject
   *        is the parent of this object (may be <code>null</code>).
   */
  public AbstractUIWidget(UIFactorySwt uiFactory, UISwtNode parentObject) {

    super(uiFactory, parentObject);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.base.AbstractUIObject#isWidget()
   */
  @Override
  public boolean isWidget() {

    return true;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UINode#getParent()
   */
  @Override
  public AbstractUIComposite getParent() {

    return (AbstractUIComposite) super.getParent();
  }

}
