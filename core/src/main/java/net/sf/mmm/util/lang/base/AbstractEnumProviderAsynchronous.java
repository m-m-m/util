/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import net.sf.mmm.util.exception.api.ObjectNotFoundException;
import net.sf.mmm.util.lang.api.EnumType;

/**
 * This is the abstract base implementation of {@link AbstractEnumProvider} that retrieves
 * {@link #getEnumValues(net.sf.mmm.util.lang.api.EnumDefinition) enum values} asynchronously.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public abstract class AbstractEnumProviderAsynchronous extends AbstractEnumProvider {

  /**
   * The constructor.
   */
  public AbstractEnumProviderAsynchronous() {

    super();
  }

  @Override
  protected void loadEnumValues(EnumContainer container) {

    throw new ObjectNotFoundException(EnumType.class, container.getEnumDefinition().getValue());
  }

}
