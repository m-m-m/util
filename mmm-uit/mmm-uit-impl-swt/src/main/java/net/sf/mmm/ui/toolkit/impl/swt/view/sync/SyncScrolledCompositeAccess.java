/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.sync;

import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;

import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;

/**
 * This class is used for synchronous access on a SWT
 * {@link org.eclipse.swt.custom.ScrolledComposite}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SyncScrolledCompositeAccess extends AbstractSyncCompositeAccess {

  /**
   * operation to set the
   * {@link ScrolledComposite#setContent(org.eclipse.swt.widgets.Control) content}
   * of the scrolled composite.
   */
  private static final String OPERATION_SET_CONTENT = "setContent";

  /**
   * operation to set the
   * {@link ScrolledComposite#setMinSize(org.eclipse.swt.graphics.Point) minimum-size}
   * of the scrolled composite.
   */
  private static final String OPERATION_SET_MINIMUM_SIZE = "setMinSize";

  /** the composite to access */
  private ScrolledComposite composite;

  /** the flag for horizontal expand */
  private boolean expandHorizontal;

  /** the flag for vertical expand */
  private boolean expandVertical;

  /** the content of the scrolled composite */
  private Control content;

  /** the minimum size */
  private Point minimumSize;

  /**
   * The constructor.
   * 
   * @param uiFactory is used to do the synchronization.
   * @param swtStyle is the
   *        {@link org.eclipse.swt.widgets.Widget#getStyle() style} of the menu.
   */
  public SyncScrolledCompositeAccess(UiFactorySwt uiFactory, int swtStyle) {

    this(uiFactory, swtStyle, null);
  }

  /**
   * The constructor.
   * 
   * @param uiFactory is used to do the synchronization.
   * @param swtStyle is the
   *        {@link org.eclipse.swt.widgets.Widget#getStyle() style} of the
   *        composite.
   * @param swtScrolledComposite is the scrolled composite to access.
   */
  public SyncScrolledCompositeAccess(UiFactorySwt uiFactory, int swtStyle,
      ScrolledComposite swtScrolledComposite) {

    super(uiFactory, swtStyle);
    this.composite = swtScrolledComposite;
    this.expandHorizontal = true;
    this.expandVertical = true;
    this.content = null;
    this.minimumSize = null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void performSynchron(String operation) {

    if (operation == OPERATION_SET_CONTENT) {
      this.composite.setContent(this.content);
    } else if (operation == OPERATION_SET_MINIMUM_SIZE) {
      this.composite.setMinSize(this.minimumSize);
    } else {
      super.performSynchron(operation);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void createSynchron() {

    this.composite = new ScrolledComposite(getParent(), getStyle());
    this.composite.setExpandHorizontal(this.expandHorizontal);
    this.composite.setExpandVertical(this.expandVertical);
    if (this.content != null) {
      this.composite.setContent(this.content);
    }
    if (this.minimumSize != null) {
      this.composite.setMinSize(this.minimumSize);
    }
    super.createSynchron();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ScrolledComposite getSwtObject() {

    return this.composite;
  }

  /**
   * This method sets the
   * {@link ScrolledComposite#setContent(org.eclipse.swt.widgets.Control) content}
   * of the scrolled composite.
   * 
   * @param swtContent is the content to set.
   */
  public void setContent(Control swtContent) {

    assert (checkReady());
    this.content = swtContent;
    invoke(OPERATION_SET_CONTENT);
  }

  /**
   * This method gets the {@link ScrolledComposite#getContent() content} of the
   * scrolled composite.
   * 
   * @return the content.
   */
  public Control getContent() {

    return this.content;
  }

  /**
   * This method sets the
   * {@link ScrolledComposite#setMinSize(org.eclipse.swt.graphics.Point) minimum-size}
   * of the scrolled composite.
   * 
   * @param minSize is the size required to display the content without
   *        scrollbars.
   */
  public void setMinimumSize(Point minSize) {

    assert (checkReady());
    this.minimumSize = minSize;
    invoke(OPERATION_SET_MINIMUM_SIZE);
  }

}
