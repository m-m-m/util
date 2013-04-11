/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api.query;

import net.sf.mmm.persistence.api.query.jpql.JpqlCore;
import net.sf.mmm.persistence.api.query.jpql.JpqlFromClause;
import net.sf.mmm.util.component.api.ComponentSpecification;

/**
 * This is the interface for a type-safe dynamic JPQL builder.<br/>
 * Creating dynamic queries with JPQL is painful due to ugly string concatenations. Further, building these
 * strings requires a lot of knowledge. Also, criteria API is not always suitable and sometimes hardly to
 * predict. This API will allow to build JPQL dynamically in a type-safe, compact, and expressive way. The API
 * (JavaDoc) will guide you and provide the required information. Of course it will use
 * {@link javax.persistence.TypedQuery#setParameter(int, Object) parameters} to prevent SQL-injection and
 * allow proper caching of the query and its execution plan.<br/>
 * <b>Example:</b><br/>
 * 
 * <pre>
 * {@link JpqlBuilder} builder = getJpqlBuilder();
 * builder.{@link #from(Class) from}(MyEntity.class).{@link JpqlFromClause#where()
 * where()}.{@link net.sf.mmm.persistence.api.query.jpql.JpqlWhereClause#property(net.sf.mmm.util.pojo.path.api.TypedProperty)
 * property}(MyEntity.TYPED_PROPERTY_AGE).{@link
 * net.sf.mmm.persistence.api.query.jpql.JpqlPropertyExpression#isCompare(net.sf.mmm.persistence.api.query.jpql.JpqlOperator, Object)
 * isCompare}({@link net.sf.mmm.persistence.api.query.jpql.JpqlOperator#GREATER_THAN}, minAge){@literal
 * .}{@link net.sf.mmm.persistence.api.query.jpql.JpqlWhereClause#select() select()};
 * </pre>
 * 
 * Will result in the following JQPL query
 * 
 * <pre>
 * SELECT myEntityAlias FROM MyEntity myEntityAlias WHERE myEntityAlias.age >= ?
 * </pre>
 * 
 * where the {@link javax.persistence.TypedQuery#setParameter(int, Object) parameter is set} to
 * <code>minAge</code>. <br/>
 * <b>NOTE:</b><br/>
 * It might appear confusing that the {@link net.sf.mmm.persistence.api.query.jpql.JpqlFragment#select()
 * select-operation} is performed at the end of the query. This is designed on purpose to be able to create a
 * dynamic {@link net.sf.mmm.persistence.api.query.jpql.JpqlFragment} and then be able to decide if
 * {@link net.sf.mmm.persistence.api.query.jpql.JpqlFragment#select() all entities should be selected} or e.g.
 * only the {@link net.sf.mmm.persistence.api.query.jpql.JpqlFragment#selectCount() total hit count} shall be
 * determined.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 0.9.0
 */
@ComponentSpecification
public interface JpqlBuilder extends JpqlCore {

  /**
   * This method is a shortcut for {@link #from(Class, String)} that automatically creates the
   * {@link JpqlFromClause#getEntityAlias() alias}.
   * 
   * @param <E> is the generic type of the <code>entityType</code>
   * 
   * @param entityType is the {@link Class} reflecting the (main) {@link JpqlFromClause#getEntityType()
   *        entity} to select.
   * @return the {@link JpqlFromClause}.
   */
  <E> JpqlFromClause<E> from(Class<E> entityType);

  /**
   * This method starts the FROM clause and automatically adds the given <code>entityType</code> associated
   * with the given <code>alias</code>. In other words this method appends
   * <code>"FROM " + entityType.getSimpleName() + " " + alias</code> to the query string.
   * 
   * @param <E> is the generic type of the <code>entityType</code>
   * 
   * @param entityType is the {@link Class} reflecting the (main) {@link JpqlFromClause#getEntityType()
   *        entity} to select.
   * @param alias is the alias to use.
   * @return the {@link JpqlFromClause}.
   */
  <E> JpqlFromClause<E> from(Class<E> entityType, String alias);

}
