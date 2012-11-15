/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa.query;

import net.sf.mmm.persistence.api.jpa.query.JpqlGroupByClause;
import net.sf.mmm.persistence.api.jpa.query.JpqlHavingClause;

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
   */
  public JpqlGroupByClauseImpl(JpqlQueryContext<E> context, String propertyBasePath) {

    super(context);
    setPropertyBasePath(propertyBasePath);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JpqlHavingClause<E> having() {

    dispose();
    return new JpqlHavingClauseImpl<E>(getContext(), "");
  }

}
