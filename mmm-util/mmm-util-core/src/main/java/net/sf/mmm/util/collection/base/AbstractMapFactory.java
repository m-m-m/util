/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import java.util.Map;

import net.sf.mmm.util.collection.api.MapFactory;

/**
 * This is the abstract base implementation of the {@link MapFactory} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
@SuppressWarnings("unchecked")
public abstract class AbstractMapFactory implements MapFactory<Map> {

  /**
   * The constructor.
   */
  public AbstractMapFactory() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public Class<Map> getMapInterface() {

    return Map.class;
  }

  /**
   * {@inheritDoc}
   */
  public Map createGeneric() {

    return create();
  }

  /**
   * {@inheritDoc}
   */
  public Map createGeneric(int capacity) {

    return create(capacity);
  }

}
