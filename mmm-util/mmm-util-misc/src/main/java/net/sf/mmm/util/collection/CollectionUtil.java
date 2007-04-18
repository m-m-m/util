/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * This class is a collection of utility functions for dealing with
 * {@link Collections}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class CollectionUtil {

  /** @see #create(Class) */
  private static final Map<Class<? extends Collection>, Class<? extends Collection>> COLLECTION_IMPLEMENTATIONS;

  static {
    COLLECTION_IMPLEMENTATIONS = new HashMap<Class<? extends Collection>, Class<? extends Collection>>();
    COLLECTION_IMPLEMENTATIONS.put(Collection.class, ArrayList.class);
    COLLECTION_IMPLEMENTATIONS.put(List.class, ArrayList.class);
    COLLECTION_IMPLEMENTATIONS.put(Set.class, HashSet.class);
    COLLECTION_IMPLEMENTATIONS.put(SortedSet.class, TreeSet.class);
    COLLECTION_IMPLEMENTATIONS.put(Queue.class, LinkedList.class);
    COLLECTION_IMPLEMENTATIONS.put(Deque.class, LinkedList.class);
  }

  /**
   * The constructor. 
   */
  private CollectionUtil() {

    super();
  }

  /**
   * This method creates a collection implementing the given collection
   * <code>type</code>.
   * 
   * @param <C>
   *        is the templated type of the collection.
   * @param type
   *        is the type of collection to create. This is either an interface ({@link List},
   *        {@link Set}, {@link Queue}, etc.) or a non-abstract implementation
   *        of a {@link Collection}.
   * @return the new instance
   * @throws InstantiationException
   *         if the given <code>type</code> is a class that can NOT be
   *         instantiated via {@link Class#newInstance()}.
   * @throws IllegalAccessException
   *         if you do NOT have access to create an instance of
   *         <code>type</code>.
   */
  @SuppressWarnings("unchecked")
  public static <C extends Collection> C create(Class<C> type) throws InstantiationException,
      IllegalAccessException {

    Class<C> implementation;
    if (type.isInterface()) {
      implementation = (Class<C>) COLLECTION_IMPLEMENTATIONS.get(type);
      if (implementation == null) {
        throw new IllegalArgumentException("Unknown collection interface: " + type);
      }
    } else {
      implementation = type;
      if (Modifier.isAbstract(implementation.getModifiers())) {
        throw new IllegalArgumentException("Can NOT instantiate abstract class: " + type);
      }
    }
    return implementation.newInstance();
  }
}
