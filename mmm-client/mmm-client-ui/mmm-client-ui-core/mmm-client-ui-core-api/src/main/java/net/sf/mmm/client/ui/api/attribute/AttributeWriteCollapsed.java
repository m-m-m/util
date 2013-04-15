/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This interface gives read and write access to the {@link #isCollapsed() collapsed} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteCollapsed extends AttributeReadCollapsed {

  /**
   * This method sets the {@link #isCollapsed() collapsed} status of this object.
   * 
   * @param collapsed - if <code>true</code> the object will be collapsed, else it will be expanded.
   */
  void setCollapsed(boolean collapsed);

}
