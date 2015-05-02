/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api.attribute;

/**
 * This interface gives read access to the {@link #isDisposed() disposed} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public interface AttributeReadDisposed {

  /**
   * This method tests if this object has been {@link AttributeWriteDisposed#dispose() disposed}. A disposed
   * object can NOT be used anymore.
   * 
   * @see AttributeWriteDisposed#dispose()
   * 
   * @return <code>true</code> if this object has been disposed.
   */
  boolean isDisposed();

}
