/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.base.query;

/**
 * The implementation of {@link SqlDialect} for JPQL.
 *
 * @author hohwille
 * @since 7.1.0
 */
public class JpqlDialect extends Object implements SqlDialect {

  /** The singleton instance of this class. */
  public static final JpqlDialect INSTANCE = new JpqlDialect();

  /**
   * The constructor.
   */
  public JpqlDialect() {
    super();
  }

  @Override
  public String variable(int index) {

    return "?" + (index + 1);
  }

  @Override
  public String offset() {

    return "";
  }

  @Override
  public String limit() {

    return "";
  }

}
