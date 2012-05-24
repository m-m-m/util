/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.handler.plain;

/**
 * This is the {@link UiHandlerPlain} for the action {@link #onConfirm() confirm}.
 * 
 * @see UiHandlerPlainApprove
 * @see UiHandlerPlainSubmit
 * 
 * @author hohwille
 * @since 1.0.0
 */
public interface UiHandlerPlainConfirm extends UiHandlerPlain {

  /**
   * This method is invoked for the action <em>confirm</em>. This means the current step or data is
   * acknowledged.
   */
  void onConfirm();

}
