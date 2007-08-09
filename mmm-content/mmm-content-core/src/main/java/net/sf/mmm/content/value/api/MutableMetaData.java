/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.value.api;

import java.io.Serializable;

/**
 * This is the interface for a set of meta-data.<br>
 * Unfortunately the {@link java.util.Map} interface is so overloaded from the
 * collection API that we needed to define our own interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface MutableMetaData extends Serializable {

  /** The name of this value type. */
  String VALUE_NAME = "MetaData";

  /**
   * This method gets the meta-data value for the given <code>key</code>.
   * 
   * @see java.util.Map#get(Object)
   * 
   * @param key is the key of the requested meta-data value.
   * @return the meta-data value for the given <code>key</code> or
   *         <code>null</code> if no meta-data is associated with this key.
   */
  Object getValue(String key);

  /**
   * This method sets the meta-data value for the given <code>key</code> to
   * the given <code>value</code>.
   * 
   * @see java.util.Map#put(Object, Object)
   * 
   * @param key is the key of the requested meta-data to set.
   * @param newValue is the new value to set.
   */
  void setValue(String key, Object newValue);

  /**
   * This method removes the meta-data value for the given <code>key</code>.
   * 
   * @see java.util.Map#remove(Object)
   * 
   * @param key is the key of the requested meta-data to remove.
   * @return the previous value associated with <code>key</code>, or
   *         <code>null</code> if there was no mapping for <code>key</code>.
   */
  Object removeValue(String key);

}
