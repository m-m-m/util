/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import net.sf.mmm.util.exception.api.NlsUnsupportedOperationException;
import net.sf.mmm.util.lang.api.EnumDefinition;

/**
 * This is a simple implementation of {@link net.sf.mmm.util.lang.api.EnumProvider}. It only supports {@link Boolean}
 * and any static {@link Enum} (where it dynamically creates definitions).
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public class SimpleEnumProvider extends AbstractEnumProvider {

  /**
   * The constructor.
   */
  public SimpleEnumProvider() {

    super();
  }

  @Override
  public void require(Runnable callback, EnumDefinition<?, ?>... enumDefinitions) {

    callback.run();
  }

  @Override
  protected void loadEnumValues(EnumContainer container) {

    throw new NlsUnsupportedOperationException("load dynamic enum");
  }

}
