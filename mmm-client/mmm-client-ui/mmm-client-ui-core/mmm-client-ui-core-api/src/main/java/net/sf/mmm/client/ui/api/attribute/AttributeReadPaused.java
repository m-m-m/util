/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This interface gives read access to the {@link #isPaused() paused} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadPaused {

  /**
   * This method gets the paused status of this object.
   * 
   * @return <code>true</code> if this object is currently paused, <code>false</code> otherwise (if it is
   *         running/playing).
   */
  boolean isPaused();

}
