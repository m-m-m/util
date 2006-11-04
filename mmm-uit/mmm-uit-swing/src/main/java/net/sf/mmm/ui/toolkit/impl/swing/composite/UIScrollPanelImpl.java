/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swing.composite;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

import net.sf.mmm.ui.toolkit.api.UIComponent;
import net.sf.mmm.ui.toolkit.api.UINode;
import net.sf.mmm.ui.toolkit.api.composite.UIComposite;
import net.sf.mmm.ui.toolkit.api.composite.UIScrollPanel;
import net.sf.mmm.ui.toolkit.impl.swing.AbstractUIComponent;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.composite.UIScrollPanel} interface using
 * Swing as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIScrollPanelImpl extends AbstractUIComposite implements UIScrollPanel {

  /** the scroll-panel or <code>null</code> if this is a regular panel */
  private final JScrollPane scrollPanel;

  /** the child component */
  private AbstractUIComponent childComponent;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the UIFactorySwing instance.
   * @param parentObject
   *        is the parent of this object (may be <code>null</code>).
   */
  public UIScrollPanelImpl(UIFactorySwing uiFactory, UINode parentObject) {

    super(uiFactory, parentObject);
    this.scrollPanel = new JScrollPane();
    this.childComponent = null;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swing.AbstractUIComponent#getSwingComponent()
   * 
   */
  @Override
  public JComponent getSwingComponent() {

    return this.scrollPanel;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.composite.UIScrollPanel#setComponent(net.sf.mmm.ui.toolkit.api.composite.UIComposite)
   * 
   */
  public void setComponent(UIComposite child) {

    if (this.childComponent != null) {
      setParent(this.childComponent, null);
    }
    this.childComponent = (AbstractUIComponent) child;
    if (this.childComponent != null) {
      this.scrollPanel.setViewportView(this.childComponent.getSwingComponent());
      System.out.println(this.childComponent.getSwingComponent().getPreferredSize());
    }
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.composite.UIComposite#getComponentCount()
   * 
   */
  public int getComponentCount() {

    return 1;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.composite.UIComposite#getComponent(int)
   * 
   */
  public UIComponent getComponent(int index) {

    if (index == 0) {
      return this.childComponent;
    }
    throw new ArrayIndexOutOfBoundsException(index);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIObject#getType()
   * 
   */
  public String getType() {

    return TYPE;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.base.AbstractUINode#refresh()
   * 
   */
  @Override
  public void refresh() {

    this.scrollPanel.invalidate();
    this.scrollPanel.updateUI();
    this.scrollPanel.repaint();
  }

}
