/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.handler.plain;

/**
 * This is the {@link UiHandlerPlain} for the action {@link #onNext() next}.
 * 
 * @see UiHandlerPlainPrevious
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiHandlerPlainNext extends UiHandlerPlain {

  /**
   * This method is invoked for the action <em>next</em>. This means that something should proceed to the next
   * object (page, dialog, song, movie, etc.).
   */
  void onNext();

}
