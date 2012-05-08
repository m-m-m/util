/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.filter.base;

import net.sf.mmm.util.filter.api.CharFilter;
import net.sf.mmm.util.lang.api.Conjunction;
import net.sf.mmm.util.nls.api.IllegalCaseException;

/**
 * This is an implementation of the {@link CharFilter} interface that combines a given list of filters using a
 * boolean {@link Conjunction}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class ConjunctionCharFilter implements CharFilter {

  /** the filters to check. */
  private final CharFilter[] filterList;

  /** The boolean conjunction. */
  private final Conjunction conjunction;

  /**
   * The constructor.
   * 
   * @param conjunction is the {@link Conjunction} used to combine the <code>filters</code>.
   * @param filters are the filters to combine.
   */
  public ConjunctionCharFilter(Conjunction conjunction, CharFilter... filters) {

    super();
    assert (conjunction != null);
    this.filterList = filters;
    this.conjunction = conjunction;
  }

  /**
   * {@inheritDoc}
   */
  public boolean accept(char c) {

    for (CharFilter filter : this.filterList) {
      boolean accept = filter.accept(c);
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
