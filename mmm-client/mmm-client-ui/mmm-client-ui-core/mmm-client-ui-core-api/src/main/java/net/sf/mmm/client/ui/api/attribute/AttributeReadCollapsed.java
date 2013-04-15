/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This interface gives read access to the {@link #isCollapsed() collapsed} attribute of an object.
 * 
 * @see AttributeWriteCollapsed
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadCollapsed {

  /**
   * This method determines if this object is <em>collapsed</em>. This means that some content is hidden
   * (folded away). If it is NOT collapsed, it is called <em>expanded</em> and the content gets visible again.
   * 
   * @return <code>true</code> if this object is collapsed, <code>false</code> if expanded.
   */
  boolean isCollapsed();

}
