/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

/**
 * This is the interface for a generic visitor that may {@link #visit(Object)} values of a specific type.
 * 
 * @param <V> is the generic type to {@link #visit(Object) visit}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public interface Visitor<V> {

  /**
   * This method is called to let the visitor inspect the given <code>value</code>.
   * 
   * @param value is the value to visit. An API that offers a {@link Visitor} as argument should specify
   *        further details about the value - especially if it may be <code>null</code>.
   */
  void visit(V value);

}
