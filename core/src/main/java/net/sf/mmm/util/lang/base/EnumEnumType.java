/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import net.sf.mmm.util.lang.api.EnumType;

/**
 * This is a simple implementation of {@link EnumType} to retro-fit {@link Enum}s that do not implement {@link EnumType}
 * .
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public final class EnumEnumType implements EnumType<Enum<?>> {

  private static final long serialVersionUID = 271598316809053979L;

  private Enum<?> value;

  /**
   * The constructor.
   *
   * @param value the {@link Enum} to wrap.
   */
  public EnumEnumType(Enum<?> value) {

    super();
    this.value = value;
  }

  @Override
  public Enum<?> getValue() {

    return this.value;
  }

  @Override
  public String toString() {

    return this.value.toString();
  }

}
