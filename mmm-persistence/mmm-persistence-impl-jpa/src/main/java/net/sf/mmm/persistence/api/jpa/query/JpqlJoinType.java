/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api.jpa.query;

/**
 * This enum contains the available types of a JOIN in JPQL.
 * 
 * @see javax.persistence.criteria.JoinType
 * @see org.hibernate.sql.JoinType
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public enum JpqlJoinType {

  /** A regular join. */
  JOIN(JpqlFragment.JPQL_JOIN),

  /** A left join. */
  LEFT_JOIN(JpqlFragment.JPQL_LEFT_JOIN),

  /** An left outer join. */
  LEFT_OUTER_JOIN(JpqlFragment.JPQL_LEFT_OUTER_JOIN),

  /** An inner join. */
  INNER_JOIN(JpqlFragment.JPQL_INNER_JOIN);

  /** @see #toString() */
  private final String title;

  /**
   * The constructor.
   * 
   * @param title is the string representation.
   */
  private JpqlJoinType(String title) {

    this.title = title;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return this.title;
  }

}
