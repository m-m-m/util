/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.api;

/**
 * This is the interface that {@link #getHashKey(Object) provides a hash key} for an object of type {@literal <O>}. <br>
 * Common implementations will return e.g. one of the following:
 * <ul>
 * <li>the given object itself</li>
 * <li>a {@link net.sf.mmm.util.collection.base.HashKey} wrapping the given object</li>
 * <li>a single property of the object (e.g. its {@link net.sf.mmm.util.lang.api.attribute.AttributeReadId#getId() ID})
 * </li>
 * </ul>
 *
 * @param <O> is the generic type of the object to {@link #getHashKey(Object) get hash keys} for.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public interface HashKeyProvider<O> {

  /**
   *
   * @param object is the object to get the hash key for.
   * @return an object that uniquely identifies the given {@code object}. The {@link Object#equals(Object) equals} and
   *         {@link Object#hashCode() hashCode} methods of the returned object have to satisfy that if two objects
   *         {@code o1} and {@code o2} are considered equal then {@code getHashKey(o1).equals(getHashKey(o2))} and
   *         {@code getHashKey(o1).hashCode() == getHashKey(o2).hashCode()}.
   */
  Object getHashKey(O object);

}
