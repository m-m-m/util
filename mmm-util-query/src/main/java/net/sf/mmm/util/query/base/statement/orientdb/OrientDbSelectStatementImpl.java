/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.statement.orientdb;

import java.util.List;
import java.util.function.Function;

import com.orientechnologies.orient.core.db.ODatabaseInternal;
import com.orientechnologies.orient.core.db.ODatabaseRecordThreadLocal;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;

import net.sf.mmm.util.bean.api.BeanFactory;
import net.sf.mmm.util.bean.api.EntityBean;
import net.sf.mmm.util.exception.api.DuplicateObjectException;
import net.sf.mmm.util.property.api.path.PropertyPath;
import net.sf.mmm.util.query.api.statement.orientdb.OrientDbSelectStatement;
import net.sf.mmm.util.query.api.variable.Variable;
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
public class OrientDbSelectStatementImpl<E> extends AbstractSelectStatement<E, OrientDbSelectStatement<E>>
    implements OrientDbSelectStatement<E> {

  private final Function<ODocument, E> mapper;

  /**
   * The constructor.
   *
   * @param alias the name of the {@link com.orientechnologies.orient.core.metadata.schema.OClass} to query.
   * @param mapper the {@link Function} to {@link Function#apply(Object) map} from {@link ODocument} to {@literal <E>}.
   */
  protected OrientDbSelectStatementImpl(Alias<E> alias, Function<ODocument, E> mapper) {
    super(OrientDbDialect.INSTANCE, alias);
    this.mapper = mapper;
  }

  private List<ODocument> query(String prefix) {

    OSQLSynchQuery<ODocument> query = new OSQLSynchQuery<>(prefix + getSql());
    List<Object> variables = getParameters();
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

  @Override
  public E fetchFirst() {

    List<ODocument> result = query(getSqlDialect().select());
    if (result.isEmpty()) {
      return null;
    }
    return this.mapper.apply(result.get(0));
  }

  @Override
  public E fetchOne() {

    List<ODocument> result = query(getSqlDialect().select());
    if (result.isEmpty()) {
      return null;
    }
    if (result.size() > 1) {
      throw new DuplicateObjectException(getAlias());
    }
    return this.mapper.apply(result.get(0));
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

  /**
   * @param <E> the generic type of the {@link EntityBean}.
   * @param prototype the {@link BeanFactory#createPrototype(Class) prototype} of the {@link EntityBean}.
   * @param mapper the {@link Function} to {@link Function#apply(Object) map} from {@link ODocument} to {@literal <E>}.
   * @return the new {@link OrientDbSelectStatement}.
   */
  public static <E extends EntityBean<?>> OrientDbSelectStatementImpl<E> ofBean(E prototype,
      Function<ODocument, E> mapper) {

    Alias<E> source = Alias.ofBean(prototype);
    return new OrientDbSelectStatementImpl<>(source, mapper);
  }

  /**
   * @param oClass the {@link OClass}.
   * @return the new {@link OrientDbSelectStatement}.
   */
  public static OrientDbSelectStatementImpl<ODocument> of(OClass oClass) {

    Alias<ODocument> source = new Alias<>(oClass.getName());
    return new OrientDbSelectStatementImpl<>(source, x -> x);
  }

}
