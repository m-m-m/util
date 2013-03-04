/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.handler.plain;

/**
 * This is the {@link UiHandlerPlain} for the action {@link #onSubmit(Object) submit}.
 * 
 * @see UiHandlerPlainConfirm
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiHandlerPlainSubmit extends UiHandlerPlain {

  /**
   * This method is invoked for the action <em>submit</em>. This means that some step or change is completed
   * and shall be submitted. This is more generic than e.g. {@link UiHandlerPlainSave#onSave(Object) save} or
   * {@link UiHandlerPlainApprove#onApprove(Object) approve}.
   * 
   * @param variant is optional the {@link net.sf.mmm.util.lang.api.Variant} to use.
   */
  void onSubmit(Object variant);

}
