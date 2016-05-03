/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import net.sf.mmm.util.lang.api.EnumDefinition;

/**
 * This is the abstract base implementation of {@link AbstractEnumProvider} that retrieves
 * {@link #getEnumValues(net.sf.mmm.util.lang.api.EnumDefinition) enum values} synchronously.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public abstract class AbstractEnumProviderSynchronous extends AbstractEnumProvider {

  /**
   * The constructor.
   */
  public AbstractEnumProviderSynchronous() {

    super();
  }

  @Override
  public void require(Runnable callback, EnumDefinition<?, ?>... enumDefinitions) {

    for (EnumDefinition<?, ?> enumDefinition : enumDefinitions) {
      getEnumValues(enumDefinition);
    }
    callback.run();
  }

}
