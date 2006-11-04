/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swing.widget;

import net.sf.mmm.ui.toolkit.api.UINode;
import net.sf.mmm.ui.toolkit.api.composite.UIComposite;
import net.sf.mmm.ui.toolkit.api.widget.UIWidget;
import net.sf.mmm.ui.toolkit.impl.swing.AbstractUIComponent;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UIWidget} interface using Swing as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractUIWidget extends AbstractUIComponent implements UIWidget {

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the UIFactorySwing instance.
   * @param parentObject
   *        is the parent of this object (may be <code>null</code>).
   */
  public AbstractUIWidget(UIFactorySwing uiFactory, UINode parentObject) {

    super(uiFactory, parentObject);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIObject#isWidget()
   * 
   */
  @Override
  public boolean isWidget() {

    return true;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UINode#getParent()
   * 
   */
  @Override
  public UIComposite getParent() {

    return (UIComposite) super.getParent();
  }

}
