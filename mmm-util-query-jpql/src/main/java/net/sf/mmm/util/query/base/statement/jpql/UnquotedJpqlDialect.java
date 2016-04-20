/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.statement.jpql;

/**
 * {@link JpqlDialect} that does not {@link #quoteReference() quote} {@link #ref(String) references}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class UnquotedJpqlDialect extends JpqlDialect {

  public String quoteReference() {

    return "";
  }

}
