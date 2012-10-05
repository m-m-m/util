/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read and write access to the {@link #isPaused() paused} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWritePaused extends AttributeReadPaused {

  /**
   * This method sets the {@link #isPaused() paused} attribute programatically. If set to <code>true</code>
   * the process (e.g. playback) is paused at the current state. After another call with <code>false</code> it
   * gets resumed at the previous position.
   * 
   * @see #isPaused()
   * 
   * @param paused is the new value of {@link #isPaused()}. Use <code>true</code> to pause and
   *        <code>false</code> to resume.
   */
  void setPaused(boolean paused);

}
