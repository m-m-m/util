/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.base;

import net.sf.mmm.util.component.base.ComponentSpecification;
import net.sf.mmm.util.date.api.Iso8601Util;
import net.sf.mmm.util.math.api.MathUtil;

/**
 * This interface bundles the dependencies for the search. It contains components required by the
 * {@link net.sf.mmm.search.engine.api.SearchEngine}, indexer, etc.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ComponentSpecification
public interface SearchDependencies {

  /**
   * @return the {@link MathUtil} instance.
   */
  MathUtil getMathUtil();

  /**
   * @return the {@link Iso8601Util} instance.
   */
  Iso8601Util getIso8601Util();

}
