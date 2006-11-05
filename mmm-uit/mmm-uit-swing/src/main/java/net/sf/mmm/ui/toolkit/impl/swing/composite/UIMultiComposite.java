/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swing.composite;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.ui.toolkit.api.UIComponent;
import net.sf.mmm.ui.toolkit.api.UINode;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;

/**
 * This is the abstract base implementation of a
 * {@link net.sf.mmm.ui.toolkit.api.composite.UIComposite} that can contain
 * any number of components.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class UIMultiComposite extends AbstractUIComposite {

  /** the component list */
  protected final List<UIComponent> components;

  /**
   * The constructor.
   * 
   * @param uiFactory
   * @param parentObject
   */
  public UIMultiComposite(UIFactorySwing uiFactory, UINode parentObject) {

    super(uiFactory, parentObject);
    this.components = new ArrayList<UIComponent>();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.composite.UIComposite#getComponentCount()
   */
  public int getComponentCount() {

    return this.components.size();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.composite.UIComposite#getComponent(int)
   */
  public UIComponent getComponent(int index) {

    return this.components.get(index);
  }

}
