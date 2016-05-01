/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.base.query.statement;

import net.sf.mmm.util.query.api.variable.Variable;
import net.sf.mmm.util.query.base.statement.SqlDialect;

/**
 * The implementation of {@link SqlDialect} for <a href="http://orientdb.com/docs/2.1/SQL.html">OrientDB</a>.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class OrientDbDialect extends Object implements SqlDialect {

  /** The singleton instance of this class. */
  public static final OrientDbDialect INSTANCE = new OrientDbDialect();

  /**
   * The constructor.
   */
  public OrientDbDialect() {
    super();
  }

  @Override
  public String quoteReference() {

    return "";
  }

  @Override
  public String variable(Variable<?> variable) {

    return "$" + variable.getName();
  }

  @Override
  public String selectDistinct() {

    return select();
  }

  @Override
  public String upsert() throws UnsupportedOperationException {

    return "UPSERT ";
  }

}
