/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.handler.plain;

/**
 * This is the {@link UiHandlerPlain} for the action {@link #onOpen() open}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiHandlerPlainOpen extends UiHandlerPlain {

  /**
   * This method is invoked for the action <em>open</em>. This means that something (e.g. an entity) is opened
   * - typically in a popup or new dialog.
   */
  void onOpen();

}
