/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import java.util.Arrays;
import java.util.List;

import net.sf.mmm.util.lang.api.EnumDefinition;

/**
 * This is the abstract base implementation of {@link EnumDefinition} with no {@link #getCategory() category}
 * and a static set of {@link #getEnumValues() enum values}. For an example see {@link BooleanEnumDefinition}.
 * 
 * @param <TYPE> is the generic type of the {@link #getEnumType() enum type}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.1
 */
public abstract class AbstractSimpleEnumDefinition<TYPE> extends AbstractEnumDefinition<TYPE, Void> {

  /** UID for serialization. */
  private static final long serialVersionUID = 6132710595390153755L;

  /** @see #getEnumValues() */
  private List<TYPE> enumValues;

  /**
   * The constructor.
   * 
   * @param enumValues are the static {@link #getEnumValues() enum values}.
   */
  public AbstractSimpleEnumDefinition(TYPE... enumValues) {

    super();
    this.enumValues = Arrays.asList(enumValues);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final EnumDefinition<Void, ?> getCategory() {

    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<TYPE> getEnumValues() {

    return this.enumValues;
  }

}
