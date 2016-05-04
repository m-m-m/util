/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import java.util.List;

/**
 * This is the default {@link net.sf.mmm.util.lang.api.EnumDefinition} for the type {@link Boolean}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public class BooleanEnumDefinition extends AbstractSimpleEnumDefinition<Boolean> {

  private static final long serialVersionUID = -5390849719342881758L;

  /**
   * The constructor.
   */
  public BooleanEnumDefinition() {

    super(Boolean.TRUE, Boolean.FALSE);
    setFormatter(BooleanFormatter.getInstance());
  }

  @Override
  public List<Boolean> getEnumValues() {

    return super.getEnumValues();
  }

  @Override
  public Class<Boolean> getEnumType() {

    return Boolean.class;
  }

}
