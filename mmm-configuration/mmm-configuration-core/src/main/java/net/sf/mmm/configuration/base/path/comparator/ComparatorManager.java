/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.base.path.comparator;

import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.configuration.api.ConfigurationException;

/**
 * This is the manager where all available
 * {@link net.sf.mmm.configuration.base.path.comparator.Comparator comparators} are
 * registered.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ComparatorManager {

  /** the singleton instance */
  private static ComparatorManager INSTANCE = null;

  /** the registered comparators */
  private final Map<String, Comparator> comparators;

  /**
   * The constructor.
   */
  public ComparatorManager() {

    super();
    this.comparators = new HashMap<String, Comparator>();
    Comparator eq = new EqualsComparator();
    this.comparators.put("=", eq);
    register(eq);
    register(new NotEqualsComparator());
  }

  /**
   * This method gets the comparator for the given
   * {@link Comparator#getSymbol() symbol}.
   * 
   * @param symbol
   *        is the {@link Comparator#getSymbol() symbol} of the requested
   *        comparator.
   * @return the comparator for the given symbol.
   */
  public final Comparator getComparator(String symbol) {

    Comparator result = this.comparators.get(symbol);
    if (result == null) {
      // TODO: i18n
      throw new ConfigurationException("Undefined comparator symbol " + symbol);
    }
    return result;
  }

  /**
   * 
   * @param comparator
   */
  protected final void register(Comparator comparator) {

    String symbol = comparator.getSymbol();
    if (this.comparators.containsKey(symbol)) {
      throw new ConfigurationException("Duplicate symbol!");
    }
  }

  /**
   * This method gets the singleton instance of the comparator manager.
   * 
   * @return the singleton instance.
   */
  public static ComparatorManager getInstance() {

    if (INSTANCE == null) {
      synchronized (ComparatorManager.class) {
        if (INSTANCE == null) {
          INSTANCE = new ComparatorManager();
        }
      }
    }
    return INSTANCE;
  }

}
