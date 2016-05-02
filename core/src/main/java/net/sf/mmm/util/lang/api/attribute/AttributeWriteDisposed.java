/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api.attribute;

/**
 * This interface gives read and write access to the {@link #isDisposed() disposed} attribute of an object. It
 * is extended or implemented by objects that can be {@link #dispose() disposed}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public interface AttributeWriteDisposed extends AttributeReadDisposed {

  /**
   * This method disposes this object. The resources of the object are deallocated and the object can not be
   * used or displayed anymore. If this is an object of the UI (user interface) it will be detached.
   */
  void dispose();

}
