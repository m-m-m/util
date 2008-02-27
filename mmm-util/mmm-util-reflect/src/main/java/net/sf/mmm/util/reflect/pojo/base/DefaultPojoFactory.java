/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.base;

import java.util.Collection;
import java.util.Map;

import net.sf.mmm.util.collection.CollectionFactoryManager;
import net.sf.mmm.util.collection.CollectionFactoryManagerImpl;
import net.sf.mmm.util.reflect.InstantiationFailedException;

/**
 * This is the default implementation of the
 * {@link net.sf.mmm.util.reflect.pojo.api.PojoFactory} interface.<br>
 * It extends {@link SimplePojoFactory} with the ability to create {@link Map}s
 * and {@link Collection}s from their interfaces.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class DefaultPojoFactory extends SimplePojoFactory {

  /** @see #getCollectionFactoryManager() */
  private CollectionFactoryManager collectionFactoryManager;

  /**
   * The constructor.
   */
  public DefaultPojoFactory() {

    this(CollectionFactoryManagerImpl.getInstance());
  }

  /**
   * The constructor.
   * 
   * @param collectionFactoryManager is the {@link CollectionFactoryManager}
   *        instance used to create {@link Map}s and {@link Collection}s.
   */
  public DefaultPojoFactory(CollectionFactoryManager collectionFactoryManager) {

    super();
    this.collectionFactoryManager = collectionFactoryManager;
  }

  /**
   * This method gets the {@link CollectionFactoryManager} instance used to
   * create {@link Map}s and {@link Collection}s.
   * 
   * @return the {@link CollectionFactoryManager} instance.
   */
  protected CollectionFactoryManager getCollectionFactoryManager() {

    return this.collectionFactoryManager;
  }

  /**
   * 
   * @param <POJO>
   * @param pojoInterface
   * @return
   * @throws InstantiationFailedException
   */
  protected <POJO> POJO newInstanceForInterface(Class<POJO> pojoInterface)
      throws InstantiationFailedException {

    if (Collection.class.isAssignableFrom(pojoInterface)) {
      return pojoInterface.cast(getCollectionFactoryManager().getCollectionFactory(
          (Class<? extends Collection>) pojoInterface).createGeneric());
    } else if (Map.class.isAssignableFrom(pojoInterface)) {
      return pojoInterface.cast(getCollectionFactoryManager().getMapFactory(
          (Class<? extends Map>) pojoInterface).createGeneric());
    }
    throw new InstantiationFailedException(pojoInterface);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <POJO> POJO newInstance(Class<POJO> pojoType) throws InstantiationFailedException {

    try {
      if (pojoType.isInterface()) {
        return newInstanceForInterface(pojoType);
      }
      return super.newInstance(pojoType);
    } catch (Exception e) {
      throw new InstantiationFailedException(e, pojoType);
    }
  }
}
