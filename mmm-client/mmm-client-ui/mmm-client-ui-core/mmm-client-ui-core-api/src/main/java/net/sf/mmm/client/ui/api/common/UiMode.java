/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.common;

/**
 * This interface represents the available modes of a UI object. It can be either in the mode {@link #VIEW} or
 * in the mode {@link #EDIT}.<br/>
 * <b>NOTE:</b><br/>
 * This is intentionally NOT realized as an enum type in order to allow extension for custom needs.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiMode {

  /**
   * The mode to show content in a read-only view.
   */
  UiMode VIEW = new UiMode() {
    // nothing to add...
  };

  /**
   * The mode to edit content.
   */
  UiMode EDIT = new UiMode() {
    // nothing to add...
  };

}
