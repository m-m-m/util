/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.common;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface ButtonContainer {

  /**
   * This method starts a new button-group and adds it to this container. All buttons (or widgets) added to
   * this {@link ButtonContainer container} until the button-group was {@link #endGroup() ended} will be added
   * to the current button-group.
   * 
   * @throws IllegalStateException if the previous button-group has NOT been {@link #endGroup() ended}.
   * 
   * @see #endGroup()
   */
  void startGroup() throws IllegalStateException;

  /**
   * This method ends a {@link #startGroup() previously started group}.
   * 
   * @return <code>true</code> if the current button-group has successfully been ended, <code>false</code>
   *         otherwise (if there was no such button-group).
   */
  boolean endGroup();

}
