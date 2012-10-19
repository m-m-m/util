/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.handler.plain;

/**
 * This is the {@link UiHandlerPlain} for the action {@link #onCancel() cancel}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiHandlerPlainCancel extends UiHandlerPlain {

  /**
   * This method is invoked for the action <em>cancel</em>. This means the current steps or changes will be
   * aborted, discarded or reverted.
   */
  void onCancel();

}
