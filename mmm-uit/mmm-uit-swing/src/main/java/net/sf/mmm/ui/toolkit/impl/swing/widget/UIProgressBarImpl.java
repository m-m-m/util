/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swing.widget;

import javax.swing.JComponent;
import javax.swing.JProgressBar;

import net.sf.mmm.ui.toolkit.api.UINode;
import net.sf.mmm.ui.toolkit.api.composite.Orientation;
import net.sf.mmm.ui.toolkit.api.widget.UIProgressBar;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.custom.MyProgressBar;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UIProgressBar} interface using
 * Swing as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIProgressBarImpl extends AbstractUIWidget implements UIProgressBar {

  /** the native swing widget */
  private final JProgressBar progressBar;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the UIFactorySwing instance.
   * @param parentObject
   *        is the parent of this object (may be <code>null</code>).
   * @param orientation
   */
  public UIProgressBarImpl(UIFactorySwing uiFactory, UINode parentObject, Orientation orientation) {

    super(uiFactory, parentObject);
    this.progressBar = new MyProgressBar(orientation);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swing.AbstractUIComponent#getSwingComponent()
   */
  public @Override
  JComponent getSwingComponent() {

    return this.progressBar;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.widget.UIProgressBar#getProgress()
   */
  public int getProgress() {

    return this.progressBar.getValue();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.widget.UIProgressBar#setProgress(int)
   */
  public void setProgress(int newProgress) {

    this.progressBar.setValue(newProgress);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIObject#getType()
   */
  public String getType() {

    return TYPE;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.widget.UIProgressBar#getOrientation()
   */
  public Orientation getOrientation() {

    if (this.progressBar.getOrientation() == JProgressBar.HORIZONTAL) {
      return Orientation.HORIZONTAL;
    } else {
      return Orientation.VERTICAL;
    }
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.widget.UIProgressBar#isIndeterminate()
   */
  public boolean isIndeterminate() {

    return this.progressBar.isIndeterminate();
  }

}
