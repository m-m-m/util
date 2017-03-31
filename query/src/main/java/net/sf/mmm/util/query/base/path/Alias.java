/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.path;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.BeanFactory;
import net.sf.mmm.util.query.api.path.EntityAlias;

/**
 * This class represents an {@link Alias} used in a {@link net.sf.mmm.util.query.base.statement.SqlDialect#from() FROM}
 * block of an {@link net.sf.mmm.util.query.base.statement.AbstractStatement SQL statement}.
 *
 * @param <E> the generic type of the {@link #getEntityType() type} of this {@link Alias}.
 *
 * @author hohwille
 * @since 8.5.0
 */
public class Alias<E> extends AbstractPathRoot<E> implements EntityAlias<E> {

  private final String source;

  private final String name;

  private final E prototype;

  private final Class<?> entityType;

  private final Class<E> resultType;

  /**
   * The constructor.
   *
   * @param source - see {@link #getSource()}.
   */
  public Alias(String source) {

    this(source, null, null, null, null);
  }

  /**
   * The constructor.
   *
   * @param source - see {@link #getSource()}.
   * @param name - see {@link #getName()}.
   */
  public Alias(String source, String name) {

    this(source, name, null, null, null);
  }

  /**
   * The constructor.
   *
   * @param source - see {@link #getSource()}.
   * @param name - see {@link #getName()}.
   * @param type - see {@link #getEntityType()}.
   */
  public Alias(String source, String name, Class<E> type) {

    this(source, name, type, null, type);
  }

  /**
   * The constructor.
   *
   * @param source - see {@link #getSource()}.
   * @param name - see {@link #getName()}.
   * @param prototype - see {@link #getPrototype()}.
   */
  public Alias(String source, String name, E prototype) {

    this(source, name, null, prototype, null);
  }

  /**
   * The constructor.
   *
   * @param source - see {@link #getSource()}.
   * @param name - see {@link #getName()}.
   * @param entityType - see {@link #getEntityType()}.
   * @param prototype - see {@link #getPrototype()}.
   * @param resultType - see {@link #getResultType()}.
   */
  @SuppressWarnings("unchecked")
  public Alias(String source, String name, Class<?> entityType, E prototype, Class<E> resultType) {
    super();
    this.source = source;
    if (name == null) {
      this.name = Character.toLowerCase(source.charAt(0)) + source.substring(1);
    } else {
      this.name = name;
    }
    this.prototype = prototype;
    if ((resultType == null) && (prototype != null)) {
      this.resultType = (Class<E>) ((Bean) prototype).access().getBeanClass();
    } else {
      this.resultType = resultType;
    }
    if (entityType == null) {
      this.entityType = this.resultType;
    } else {
      this.entityType = entityType;
    }
  }

  @Override
  public String getSource() {

    return this.source;
  }

  @Override
  public String getName() {

    return this.name;
  }

  @Override
  public E getPrototype() {

    return this.prototype;
  }

  @Override
  public Class<?> getEntityType() {

    return this.entityType;
  }

  @Override
  public Class<E> getResultType() {

    return this.resultType;
  }

  @Override
  public Alias<E> as(String aliasName) {

    return new Alias<>(this.source, aliasName, this.entityType, this.prototype, this.resultType);
  }

  @Override
  public String toString() {

    if (this.name == null) {
      return this.source;
    } else {
      return this.source + " AS " + this.name;
    }
  }

  /**
   * @param <E> the generic type of the {@link Bean}.
   * @param prototype the {@link BeanFactory#createPrototype(Class) prototype} of the {@link Bean} to use as
   *        {@link #getPrototype() prototype}.
   * @return the new instance of {@link Alias}.
   */
  public static <E extends Bean> Alias<E> ofBean(E prototype) {

    return new Alias<>(prototype.access().getSimpleName(), null, prototype);
  }

  /**
   * @param <E> the generic type of the {@link Bean}.
   * @param prototype the {@link BeanFactory#createPrototype(Class) prototype} of the {@link Bean} to use as
   *        {@link #getPrototype() prototype}.
   * @param alias the {@link #getName()}.
   * @return the new instance of {@link Alias}.
   */
  public static <E extends Bean> Alias<E> ofBean(E prototype, String alias) {

    return new Alias<>(prototype.access().getSimpleName(), alias, prototype);
  }

}
