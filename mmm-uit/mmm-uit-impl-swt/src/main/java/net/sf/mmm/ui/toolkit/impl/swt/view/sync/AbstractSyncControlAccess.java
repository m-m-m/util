/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.sync;

import net.sf.mmm.ui.toolkit.api.view.UiNode;
import net.sf.mmm.ui.toolkit.base.view.AbstractUiElement;
import net.sf.mmm.ui.toolkit.base.view.UiElementAdapter;
import net.sf.mmm.ui.toolkit.base.view.composite.AbstractUiComposite;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.view.window.AbstractUiWindowSwt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * This is the abstract base class used for synchronous access on a SWT
 * {@link org.eclipse.swt.widgets.Control}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @param <DELEGATE> is the generic type of the {@link #getDelegate() delegate}.
 * @since 1.0.0
 */
public abstract class AbstractSyncControlAccess<DELEGATE extends Control> extends AbstractSyncWidgetAccess<DELEGATE>
    implements UiElementAdapter<DELEGATE> {

  /**
   * operation to set the {@link org.eclipse.swt.widgets.Control#setParent(org.eclipse.swt.widgets.Composite)
   * parent}
   */
  protected static final String OPERATION_SET_PARENT = "setParent";

  /**
   * operation to get the {@link org.eclipse.swt.widgets.Control#getSize() bounds}
   */
  protected static final String OPERATION_GET_SIZE = "getSize";

  /**
   * operation to set the {@link org.eclipse.swt.widgets.Control#setSize(int, int) size}
   */
  protected static final String OPERATION_SET_SIZE = "setSize";

  /**
   * operation to get the {@link org.eclipse.swt.widgets.Control#getLocation() location}
   */
  protected static final String OPERATION_GET_LOCATION = "getLocation";

  /**
   * operation to set the {@link org.eclipse.swt.widgets.Control#setLocation(int, int) location}
   */
  protected static final String OPERATION_SET_LOCATION = "setLocation";

  /**
   * operation to set the {@link org.eclipse.swt.widgets.Control#setToolTipText(String) tooltip-text}
   */
  protected static final String OPERATION_SET_TOOLTIP = "setTooltip";

  /**
   * operation to get the {@link org.eclipse.swt.widgets.Control#isVisible() "visible-flag"}
   */
  protected static final String OPERATION_IS_VISIBLE = "isVisible";

  /**
   * operation to {@link org.eclipse.swt.widgets.Control#pack() pack} the control.
   */
  protected static final String OPERATION_PACK = "pack";

  /**
   * operation to {@link org.eclipse.swt.widgets.Control#computeSize(int, int) "compute size"} of the control.
   */
  protected static final String OPERATION_COMPUTE_SIZE = "computeSize";

  /**
   * operation to set the {@link org.eclipse.swt.widgets.Control#setFont(org.eclipse.swt.graphics.Font) font}
   * of the control.
   */
  protected static final String OPERATION_SET_FONT = "setFont";

  /**
   * operation to set the {@link org.eclipse.swt.widgets.Control#setLayoutData(Object) "layout data"} of the
   * control.
   */
  protected static final String OPERATION_SET_LAYOUT_DATA = "setLayoutData";

  /**
   * operation to set the
   * {@link org.eclipse.swt.widgets.Control#setForeground(org.eclipse.swt.graphics.Color) foreground-color} of
   * the control.
   */
  protected static final String OPERATION_SET_FOREGROUND = "setForeground";

  /**
   * operation to set the
   * {@link org.eclipse.swt.widgets.Control#setBackground(org.eclipse.swt.graphics.Color) background-color} of
   * the control.
   */
  protected static final String OPERATION_SET_BACKGROUND = "setBackground";

  /** the size to set */
  private Point size;

  /** the location to set */
  private Point location;

  /** @see #getTooltip() */
  private String tooltip;

  /** the width hint for compute-size */
  private int wHint;

  /** the height hint for compute-size */
  private int hHint;

  /** the computed size */
  private Point computedSize;

  /** the layout data */
  private Object layoutData;

  /** @see #getParentAccess() */
  private AbstractSyncCompositeAccess<? extends Composite> parentAccess;

  /** the font of this control */
  private Font font;

  /** the foreground color (typically for text) */
  private Color foreground;

  /** the background color */
  private Color background;

  /**
   * The constructor.
   * 
   * @param uiFactory is used to do the synchronization.
   * @param node is the owning {@link #getNode() node}.
   * @param swtStyle is the {@link org.eclipse.swt.widgets.Widget#getStyle() style} of the control.
   */
  public AbstractSyncControlAccess(UiFactorySwt uiFactory, UiNode node, int swtStyle) {

    super(uiFactory, node, swtStyle);
    this.parentAccess = null;
    this.size = null;
    this.location = null;
    this.tooltip = null;
    this.computedSize = null;
    this.wHint = 0;
    this.hHint = 0;
    this.layoutData = null;
    this.font = null;
    this.foreground = null;
    this.background = null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void performSynchron(String operation) {

    if (operation == OPERATION_GET_SIZE) {
      this.size = getDelegate().getSize();
    } else if (operation == OPERATION_SET_SIZE) {
      getDelegate().setSize(this.size);
    } else if (operation == OPERATION_SET_LOCATION) {
      getDelegate().setLocation(this.location);
    } else if (operation == OPERATION_GET_LOCATION) {
      this.location = getDelegate().getLocation();
    } else if (operation == OPERATION_SET_ENABLED) {
      getDelegate().setEnabled(super.isEnabled());
    } else if (operation == OPERATION_SET_TOOLTIP) {
      getDelegate().setToolTipText(this.tooltip);
    } else if (operation == OPERATION_SET_VISIBLE) {
      boolean visible = doIsVisible();
      getDelegate().setVisible(visible);
      if (visible && (getDelegate().getClass() == Shell.class)) {
        ((Shell) getDelegate()).forceActive();
      }
    } else if (operation == OPERATION_IS_VISIBLE) {
      doSetVisible(getDelegate().isVisible());
    } else if (operation == OPERATION_PACK) {
      getDelegate().pack();
    } else if (operation == OPERATION_COMPUTE_SIZE) {
      this.computedSize = getDelegate().computeSize(this.wHint, this.hHint);
    } else if (operation == OPERATION_SET_LAYOUT_DATA) {
      getDelegate().setLayoutData(this.layoutData);
    } else if (operation == OPERATION_SET_PARENT) {
      getDelegate().setParent(getParent());
    } else if (operation == OPERATION_SET_FONT) {
      getDelegate().setFont(this.font);
    } else if (operation == OPERATION_SET_FOREGROUND) {
      getDelegate().setForeground(this.foreground);
    } else if (operation == OPERATION_SET_BACKGROUND) {
      getDelegate().setBackground(this.background);
    } else {
      super.performSynchron(operation);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doCreateSynchron() {

    if (this.parentAccess != null) {
      if (this.parentAccess.getDelegate() == null) {
        this.parentAccess.doCreateSynchron();
      }
      if (this.parentAccess.getDelegate() != null) {
        super.doCreateSynchron();
      } else {
        new Exception("Warning: parent (" + this.parentAccess.getClass() + ") is empty!").printStackTrace();
      }
    } else {
      new Exception("Warning: parent is null!").printStackTrace();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void createSynchron() {

    Control c = getDelegate();
    if (this.layoutData != null) {
      c.setLayoutData(this.layoutData);
    }
    if (this.font == null) {
      this.font = c.getFont();
    } else {
      c.setFont(this.font);
    }
    if (this.foreground == null) {
      this.foreground = c.getForeground();
    } else {
      c.setForeground(this.foreground);
    }
    if (this.background == null) {
      this.background = c.getBackground();
    } else {
      c.setBackground(this.background);
    }
    if (this.tooltip != null) {
      c.setToolTipText(this.tooltip);
    }
    if (this.size != null) {
      c.setSize(this.size);
    }
    if (this.location != null) {
      c.setLocation(this.location);
    }
    if (!isEnabled()) {
      c.setEnabled(false);
    }
    c.setVisible(isVisible());
    // TODO: pack
    super.createSynchron();
  }

  /**
   * This method gets the {@link Control#getParent() parent} of this control.
   * 
   * @return the parent or <code>null</code> if NOT {@link #setParentAccess(AbstractSyncCompositeAccess) set}
   *         and {@link AbstractSyncWidgetAccess#create() created}.
   */
  public Composite getParent() {

    if (this.parentAccess == null) {
      return null;
    }
    return this.parentAccess.getDelegate();
  }

  /**
   * This method gets the synchronous access to the {@link Control#getParent() parent} of this control.
   * 
   * @return the synchronous access to the parent.
   */
  public AbstractSyncCompositeAccess<? extends Composite> getParentAccess() {

    return this.parentAccess;
  }

  /**
   * This method sets the parent sync-access of this control. If the parent
   * {@link AbstractSyncCompositeAccess#getDelegate() exists}, it will be set as parent of this control. Else
   * if the control does NOT yet exist, the parent will be set on {@link #create() creation}.
   * 
   * @param newParentAccess is the synchronous access to the new parent
   */
  public void setParentAccess(AbstractSyncCompositeAccess<? extends Composite> newParentAccess) {

    this.parentAccess = newParentAccess;
    if (this.parentAccess.getDelegate() != null) {
      if (getDelegate() != null) {
        assert (checkReady());
        invoke(OPERATION_SET_PARENT);
      }
    }
  }

  /**
   * This method determines if the control can be created.
   * 
   * @return <code>true</code> if there is an ancestor that is already created, <code>false</code> otherwise.
   */
  protected boolean canCreate() {

    if (this.parentAccess == null) {
      return false;
    } else if (this.parentAccess.getDelegate() != null) {
      return true;
    } else {
      return this.parentAccess.canCreate();
    }
  }

  /**
   * The {@link #setParentAccess(AbstractSyncCompositeAccess) parent} must be set before this method is
   * called.
   * 
   * @see net.sf.mmm.ui.toolkit.impl.swt.view.sync.AbstractSyncWidgetAccess#create()
   */
  @Override
  public void create() {

    // if (canCreate()) {
    super.create();
    // }
  }

  /**
   * This method gets the {@link Point}.
   * 
   * @return the {@link Point}.
   */
  public Point getSize() {

    invoke(OPERATION_GET_SIZE);
    return this.size;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSizeInPixel(int width, int height) {

    if (this.size == null) {
      this.size = new Point(width, height);
    } else {
      this.size.x = width;
      this.size.y = height;
    }
    invoke(OPERATION_SET_SIZE);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setWidthInPixel(int width) {

    if (this.size == null) {
      this.size = new Point(width, getHeightInPixel());
    } else {
      this.size.x = width;
      this.size.y = getHeightInPixel();
    }
    invoke(OPERATION_SET_SIZE);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHeightInPixel(int height) {

    if (this.size == null) {
      this.size = new Point(getWidthInPixel(), height);
    } else {
      this.size.x = getWidthInPixel();
      this.size.y = height;
    }
    invoke(OPERATION_SET_SIZE);
  }

  /**
   * This method sets the {@link Control#setSize(int, int) size} of the control.
   * 
   * @param newSize is the new size.
   */
  public void setSize(Point newSize) {

    if (newSize != null) {
      setSizeInPixel(newSize.x, newSize.y);
    }
  }

  /**
   * {@inheritDoc}
   */
  public boolean isResizable() {

    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getHeightInPixel() {

    return getSize().y;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getWidthInPixel() {

    return getSize().x;
  }

  public Point getPosition() {

    invoke(OPERATION_GET_LOCATION);
    return this.location;
  }

  /**
   * This method sets the {@link Control#setLocation(int, int) location} of the control.
   * 
   * @param x is the x-coordinate to set.
   * @param y is the y-coordinate to set.
   */
  public void setPosition(int x, int y) {

    assert (checkReady());
    if (this.location == null) {
      this.location = new Point(x, y);
    } else {
      this.location.x = x;
      this.location.y = y;
    }
    invoke(OPERATION_SET_LOCATION);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTooltip(String tooltipText) {

    assert (checkReady());
    this.tooltip = tooltipText;
    invoke(OPERATION_SET_TOOLTIP);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTooltip() {

    // tooltip can NOT be modified externally (e.g. by user-interaction).
    return this.tooltip;
  }

  /**
   * This method {@link org.eclipse.swt.widgets.Control#pack() packs} the control.
   */
  public void pack() {

    assert (checkReady());
    invoke(OPERATION_PACK);
  }

  /**
   * This method {@link Control#computeSize(int, int) computes} the size of the control.
   * 
   * @return the computed size.
   */
  public Point computeSize() {

    return computeSize(SWT.DEFAULT, SWT.DEFAULT);
  }

  /**
   * This method {@link Control#computeSize(int, int) computes} the size of the control.
   * 
   * @param widthHint the suggested width or {@link org.eclipse.swt.SWT#DEFAULT} .
   * @param heightHint the suggested height or {@link org.eclipse.swt.SWT#DEFAULT}.
   * @return the computed size.
   */
  public Point computeSize(int widthHint, int heightHint) {

    assert (checkReady());
    this.wHint = widthHint;
    this.hHint = heightHint;
    invoke(OPERATION_COMPUTE_SIZE);
    return this.computedSize;
  }

  /**
   * This method sets the {@link org.eclipse.swt.widgets.Control#setLayoutData(Object) "layout data"} of the
   * control.
   * 
   * @param data is the layout-data to set.
   */
  public void setLayoutData(Object data) {

    assert (checkReady());
    this.layoutData = data;
    invoke(OPERATION_SET_LAYOUT_DATA);
  }

  /**
   * This method gets the {@link org.eclipse.swt.widgets.Control#getFont() font} of the control.
   * 
   * @return the font of the control. May be <code>null</code> if the control has NOT been created.
   */
  public Font getFont() {

    return this.font;
  }

  /**
   * This method sets the {@link org.eclipse.swt.widgets.Control#setFont(org.eclipse.swt.graphics.Font) font}
   * of the control.
   * 
   * @param newFont is the new font to set.
   */
  public void setFont(Font newFont) {

    assert (checkReady());
    this.font = newFont;
    invoke(OPERATION_SET_FONT);
  }

  /**
   * This method gets the {@link org.eclipse.swt.widgets.Control#getForeground() foreground-color} of the
   * control.
   * 
   * @return the foreground of the control. May be <code>null</code> if the control has NOT been created.
   */
  public Color getForeground() {

    return this.foreground;
  }

  /**
   * This method sets the
   * {@link org.eclipse.swt.widgets.Control#setForeground(org.eclipse.swt.graphics.Color) foreground-color} of
   * the control.
   * 
   * @param foregroundColor is the foreground-color to set.
   */
  public void setForeground(Color foregroundColor) {

    assert (checkReady());
    this.foreground = foregroundColor;
    invoke(OPERATION_SET_FOREGROUND);
  }

  /**
   * This method gets the {@link org.eclipse.swt.widgets.Control#getBackground() background-color} of the
   * control.
   * 
   * @return the background of the control. May be <code>null</code> if the control has NOT been created.
   */
  public Color getBackground() {

    return this.background;
  }

  /**
   * This method sets the
   * {@link org.eclipse.swt.widgets.Control#setBackground(org.eclipse.swt.graphics.Color) background-color} of
   * the control.
   * 
   * @param backgroundColor is the background-color to set.
   */
  public void setBackground(Color backgroundColor) {

    assert (checkReady());
    this.background = backgroundColor;
    invoke(OPERATION_SET_BACKGROUND);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setParent(UiNode newParent) {

    if (newParent == null) {
      System.out.println("AbstractUiElement: This should be kicked out!");
      // getSwtControl().setParent(getFactory().getDummyParent());
    } else {
      AbstractSyncCompositeAccess<?> parentAccess2;
      boolean autoAttach = true;
      if (newParent instanceof AbstractUiComposite<?, ?>) {
        AbstractUiComposite<?, ?> parentComposite = (AbstractUiComposite<?, ?>) newParent;
        parentAccess2 = (AbstractSyncCompositeAccess<?>) parentComposite.getAdapter();
        autoAttach = true; // parentComposite.isAttachToActiveAccess();
      } else {
        parentAccess2 = ((AbstractUiWindowSwt) newParent).getAdapter();
      }
      if (autoAttach) {
        setParentAccess(parentAccess2);
      }
      if ((getDelegate() == null) && (parentAccess2.getDelegate() != null)) {
        create();
      }
    }
    super.setParent(newParent);
    update();
  }

  /**
   *
   */
  public void update() {

    UiNode parent = getNode().getParent();
    if (parent instanceof AbstractUiElement<?>) {
      AbstractUiElement<?> p = (AbstractUiElement<?>) parent;
      ((AbstractSyncControlAccess<?>) p.getAdapter()).update();
    }
    // nothing to do so far
  }

}
