/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.base;

import java.util.Collection;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import net.sf.mmm.util.collection.api.CollectionFactory;
import net.sf.mmm.util.collection.api.CollectionFactoryManager;
import net.sf.mmm.util.collection.api.MapFactory;
import net.sf.mmm.util.collection.impl.CollectionFactoryManagerImpl;
import net.sf.mmm.util.reflect.api.InstantiationFailedException;

/**
 * This is the default implementation of the {@link net.sf.mmm.util.pojo.api.PojoFactory} interface. <br>
 * It extends {@link SimplePojoFactory} with the ability to create {@link Map}s and {@link Collection}s from
 * their interfaces.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
@Singleton
public class DefaultPojoFactory extends SimplePojoFactory {

  private  CollectionFactoryManager collectionFactoryManager;

  /**
   * The constructor.
   */
  public DefaultPojoFactory() {

    super();
    this.collectionFactoryManager = null;
  }

  /**
   * The constructor.
   * 
   * @param collectionFactoryManager is the {@link CollectionFactoryManager} instance used to create
   *        {@link Map}s and {@link Collection}s.
   */
  public DefaultPojoFactory(CollectionFactoryManager collectionFactoryManager) {

    super();
    this.collectionFactoryManager = collectionFactoryManager;
  }

  /**
   * This method gets the {@link CollectionFactoryManager} instance used to create {@link Map}s and
   * {@link Collection}s.
   * 
   * @return the {@link CollectionFactoryManager} instance.
   */
  protected CollectionFactoryManager getCollectionFactoryManager() {

    return this.collectionFactoryManager;
  }

  /**
   * @param collectionFactoryManager is the {@link CollectionFactoryManager} instance used to create
   *        {@link Map}s and {@link Collection}s.
   */
  @Inject
  public void setCollectionFactoryManager(CollectionFactoryManager collectionFactoryManager) {

    getInitializationState().requireNotInitilized();
    this.collectionFactoryManager = collectionFactoryManager;
  }

  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.collectionFactoryManager == null) {
      this.collectionFactoryManager = CollectionFactoryManagerImpl.getInstance();
    }
  }

  /**
   * This method is invoked from {@link #newInstance(Class)} if the given {@link Class} is an
   * {@link Class#isInterface() interface}.
   * 
   * @param <POJO> is the generic type of the {@link net.sf.mmm.util.pojo.api.Pojo} to create.
   * @param pojoInterface is the interface reflecting the {@link net.sf.mmm.util.pojo.api.Pojo} to create.
   * @return the new instance of the given {@code pojoType}.
   * @throws InstantiationFailedException if the instantiation failed.
   */
  @Override
  @SuppressWarnings({ "rawtypes", "unchecked" })
  protected <POJO> POJO newInstanceForInterface(Class<POJO> pojoInterface) throws InstantiationFailedException {

    if (Collection.class.isAssignableFrom(pojoInterface)) {
      CollectionFactory<? extends Collection> collectionFactory = getCollectionFactoryManager().getCollectionFactory(
          (Class<? extends Collection>) pojoInterface);
      if (collectionFactory == null) {
        throw new InstantiationFailedException(pojoInterface);
      }
      return pojoInterface.cast(collectionFactory.createGeneric());
    } else if (Map.class.isAssignableFrom(pojoInterface)) {
      MapFactory mapFactory = getCollectionFactoryManager().getMapFactory((Class<? extends Map>) pojoInterface);
      if (mapFactory == null) {
        throw new InstantiationFailedException(pojoInterface);
      }
      return pojoInterface.cast(mapFactory.createGeneric());
    }
    return null;
  }

}
