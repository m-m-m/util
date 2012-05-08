/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.context.base;

import net.sf.mmm.util.collection.base.HashMapFactory;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.context.api.GenericContextFactory;
import net.sf.mmm.util.context.api.MutableGenericContext;

/**
 * This is the abstract base-implementation of the {@link GenericContextFactory} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public abstract class AbstractGenericContextFactory extends AbstractLoggableComponent implements GenericContextFactory {

  /**
   * The constructor.
   */
  public AbstractGenericContextFactory() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public MutableGenericContext createContext() {

    return createContext(HashMapFactory.INSTANCE);
  }

}
