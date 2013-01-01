/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api.query.jpql;

import net.sf.mmm.util.lang.api.attribute.AttributeReadTitle;
import net.sf.mmm.util.nls.api.IllegalCaseException;

/**
 * The enum contains the available operators for {@link JpqlConditionalExpression conditional expressions} in
 * JPQL.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 0.9.0
 */
public enum JpqlCondition implements AttributeReadTitle<String> {

  /** The null condition: {@value} . */
  IS_NULL(" IS NULL"),

  /** The not null condition: {@value} . */
  IS_NOT_NULL(" IS NOT NULL"),

  /** The empty condition: {@value} . */
  IS_EMPTY(" IS EMPTY"),

  /** The not empty condition: {@value} . */
  IS_NOT_EMPTY(" IS NOT EMPTY");

  /** @see #getTitle() */
  private final String title;

  /**
   * The constructor.
   * 
   * @param symbol - see {@link #getTitle()}.
   */
  private JpqlCondition(String symbol) {

    this.title = symbol;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTitle() {

    return this.title;
  }

  /**
   * @return the negated (inverse) operator. E.g. {@link #IS_EMPTY}.{@link #negate()} will return
   *         {@link #IS_NOT_EMPTY}.
   */
  public JpqlCondition negate() {

    switch (this) {
      case IS_EMPTY:
        return IS_NOT_EMPTY;
      case IS_NOT_EMPTY:
        return IS_EMPTY;
      case IS_NULL:
        return IS_NOT_NULL;
      case IS_NOT_NULL:
        return IS_NULL;
      default :
        throw new IllegalCaseException(JpqlCondition.class, this);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return this.title;
  }

}
