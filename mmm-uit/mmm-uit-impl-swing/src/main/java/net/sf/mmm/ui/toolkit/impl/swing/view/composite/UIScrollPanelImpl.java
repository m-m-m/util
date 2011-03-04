/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.composite;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import net.sf.mmm.ui.toolkit.api.common.ScrollbarPolicy;
import net.sf.mmm.ui.toolkit.api.event.UIRefreshEvent;
import net.sf.mmm.ui.toolkit.api.view.composite.UiScrollPanel;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.view.AbstractUiElement;
import net.sf.mmm.util.nls.api.IllegalCaseException;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.composite.UiScrollPanel} interface
 * using Swing as the UI toolkit.
 * 
 * @param <E> is the generic type of the {@link #getChild(int) children}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UIScrollPanelImpl<E extends AbstractUiElement> extends AbstractUiSingleComposite<E>
    implements UiScrollPanel<E> {

  /** the scroll-panel or <code>null</code> if this is a regular panel */
  private final JScrollPane scrollPanel;

  /** @see #getHorizontalScrollbarPolicy() */
  private ScrollbarPolicy horizontalScrollbarPolicy;

  /** @see #getVerticalScrollbarPolicy() */
  private ScrollbarPolicy verticalScrollbarPolicy;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public UIScrollPanelImpl(UIFactorySwing uiFactory) {

    super(uiFactory);
    this.scrollPanel = new JScrollPane();
    this.horizontalScrollbarPolicy = ScrollbarPolicy.DYNAMIC;
    this.verticalScrollbarPolicy = ScrollbarPolicy.DYNAMIC;
    this.scrollPanel.setHorizontalScrollBarPolicy(convertScrollbarPolicy(
        this.horizontalScrollbarPolicy, true));
    this.scrollPanel.setVerticalScrollBarPolicy(convertScrollbarPolicy(
        this.verticalScrollbarPolicy, false));
    initialize();
  }

  /**
   * This method converts the given {@link ScrollbarPolicy} to one of the
   * swing {@link ScrollPaneConstants}.
   * 
   * @param scrollbarPolicy is the {@link ScrollbarPolicy}.
   * @param horizontal - <code>true</code> for horizontal scrollbar,
   *        <code>false</code> for vertical scrollbar.
   * @return the integer constant.
   */
  private static int convertScrollbarPolicy(ScrollbarPolicy scrollbarPolicy,
      boolean horizontal) {

    switch (scrollbarPolicy) {
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
        throw new IllegalCaseException(ScrollbarPolicy.class, scrollbarPolicy);
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
  @Override
  public void setChild(E child) {

    super.setChild(child);
    if (child != null) {
      this.scrollPanel.setViewportView(child.getSwingComponent());
    }
  }

  /**
   * {@inheritDoc}
   */
  public ScrollbarPolicy getHorizontalScrollbarPolicy() {

    return this.horizontalScrollbarPolicy;
  }

  /**
   * {@inheritDoc}
   */
  public ScrollbarPolicy getVerticalScrollbarPolicy() {

    return this.verticalScrollbarPolicy;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getChildCount() {

    return 1;
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
