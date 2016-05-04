/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import java.util.List;

import net.sf.mmm.util.lang.api.EnumDefinition;
import net.sf.mmm.util.lang.api.Formatter;

/**
 * This class implements {@link EnumDefinition} for a java {@link Enum}.
 *
 * @param <TYPE> is the generic type of the {@link #getEnumType() enum type}.
 * @param <CATEGORY> is the generic type of the {@link #getCategory() category} or {@link Void} for none.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public abstract class AbstractEnumDefinition<TYPE, CATEGORY> implements EnumDefinition<TYPE, CATEGORY> {

  private static final long serialVersionUID = 5984869515955299731L;

  private Formatter<TYPE> formatter;

  /**
   * The constructor.
   */
  public AbstractEnumDefinition() {

    super();
  }

  /**
   * This method is called from the constructor to set the {@link #getValue() value}. By default it returns
   * {@link Class#getName()}. Override to change (e.g. to {@link Class#getSimpleName()}).
   *
   * @param type is the {@link #getEnumType()}.
   * @return the {@link #getValue()} to use.
   */
  protected String getKey(Class<TYPE> type) {

    return type.getName();
  }

  @Override
  public final Formatter<TYPE> getFormatter() {

    if (this.formatter == null) {
      this.formatter = FormatterToString.getInstance();
    }
    return this.formatter;
  }

  /**
   * @param formatter is the new value of {@link #getFormatter()}.
   */
  protected void setFormatter(Formatter<TYPE> formatter) {

    this.formatter = formatter;
  }

  @Override
  public String getValue() {

    return getEnumType().getName();
  }

  @Override
  public String toString() {

    return getEnumType().getSimpleName();
  }

  /**
   * @return the {@link List} of the {@link Class#getEnumConstants() enum values} or {@code null} if the values are NOT
   *         static and have to be loaded asynchronous via {@link net.sf.mmm.util.lang.api.EnumProvider}.
   */
  public List<TYPE> getEnumValues() {

    return null;
  }

  @Override
  public boolean isMutable() {

    return false;
  }

  @Override
  public boolean isCachable() {

    return true;
  }

}
