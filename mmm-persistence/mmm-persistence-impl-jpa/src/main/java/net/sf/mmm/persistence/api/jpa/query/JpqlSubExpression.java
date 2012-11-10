/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api.jpa.query;

/**
 * This is the interface for a sub-{@link JpqlConditionalExpression expression}.
 * 
 * @param <E> is the generic type of the {@link #getEntityType() entity type}.
 * @param <P> is the generic type of the parent {@link JpqlConditionalExpression expression}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface JpqlSubExpression<E, P extends JpqlConditionalExpression<E, P>> extends
    JpqlConditionalExpression<E, JpqlSubExpression<E, P>> {

  /**
   * This method closes this sub-expression and returns to the parent expression.
   * 
   * @return the parent expression.
   */
  P close();

}
