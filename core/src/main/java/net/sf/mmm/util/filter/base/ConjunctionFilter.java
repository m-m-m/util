/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.filter.base;

import net.sf.mmm.util.exception.api.IllegalCaseException;
import net.sf.mmm.util.filter.api.Filter;
import net.sf.mmm.util.lang.api.Conjunction;

/**
 * This is an implementation of the {@link Filter} interface that combines a given list of filters using a
 * boolean {@link Conjunction}.
 * 
 * @param <V> is the generic type of the value to check.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ConjunctionFilter<V> implements Filter<V> {

  /** the filters to check. */
  private final Filter<V>[] filterList;

  /** The boolean conjunction. */
  private final Conjunction conjunction;

  /**
   * The constructor.
   * 
   * @param conjunction is the {@link Conjunction} used to combine the {@code filters}.
   * @param filters are the filters to combine.
   */
  @SafeVarargs
  public ConjunctionFilter(Conjunction conjunction, Filter<V>... filters) {

    super();
    assert (conjunction != null);
    this.filterList = filters;
    this.conjunction = conjunction;
  }

  @Override
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
          throw new IllegalCaseException(Conjunction.class, this.conjunction);
      }
    }
    switch (this.conjunction) {
      case OR:
      case NAND:
        return false;
      case AND:
      case NOR:
        return true;
      default :
        throw new IllegalCaseException(Conjunction.class, this.conjunction);
    }
  }
}
