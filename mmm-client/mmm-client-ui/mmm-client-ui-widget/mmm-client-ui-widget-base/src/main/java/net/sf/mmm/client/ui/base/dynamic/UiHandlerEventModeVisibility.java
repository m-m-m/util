/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.dynamic;

import net.sf.mmm.client.ui.api.common.UiMode;
import net.sf.mmm.client.ui.api.event.UiEventMode;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventMode;
import net.sf.mmm.client.ui.api.widget.UiWidget;

/**
 * This is the implementation of a {@link UiHandlerEventMode} that shows or hides a {@link UiWidget} if the
 * {@link UiMode} is (not) {@link UiMode#isEditable() editable}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiHandlerEventModeVisibility extends UiHandlerEventMode {

  /** @see #onModeChange(UiEventMode, UiMode) */
  private final boolean showIfEditable;

  /**
   * The constructor.
   * 
   * @param showIfEditable - see {@link #isShowIfEditable()}.
   */
  public UiHandlerEventModeVisibility(boolean showIfEditable) {

    super();
    this.showIfEditable = showIfEditable;
  }

  /**
   * @return <code>true</code> to show if the new {@link UiMode} is {@link UiMode#isEditable() editable} and
   *         hide otherwise, <code>false</code> for the opposite (show only if NOT editable).
   */
  public boolean isShowIfEditable() {

    return this.showIfEditable;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onModeChange(UiEventMode event, UiMode newMode) {

    UiWidget widget = (UiWidget) event.getSource();
    widget.getVisibleFlag()
        .setFlag((newMode.isEditable() == this.showIfEditable), VisibilityFlagModifier.MODIFIER_MODE);
  }

  /**
   * Creates a new instance of this class and adds it to the given {@link UiWidget}.
   * 
   * @param widget is the {@link UiWidget} where to
   *        {@link UiWidget#addEventHandler(net.sf.mmm.client.ui.api.handler.event.UiHandlerEvent) add} the
   *        created {@link UiHandlerEventModeVisibility}.
   * @param showIfEditable - see {@link #isShowIfEditable()}.
   */
  public static void createAndAdd(UiWidget widget, boolean showIfEditable) {

    UiHandlerEventModeVisibility handler = new UiHandlerEventModeVisibility(showIfEditable);
    widget.addEventHandler(handler);
  }

}
