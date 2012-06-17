/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.handler.plain;

/**
 * This is the {@link UiHandlerPlain} for the action {@link #onSave() save}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiHandlerPlainSave extends UiHandlerPlain {

  /**
   * This method is invoked for the action <em>save</em>. This means that a step, change or creation is saved
   * to a persistent store.
   */
  void onSave();

}
