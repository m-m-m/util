/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa.query.jpql;

import java.util.Locale;

import net.sf.mmm.persistence.api.query.jpql.JpqlOrderByClause;
import net.sf.mmm.util.lang.api.SortOrder;

/**
 * This is the implementation of {@link JpqlOrderByClause}.
 * 
 * @param <E> is the generic type of the {@link #getEntityType() entity type}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class JpqlOrderByClauseImpl<E> extends AbstractJpqlFragment<E> implements JpqlOrderByClause<E> {

  /**
   * The constructor.
   * 
   * @param context - see {@link #getContext()}.
   * @param propertyBasePath - see {@link #getPropertyBasePath()}.
   * @param property - see {@link net.sf.mmm.persistence.api.query.jpql.JpqlWhereClause#orderBy(String)}.
   * @param order - see
   *        {@link net.sf.mmm.persistence.api.query.jpql.JpqlWhereClause#orderBy(String, SortOrder)}. May be
   *        <code>null</code>.
   */
  public JpqlOrderByClauseImpl(JpqlContext<E> context, String propertyBasePath, String property, SortOrder order) {

    super(context);
    setPropertyBasePath(propertyBasePath);
    context.getQueryBuffer().append(JPQL_ORDER_BY);
    appendOrderByProperty(property, order);
  }

  /**
   * This method appends a single order by property.
   * 
   * @param property - see {@link #also(String, SortOrder)}.
   * @param order - see {@link #also(String, SortOrder)}.
   */
  private void appendOrderByProperty(String property, SortOrder order) {

    appendProperty(property);
    if (order != null) {
      StringBuilder queryBuffer = getContext().getQueryBuffer();
      queryBuffer.append(' ');
      queryBuffer.append(order.getValue().toUpperCase(Locale.US));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JpqlOrderByClause<E> also(String property) {

    return also(property, null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JpqlOrderByClause<E> also(String property, SortOrder order) {

    getContext().getQueryBuffer().append(',');
    appendOrderByProperty(property, order);
    return this;
  }

}
