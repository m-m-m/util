/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.sync;

import net.sf.mmm.ui.toolkit.api.view.UiElement;
import net.sf.mmm.ui.toolkit.api.view.composite.UiScrollPanel;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;

/**
 * This class is used for synchronous access on a SWT
 * {@link org.eclipse.swt.custom.ScrolledComposite}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SyncScrolledCompositeAccess extends AbstractSyncCompositeAccess<ScrolledComposite> {

  /**
   * operation to set the
   * {@link ScrolledComposite#setContent(org.eclipse.swt.widgets.Control)
   * content} of the scrolled composite.
   */
  private static final String OPERATION_SET_CONTENT = "setContent";

  /** the composite to access */
  private ScrolledComposite composite;

  /** the flag for horizontal expand */
  private boolean expandHorizontal;

  /** the flag for vertical expand */
  private boolean expandVertical;

  /** the content of the scrolled composite */
  private Control content;

  /**
   * The constructor.
   * 
   * @param uiFactory is used to do the synchronization.
   * @param node is the owning {@link #getNode() node}.
   * @param swtStyle is the {@link org.eclipse.swt.widgets.Widget#getStyle()
   *        style} of the menu.
   */
  public SyncScrolledCompositeAccess(UiFactorySwt uiFactory,
      UiScrollPanel<? extends UiElement> node, int swtStyle) {

    this(uiFactory, node, swtStyle, null);
  }

  /**
   * The constructor.
   * 
   * @param uiFactory is used to do the synchronization.
   * @param node is the owning {@link #getNode() node}.
   * @param swtStyle is the {@link org.eclipse.swt.widgets.Widget#getStyle()
   *        style} of the composite.
   * @param swtScrolledComposite is the scrolled composite to access.
   */
  public SyncScrolledCompositeAccess(UiFactorySwt uiFactory,
      UiScrollPanel<? extends UiElement> node, int swtStyle, ScrolledComposite swtScrolledComposite) {

    super(uiFactory, node, swtStyle);
    this.composite = swtScrolledComposite;
    this.expandHorizontal = true;
    this.expandVertical = true;
    this.content = null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void performSynchron(String operation) {

    if (operation == OPERATION_SET_CONTENT) {
      updateContentSynchron();
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
      updateContentSynchron();
    }
    super.createSynchron();
  }

  /**
   * {@inheritDoc}
   */
  public ScrolledComposite getDelegate() {

    return this.composite;
  }

  /**
   * Synchronous part of {@link #setContent(Control)}.
   */
  private void updateContentSynchron() {

    this.composite.setContent(this.content);
    Point size = this.content.computeSize(SWT.DEFAULT, SWT.DEFAULT);
    this.composite.setMinSize(size);
  }

  /**
   * This method sets the
   * {@link ScrolledComposite#setContent(org.eclipse.swt.widgets.Control)
   * content} of the scrolled composite.
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

}
