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
 * @param <E> the generic type of the {@link #getType() type} of this {@link Alias}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class Alias<E> extends AbstractPathRoot<E> implements EntityAlias<E> {

  private final String source;

  private final String name;

  private final E prototype;

  private final Class<E> type;

  /**
   * The constructor.
   *
   * @param source - see {@link #getSource()}.
   */
  public Alias(String source) {

    this(source, null, null, null);
  }

  /**
   * The constructor.
   *
   * @param source - see {@link #getSource()}.
   * @param name - see {@link #getName()}.
   */
  public Alias(String source, String name) {

    this(source, name, null, null);
  }

  /**
   * The constructor.
   *
   * @param source - see {@link #getSource()}.
   * @param name - see {@link #getName()}.
   * @param type - see {@link #getType()}.
   */
  public Alias(String source, String name, Class<E> type) {

    this(source, name, type, null);
  }

  /**
   * The constructor.
   *
   * @param source - see {@link #getSource()}.
   * @param name - see {@link #getName()}.
   * @param prototype - see {@link #getPrototype()}.
   */
  public Alias(String source, String name, E prototype) {

    this(source, name, null, prototype);
  }

  /**
   * The constructor.
   *
   * @param source - see {@link #getSource()}.
   * @param name - see {@link #getName()}.
   * @param type - see {@link #getType()}.
   * @param prototype - see {@link #getPrototype()}.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  private Alias(String source, String name, Class<E> type, E prototype) {
    super();
    this.source = source;
    this.name = name;
    this.prototype = prototype;
    if ((type == null) && (prototype != null)) {
      this.type = (Class) ((Bean) prototype).access().getBeanClass();
    } else {
      this.type = type;
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
  public Class<E> getType() {

    return this.type;
  }

  @Override
  public Alias<E> as(String aliasName) {

    return new Alias<>(this.source, aliasName, this.type, this.prototype);
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
