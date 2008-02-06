/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection;

import java.util.Collection;

/**
 * This is the interface for a manager of {@link CollectionFactory} instances.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface CollectionFactoryManager {

  /**
   * This method gets the {@link CollectionFactory} for the given
   * <code>collectionType</code>.
   * 
   * @param <C> is the generic type of the {@link Collection}.
   * @param collectionType is the type of the {@link Collection}. This should
   *        be the {@link Collection} interface such as
   *        <code>{@link java.util.List}.class</code>.
   * @return the {@link CollectionFactory} for the given
   *         <code>collectionType</code>. The <code>collectionType</code>
   *         has to be {@link Class#isAssignableFrom(Class) assignable from}
   *         {@link CollectionFactory#getCollectionInterface()} of the returned
   *         instance. Typically it will be equal.
   */
  <C extends Collection> CollectionFactory<C> getFactory(Class<C> collectionType);

}
