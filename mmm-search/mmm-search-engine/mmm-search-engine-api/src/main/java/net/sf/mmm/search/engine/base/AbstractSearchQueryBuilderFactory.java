/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.base;

import net.sf.mmm.search.engine.api.SearchQueryBuilderFactory;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;

/**
 * This is the abstract base implementation of the {@link SearchQueryBuilderFactory} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractSearchQueryBuilderFactory extends AbstractLoggableComponent implements
    SearchQueryBuilderFactory {

  /**
   * The constructor.
   */
  public AbstractSearchQueryBuilderFactory() {

    super();
  }

}
