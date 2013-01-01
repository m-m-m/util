/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api.query;

import javax.persistence.Query;

/**
 * This is the interface for a query. It is created via {@link JpqlBuilder}.<br/>
 * <b>ATTENTION:</b><br/>
 * A query is created lazily. This means that methods such as
 * {@link net.sf.mmm.persistence.api.query.jpql.JpqlFragment#select()} only create an instance of this
 * interface containing the {@link #getJpqlStatement() query string}. The actual query parsing and execution
 * via JPA happens if an according method like {@link SimpleQuery#getSingleResult()} is called. This fact is
 * used for {@link net.sf.mmm.persistence.api.query.jpql.JpqlConditionalExpression#newSubQuery(Class, String)
 * creating sub-queries} that can then be passed as {@link #getJpqlStatement() query string} to another
 * argument of a parent query.
 * 
 * @see javax.persistence.Query
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 0.9.0
 */
public abstract interface JpqlStatement {

  /**
   * @return the original JPQL statement.
   */
  String getJpqlStatement();

  /**
   * This method gets the {@link Query} created from {@link #getJpqlStatement()} with all parameters assigned.
   * The {@link Query} is lazily created on the first call of this method.
   * 
   * @return the {@link Query}.
   */
  Query getOrCreateQuery();

}
