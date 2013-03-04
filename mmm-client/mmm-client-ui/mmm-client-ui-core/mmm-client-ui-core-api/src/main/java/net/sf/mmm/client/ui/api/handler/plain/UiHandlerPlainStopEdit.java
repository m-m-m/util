/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.handler.plain;

/**
 * This is the {@link UiHandlerPlain} for the action {@link #onStopEditMode(Object) stop edit mode}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiHandlerPlainStopEdit extends UiHandlerPlain {

  /**
   * This method is invoked for the action <em>stop edit mode</em>, typically labeled with <em>revert</em>.
   * This means that the changes in the (sub) dialog are discarded and it is
   * {@link net.sf.mmm.client.ui.api.attribute.AttributeWriteMode#setMode(net.sf.mmm.client.ui.api.common.UiMode)
   * switched} to the {@link net.sf.mmm.client.ui.api.common.UiMode#VIEW view mode}. If the current dialog is
   * {@link net.sf.mmm.client.ui.api.attribute.AttributeReadModified#isModified() modified} a popup should ask
   * the user to confirm if he really wants to discard his changes or likes to cancel the operation.
   * 
   * @param variant is optional the {@link net.sf.mmm.util.lang.api.Variant} to use.
   */
  void onStopEditMode(Object variant);

}
