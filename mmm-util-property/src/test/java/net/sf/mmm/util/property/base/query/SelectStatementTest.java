/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.base.query;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.BeanFactory;
import net.sf.mmm.util.bean.impl.BeanFactoryImpl;
import net.sf.mmm.util.bean.impl.example.ExampleBean;
import net.sf.mmm.util.lang.api.Orientation;
import net.sf.mmm.util.property.api.path.PropertyPath;
import net.sf.mmm.util.property.api.query.SelectStatement;
import net.sf.mmm.util.property.base.expression.Expressions;

/**
 * Test of {@link SelectStatement} and {@link AbstractSelectStatement}.
 *
 * @author hohwille
 */
public class SelectStatementTest extends Assertions {

  protected <E extends Bean> TestSelectStatement<E> createSelectStatement(Class<E> beanType) {

    E prototype = BeanFactoryImpl.getInstance().createPrototype(beanType);
    return new TestSelectStatement<E>(prototype);
  }

  @Test
  public void testSql() {

    // test "empty" statement
    TestSelectStatement<ExampleBean> statement = createSelectStatement(ExampleBean.class);
    String sqlFrom = " FROM \"ExampleBean\"";
    assertThat(statement.getSql()).isEqualTo(sqlFrom);
    assertThat(statement.getVariables()).isEmpty();

    // add regular equals condition to where clause
    ExampleBean prototype = statement.getPrototype();
    String countryCode = "DE";
    statement.where(Expressions.of(prototype.CountryCode()).eq(countryCode));
    assertThat(statement.getSql()).isEqualTo(sqlFrom + " WHERE \"CountryCode\" = ?");
    assertThat(statement.getVariables()).containsExactly(countryCode);

    // add between expression to where clause
    Integer min = 16;
    Integer max = 21;
    statement.where(Expressions.ofNumber((PropertyPath) prototype.Age()).between(min, max));
    assertThat(statement.getSql()).isEqualTo(sqlFrom + " WHERE (\"CountryCode\" = ? AND \"Age\" BETWEEN ? AND ?)");
    assertThat(statement.getVariables()).containsExactly(countryCode, min, max);

    // add like expression to where clause
    String pattern = "%\\%";
    char escape = '\\';
    statement.where(Expressions.ofString(prototype.Name()).like(pattern, escape));
    assertThat(statement.getSql()).isEqualTo(
        sqlFrom + " WHERE (\"CountryCode\" = ? AND \"Age\" BETWEEN ? AND ? AND \"Name\" LIKE ? ESCAPE '\\')");
    assertThat(statement.getVariables()).containsExactly(countryCode, min, max, pattern);

    // add 2 expressions combined with OR to where clause
    Orientation orientation = Orientation.HORIZONTAL;
    statement.where(Expressions.ofBoolean(prototype.Friend()).isFalse()
        .or(Expressions.of(prototype.Orientation()).eq(orientation)));
    String sqlWhere = sqlFrom
        + " WHERE (\"CountryCode\" = ? AND \"Age\" BETWEEN ? AND ? AND \"Name\" LIKE ? ESCAPE '\\' AND (\"Friend\" = FALSE OR \"Orientation\" = ?))";
    assertThat(statement.getSql()).isEqualTo(sqlWhere);
    assertThat(statement.getVariables()).containsExactly(countryCode, min, max, pattern, orientation);

    // add pointless expression to where clause
    statement.where(Expressions.of(Boolean.FALSE).isFalse());
    assertThat(statement.getSql()).isEqualTo(sqlWhere);
    assertThat(statement.getVariables()).containsExactly(countryCode, min, max, pattern, orientation);

    // add first property to group by clause
    statement.groupBy(prototype.Age());
    String sqlGroupBy = sqlWhere + " GROUP BY \"Age\"";
    assertThat(statement.getSql()).isEqualTo(sqlGroupBy);
    assertThat(statement.getVariables()).containsExactly(countryCode, min, max, pattern, orientation);

    // add second property to group by clause
    statement.groupBy(prototype.CountryCode());
    sqlGroupBy += ", \"CountryCode\"";
    assertThat(statement.getSql()).isEqualTo(sqlGroupBy);
    assertThat(statement.getVariables()).containsExactly(countryCode, min, max, pattern, orientation);

    // add first property to order by clause
    statement.orderBy(prototype.Age());
    String sqlOrderBy = sqlGroupBy + " ORDER BY \"Age\"";
    assertThat(statement.getSql()).isEqualTo(sqlOrderBy);
    assertThat(statement.getVariables()).containsExactly(countryCode, min, max, pattern, orientation);

    // add second property to order by clause
    statement.orderBy(prototype.CountryCode());
    sqlOrderBy += ", \"CountryCode\"";
    assertThat(statement.getSql()).isEqualTo(sqlOrderBy);
    assertThat(statement.getVariables()).containsExactly(countryCode, min, max, pattern, orientation);

    // add offset
    long offset = 500;
    statement.offset(offset);
    String sqlPaging = sqlOrderBy + " OFFSET ?";
    assertThat(statement.getSql()).isEqualTo(sqlPaging);
    assertThat(statement.getVariables()).containsExactly(countryCode, min, max, pattern, orientation, offset);

    // add limit
    long limit = 100;
    statement.limit(limit);
    sqlPaging += " LIMIT ?";
    assertThat(statement.getSql()).isEqualTo(sqlPaging);
    assertThat(statement.getVariables()).containsExactly(countryCode, min, max, pattern, orientation, offset,
        limit);

  }

  @Test
  public void testSqlOptimization() {

    TestSelectStatement<ExampleBean> statement = createSelectStatement(ExampleBean.class);

    // add regular equals condition to where clause
    ExampleBean prototype = statement.getPrototype();
    statement.where(Expressions.of(Boolean.FALSE).isFalse());
    assertThat(statement.getSql()).isEqualTo(" FROM \"ExampleBean\"");
    assertThat(statement.getVariables()).isEmpty();

    try {
      statement.where(Expressions.of(Boolean.FALSE).isTrue());
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    } catch (IllegalArgumentException e) {
      assertThat(e).hasMessage("Expression can never match!");
    }
    assertThat(statement.getSql()).isEqualTo(" FROM \"ExampleBean\"");
    assertThat(statement.getVariables()).isEmpty();
  }

  public static class TestSelectStatement<E extends Bean>
      extends AbstractSelectStatement<E, TestSelectStatement<E>> {

    private final E prototype;

    /**
     * The constructor.
     *
     * @param prototype the {@link Bean}-{@link BeanFactory#getPrototype(Bean) prototype}.
     */
    public TestSelectStatement(E prototype) {
      super(new DefaultSqlDialect());
      this.prototype = prototype;
    }

    /**
     * @return the prototype
     */
    public E getPrototype() {

      return this.prototype;
    }

    @Override
    protected String getSource() {

      return this.prototype.access().getSimpleName();
    }

    @Override
    public List<E> fetch() {

      throw new UnsupportedOperationException();
    }

    @Override
    public E fetchFirst() {

      throw new UnsupportedOperationException();
    }

    @Override
    public E fetchOne() {

      throw new UnsupportedOperationException();
    }

    @Override
    public long fetchCount() {

      throw new UnsupportedOperationException();
    }

  }

}
