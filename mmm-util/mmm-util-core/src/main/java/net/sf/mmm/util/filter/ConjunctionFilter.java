/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.filter;

/**
 * This is an implementation of the {@link Filter} interface that combines a
 * given list of filters using a boolean conjunction.
 * 
 * @param <V> is the generic type of the value to check.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ConjunctionFilter<V> implements Filter<V> {

  /** the filters to check. */
  private final Filter<V>[] filterList;

  /** The boolean conjunction. */
  private final Conjunction conjunction;

  /**
   * The constructor.
   * 
   * @param conjunction 
   * @param filters
   */
  public ConjunctionFilter(Conjunction conjunction, Filter<V>... filters) {

    super();
    assert (conjunction != null);
    this.filterList = filters;
    this.conjunction = conjunction;
  }

  /**
   * {@inheritDoc}
   */
  public boolean accept(V value) {

    for (Filter<V> filter : this.filterList) {
      boolean accept = filter.accept(value);
      if (this.conjunction == Conjunction.OR) {
        if (accept) {
          return true;
        }
      } else if (!accept) {
        return false;
      }
    }
    return (this.conjunction == Conjunction.AND);
  }

  public static enum Conjunction {
    AND, OR
  }

}
