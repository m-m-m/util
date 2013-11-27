/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.handler.plain;

/**
 * This is the {@link UiHandlerPlain} for the action {@link #onApply(Object) apply}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiHandlerPlainApply extends UiHandlerPlain {

  /**
   * This method is invoked for the action <em>apply</em>. This means that some change is immediately applied
   * to something so you will see the result. This is similar to {@link UiHandlerPlainSubmit#onSubmit(Object)
   * submit} but submit often indicates that a change is send to the server while apply typically makes the
   * change take effect visible locally.
   * 
   * @param variant is optional the {@link net.sf.mmm.util.lang.api.Variant} to use.
   */
  void onApply(Object variant);

}
