/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.handler.plain;

/**
 * This is the {@link UiHandlerPlain} for the action {@link #onStopEditMode() stop edit mode}.
 * 
 * @author hohwille
 * @since 1.0.0
 */
public interface UiHandlerPlainStopEdit extends UiHandlerPlain {

  /**
   * This method is invoked for the action <em>stop edit mode</em>, typically labeled with <em>revert</em>.
   * This means that the changes in the (sub) dialog are discarded and it is
   * {@link net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteMode#setMode(net.sf.mmm.ui.toolkit.api.common.UiMode)
   * switched} to the {@link net.sf.mmm.ui.toolkit.api.common.UiMode#VIEW view mode}. If the current dialog is
   * {@link net.sf.mmm.ui.toolkit.api.attribute.AttributeReadModified#isModified() modified} a popup should
   * ask the user to confirm if he really wants to discard his changes or likes to cancel the operation.
   */
  void onStopEditMode();

}
