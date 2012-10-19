/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.common;

/**
 * This enum contains the available values for the scrolling. This is the behavior of a scrollbar.
 * 
 * @see javax.swing.ScrollPaneConstants
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public enum ScrollbarPolicy {

  /**
   * Scrollbar and scrolling is disabled.
   */
  NEVER,

  /**
   * The scrollbar will always be visible - even if there is enough space and nothing to scroll.
   */
  ALWAYS,

  /**
   * The scrollbar will appear as needed. It is visible only if there is not enough space for the content and
   * will disappear otherwise. This will auto adjust dynamically when the content changes.
   */
  DYNAMIC

}
