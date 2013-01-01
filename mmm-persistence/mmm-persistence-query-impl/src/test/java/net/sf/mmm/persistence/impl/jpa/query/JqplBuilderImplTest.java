/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa.query;

import java.text.MessageFormat;

import javax.persistence.TypedQuery;

import net.sf.mmm.persistence.api.query.JpqlBuilder;
import net.sf.mmm.persistence.api.query.ListQuery;
import net.sf.mmm.persistence.api.query.SimpleQuery;
import net.sf.mmm.persistence.api.query.jpql.JpqlFromClause;
import net.sf.mmm.persistence.api.query.jpql.JpqlHavingClause;
import net.sf.mmm.persistence.api.query.jpql.JpqlOperator;
import net.sf.mmm.persistence.api.query.jpql.JpqlOrderByClause;
import net.sf.mmm.persistence.api.query.jpql.JpqlWhereClause;
import net.sf.mmm.test.jpa.EntityManagerMock;
import net.sf.mmm.util.lang.api.SortOrder;
import net.sf.mmm.util.pojo.path.api.TypedProperty;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test-case for {@link JpqlBuilderImpl}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 0.9.0
 */
public class JqplBuilderImplTest {

  /**
   * @return the {@link JpqlBuilder} instance to test.
   */
  protected JpqlBuilder getQueryBuilder() {

    JpqlBuilderImpl builder = new JpqlBuilderImpl();
    builder.setEntityManager(new EntityManagerMock());
    builder.initialize();
    return builder;
  }

  /**
   * Test for {@link JpqlBuilder#from(Class)} with most simple query.
   */
  @Test
  public void testSimpleQuery() {

    // given
    Class<DummyBarEntity> entityType = DummyBarEntity.class;

    // then
    ListQuery<DummyBarEntity> query = getQueryBuilder().from(entityType).select();

    // test
    Assert.assertNotNull(query);
    String entityName = entityType.getSimpleName();
    String expectedQuery = MessageFormat.format("SELECT {0}Alias FROM {0} {0}Alias", entityName);
    Assert.assertEquals(expectedQuery, query.getJpqlStatement());
  }

  /**
   * Test for {@link JpqlBuilder#from(Class)} with
   * {@link net.sf.mmm.persistence.api.query.jpql.JpqlFragment#selectCount() count selection}.
   */
  @Test
  public void testCountQuery() {

    // given
    Class<DummyBarEntity> entityType = DummyBarEntity.class;
    String entityAlias = entityType.getSimpleName() + "Alias";

    // then
    SimpleQuery<Long> query = getQueryBuilder().from(entityType).selectCount();

    // test
    Assert.assertNotNull(query);
    String entityName = entityType.getSimpleName();
    String expectedQuery = MessageFormat.format("SELECT COUNT({1}) FROM {0} {1}", entityName, entityAlias);
    Assert.assertEquals(expectedQuery, query.getJpqlStatement());
  }

  /**
   * Test for {@link JpqlBuilder#from(Class, String)} with WHERE clause.
   */
  @Test
  public void testQueryWithWhereClause() {

    // given
    Class<DummyFooEntity> entityType = DummyFooEntity.class;
    String alias = "foo";
    JpqlBuilder queryBuilder = getQueryBuilder();
    String property1Name = TypedProperty.createPath(DummyFooEntity.PROPERTY_BAR, DummyBarEntity.PROPERTY_VALUE);
    String property1Value = "magic";
    String property2Name = DummyFooEntity.PROPERTY_NUMBER;
    Integer property2Value = Integer.valueOf(42);

    // then
    JpqlFromClause<DummyFooEntity> from = queryBuilder.from(entityType, alias);
    ListQuery<DummyFooEntity> query = from.where().property(property1Name).isEqual(property1Value)
        .property(property2Name).not().isEqual(property2Value).or().property(property2Name)
        .isCompare(JpqlOperator.GREATER_EQUAL, property2Value).select();

    // test
    Assert.assertNotNull(query);
    String entityName = entityType.getSimpleName();
    String expectedQuery = MessageFormat.format(
        "SELECT {1} FROM {0} {1} WHERE {1}.{2} = ? AND {1}.{3} <> ? OR {1}.{3} >= ?", entityName, alias, property1Name,
        property2Name);
    Assert.assertEquals(expectedQuery, query.getJpqlStatement());
    TypedQuery<DummyFooEntity> typedQuery = query.getOrCreateQuery();
    Assert.assertNotNull(typedQuery);
    Assert.assertSame(property1Value, typedQuery.getParameterValue(0));
    Assert.assertSame(property2Value, typedQuery.getParameterValue(1));
  }

