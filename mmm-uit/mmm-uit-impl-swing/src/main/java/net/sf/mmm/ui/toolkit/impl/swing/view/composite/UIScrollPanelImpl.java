/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.composite;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import net.sf.mmm.ui.toolkit.api.common.ScrollbarVisibility;
import net.sf.mmm.ui.toolkit.api.event.UIRefreshEvent;
import net.sf.mmm.ui.toolkit.api.view.UiElement;
import net.sf.mmm.ui.toolkit.api.view.UiNode;
import net.sf.mmm.ui.toolkit.api.view.composite.UiScrollPanel;
import net.sf.mmm.ui.toolkit.impl.swing.AbstractUiElement;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;
import net.sf.mmm.util.nls.api.IllegalCaseException;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.composite.UiScrollPanel} interface
 * using Swing as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UIScrollPanelImpl extends AbstractUiComposite implements UiScrollPanel {

  /** the scroll-panel or <code>null</code> if this is a regular panel */
  private final JScrollPane scrollPanel;

  /** the child component */
  private AbstractUiElement childComponent;

  /** @see #getHorizontalScrollbarVisibility() */
  private ScrollbarVisibility horizontalScrollbarVisibility;

  /** @see #getVerticalScrollbarVisibility() */
  private ScrollbarVisibility verticalScrollbarVisibility;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwing instance.
   * @param parentObject is the parent of this object (may be <code>null</code>
   *        ).
   */
  public UIScrollPanelImpl(UIFactorySwing uiFactory, UiNode parentObject) {

    super(uiFactory, parentObject);
    this.scrollPanel = new JScrollPane();
    this.horizontalScrollbarVisibility = ScrollbarVisibility.DYNAMIC;
    this.verticalScrollbarVisibility = ScrollbarVisibility.DYNAMIC;
    this.scrollPanel.setHorizontalScrollBarPolicy(convertScrollbarVisibility(
        this.horizontalScrollbarVisibility, true));
    this.scrollPanel.setVerticalScrollBarPolicy(convertScrollbarVisibility(
        this.verticalScrollbarVisibility, false));
    this.childComponent = null;
    initialize();
  }

  /**
   * This method converts the given {@link ScrollbarVisibility} to one of the
   * swing {@link ScrollPaneConstants}.
   * 
   * @param scrollbarVisibility is the {@link ScrollbarVisibility}.
   * @param horizontal - <code>true</code> for horizontal scrollbar,
   *        <code>false</code> for vertical scrollbar.
   * @return the integer constant.
   */
  private static int convertScrollbarVisibility(ScrollbarVisibility scrollbarVisibility,
      boolean horizontal) {

    switch (scrollbarVisibility) {
      case ALWAYS:
        if (horizontal) {
          return ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
        } else {
          return ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
        }
      case NEVER:
        if (horizontal) {
          return ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;
        } else {
          return ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER;
        }
      case DYNAMIC:
        if (horizontal) {
          return ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
        } else {
          return ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
        }
      default :
        throw new IllegalCaseException(ScrollbarVisibility.class, scrollbarVisibility);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JComponent getSwingComponent() {

    return this.scrollPanel;
  }

  /**
   * {@inheritDoc}
   */
  public void setComponent(UiElement child) {

    if (this.childComponent != null) {
      setParent(this.childComponent, null);
    }
    this.childComponent = (AbstractUiElement) child;
    if (this.childComponent != null) {
      this.scrollPanel.setViewportView(this.childComponent.getSwingComponent());
    }
  }

  /**
   * {@inheritDoc}
   */
  public ScrollbarVisibility getHorizontalScrollbarVisibility() {

    return this.horizontalScrollbarVisibility;
  }

  /**
   * {@inheritDoc}
   */
  public ScrollbarVisibility getVerticalScrollbarVisibility() {

    return this.verticalScrollbarVisibility;
  }

  /**
   * {@inheritDoc}
   */
  public int getChildCount() {

    return 1;
  }

  /**
   * {@inheritDoc}
   */
  public UiElement getChild() {

    return this.childComponent;
  }

  /**
   * {@inheritDoc}
   */
  public UiElement getChild(int index) {

    if (index == 0) {
      return this.childComponent;
    }
    throw new ArrayIndexOutOfBoundsException(index);
  }

  /**
   * {@inheritDoc}
   */
  public String getType() {

    return TYPE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void refresh(UIRefreshEvent event) {

    super.refresh(event);
    this.scrollPanel.invalidate();
    this.scrollPanel.updateUI();
    this.scrollPanel.repaint();
  }

}
