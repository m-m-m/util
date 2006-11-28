/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swing;

import java.awt.Dimension;

import javax.swing.JComponent;

import net.sf.mmm.ui.toolkit.api.UIComponent;
import net.sf.mmm.ui.toolkit.api.UINode;
import net.sf.mmm.ui.toolkit.impl.awt.UIAwtNode;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.UIComponent} interface using Swing as the
 * UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractUIComponent extends UIAwtNode implements UIComponent {

  /** the disposed flag */
  private boolean disposed;

  /** the (mimimum) size */
  private final Dimension size;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the UIFactorySwing instance.
   * @param parentObject
   *        is the parent of this object (may be <code>null</code>).
   */
  public AbstractUIComponent(UIFactorySwing uiFactory, UINode parentObject) {

    super(uiFactory, parentObject);
    this.disposed = false;
    this.size = new Dimension();
  }

  /**
   * This method gets the unwrapped swing component that is the top ancestor of
   * this component.
   * 
   * @return the unwrapped swing component.
   */
  public abstract JComponent getSwingComponent();

  /**
   * This method gets the unwrapped component that represents the active part of
   * this component. This method is used by methods such as setEnabled() and
   * setTooltipText(). It can be overriden if the implemented component is build
   * out of multiple swing components and the top ancestor is not the active
   * component (e.g. a {@link javax.swing.JTree} is the active component and a
   * {@link javax.swing.JScrollPane} is the
   * {@link #getSwingComponent() top-ancestor}).
   * 
   * @return the active unwrapped swing component.
   */
  protected JComponent getActiveSwingComponent() {

    return getSwingComponent();
  }

  /**
   * This method removes this component from its {@link #getParent() parent}.
   */
  public void removeFromParent() {
    UINode parent = getParent();
    if (parent != null) {
      setParent(null);    
      throw new IllegalArgumentException("Currently unsupported!");      
    }
  }
  
  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteTooltip#getTooltipText()
   */
  public String getTooltipText() {

    return getActiveSwingComponent().getToolTipText();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIComponent#setTooltipText(java.lang.String)
   */
  public void setTooltipText(String tooltip) {

    getActiveSwingComponent().setToolTipText(tooltip);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIComponent#setEnabled(boolean)
   */
  public void setEnabled(boolean enabled) {

    getActiveSwingComponent().setEnabled(enabled);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadEnabled#isEnabled()
   */
  public boolean isEnabled() {

    return getActiveSwingComponent().isEnabled();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIObject#setId(java.lang.String)
   */
  @Override
  public void setId(String newId) {

    super.setId(newId);
    getSwingComponent().setName(newId);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadSize#getHeight()
   */
  public int getHeight() {

    return getSwingComponent().getHeight();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadSize#getWidth()
   */
  public int getWidth() {

    return getSwingComponent().getWidth();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSize#isResizeable()
   */
  public boolean isResizeable() {

    // TODO:
    return true;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSize#setSize(int, int)
   */
  public void setSize(int width, int height) {

    if (isResizeable()) {
      // getSwingComponent().setSize(width, height);
      this.size.height = height;
      this.size.width = width;
      getSwingComponent().setMinimumSize(this.size);
    }
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteDisposed#dispose()
   */
  public void dispose() {

    this.disposed = true;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteDisposed#isDisposed()
   */
  public boolean isDisposed() {

    return this.disposed;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadPreferredSize#getPreferredHeight()
   */
  public int getPreferredHeight() {

    return (int) getActiveSwingComponent().getPreferredSize().getHeight();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadPreferredSize#getPreferredWidth()
   */
  public int getPreferredWidth() {

    return (int) getActiveSwingComponent().getPreferredSize().getWidth();
  }

}
