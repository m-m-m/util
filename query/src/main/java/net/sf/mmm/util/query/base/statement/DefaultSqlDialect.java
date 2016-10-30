/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.statement;

/**
 * The default implementation of {@link SqlDialect}.
 *
 * @author hohwille
 * @since 8.4.0
 */
public class DefaultSqlDialect implements SqlDialect {

  /** The singleton instance of this class. */
  public static final DefaultSqlDialect INSTANCE = new DefaultSqlDialect();

  /**
   * The constructor.
   */
  public DefaultSqlDialect() {
    super();
  }

}
