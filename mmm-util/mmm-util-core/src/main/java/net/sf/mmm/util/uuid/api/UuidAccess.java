/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.uuid.api;

import net.sf.mmm.util.uuid.base.RandomUuidFactory;

/**
 * This is an ugly static accessor for the {@link UuidFactory} used to create
 * instances of {@link java.util.UUID} and allowing to exchange the default
 * implementation.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class UuidAccess {

  /** @see #getFactory() */
  private static UuidFactory factory = new RandomUuidFactory();

  /**
   * The constructor.
   */
  private UuidAccess() {

    super();
  }

  /**
   * This method gets the {@link UuidFactory} used to create
   * {@link java.util.UUID}s.
   * 
   * @return the {@link UuidFactory} to use.
   */
  public static UuidFactory getFactory() {

    return factory;
  }

  /**
   * This method sets (overrides) the {@link UuidFactory}. This allows to
   * exchange the {@link UuidFactory} and thereby the type of the created
   * {@link java.util.UUID}s.<br>
   * <b>ATTENTION:</b><br>
   * No synchronization is performed setting the factory instance. This assumes
   * that an assignment is an atomic operation in the JVM you are using.
   * Additionally this method should only be invoked in the initialization phase
   * of your application.
   * 
   * @param factory is the factory to set.
   */
  public static void setFactory(UuidFactory factory) {

    UuidAccess.factory = factory;
  }

}
