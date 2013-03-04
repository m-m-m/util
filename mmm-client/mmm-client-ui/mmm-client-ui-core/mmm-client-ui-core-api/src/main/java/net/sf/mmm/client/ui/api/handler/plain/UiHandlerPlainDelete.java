/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.handler.plain;

/**
 * This is the {@link UiHandlerPlain} for the action {@link #onDelete(Object) delete}.
 * 
 * @see UiHandlerPlainRemove
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiHandlerPlainDelete extends UiHandlerPlain {

  /**
   * This method is invoked for the action <em>delete</em>. This means that something (e.g. an entity) is to
   * be deleted and will no longer exist.
   * 
   * @param variant is optional the {@link net.sf.mmm.util.lang.api.Variant} to use.
   */
  void onDelete(Object variant);

}
