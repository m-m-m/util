/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.common;

/**
 * This enum contains the available values for the style of a
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiButton} or
 * {@link net.sf.mmm.ui.toolkit.api.view.menu.UiMenuItem}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public enum ButtonStyle {

  /**
   * This is the default style. The according button will be stateless and can
   * be selected by the user and automatically gets unselected.
   */
  DEFAULT,

  /**
   * This is the style for a checkbox-button. Such button has a selection-status
   * typically visualized by a box that contains a checkmark or not. If the
   * button is selected, its selection-status will be inverted. The initial
   * selection-status is de-selected (NOT selected).
   */
  CHECK,

  /**
   * This is the style for a toggle-button. The behavior is the same as
   * {@link #CHECK} except for the visualization. A toggle-button is viewed like
   * a {@link #DEFAULT default} button, but if it is selected, its border will
   * appear inverted so the button looks as it is pushed inside the GUI. After
   * the button is selected again, it will be de-selected and looks normal
   * again.<br>
   * ATTENTION: A {@link net.sf.mmm.ui.toolkit.api.view.menu.UiMenuItem} with
   * this style is unspecified.
   */
  TOGGLE,

  /**
   * This is the style for a radio-button. The according button is automatically
   * grouped with all other buttons of this style in the same
   * {@link net.sf.mmm.ui.toolkit.api.view.composite.UiComposite composite} or
   * {@link net.sf.mmm.ui.toolkit.api.view.menu.UiMenu menu}. Like for
   * {@link #CHECK} and {@link #TOGGLE} buttons of this style have a
   * selection-status. This is typically visualized by a circle or square that
   * is either filled or not. The difference in behavior is that the button gets
   * not de-selected if it is selected again but if another button of the same
   * group is selected.
   */
  RADIO;

}
