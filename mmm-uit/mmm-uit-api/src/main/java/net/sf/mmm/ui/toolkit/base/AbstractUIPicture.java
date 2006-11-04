/* $Id$ */
package net.sf.mmm.ui.toolkit.base;

import net.sf.mmm.ui.toolkit.api.UIFactory;
import net.sf.mmm.ui.toolkit.api.UIPicture;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.UIPicture} interface.<br>
 * Set initial {@link #setSize(int, int) site} in constructor of the
 * implementation.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractUIPicture extends AbstractUIObject implements UIPicture {

  /** the current width (may scale) */
  private int width;

  /** the current height (may scale) */
  private int height;

  /**
   * The constructor.
   * 
   * @param uiFactory
   */
  public AbstractUIPicture(UIFactory uiFactory) {

    super(uiFactory);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIObject#getType()
   * 
   */
  public String getType() {

    return TYPE;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSize#setSize(int, int)
   * 
   */
  public void setSize(int newWidth, int newHeight) {

    this.width = newWidth;
    this.height = newHeight;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSize#isResizeable()
   * 
   */
  public boolean isResizeable() {

    return true;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadSize#getWidth()
   * 
   */
  public int getWidth() {

    return this.width;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadSize#getHeight()
   * 
   */
  public int getHeight() {

    return this.height;
  }

}
