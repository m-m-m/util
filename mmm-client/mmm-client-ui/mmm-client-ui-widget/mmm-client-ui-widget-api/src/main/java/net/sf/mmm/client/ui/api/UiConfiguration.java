/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api;


/**
 * This is the interface for the configuration of the UI (user interface).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiConfiguration {

  /** The default value for {@link #getTheme() theme}. */
  String DEFAULT_THEME = "standard";

  /**
   * This method gets the path for the current theme. This is attached to all relative image URLs. You can use
   * this to switch to a different theme in order to get a different appearance of the UI. Typically you
   * decide for a theme at build-time. However, this allows to switch themes at runtime.
   * 
   * @return the URL path for the current theme.
   */
  String getTheme();

  // focus on set edit mode: first editable field, off, ...
  // focus on validation failure: first invalid field, off, ...
  // validation failure handling: popup, message panel, popup & message panel, ...
  // tab control: tab passive fields, only active fields (accessibility setting?)

}
