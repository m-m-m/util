/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.statement.jpql;

import net.sf.mmm.util.property.api.ReadableProperty;
import net.sf.mmm.util.query.base.statement.SqlDialect;

/**
 * The implementation of {@link SqlDialect} for
 * <a href="http://docs.oracle.com/cd/E12839_01/apirefs.1111/e13946/ejb3_langref.html">Java Persistence Query Language
 * (JPQL)</a>.
 *
 * @author hohwille
 * @since 8.0.0
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
  public String parameter(int index) {

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

  @Override
  public String property(ReadableProperty<?> property) {

    String name = property.getName();
    return Character.toLowerCase(name.charAt(0)) + name.substring(1);
  }

}
