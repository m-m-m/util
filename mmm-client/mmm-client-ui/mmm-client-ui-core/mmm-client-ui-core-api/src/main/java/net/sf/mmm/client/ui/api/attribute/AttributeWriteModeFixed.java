/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

import net.sf.mmm.client.ui.api.common.UiMode;

/**
 * This interface gives read and write access to {@link #getModeFixed() fixed mode} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteModeFixed extends AttributeReadModeFixed {

  /**
   * This method sets the {@link #getModeFixed() fixed mode} of this object. This will
   * {@link AttributeWriteMode#setMode(UiMode) set} the {@link AttributeReadMode#getMode() mode} to the given
   * mode (if not <code>null</code>, recursively) and then mark the mode as fixed so
   * {@link AttributeWriteMode#setMode(UiMode)} will have no effect anymore. The {@link #getModeFixed() fixed
   * mode} itself will NOT be set recursively.<br/>
   * The typical use-case is to set the fixed mode to {@link UiMode#VIEW} in order to prevent a UI widget (a
   * field or even an entire sub-dialog) from switching to edit-mode ({@link UiMode#EDIT}) so the UI widget
   * will always remain read-only.
   * 
   * @param mode is the new fixed {@link UiMode} to set. May be <code>null</code> to unset fixation.
   */
  void setModeFixed(UiMode mode);

}
