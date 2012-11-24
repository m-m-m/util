/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa.query.jpql;

import net.sf.mmm.persistence.api.query.jpql.JpqlGroupByClause;
import net.sf.mmm.persistence.api.query.jpql.JpqlGroupBySupport;
import net.sf.mmm.util.nls.api.NlsIllegalArgumentException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.pojo.path.api.TypedProperty;

/**
 * This class extends {@link AbstractJpqlFragment} with {@link JpqlGroupBySupport}.
 * 
 * @param <E> is the generic type of the {@link #getEntityType() entity type}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractJpqlFragmentWithGroupBySupport<E> extends AbstractJpqlFragment<E> implements
    JpqlGroupBySupport<E> {

  /**
   * The constructor.
   * 
   * @param context is the {@link JpqlContext}.
   */
  public AbstractJpqlFragmentWithGroupBySupport(JpqlContext<E> context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JpqlGroupByClause<E> groupBy(TypedProperty<?>... properties) {

    String[] propertyNames = new String[properties.length];
    for (int i = 0; i < properties.length; i++) {
      propertyNames[i] = properties[i].getPojoPath();
    }
    return groupBy(propertyNames);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JpqlGroupByClause<E> groupBy(String... groupByItems) {

    NlsNullPointerException.checkNotNull("groupByItems", groupByItems);
    if (groupByItems.length == 0) {
      throw new NlsIllegalArgumentException(Integer.valueOf(0), "groupByItems.length");
    }
    dispose();
    return new JpqlGroupByClauseImpl<E>(getContext(), getPropertyBasePath(), groupByItems);
  }

}
