/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.base;

import net.sf.mmm.util.collection.api.CollectionFactoryManager;
import net.sf.mmm.util.reflect.api.InstantiationFailedException;

/**
 * This is the default implementation of the
 * {@link net.sf.mmm.util.pojo.api.PojoFactory} interface.<br>
 * In advance to {@link DefaultPojoFactory} it tries to find implementations for
 * interfaces by guessing according to conventions:<br>
 * For an interface <code>some.package.api.Foo</code> it will try to find the
 * following classes...
 * <ul>
 * <li><code>some.package.api.FooImpl</code></li>
 * <li><code>some.package.api.impl.FooImpl</code></li>
 * <li><code>some.package.impl.FooImpl</code> (only if package of interface ends
 * with ".api")</li>
 * </ul>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class GuessingPojoFactory extends DefaultPojoFactory {

  /** The conventional suffix for an implementation class. */
  private static final String IMPL_CLASS_SUFFIX = "Impl";

  /** The conventional suffix for an implementation package. */
  private static final String IMPL_PKG_SUFFIX = "impl.";

  /** The conventional suffix for an api package. */
  private static final String API_PKG_SUFFIX = "api.";

  /**
   * The constructor.
   */
  public GuessingPojoFactory() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param collectionFactoryManager is the {@link CollectionFactoryManager}
   *        instance used to create {@link java.util.Map}s and
   *        {@link java.util.Collection}s.
   */
  public GuessingPojoFactory(CollectionFactoryManager collectionFactoryManager) {

    super(collectionFactoryManager);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected <POJO> POJO newInstanceForInterface(Class<POJO> pojoInterface)
      throws InstantiationFailedException {

    POJO pojo = super.newInstanceForInterface(pojoInterface);
    if (pojo == null) {
      String packageName = pojoInterface.getPackage().getName() + ".";
      String className = pojoInterface.getSimpleName() + IMPL_CLASS_SUFFIX;
      Class<?> implementation = null;
      try {
        implementation = Class.forName(packageName + className);
      } catch (ClassNotFoundException e) {
        try {
          implementation = Class.forName(packageName + IMPL_PKG_SUFFIX + className);
        } catch (ClassNotFoundException e1) {
          if (packageName.endsWith(API_PKG_SUFFIX)) {
            String implPackageName = packageName.substring(0, packageName.length()
                - API_PKG_SUFFIX.length());
            try {
              implementation = Class.forName(implPackageName + className);
            } catch (ClassNotFoundException e2) {
              // ignore...
            }
          }
        }
      }
      if (implementation != null) {
        if (pojoInterface.isAssignableFrom(implementation)) {
          pojo = pojoInterface.cast(newInstanceForClass(implementation));
        } else {
          getLogger().warn(
              "Class '" + implementation + "' does NOT implement '" + pojoInterface + "'!");
        }
      }
    }
    return pojo;
  }

}
