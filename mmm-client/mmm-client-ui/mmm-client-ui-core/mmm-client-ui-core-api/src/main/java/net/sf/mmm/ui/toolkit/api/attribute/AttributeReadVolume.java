/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read access to the {@link #getVolume() volume} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadVolume {

  /**
   * This method gets the volume of this object. Please note that the actual volume might be influenced by the
   * mixer of the operating system. This is just a relative scale to the system volume.
   * 
   * @return the volume in the range from <code>0.0</code> (mute) to <code>1.0</code> (maximum).
   */
  double getVolume();

}
