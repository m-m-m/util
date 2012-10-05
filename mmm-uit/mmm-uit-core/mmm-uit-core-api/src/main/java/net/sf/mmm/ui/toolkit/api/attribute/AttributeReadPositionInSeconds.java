/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read access to the {@link #getPositionInSeconds() position in seconds} attribute of an
 * object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadPositionInSeconds {

  /**
   * This method gets the current position of a playback in seconds. A value of <code>0.0</code> indicates the
   * start of the playback.
   * 
   * @return the current position of the playback in seconds.
   */
  double getPositionInSeconds();

}
