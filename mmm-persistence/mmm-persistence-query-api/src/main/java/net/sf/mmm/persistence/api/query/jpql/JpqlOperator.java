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
public enum JpqlOperator implements AttributeReadTitle<String> {

  /** The equal operator: {@value} . */
  EQUAL(" = "),

  /** The not-equal operator: {@value} . */
  NOT_EQUAL(" <> "),

  /** The greater-than operator: {@value} . */
  GREATER_THAN(" > "),

  /** The greater-or-equal operator: {@value} . */
  GREATER_EQUAL(" >= "),

  /** The less-than operator: {@value} . */
  LESS_THAN(" < "),

  /** The less-or-equal operator: {@value} . */
  LESS_EQUAL(" <= "),

  /** The like operator: {@value} . Allows to compare using patterns. */
  LIKE(" LIKE "),

  /** The not like operator: {@value} . Allows to compare using patterns. */
  NOT_LIKE(" NOT LIKE "),

  /** The member operator: {@value} . */
  MEMBER(" IS MEMBER "),

  /** The not member operator: {@value} . */
  NOT_MEMBER(" IS NOT MEMBER ");

  /** @see #getTitle() */
  private final String title;

  /**
   * The constructor.
   * 
   * @param symbol - see {@link #getTitle()}.
   */
  private JpqlOperator(String symbol) {

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
   * @return <code>true</code> if {@link #EQUAL} or {@link #NOT_EQUAL}, <code>false</code> otherwise.
   */
  public boolean isEqualBased() {

    return ((this == EQUAL) || (this == NOT_EQUAL));
  }

  /**
   * @return <code>true</code> if {@link #LIKE} or {@link #NOT_LIKE}, <code>false</code> otherwise.
   */
  public boolean isLikeBased() {

    return ((this == LIKE) || (this == NOT_LIKE));
  }

  /**
   * @return <code>true</code> if {@link #MEMBER} or {@link #NOT_MEMBER}, <code>false</code> otherwise.
   */
  public boolean isMemberBased() {

    return ((this == MEMBER) || (this == NOT_MEMBER));
  }

  /**
   * @return the negated (inverse) operator. E.g. {@link #GREATER_EQUAL}.{@link #negate()} will return
   *         {@link #LESS_THAN}.
   */
  public JpqlOperator negate() {

    switch (this) {
      case EQUAL:
        return NOT_EQUAL;
      case NOT_EQUAL:
        return EQUAL;
      case GREATER_EQUAL:
        return LESS_THAN;
      case GREATER_THAN:
        return LESS_EQUAL;
      case LESS_EQUAL:
        return GREATER_THAN;
      case LESS_THAN:
        return GREATER_EQUAL;
      case LIKE:
        return NOT_LIKE;
      case NOT_LIKE:
        return LIKE;
      case MEMBER:
        return NOT_MEMBER;
      case NOT_MEMBER:
        return MEMBER;
      default :
        throw new IllegalCaseException(JpqlOperator.class, this);
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
