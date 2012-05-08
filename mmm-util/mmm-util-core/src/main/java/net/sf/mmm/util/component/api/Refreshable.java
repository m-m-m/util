/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.component.api;

/**
 * This is the interface for an object that can be {@link #refresh() refreshed}.
 * 
 * @see java.io.Closeable
 * @see java.io.Flushable
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public interface Refreshable {

  /**
   * This method refreshes the given object. This can be anything from reloading configuration to rebuilding
   * the internal state. An invocation of this method may be expensive and should only be invoked as needed.
   * The implementation of this method should be thread-safe if the object itself is to be used by separate
   * consumers at all.
   * 
   * @return <code>true</code> if something has changed, <code>false</code> otherwise. If the implementation
   *         can not determine the change it should return <code>true</code>.
   */
  boolean refresh();

}
