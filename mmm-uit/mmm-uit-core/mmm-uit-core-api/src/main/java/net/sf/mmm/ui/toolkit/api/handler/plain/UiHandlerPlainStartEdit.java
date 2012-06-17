/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.handler.plain;

/**
 * This is the {@link UiHandlerPlain} for the action {@link #onStartEditMode() start edit mode}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiHandlerPlainStartEdit extends UiHandlerPlain {

  /**
   * This method is invoked for the action <em>start edit mode</em>, typically labeled with <em>edit</em>.
   * This means the current dialog is
   * {@link net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteMode#setMode(net.sf.mmm.ui.toolkit.api.common.UiMode)
   * switched} to the {@link net.sf.mmm.ui.toolkit.api.common.UiMode#EDIT edit mode}.
   */
  void onStartEditMode();

}
