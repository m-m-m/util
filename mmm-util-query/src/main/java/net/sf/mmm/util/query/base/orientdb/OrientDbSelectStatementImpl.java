/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.orientdb;

import java.util.List;

import com.orientechnologies.orient.core.db.ODatabaseInternal;
import com.orientechnologies.orient.core.db.ODatabaseRecordThreadLocal;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;

import net.sf.mmm.util.exception.api.DuplicateObjectException;
import net.sf.mmm.util.property.api.path.PropertyPath;
import net.sf.mmm.util.query.api.orientdb.OrientDbSelectStatement;
import net.sf.mmm.util.query.api.variable.Variable;
import net.sf.mmm.util.query.base.AbstractSelectStatement;
import net.sf.mmm.util.query.base.feature.FeatureLetImpl;

/**
 * Implementation of {@link OrientDbSelectStatement}.
 *
 * @param <E> the generic type of the queried object (typically a {@link net.sf.mmm.util.bean.api.Bean}).
 *
 * @author hohwille
 * @since 8.0.0
 */
public class OrientDbSelectStatementImpl<E> extends AbstractSelectStatement<E, OrientDbSelectStatement<E>>
    implements OrientDbSelectStatement<E> {

  private final String source;

  /**
   * The constructor.
   *
   * @param source the name of the {@link com.orientechnologies.orient.core.metadata.schema.OClass} to query.
   */
  public OrientDbSelectStatementImpl(String source) {
    super(OrientDbDialect.INSTANCE);
    this.source = source;
  }

  @Override
  protected String getSource() {

    return this.source;
  }

  private List<ODocument> query(String prefix) {

    OSQLSynchQuery<ODocument> query = new OSQLSynchQuery<>(prefix + getSql());
    List<Object> variables = getVariables();
    ODatabaseRecordThreadLocal singleton = ODatabaseRecordThreadLocal.INSTANCE;
    // if (!singleton.isDefined()) {
    // throw new IllegalStateException("No transaction");
    // }
    Object[] variablesArray = variables.toArray(new Object[variables.size()]);
    ODatabaseInternal<?> connection = singleton.get().getDatabaseOwner();
    return connection.command(query).execute(variablesArray);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<E> fetch() {

    return (List<E>) query(getSqlDialect().select());
  }

  @SuppressWarnings("unchecked")
  @Override
  public E fetchFirst() {

    List<ODocument> result = query(getSqlDialect().select());
    if (result.isEmpty()) {
      return null;
    }
    return (E) result.get(0);
  }

  @SuppressWarnings("unchecked")
  @Override
  public E fetchOne() {

    List<ODocument> result = query(getSqlDialect().select());
    if (result.isEmpty()) {
      return null;
    }
    if (result.size() > 1) {
      throw new DuplicateObjectException(getSource());
    }
    return (E) result.get(0);
  }

  @Override
  public long fetchCount() {

    List<ODocument> result = query(getSqlDialect().selectCountAll());
    Long count = result.get(0).field("count");
    return count.longValue();
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

}
