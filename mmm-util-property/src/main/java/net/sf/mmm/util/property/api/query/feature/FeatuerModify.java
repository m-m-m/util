/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.query.feature;

import net.sf.mmm.util.property.api.query.Statement;

/**
 * Extends {@link Statement} for a query {@link net.sf.mmm.util.property.api.query.Statement} that modifies data.
 *
 * @see net.sf.mmm.util.property.api.query.ModifyStatement
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract interface FeatuerModify {

  /**
   * Execute the clause and return the amount of affected rows
   *
   * @return amount of affected rows
   */
  long execute();

}
