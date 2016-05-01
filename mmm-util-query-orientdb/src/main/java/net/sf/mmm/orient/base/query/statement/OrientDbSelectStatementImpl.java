/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.base.query.statement;

import java.util.List;
import java.util.function.Function;

import com.orientechnologies.orient.core.db.ODatabaseInternal;
import com.orientechnologies.orient.core.db.ODatabaseRecordThreadLocal;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;

import net.sf.mmm.orient.api.query.statement.OrientDbSelectStatement;
import net.sf.mmm.util.bean.api.BeanFactory;
import net.sf.mmm.util.bean.api.EntityBean;
import net.sf.mmm.util.property.api.path.PropertyPath;
import net.sf.mmm.util.query.api.path.EntityAlias;
import net.sf.mmm.util.query.api.path.Path;
import net.sf.mmm.util.query.api.variable.Variable;
import net.sf.mmm.util.query.base.QueryMode;
import net.sf.mmm.util.query.base.feature.FeatureLetImpl;
import net.sf.mmm.util.query.base.path.Alias;
import net.sf.mmm.util.query.base.statement.AbstractSelectStatement;

/**
 * Implementation of {@link OrientDbSelectStatement}.
 *
 * @param <E> the generic type of the queried object (typically a {@link net.sf.mmm.util.bean.api.Bean}).
 *
 * @author hohwille
 * @since 8.0.0
 */
public class OrientDbSelectStatementImpl<E> extends
    AbstractSelectStatement<E, OrientDbSelectStatement<E>, ODocument> implements OrientDbSelectStatement<E> {

  /**
   * The constructor.
   *
   * @param dialect - see {@link #getDialect()}. Typically {@link OrientDbDialect#INSTANCE}.
   * @param alias - see {@link #getAlias()}. E.g. {@link ODB#alias(OClass)}.
   * @param mapper - see {@link #getMapper()}.
   */
  protected OrientDbSelectStatementImpl(OrientDbDialect dialect, EntityAlias<E> alias,
      Function<ODocument, E> mapper) {
    super(dialect, alias, mapper);
  }

  /**
   * The constructor.
   *
   * @param dialect - see {@link #getDialect()}. Typically {@link OrientDbDialect#INSTANCE}.
   * @param alias - see {@link #getAlias()}. E.g. {@link ODB#alias(OClass)}.
   * @param mapper - see {@link #getMapper()}.
   * @param toClass - see
   *        {@link net.sf.mmm.util.query.api.statement.StatementFactory#selectFrom(EntityAlias, Class, Path...)}.
   * @param constructorArgs - see
   *        {@link net.sf.mmm.util.query.api.statement.StatementFactory#selectFrom(EntityAlias, Class, Path...)}.
   */
  public OrientDbSelectStatementImpl(OrientDbDialect dialect, EntityAlias<?> alias, Function<ODocument, E> mapper,
      Class<E> toClass, PropertyPath<?>... constructorArgs) {
    super(dialect, alias, mapper, toClass, constructorArgs);
  }

  /**
   * The constructor.
   *
   * @param dialect - see {@link #getDialect()}. Typically {@link OrientDbDialect#INSTANCE}.
   * @param alias - see {@link #getAlias()}. E.g. {@link ODB#alias(OClass)}.
   * @param mapper - see {@link #getMapper()}.
   * @param paths - see
   *        {@link net.sf.mmm.util.query.api.statement.StatementFactory#selectFrom(EntityAlias, Class, PropertyPath...)}
   *        .
   */
  public OrientDbSelectStatementImpl(OrientDbDialect dialect, EntityAlias<?> alias, Function<ODocument, E> mapper,
      PropertyPath<?>... paths) {
    super(dialect, alias, mapper, paths);
  }

  @Override
  protected Object doExecute(String sql, QueryMode mode, Long offset, Integer limit) {

    OSQLSynchQuery<ODocument> query = new OSQLSynchQuery<>(sql);
    ODatabaseRecordThreadLocal singleton = ODatabaseRecordThreadLocal.INSTANCE;
    // if (!singleton.isDefined()) {
    // throw new IllegalStateException("No transaction");
    // }
    ODatabaseInternal<?> connection = singleton.get().getDatabaseOwner();

    List<Object> variables = getParameters();
    Object[] variablesArray = variables.toArray(new Object[variables.size()]);
    Object result = connection.command(query).execute(variablesArray);
    return result;
  }

  @Override
  public <V> OrientDbSelectStatement<E> let(Variable<V> variable, PropertyPath<V> path) {

    feature(FeatureLetImpl.class).let(variable, path);
    return self();
  }

  @Override
  public OrientDbSelectStatement<E> let(String variable, PropertyPath<?> path) {

    feature(FeatureLetImpl.class).let(variable, path);
    return self();
  }

  @Override
  public <V> Variable<V> let(PropertyPath<V> path, String variable) {

    return feature(FeatureLetImpl.class).let(path, variable);
  }

  /**
   * @param <E> the generic type of the {@link EntityBean}.
   * @param prototype the {@link BeanFactory#createPrototype(Class) prototype} of the {@link EntityBean}.
   * @param mapper the {@link Function} to {@link Function#apply(Object) map} from {@link ODocument} to {@literal <E>}.
   * @return the new {@link OrientDbSelectStatement}.
   */
  public static <E extends EntityBean> OrientDbSelectStatementImpl<E> ofBean(E prototype,
      Function<ODocument, E> mapper) {

    Alias<E> source = Alias.ofBean(prototype);
    return new OrientDbSelectStatementImpl<>(OrientDbDialect.INSTANCE, source, mapper);
  }

  /**
   * @param oClass the {@link OClass}.
   * @return the new {@link OrientDbSelectStatement}.
   */
  public static OrientDbSelectStatementImpl<ODocument> of(OClass oClass) {

    Alias<ODocument> source = new Alias<>(oClass.getName());
    return new OrientDbSelectStatementImpl<>(OrientDbDialect.INSTANCE, source, null);
  }

}
