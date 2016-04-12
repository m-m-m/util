/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.path;

import javafx.beans.property.ReadOnlyProperty;
import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.EntityBean;
import net.sf.mmm.util.bean.api.link.Link;
import net.sf.mmm.util.pojo.path.api.TypedPath;
import net.sf.mmm.util.property.api.ReadableProperty;
import net.sf.mmm.util.property.api.link.LinkProperty;
import net.sf.mmm.util.property.api.path.PropertyPath;
import net.sf.mmm.util.query.api.variable.Variable;
import net.sf.mmm.util.query.base.statement.SqlDialect;
import net.sf.mmm.util.reflect.api.GenericType;

/**
 * This is an implementation of {@link TypedPath} that can be build from any {@link ReadOnlyProperty}.
 *
 * @param <V> the generic type of the property value identified by this path.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class SimplePropertyPath<V> implements PropertyPath<V>, TypedPath<V> {

  private final String pojoPath;

  private final SimplePropertyPath<?> parent;

  private final Variable<?> variable;

  private final ReadableProperty<?> property;

  /**
   * The constructor.
   *
   * @param segment - see {@link #getSegment()}.
   * @param parentPath - see {@link #getParentPath()}.
   */
  private SimplePropertyPath(SimplePropertyPath<?> parent, ReadableProperty<?> property) {
    super();
    this.parent = parent;
    this.property = property;
    String propertyName = property.getName();
    if (this.parent == null) {
      this.pojoPath = propertyName;
      this.variable = null;
    } else {
      this.pojoPath = parent.getPojoPath() + SEPARATOR + propertyName;
      this.variable = parent.variable;
    }
    assert verify();
  }

  private boolean verify() {

    if (this.parent != null) {
      if ((this.parent.property != null) && (this.property != null)) {
        Bean bean = this.property.getBean();
        if (bean != null) {
          GenericType<?> type = this.parent.property.getType();
          if (type != null) {
            if (Link.class.isAssignableFrom(type.getRetrievalClass())) {
              int argCount = type.getTypeArgumentCount();
              if (argCount == 2) {
                type = type.getTypeArgument(1);
              } else {
                throw new IllegalStateException("Illegal link type " + type);
              }
            }
            Class<?> typeClass = type.getRetrievalClass();
            if (typeClass.isInstance(bean)) {
              throw new IllegalArgumentException("Path " + this.parent.pojoPath + " leads to type " + typeClass
                  + " but property " + this.property.getName() + " belongs to " + bean.access().getSimpleName());
            }
          }
        }
      }
    }
    return true;
  }

  /**
   * The constructor.
   *
   * @param segment - see {@link #getSegment()}.
   * @param parentPath - see {@link #getParentPath()}.
   */
  private SimplePropertyPath(Variable<V> variable, String path) {

    super();
    this.variable = variable;
    this.pojoPath = path;
    this.parent = null;
    this.property = null;
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

    if (this.parent == null) {
      return null;
    }
    return this.parent.getPojoPath();
  }

  @Override
  public String getSegment() {

    if (this.property == null) {
      return this.variable.getName();
    }
    return this.property.getName();
  }

  @SuppressWarnings("unchecked")
  @Override
  public V getValue() {

    Object value = this.property.getValue();
    if (value instanceof Link) {
      value = ((Link<?, ?>) value).getTarget();
    }
    return (V) value;
  }

  /**
   * @param <T> the generic type of the {@link ReadableProperty property} {@link ReadableProperty#getValue() value}.
   * @param readableProperty the {@link ReadableProperty property} to append to the path.
   * @return the {@link SimplePropertyPath} build by appending the given {@link ReadableProperty property} to this
   *         {@link SimplePropertyPath}.
   */
  public <T> SimplePropertyPath<T> to(ReadableProperty<T> readableProperty) {

    return new SimplePropertyPath<>(this, readableProperty);
  }

  /**
   * @param <ID> the generic type of the {@link Link#getId() unique ID}.
   * @param <E> the generic type of the {@link Link#getTarget() linked} {@link EntityBean}.
   * @param linkProperty the {@link LinkProperty property} to append to the path.
   * @return the {@link SimplePropertyPath} build by appending the given {@link LinkProperty property} to this
   *         {@link SimplePropertyPath}.
   */
  public <ID, E extends EntityBean<ID>> SimplePropertyPath<E> toLink(LinkProperty<ID, E> linkProperty) {

    return new SimplePropertyPath<>(this, linkProperty);
  }

  private SimplePropertyPath<?> addRoot(SimplePropertyPath<?> root) {

    if (this.parent == null) {
      return root.to(this.property);
    } else {
      return this.parent.addRoot(root).to(this.property);
    }
  }

  /**
   * @return the root {@link SimplePropertyPath path} segment.
   */
  protected SimplePropertyPath<?> getRoot() {

    if (this.parent == null) {
      return this;
    }
    return this.parent.getRoot();
  }

  /**
   * @return the root {@link ReadableProperty property} this {@link SimplePropertyPath} was {@link #of(ReadableProperty)
   *         created from} or {@code null} if NOT available.
   */
  public ReadableProperty<?> getRootBean() {

    return getRoot().property;
  }

  /**
   * @return the root {@link Variable} this {@link SimplePropertyPath} was
   *         {@link #of(Variable, SqlDialect, PropertyPath) created from} or {@code null} if NOT available.
   */
  public Variable<?> getRootVariable() {

    return getRoot().variable;
  }

  /**
   * @param bean the potential root {@link Bean}.
   * @return {@code true} if this {@link SimplePropertyPath} was {@link #of(ReadableProperty) created from a root
   *         property} {@link ReadableProperty#getBean() owned} by the given bean.
   */
  public boolean hasRoot(Bean bean) {

    SimplePropertyPath<?> root = getRoot();
    if (root.property != null) {
      Bean rootBean = root.property.getBean();
      if (rootBean != null) {
        if ((rootBean == bean) || rootBean.access().getQualifiedName().equals(bean.access().getQualifiedName())) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public String toString() {

    return this.pojoPath;
  }

  /**
   * @param <V> the generic type of the {@link ReadableProperty property} {@link ReadableProperty#getValue() value}.
   * @param readableProperty the {@link ReadableProperty property} to get as path.
   * @return the {@link SimplePropertyPath} for the given {@link ReadableProperty property}.
   */
  public static <V> SimplePropertyPath<V> of(ReadableProperty<V> readableProperty) {

    return new SimplePropertyPath<>(null, readableProperty);
  }

  /**
   * @param <ID> the generic type of the {@link Link#getId() unique ID}.
   * @param <E> the generic type of the {@link Link#getTarget() linked} {@link EntityBean}.
   * @param linkProperty the {@link LinkProperty property} to get as path.
   * @return the {@link SimplePropertyPath} for the given {@link LinkProperty property}.
   */
  public static <ID, E extends EntityBean<ID>> SimplePropertyPath<E> ofLink(LinkProperty<ID, E> linkProperty) {

    return new SimplePropertyPath<>(null, linkProperty);
  }

  /**
   * @param <V> the generic type of the {@link ReadOnlyProperty property} {@link ReadOnlyProperty#getValue() value}.
   * @param variable the {@link Variable} to start the path with.
   * @param dialect the {@link SqlDialect}.
   * @param path the {@link PropertyPath} to join to the given {@link Variable}.
   * @return the {@link SimplePropertyPath} joining the {@link Variable} with the {@link PropertyPath}.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static <V> SimplePropertyPath<V> of(Variable<?> variable, SqlDialect dialect, PropertyPath<V> path) {

    SimplePropertyPath<?> varPath = of(variable, dialect);
    if (path instanceof ReadableProperty) {
      return varPath.to((ReadableProperty<V>) path);
    } else {
      SimplePropertyPath<V> spp = (SimplePropertyPath<V>) path;
      return (SimplePropertyPath) spp.addRoot(varPath);
    }
  }

  /**
   * @param <V> the generic type of the {@link ReadOnlyProperty property} {@link ReadOnlyProperty#getValue() value}.
   * @param variable the {@link Variable} to start the path with.
   * @param dialect the {@link SqlDialect}.
   * @return the {@link SimplePropertyPath} for the {@link Variable}.
   */
  private static <V> SimplePropertyPath<V> of(Variable<V> variable, SqlDialect dialect) {

    return new SimplePropertyPath<>(variable, dialect.variable(variable));
  }
}
