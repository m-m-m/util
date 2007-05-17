/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.composite;

import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;

import net.sf.mmm.ui.toolkit.api.UINode;
import net.sf.mmm.ui.toolkit.api.composite.Orientation;
import net.sf.mmm.ui.toolkit.api.composite.UIComposite;
import net.sf.mmm.ui.toolkit.api.event.UIRefreshEvent;
import net.sf.mmm.ui.toolkit.api.state.UIWriteBorderTitle;
import net.sf.mmm.ui.toolkit.base.AbstractUINode;
import net.sf.mmm.ui.toolkit.impl.swing.AbstractUIComponent;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.UIComponent} interface using Swing as the UI
 * toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractUIComposite extends AbstractUIComponent implements UIComposite,
    UIWriteBorderTitle {

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
   * {@inheritDoc}
   */
  public Orientation getOrientation() {

    return Orientation.HORIZONTAL;
  }

  /**
   * {@inheritDoc}
   */
  public void setEnabled(boolean enabled) {

    super.setEnabled(enabled);
    for (int i = 0; i < getComponentCount(); i++) {
      getComponent(i).setEnabled(enabled);
    }
  }

  /**
   * {@inheritDoc}
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
        setBorderJustification();
        getSwingComponent().setBorder(this.border);
      } else {
        this.border.setTitle(borderTitle);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public String getBorderTitle() {

    if (this.border == null) {
      return null;
    } else {
      return this.border.getTitle();
    }
  }

  /**
   * This method sets the justification of the border according to the
   * script-orientation.
   */
  public void setBorderJustification() {

    if (this.border != null) {
      if (getFactory().getScriptOrientation().isLeftToRight()) {
        this.border.setTitleJustification(TitledBorder.LEFT);
      } else {
        this.border.setTitleJustification(TitledBorder.RIGHT);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void refresh(UIRefreshEvent event) {

    super.refresh(event);
    if (event.isOrientationModified()) {
      setBorderJustification();
    }
    int count = getComponentCount();
    for (int componentIndex = 0; componentIndex < count; componentIndex++) {
      ((AbstractUINode) getComponent(componentIndex)).refresh(event);
    }
  }
}
