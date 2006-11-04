/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swing.composite;

import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;

import net.sf.mmm.ui.toolkit.api.UINode;
import net.sf.mmm.ui.toolkit.api.composite.UIComposite;
import net.sf.mmm.ui.toolkit.impl.swing.AbstractUIComponent;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.UIComponent} interface using Swing as the
 * UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractUIComposite extends AbstractUIComponent implements UIComposite {

  /** the titled border of this composite */
  private TitledBorder border;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the UIFactorySwing instance.
   * @param parentObject
   *        is the parent of this object (may be <code>null</code>).
   */
  public AbstractUIComposite(UIFactorySwing uiFactory, UINode parentObject) {

    super(uiFactory, parentObject);
    this.border = null;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIComponent#setEnabled(boolean)
   * 
   */
  public void setEnabled(boolean enabled) {

    super.setEnabled(enabled);
    for (int i = 0; i < getComponentCount(); i++) {
      getComponent(i).setEnabled(enabled);
    }
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteBorderTitle#setBorderTitle(java.lang.String)
   * 
   */
  public void setBorderTitle(String borderTitle) {

    if (borderTitle == null) {
      if (this.border != null) {
        getSwingComponent().setBorder(BorderFactory.createEmptyBorder());
        this.border = null;
      }
    } else {
      if (this.border == null) {
        this.border = BorderFactory.createTitledBorder(borderTitle);
        getSwingComponent().setBorder(this.border);
      } else {
        this.border.setTitle(borderTitle);
      }
    }
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteBorderTitle#getBorderTitle()
   * 
   */
  public String getBorderTitle() {

    if (this.border == null) {
      return null;
    } else {
      return this.border.getTitle();
    }
  }

  /**
   * @see net.sf.mmm.ui.toolkit.base.AbstractUINode#refresh()
   * 
   */
  @Override
  public void refresh() {

    super.refresh();
    int count = getComponentCount();
    for (int componentIndex = 0; componentIndex < count; componentIndex++) {
      getComponent(componentIndex).refresh();
    }
  }
}
