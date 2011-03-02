/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view;

import java.awt.ComponentOrientation;

import javax.swing.JComponent;

import net.sf.mmm.ui.toolkit.api.common.ScriptOrientation;
import net.sf.mmm.ui.toolkit.api.event.UIRefreshEvent;
import net.sf.mmm.ui.toolkit.api.view.UiElement;
import net.sf.mmm.ui.toolkit.api.view.UiNode;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.UiElement} interface using Swing as the
 * UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiElement extends AbstractUiNodeAwt implements UiElement {

  /** the disposed flag */
  private boolean disposed;

  /** the (minimum) size */
  // private final Dimension size;
  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public AbstractUiElement(UIFactorySwing uiFactory) {

    super(uiFactory);
    this.disposed = false;
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
   * setTooltipText(). It can be overridden if the implemented component is
   * build out of multiple swing components and the top ancestor is not the
   * active component (e.g. a {@link javax.swing.JTree} is the active component
   * and a {@link javax.swing.JScrollPane} is the {@link #getSwingComponent()
   * top-ancestor}).
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

    UiNode parent = getParent();
    if (parent != null) {
      setParent(null);
      // throw new IllegalArgumentException("Currently unsupported!");
    }
  }

  /**
   * {@inheritDoc}
   */
  public String getTooltipText() {

    return getActiveSwingComponent().getToolTipText();
  }

  /**
   * {@inheritDoc}
   */
  public void setTooltipText(String tooltip) {

    getActiveSwingComponent().setToolTipText(tooltip);
  }

  /**
   * {@inheritDoc}
   */
  public void setEnabled(boolean enabled) {

    getActiveSwingComponent().setEnabled(enabled);
  }

  /**
   * {@inheritDoc}
   */
  public boolean isEnabled() {

    return getActiveSwingComponent().isEnabled();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setId(String newId) {

    super.setId(newId);
    getSwingComponent().setName(newId);
  }

  /**
   * {@inheritDoc}
   */
  public int getHeight() {

    return getSwingComponent().getHeight();
  }

  /**
   * {@inheritDoc}
   */
  public int getWidth() {

    return getSwingComponent().getWidth();
  }

  /**
   * {@inheritDoc}
   */
  public boolean isResizable() {

    // TODO:
    return true;
  }

  /**
   * {@inheritDoc}
   */
  public void setSize(int width, int height) {

    if (isResizable()) {
      getSwingComponent().setSize(width, height);
      // this.size.height = height;
      // this.size.width = width;
      // getSwingComponent().setMinimumSize(this.size);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void dispose() {

    this.disposed = true;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isDisposed() {

    return this.disposed;
  }

  /**
   * {@inheritDoc}
   */
  public int getPreferredHeight() {

    return (int) getActiveSwingComponent().getPreferredSize().getHeight();
  }

  /**
   * {@inheritDoc}
   */
  public int getPreferredWidth() {

    return (int) getActiveSwingComponent().getPreferredSize().getWidth();
  }

  /**
   * This method should be called at the end of the constructor to initialize
   * general settings.
   */
  protected void initialize() {

    updateOrientation();
  }

  /**
   * This method updates the orientation of the GUI elements.
   */
  protected void updateOrientation() {

    ScriptOrientation orientation = getFactory().getScriptOrientation();
    ComponentOrientation componentOrientation;
    if (orientation.isLeftToRight()) {
      componentOrientation = ComponentOrientation.LEFT_TO_RIGHT;
    } else {
      componentOrientation = ComponentOrientation.RIGHT_TO_LEFT;
    }
    JComponent component = getSwingComponent();
    component.setComponentOrientation(componentOrientation);
    JComponent activeComponent = getActiveSwingComponent();
    if (activeComponent != component) {
      activeComponent.setComponentOrientation(componentOrientation);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void refresh(UIRefreshEvent event) {

    super.refresh(event);
    if (event.isOrientationModified()) {
      updateOrientation();
    }
    getSwingComponent().repaint();
  }

}
