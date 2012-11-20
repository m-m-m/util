/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa.query;

import java.text.MessageFormat;

import javax.persistence.TypedQuery;

import net.sf.mmm.persistence.api.jpa.query.JpqlFromClause;
import net.sf.mmm.persistence.api.jpa.query.JpqlOperator;
import net.sf.mmm.persistence.api.jpa.query.JpqlOrderByClause;
import net.sf.mmm.persistence.api.jpa.query.JpqlQueryBuilder;
import net.sf.mmm.test.jpa.EntityManagerMock;
import net.sf.mmm.util.lang.api.SortOrder;
import net.sf.mmm.util.pojo.path.api.TypedProperty;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test-case for {@link JpqlQueryBuilderImpl}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class JqplQueryBuilderImplTest {

  /**
   * @return the {@link JpqlQueryBuilder} instance to test.
   */
  protected JpqlQueryBuilder getQueryBuilder() {

    JpqlQueryBuilderImpl builder = new JpqlQueryBuilderImpl();
    builder.setEntityManager(new EntityManagerMock());
    builder.initialize();
    return builder;
  }

  /**
   * Test for {@link JpqlQueryBuilder#from(Class)} with most simple query.
   */
  @Test
  public void testSimpleQuery() {

    // given
    Class<DummyBarEntity> entityType = DummyBarEntity.class;

    // then
    TypedQuery<DummyBarEntity> query = getQueryBuilder().from(entityType).select();

    // test
    Assert.assertNotNull(query);
    String entityName = entityType.getSimpleName();
    String expectedQuery = MessageFormat.format("SELECT {0}Alias FROM {0} {0}Alias", entityName);
    Assert.assertEquals(expectedQuery, query.toString());
  }

  /**
   * Test for {@link JpqlQueryBuilder#from(Class)} with
   * {@link net.sf.mmm.persistence.api.jpa.query.JpqlFragment#selectCount() count selection}.
   */
  @Test
  public void testCountQuery() {

    // given
    Class<DummyBarEntity> entityType = DummyBarEntity.class;

    // then
    TypedQuery<Long> query = getQueryBuilder().from(entityType).selectCount();

    // test
    Assert.assertNotNull(query);
    String entityName = entityType.getSimpleName();
    String expectedQuery = MessageFormat.format("SELECT COUNT(*) FROM {0} {0}Alias", entityName);
    Assert.assertEquals(expectedQuery, query.toString());
  }

  /**
   * Test for {@link JpqlQueryBuilder#from(Class, String)} with WHERE clause.
   */
  @Test
  public void testQueryWithWhereClause() {

    // given
    Class<DummyFooEntity> entityType = DummyFooEntity.class;
    String alias = "foo";
    JpqlQueryBuilder queryBuilder = getQueryBuilder();
    String property1Name = TypedProperty.createPath(DummyFooEntity.PROPERTY_BAR, DummyBarEntity.PROPERTY_VALUE);
    String property1Value = "magic";
    String property2Name = DummyFooEntity.PROPERTY_NUMBER;
    Integer property2Value = Integer.valueOf(42);

    // then
    JpqlFromClause<DummyFooEntity> from = queryBuilder.from(entityType, alias);
    TypedQuery<DummyFooEntity> query = from.where().isCompareValue(property1Name, JpqlOperator.EQUAL, property1Value)
        .isCompareValue(property2Name, JpqlOperator.NOT_EQUAL, property2Value).or()
        .isCompareValue(property2Name, JpqlOperator.GREATER_EQUAL, property2Value).select();

    // test
    Assert.assertNotNull(query);
    String entityName = entityType.getSimpleName();
    String expectedQuery = MessageFormat.format(
        "SELECT {1} FROM {0} {1} WHERE {1}.{2} = ? AND {1}.{3} <> ? OR {1}.{3} >= ?", entityName, alias, property1Name,
        property2Name);
    Assert.assertEquals(expectedQuery, query.toString());
    Assert.assertSame(property1Value, query.getParameterValue(0));
    Assert.assertSame(property2Value, query.getParameterValue(1));
  }

  /**
   * Test for {@link JpqlQueryBuilder#from(Class, String)} with ORDER BY clause.
   */
  @Test
  public void testQueryWithOrderBy() {

    // given
    Class<DummyFooEntity> entityType = DummyFooEntity.class;
    JpqlQueryBuilder queryBuilder = getQueryBuilder();
    String property2Name = DummyFooEntity.PROPERTY_NUMBER;
    Integer property2Min = Integer.valueOf(42);
    Integer property2Max = Integer.valueOf(142);

    // then
    JpqlFromClause<DummyFooEntity> from = queryBuilder.from(entityType);
    JpqlOrderByClause<DummyFooEntity> clause = from.where().isBetween(property2Name, property2Min, property2Max)
        .orderBy(property2Name, SortOrder.DESCENDING);
    TypedQuery<DummyFooEntity> query = clause.select();

    // test
    Assert.assertNotNull(query);
    String entityName = entityType.getSimpleName();
    String alias = clause.getEntityAlias();
    String expectedQuery = MessageFormat
        .format("SELECT {1} FROM {0} {1} WHERE {1}.{2} BETWEEN ? AND ? ORDER BY {1}.{2} DESC", entityName, alias,
            property2Name);
    Assert.assertEquals(expectedQuery, query.toString());
    Assert.assertSame(property2Min, query.getParameterValue(0));
    Assert.assertSame(property2Max, query.getParameterValue(1));
  }

}
