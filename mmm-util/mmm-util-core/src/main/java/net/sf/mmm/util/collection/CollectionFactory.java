/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection;

import java.util.Collection;

/**
 * This is the interface for a factory of {@link Collection}s. It allows to
 * abstract from {@link Collection} implementations.<br>
 * A {@link Collection} instance can be used for different purposes such as a
 * set or a list or with different aspects such as a thread-safe collection. If
 * you write a generic component different users of that component may expect
 * different aspects of your component and therefore the underlying
 * {@link Collection}.<br>
 * If you use this interface and allow the user to
 * {@link javax.annotation.Resource inject} an instance of this interface to
 * override the default, your code will increase usability.<br>
 * <br>
 * <b>Why passing a {@link CollectionFactory} rather than a {@link Collection}
 * instance to the constructor?</b><br>
 * Since java 5 you want to use generics for type-safe code. If these generic
 * types change slightly over the time of development (e.g. from
 * <code>Class</code> to <code>Class&lt;?&gt;</code>) you would break
 * compatibility of the users of your code. Additionally you may want to express
 * that the {@link Collection} should be empty and/or NOT shared with others.
 * Anyways the interface can obviously NOT guarantee this.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface CollectionFactory {

  /**
   * This method creates a new {@link Collection} instance.
   * 
   * @param <E> the type of elements contained in the collection.
   * @return the new collection instance.
   */
  <E> Collection<E> create();

  /**
   * This method creates a new {@link Collection} instance.
   * 
   * @param <E> the type of elements contained in the collection.
   * @param capacity is the initial capacity of the collection.
   * @return the new collection instance.
   */
  <E> Collection<E> create(int capacity);

}
