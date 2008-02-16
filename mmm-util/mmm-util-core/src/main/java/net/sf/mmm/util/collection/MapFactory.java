/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection;

import java.util.HashMap;
import java.util.Map;

/**
 * This is the interface for a factory of {@link Map maps}. It allows to
 * abstract from {@link Map} implementations.<br>
 * A {@link Map} instance can be used for different purposes such as a cache or
 * with different aspects such as a thread-safe map. If you write a generic
 * component different users of that component may expect different aspects of
 * your component and therefore the underlying {@link Map}.<br>
 * If you use this interface and allow the user to
 * {@link javax.annotation.Resource inject} an instance of this interface to
 * override the default, your code will increase usability.<br>
 * <b>Why passing a {@link MapFactory} rather than a {@link Map} instance to the
 * constructor?</b><br>
 * Since java 5 you want to use generics for type-safe code. If these generic
 * types change slightly over the time of development (e.g. from
 * <code>Class</code> to <code>Class&lt;?&gt;</code>) you would break
 * compatibility of the users of your code. Additionally you may want to express
 * that the {@link Map} should be empty and/or NOT shared with others. Anyways
 * the interface can obviously NOT guarantee this.
 * 
 * @param <MAP> is the generic {@link Map}-type.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("unchecked")
public interface MapFactory<MAP extends Map> {

  /**
   * This method gets the interface of the {@link Map} managed by this factory.
   * 
   * @return the {@link Map} interface.
   */
  Class<MAP> getMapInterface();

  /**
   * This method gets the implementation of the
   * {@link #getMapInterface() map-interface} used by this factory.
   * 
   * @return the {@link Map} implementation.
   */
  Class<? extends MAP> getMapImplementation();

  /**
   * This method creates a new {@link Map} instance.<br>
   * It is explicitly typed and respects the generic key and value type of the
   * map. Therefore the type of the {@link Map} can NOT be bound to the generic
   * type <code>&lt;MAP&gt;</code> because of limitations in Java's generic
   * type system. You need to work on the actual sub-interface (e.g.
   * {@link SortedMapFactory}) to get a more specific result type.
   * 
   * @param <K> the type of keys maintained by the map.
   * @param <V> the type of mapped values.
   * @return the new map instance.
   */
  <K, V> Map<K, V> create();

  /**
   * This method creates a new {@link Map} instance with the given
   * <code>capacity</code>. For a regular map this will be the initial
   * capacity while a cache may never grow beyond this capacity limit and if
   * reached force out entries last recently of frequently used.
   * 
   * @param <K> the type of keys maintained by the map.
   * @param <V> the type of mapped values.
   * @param capacity is the capacity of the map to create.
   * @return the new map instance.
   */
  <K, V> Map<K, V> create(int capacity);

  /**
   * This method creates a new instance of the generic {@link Map} type
   * <code>&lt;MAP&gt;</code>.
   * 
   * @return the new {@link Map} instance.
   */
  MAP createGeneric();

  /**
   * This method creates a new instance of the generic {@link Map} type
   * <code>&lt;MAP&gt;</code>.
   * 
   * @param capacity is the capacity of the map to create.
   * @return the new {@link Map} instance.
   */
  MAP createGeneric(int capacity);

  /** The default instance creating a {@link HashMap}. */
  MapFactory INSTANCE_HASH_MAP = new AbstractMapFactory() {

    /**
     * {@inheritDoc}
     */
    public Class<? extends Map> getMapImplementation() {

      return HashMap.class;
    }

    /**
     * {@inheritDoc}
     */
    public <K, V> Map<K, V> create() {

      return new HashMap<K, V>();
    };

    /**
     * {@inheritDoc}
     */
    public <K, V> Map<K, V> create(int capacity) {

      return new HashMap<K, V>(capacity);
    }
  };

}
