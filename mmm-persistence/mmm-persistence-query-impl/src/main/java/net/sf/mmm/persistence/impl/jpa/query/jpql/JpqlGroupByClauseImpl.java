/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa.query.jpql;

import net.sf.mmm.persistence.api.query.jpql.JpqlCore;
import net.sf.mmm.persistence.api.query.jpql.JpqlGroupByClause;
import net.sf.mmm.persistence.api.query.jpql.JpqlHavingClause;

/**
 * This is the implementation of {@link JpqlGroupByClause}.
 * 
 * @param <E> is the generic type of the {@link #getEntityType() entity type}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class JpqlGroupByClauseImpl<E> extends AbstractJpqlFragment<E> implements JpqlGroupByClause<E> {

  /**
   * The constructor.
   * 
   * @param context - see {@link #getContext()}.
   * @param propertyBasePath - see {@link #getPropertyBasePath()}.
   * @param groupByItems - see
   *        {@link net.sf.mmm.persistence.api.query.jpql.JpqlGroupBySupport#groupBy(String...)}.
   */
  public JpqlGroupByClauseImpl(JpqlContext<E> context, String propertyBasePath, String... groupByItems) {

    super(context);
    setPropertyBasePath(propertyBasePath);
    StringBuilder queryBuffer = context.getQueryBuffer();
    queryBuffer.append(JpqlCore.JPQL_GROUP_BY);
    String separator = "";
    for (String property : groupByItems) {
      queryBuffer.append(separator);
      appendProperty(property);
      separator = ", ";
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JpqlHavingClause<E> having() {

    dispose();
    return new JpqlHavingClauseImpl<E>(getContext(), getPropertyBasePath());
  }

}