  /**
   * Test for {@link JpqlBuilder#from(Class, String)} with WHERE clause.
   */
  @Test
  public void testQueryWithTwoEntitesAndWhereComparingThem() {

    // given
    Class<DummyFooEntity> entityType = DummyFooEntity.class;
    String alias = "foo";
    JpqlBuilder queryBuilder = getQueryBuilder();
    String property1Name = TypedProperty.createPath(DummyFooEntity.PROPERTY_BAR, DummyBarEntity.PROPERTY_VALUE);

    Class<DummyBarEntity> entityType2 = DummyBarEntity.class;
    String alias2 = "bar";
    String property2Name = DummyBarEntity.PROPERTY_VALUE;

    // then
    JpqlFromClause<DummyFooEntity> from = queryBuilder.from(entityType, alias).also(entityType2, alias2);
    ListQuery<DummyFooEntity> query = from.where().property(property1Name).isEqual(alias2, property2Name).select();

    // test
    Assert.assertNotNull(query);
    String entityName = entityType.getSimpleName();
    String expectedQuery = MessageFormat.format("SELECT {1} FROM {0} {1},{3} {4} WHERE {1}.{2} = {4}.{5}", entityName,
        alias, property1Name, entityType2.getSimpleName(), alias2, property2Name);
    Assert.assertEquals(expectedQuery, query.getJpqlStatement());
    TypedQuery<DummyFooEntity> typedQuery = query.getOrCreateQuery();
    Assert.assertNotNull(typedQuery);
  }

  /**
   * Test for {@link JpqlBuilder#from(Class, String)} with a sub-query.
   */
  @Test
  public void testQueryWithSubQuery() {

    // given
    Class<DummyFooEntity> entityType = DummyFooEntity.class;
    String alias = "foo";
    JpqlBuilder queryBuilder = getQueryBuilder();
    String propertyName = DummyFooEntity.PROPERTY_BAR;
    String barAlias = "barAlias";
    Long countValue = Long.valueOf(10);

    // then
    JpqlFromClause<DummyFooEntity> from = queryBuilder.from(entityType, alias);
    JpqlWhereClause<DummyFooEntity> whereClause = from.where();
    SimpleQuery<Long> subQuery = whereClause.newSubQuery(null, propertyName, barAlias).selectCount();
    ListQuery<DummyFooEntity> query = whereClause.subQuery(subQuery).isCompare(JpqlOperator.GREATER_THAN, countValue)
        .selectDistinct();

    // test
    Assert.assertNotNull(query);
    String entityName = entityType.getSimpleName();
    String expectedQuery = MessageFormat.format(
        "SELECT DISTINCT {1} FROM {0} {1} WHERE (SELECT COUNT({3}) FROM {1}.{2} {3}) > ?", entityName, alias,
        propertyName, barAlias);
    Assert.assertEquals(expectedQuery, query.getJpqlStatement());
    TypedQuery<DummyFooEntity> typedQuery = query.getOrCreateQuery();
    Assert.assertNotNull(typedQuery);
    Assert.assertSame(countValue, typedQuery.getParameterValue(0));
  }

  /**
   * Test for {@link JpqlBuilder#from(Class, String)} with ORDER BY clause.
   */
  @Test
  public void testQueryWithGroupBy() {

    // given
    Class<DummyFooEntity> entityType = DummyFooEntity.class;
    JpqlBuilder queryBuilder = getQueryBuilder();
    String propertyName = DummyFooEntity.PROPERTY_NUMBER;
    Integer propertyMin = Integer.valueOf(42);
    Integer propertyMax = Integer.valueOf(142);

    // then
    JpqlFromClause<DummyFooEntity> from = queryBuilder.from(entityType);
    JpqlHavingClause<DummyFooEntity> clause = from.groupBy(propertyName).having().property(propertyName)
        .isBetween(propertyMin, propertyMax);
    ListQuery<DummyFooEntity> query = clause.select();

    // test
    Assert.assertNotNull(query);
    String entityName = entityType.getSimpleName();
    String alias = clause.getEntityAlias();
    String expectedQuery = MessageFormat.format(
        "SELECT {1} FROM {0} {1} GROUP BY {1}.{2} HAVING {1}.{2} BETWEEN ? AND ?", entityName, alias, propertyName);
    Assert.assertEquals(expectedQuery, query.getJpqlStatement());
    Assert.assertSame(propertyMin, query.getOrCreateQuery().getParameterValue(0));
    Assert.assertSame(propertyMax, query.getOrCreateQuery().getParameterValue(1));
  }

  /**
   * Test for {@link JpqlBuilder#from(Class, String)} with ORDER BY clause.
   */
  @Test
  public void testQueryWithOrderBy() {

    // given
    Class<DummyFooEntity> entityType = DummyFooEntity.class;
    JpqlBuilder queryBuilder = getQueryBuilder();
    String propertyName = DummyFooEntity.PROPERTY_NUMBER;
    Integer propertyMin = Integer.valueOf(42);
    Integer propertyMax = Integer.valueOf(142);

    // then
    JpqlFromClause<DummyFooEntity> from = queryBuilder.from(entityType);
    JpqlOrderByClause<DummyFooEntity> clause = from.where().property(null, propertyName)
        .isBetween(propertyMin, propertyMax).orderBy(propertyName, SortOrder.DESCENDING);
    ListQuery<DummyFooEntity> query = clause.select();

    // test
    Assert.assertNotNull(query);
    String entityName = entityType.getSimpleName();
    String alias = clause.getEntityAlias();
    String expectedQuery = MessageFormat.format(
        "SELECT {1} FROM {0} {1} WHERE {1}.{2} BETWEEN ? AND ? ORDER BY {1}.{2} DESC", entityName, alias, propertyName);
    Assert.assertEquals(expectedQuery, query.toString());
    Assert.assertSame(propertyMin, query.getOrCreateQuery().getParameterValue(0));
    Assert.assertSame(propertyMax, query.getOrCreateQuery().getParameterValue(1));
  }

}
