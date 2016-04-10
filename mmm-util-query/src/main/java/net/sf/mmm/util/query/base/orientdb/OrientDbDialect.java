/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.orientdb;

import net.sf.mmm.util.query.base.SqlDialect;

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

}
