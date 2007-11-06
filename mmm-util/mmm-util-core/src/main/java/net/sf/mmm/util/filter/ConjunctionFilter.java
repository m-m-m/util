/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.filter;

import net.sf.mmm.util.Conjunction;

/**
 * This is an implementation of the {@link Filter} interface that combines a
 * given list of filters using a boolean {@link Conjunction}.
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
   * @param conjunction is the {@link Conjunction} used to combine the
   *        <code>filters</code>.
   * @param filters are the filters to combine.
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
      switch (this.conjunction) {
        case OR:
          if (accept) {
            return true;
          }
          break;
        case AND:
          if (!accept) {
            return false;
          }
          break;
        case NOR:
          if (accept) {
            return false;
          }
          break;
        case NAND:
          if (!accept) {
            return true;
          }
          break;
        default :
          throw new IllegalStateException("Unknown conjunction: " + this.conjunction);
      }
    }
    switch (this.conjunction) {
      case OR:
        return false;
      case AND:
        return true;
      case NOR:
        return true;
      case NAND:
        return false;
      default :
        throw new IllegalStateException("Unknown conjunction: " + this.conjunction);
    }
  }
}
