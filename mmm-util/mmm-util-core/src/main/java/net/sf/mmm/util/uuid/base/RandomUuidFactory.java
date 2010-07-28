/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.uuid.base;

import java.util.UUID;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.uuid.api.UuidFactory;

/**
 * This is an implementation of the {@link UuidFactory} interface that simply
 * delegates to {@link UUID#randomUUID()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
@Singleton
@Named
public class RandomUuidFactory implements UuidFactory {

  /**
   * The constructor.
   */
  public RandomUuidFactory() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public UUID createUuid() {

    return UUID.randomUUID();
  }

}
