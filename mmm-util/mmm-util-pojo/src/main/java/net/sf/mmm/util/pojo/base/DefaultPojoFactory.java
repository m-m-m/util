/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.base;

import java.util.Collection;
import java.util.Map;

import net.sf.mmm.util.collection.CollectionFactoryManager;
import net.sf.mmm.util.collection.CollectionFactoryManagerImpl;
import net.sf.mmm.util.collection.MapFactory;
import net.sf.mmm.util.reflect.InstantiationFailedException;

/**
 * This is the default implementation of the
 * {@link net.sf.mmm.util.pojo.api.PojoFactory} interface.<br>
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
   * This method is invoked from {@link #newInstance(Class)} if the given
   * {@link Class} is an {@link Class#isInterface() interface}.
   * 
   * @param <POJO> is the generic type of the
   *        {@link net.sf.mmm.util.pojo.api.Pojo} to create.
   * @param pojoInterface is the interface reflecting the
   *        {@link net.sf.mmm.util.pojo.api.Pojo} to create.
   * @return the new instance of the given <code>pojoType</code>.
   * @throws InstantiationFailedException if the instantiation failed.
   */
  @SuppressWarnings("unchecked")
  protected <POJO> POJO newInstanceForInterface(Class<POJO> pojoInterface)
      throws InstantiationFailedException {

    if (Collection.class.isAssignableFrom(pojoInterface)) {
      return pojoInterface.cast(getCollectionFactoryManager().getCollectionFactory(
          (Class<? extends Collection>) pojoInterface).createGeneric());
    } else if (Map.class.isAssignableFrom(pojoInterface)) {
      MapFactory mapFactory = getCollectionFactoryManager().getMapFactory(
          (Class<? extends Map>) pojoInterface);
      if (mapFactory == null) {
        throw new InstantiationFailedException(pojoInterface);
      }
      return pojoInterface.cast(mapFactory.createGeneric());
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
    } catch (InstantiationFailedException e) {
      throw e;
    } catch (Exception e) {
      throw new InstantiationFailedException(e, pojoType);
    }
  }
}
