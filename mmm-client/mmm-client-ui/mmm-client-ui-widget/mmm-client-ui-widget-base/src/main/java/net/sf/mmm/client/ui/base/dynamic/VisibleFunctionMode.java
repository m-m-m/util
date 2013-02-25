/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.dynamic;

import net.sf.mmm.client.ui.api.attribute.AttributeReadVisible;
import net.sf.mmm.client.ui.api.common.UiMode;
import net.sf.mmm.client.ui.api.widget.UiWidget;

/**
 * This is the implementation of a {@link UiWidget#addVisibleFunction(AttributeReadVisible) visible-function}
 * that shows or hides a {@link UiWidget} if the {@link UiMode} is (not) {@link UiMode#isEditable() editable}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class VisibleFunctionMode implements AttributeReadVisible {

  /** @see #isVisible() */
  private final UiWidget widget;

  /** @see #isVisible() */
  private final boolean showIfEditable;

  /**
   * The constructor.
   * 
   * @param widget is the {@link UiWidget} to show/hide when to its {@link UiWidget#getMode() mode} changes.
   * @param autoAdd - if <code>true</code> this new visible-function instance automatically
   *        {@link UiWidget#addVisibleFunction(AttributeReadVisible) adds} itself to the given
   *        <code>widget</code>. Otherwise, if <code>false</code>, this is omitted.
   * @param showIfEditable - if <code>true</code> to show if the new {@link UiMode} is
   *        {@link UiMode#isEditable() editable} and hide otherwise, <code>false</code> for the opposite (show
   *        only if NOT editable).
   */
  public VisibleFunctionMode(UiWidget widget, boolean autoAdd, boolean showIfEditable) {

    super();
    this.widget = widget;
    this.showIfEditable = showIfEditable;
    if (autoAdd) {
      this.widget.addVisibleFunction(this);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isVisible() {

    UiMode mode = this.widget.getMode();
    if (mode == null) {
      // should never happend...
      return false;
    }
    return (this.showIfEditable == mode.isEditable());
  }

}
