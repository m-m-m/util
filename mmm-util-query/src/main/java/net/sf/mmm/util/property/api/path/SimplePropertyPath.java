/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.path;

import javafx.beans.property.ReadOnlyProperty;
import net.sf.mmm.util.pojo.path.api.TypedPath;
import net.sf.mmm.util.property.api.ReadableProperty;
import net.sf.mmm.util.query.api.variable.Variable;
import net.sf.mmm.util.query.base.SqlDialect;

/**
 * This is an implementation of {@link TypedPath} that can be build from any {@link ReadOnlyProperty}.
 *
 * @param <V> the generic type of the property value identified by this path.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class SimplePropertyPath<V> implements PropertyPath<V>, TypedPath<V> {

  /** @see #getPojoPath() */
  private final String pojoPath;

  /** @see #getParentPath() */
  private final String parentPath;

  private final ReadableProperty<V> property;

  /**
   * The constructor.
   *
   * @param segment - see {@link #getSegment()}.
   * @param parentPath - see {@link #getParentPath()}.
   */
  private SimplePropertyPath(String parentPath, ReadableProperty<V> property) {
    super();
    this.parentPath = parentPath;
    this.property = property;
    String propertyName = property.getName();
    if (this.parentPath == null) {
      this.pojoPath = propertyName;
    } else {
      this.pojoPath = parentPath + SEPARATOR + propertyName;
    }
  }

  @Override
  public String getName() {

    return this.pojoPath;
  }

  @Override
  public String getPojoPath() {

    return this.pojoPath;
  }

  @Override
  public String getParentPath() {

    return this.parentPath;
  }

  @Override
  public String getSegment() {

    return this.property.getName();
  }

  @Override
  public V getValue() {

    return this.property.getValue();
  }

  /**
   * @param <T> the generic type of the {@link ReadOnlyProperty property} {@link ReadOnlyProperty#getValue() value}.
   * @param readableProperty the {@link ReadOnlyProperty property} to append to the path.
   * @return the {@link SimplePropertyPath} build by appending the given {@link ReadOnlyProperty property} to this
   *         {@link SimplePropertyPath}.
   */
  public <T> SimplePropertyPath<T> to(ReadableProperty<T> readableProperty) {

    return new SimplePropertyPath<>(this.pojoPath, readableProperty);
  }

  /**
   * @param <V> the generic type of the {@link ReadOnlyProperty property} {@link ReadOnlyProperty#getValue() value}.
   * @param readableProperty the {@link ReadOnlyProperty property} to get as path.
   * @return the {@link SimplePropertyPath} for the given {@link ReadOnlyProperty property}.
   */
  public static <V> SimplePropertyPath<V> of(ReadableProperty<V> readableProperty) {

    return new SimplePropertyPath<>(null, readableProperty);
  }

  /**
   * @param <V> the generic type of the {@link ReadOnlyProperty property} {@link ReadOnlyProperty#getValue() value}.
   * @param variable the {@link Variable} to start the path with.
   * @param dialect the {@link SqlDialect}.
   * @param path the {@link PropertyPath} to join to the given {@link Variable}.
   * @return the {@link SimplePropertyPath} joining the {@link Variable} with the {@link PropertyPath}.
   */
  public static <V> SimplePropertyPath<V> of(Variable<?> variable, SqlDialect dialect, PropertyPath<V> path) {

    String var = dialect.variable(variable);
    if (path instanceof ReadableProperty) {
      return new SimplePropertyPath<>(var, (ReadableProperty<V>) path);
    } else {
      SimplePropertyPath<V> spp = (SimplePropertyPath<V>) path;
      return new SimplePropertyPath<>(var + "." + spp.parentPath, spp.property);
    }
  }
}
