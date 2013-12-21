/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.common;

/**
 * This interface contains the names of common CSS
 * {@link net.sf.mmm.client.ui.api.attribute.AttributeReadStyles#getStyles() styles}.<br/>
 * <b>NOTE:</b><br/>
 * This interface is partially biased. However, we wanted to both have a central definition of the CSS style
 * constants AND a way to provide our internal widgets in a reusable way without a dependency on the
 * <code>UiWidget</code>-API so this is the best compromise done on purpose.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface CssStyles {

  /** The name of the CSS style for a separator row. */
  String GRID_ROW_SEPARATOR = "Separator";

  /** The name of the CSS style for a label. */
  String LABEL = "Label";

  /** The name of the CSS style for a link. */
  String LINK = "Link";

  /** The name of the CSS style for a section. */
  String SECTION = "Section";

  /** The name of the CSS style for a button to collapse/expand something (e.g. a {@link #SECTION}). */
  String COLLAPSE_BUTTON = "CollapseButton";

  /**
   * The name of the CSS style for a {@link #COLLAPSE_BUTTON} in collapsed state. Will be removed in expanded
   * state.
   */
  String COLLAPSED = "Collapsed";

  /** The name of the CSS style for a popup window. */
  String POPUP = "Popup";

  /** The name of the CSS style for a window. */
  String WINDOW = "Window";

  /** The name of the CSS style for the content panel inside a {@link #WINDOW}. */
  String WINDOW_CONTENT = "WindowContent";

  /** The name of the CSS style for the title-bar of a {@link #WINDOW} (inside {@link #WINDOW_CONTENT}). */
  String WINDOW_TITLE_BAR = "WindowTitleBar";

  /** The name of the CSS style for the title inside a {@link #WINDOW_TITLE_BAR}. */
  String WINDOW_TITLE = "WindowTitle";

  /** The name of the CSS style for the footer-bar of a {@link #WINDOW} (inside {@link #WINDOW_CONTENT}). */
  String WINDOW_FOOTER_BAR = "WindowFooterBar";

  /** The name of the CSS style for a the left border of a {@link #WINDOW}. */
  String BORDER_WEST = "BorderW";

  /** The name of the CSS style for a the right border of a {@link #WINDOW}. */
  String BORDER_EAST = "BorderE";

  /** The name of the CSS style for a the bottom border of a {@link #WINDOW}. */
  String BORDER_SOUTH = "BorderS";

  /** The name of the CSS style for a the bottom left corner of a {@link #WINDOW}. */
  String BORDER_SOUTH_WEST = "BorderSW";

  /** The name of the CSS style for a the bottom right corner of a {@link #WINDOW}. */
  String BORDER_SOUTH_EAST = "BorderSE";

  /** The name of the CSS style for a the top border of a {@link #WINDOW}. */
  String BORDER_NORTH = "BorderN";

  /** The name of the CSS style for a the top left corner of a {@link #WINDOW}. */
  String BORDER_NORTH_WEST = "BorderNW";

  /** The name of the CSS style for a the top right corner of a {@link #WINDOW}. */
  String BORDER_NORTH_EAST = "BorderNE";

  /** The name of the CSS style for a button. */
  String BUTTON = "Button";

  /**
   * The name of the additional CSS style for a {@link #BUTTON} that is toggled between normal and
   * {@link #PRESSED} state.
   */
  String TOGGLE_BUTTON = "ToggleButton";

  /** The name of the additional CSS style for a {@link #TOGGLE_BUTTON} that is pressed. */
  String PRESSED = "Pressed";

  /** The name of the CSS style for a button group. */
  String BUTTON_GROUP = "ButtonGroup";

  /** The name of the CSS style for a button panel. */
  String BUTTON_PANEL = "ButtonPanel";

  /** The name of the CSS style for a tab (in a tab-panel). */
  String TAB = "Tab";

  /** The name of the CSS style for a generic slot (where any sub-dialog content can be embedded). */
  String SLOT = "Slot";

  /** The name of the CSS style for a button separator. */
  String BUTTON_SEPARATOR = "ButtonSeparator";

  /** The name of the CSS style for a toolbar. */
  String TOOLBAR = "Toolbar";

  /** The name of the CSS style for an icon indicating a step to the next item. */
  String ICON_STEP_NEXT = "IconStepNext";

  /** The name of the CSS style for an icon indicating a step to the previous item. */
  String ICON_STEP_PREVIOUS = "IconStepPrevious";

  /** The name of the CSS style for an icon with an arrow up (e.g. move selected item up). */
  String ICON_UP = "IconUp";

  /** The name of the CSS style for an icon with an arrow down (e.g. move selected item down). */
  String ICON_DOWN = "IconDown";

  /** The name of the CSS style for a horizontal panel. */
  String HORIZONTAL_PANEL = "HorizontalPanel";

  /** The name of the CSS style for a vertical panel. */
  String VERTICAL_PANEL = "VerticalPanel";

  /** The name of the CSS style for an object that can be moved around by dragging. */
  String MOVABLE = "Movable";

  /** The name of the CSS style for the glass panel used for modal popups. */
  String GLASS_PANEL = "GlassPanel";

  /** The name of the CSS style for a label corresponding to a field. */
  String FIELD_LABEL = "FieldLabel";

  /** The name of the CSS style for a border panel (fieldset). */
  String BORDER_PANEL = "BorderPanel";

}
