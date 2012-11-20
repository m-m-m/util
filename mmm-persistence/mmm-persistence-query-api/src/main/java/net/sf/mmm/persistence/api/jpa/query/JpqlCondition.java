/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api.jpa.query;

import net.sf.mmm.util.lang.api.attribute.AttributeReadTitle;
import net.sf.mmm.util.nls.api.IllegalCaseException;

/**
 * The enum contains the available operators for {@link JpqlConditionalExpression conditional expressions} in
 * JPQL.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public enum JpqlCondition implements AttributeReadTitle<String> {

  /** The null condition: {@value} . */
  NULL(" IS NULL"),

  /** The not null condition: {@value} . */
  NOT_NULL(" IS NOT NULL"),

  /** The empty condition: {@value} . */
  EMPTY(" IS EMPTY"),

  /** The not empty condition: {@value} . */
  NOT_EMPTY(" IS NOT EMPTY");

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
   * @return the negated (inverse) operator. E.g. {@link #EMPTY}.{@link #negate()} will return
   *         {@link #NOT_EMPTY}.
   */
  public JpqlCondition negate() {

    switch (this) {
      case EMPTY:
        return NOT_EMPTY;
      case NOT_EMPTY:
        return EMPTY;
      case NULL:
        return NOT_NULL;
      case NOT_NULL:
        return NULL;
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
