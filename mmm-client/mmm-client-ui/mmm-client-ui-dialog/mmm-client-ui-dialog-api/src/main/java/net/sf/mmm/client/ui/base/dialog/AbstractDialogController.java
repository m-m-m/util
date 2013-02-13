/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.dialog;

import net.sf.mmm.client.ui.api.dialog.AbstractDialog;
import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;

/**
 * This is the abstract base class for the controller of a {@link net.sf.mmm.client.ui.api.dialog.Dialog} or
 * {@link net.sf.mmm.client.ui.api.dialog.PopupDialog}. It implements {@link AbstractDialog} as it represents
 * the actual {@link net.sf.mmm.client.ui.api.dialog.Dialog} or
 * {@link net.sf.mmm.client.ui.api.dialog.PopupDialog} and is its main entry point. A
 * {@link AbstractDialogController} is supposed to be a lightweight component and
 * {@link #AbstractDialogController() construction} shall be cheap and fast. It contains a {@link #getView()
 * view} that is lazily created for performance reasons. The {@link #getView() view} can also be
 * {@link #reset() reseted} to free resources or for testing hot-code changes during development. Therefore
 * implementations have to properly reset their state in {@link #onReset()}.
 * 
 * @param <VIEW> is the generic type of the {@link #getView() view}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractDialogController<VIEW extends UiWidget> extends AbstractLoggableComponent implements
    AbstractDialog {

  /** @see #getView() */
  private VIEW view;

  /** @see #isVisible() */
  private boolean visible;

  /**
   * The constructor.
   */
  public AbstractDialogController() {

    super();
    this.visible = false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean isVisible() {

    return this.visible;
  }

  /**
   * This method sets the {@link #isVisible() visible} flag and on change it triggers {@link #onShow()} or
   * {@link #onHide()} accordingly.
   * 
   * @param visible - <code>true</code> if this controller is about to show its {@link #getView() view},
   *        <code>false</code> if it will hide it.
   */
  void setVisible(boolean visible) {

    if (this.visible != visible) {
      if (visible) {
        onShow();
      } else {
        onHide();
      }
    }
    this.visible = visible;
  }

  /**
   * This method resets this {@link AbstractDialogController}.
   */
  protected final void reset() {

    this.view = null;
  }

  /**
   * This method gets called whenever this controller is {@link #reset() reseted}. If you keep custom state
   * information you need to override and reset your state.
   */
  protected void onReset() {

    // nothing to add...
  }

  /**
   * This method gets called whenever the {@link #getView() view} is shown on the screen. It can be overridden
   * to trigger custom logic - e.g. to update data.
   */
  protected void onShow() {

    // nothing by default...
  }

  /**
   * This method gets called whenever the {@link #getView() view} is hidden from the screen. It can be
   * overridden to trigger custom logics or to clean up resources.
   */
  protected void onHide() {

    // nothing by default...
  }

  /**
   * This method creates the {@link #getView() view} and has to be implemented for each individual dialog.<br/>
   * <b>ATTENTION:</b><br/>
   * This method can be called more than once.
   * 
   * @return the new {@link #getView() view}.
   */
  protected abstract VIEW createView();

  /**
   * This method gets the view of this {@link AbstractDialogController}. TODO
   * 
   * @return the view.
   */
  public VIEW getView() {

    return this.view;
  }

}
