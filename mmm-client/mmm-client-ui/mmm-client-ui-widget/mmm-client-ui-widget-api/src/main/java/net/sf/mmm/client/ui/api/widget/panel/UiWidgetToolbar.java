/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.panel;

import net.sf.mmm.client.ui.api.widget.UiWidgetNative;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;

/**
 * This is the interface for a {@link UiWidgetDynamicPanel dynamic panel} that represents a <em>toolbar</em>.
 * Such toolbar typically contains a number of
 * {@link net.sf.mmm.client.ui.api.widget.core.UiWidgetAbstractButton buttons} that have an icon instead of a
 * label. To group related buttons in a {@link UiWidgetButtonGroup} you may use {@link #startGroup()} and
 * {@link #endGroup()}.<br/>
 * As a toolbar typically contains many icon buttons it has its own focus management: Only one button of the
 * toolbar is <em>active</em> and will receive the focus when you tab. Using arrow left/right keys you can
 * move the focus to the previous/next button that will then become active.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetToolbar extends UiWidgetDynamicPanel<UiWidgetRegular>, UiWidgetNative {

  /** The default {@link #getPrimaryStyle() primary style} of this widget. */
  String STYLE_PRIMARY = "Toolbar";

  /**
   * This method starts a new {@link UiWidgetButtonGroup} and {@link #addChild(UiWidgetRegular) adds} it to
   * this toolbar. All {@link UiWidgetRegular widgets} {@link #addChild(UiWidgetRegular) added} next will be
   * added to the new {@link UiWidgetButtonGroup} instead.
   * 
   * @throws IllegalStateException if the previous {@link UiWidgetButtonGroup} has NOT been
   *         {@link #endGroup() ended}.
   * 
   * @see #endGroup()
   */
  void startGroup() throws IllegalStateException;

  /**
   * This method ends a {@link #startGroup() previously started group}.
   * 
   * @return <code>true</code> if the current {@link UiWidgetButtonGroup} has successfully been ended,
   *         <code>false</code> otherwise (if there was no such {@link UiWidgetButtonGroup}).
   */
  boolean endGroup();

}
