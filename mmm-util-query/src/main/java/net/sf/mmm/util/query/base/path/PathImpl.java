/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.path;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.link.Link;
import net.sf.mmm.util.property.api.ReadableProperty;
import net.sf.mmm.util.query.api.path.Path;
import net.sf.mmm.util.query.api.path.PathRoot;
import net.sf.mmm.util.query.base.argument.AbstractArgument;
import net.sf.mmm.util.reflect.api.GenericType;

/**
 * This is the implementation of {@link Path}.
 *
 * @param <V> the generic type of the property value identified by this path.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class PathImpl<V> extends AbstractPathFactory implements Path<V>, AbstractArgument<V> {

  private final PathRoot<?> root;

  private final PathImpl<?> parent;

  private final String pojoPath;

  private final ReadableProperty<?> property;

  /**
   * The constructor.
   *
   * @param root - {@link #getRoot()}.
   * @param property - see {@link #getProperty()}.
   */
  public PathImpl(PathRoot<?> root, ReadableProperty<?> property) {
    super();
    this.parent = null;
    this.property = property;
    this.root = root;
    String propertyName = property.getName();
    String rootName = root.getName();
    if (rootName == null) {
      this.pojoPath = propertyName;
    } else {
      this.pojoPath = rootName + SEPARATOR + propertyName;
    }
    assert verify();
  }

  /**
   * The constructor.
   *
   * @param parent - {@link #getParent()}.
   * @param property - see {@link #getProperty()}.
   */
  public PathImpl(PathImpl<?> parent, ReadableProperty<?> property) {
    super();
    this.parent = parent;
    this.property = property;
    this.root = parent.root;
    this.pojoPath = parent.getPojoPath() + SEPARATOR + property.getName();
    assert verify();
  }

  private boolean verify() {

    Bean bean = this.property.getBean();
    if (this.parent == null) {
      Object prototype = this.root.getPrototype();
      if ((prototype != null) && (bean != null) && (prototype != bean)) {
        String expectedType;
        if (prototype instanceof Bean) {
          expectedType = ((Bean) prototype).access().getQualifiedName();
        } else {
          expectedType = prototype.getClass().getName();
        }
        fail(expectedType, bean);
      }
    } else {
      verifyBeanProperty(bean, this.parent.property);
    }
    return true;
  }

  private void fail(String expectedType, Bean bean) {

    throw new IllegalArgumentException("Path " + getParentPath() + " leads to type " + expectedType
        + " but property " + this.property.getName() + " belongs to " + bean.access().getSimpleName());
  }

  private void verifyBeanProperty(Bean bean, ReadableProperty<?> prop) {

    if (bean == null) {
      return;
    }
    GenericType<?> type = prop.getType();
    if (type == null) {
      return;
    }
    if (Link.class.isAssignableFrom(type.getRetrievalClass())) {
      int argCount = type.getTypeArgumentCount();
      if (argCount == 2) {
        type = type.getTypeArgument(1);
      } else {
        throw new IllegalStateException("Illegal link type " + type);
      }
    }
    Class<?> typeClass = type.getRetrievalClass();
    if (!typeClass.isInstance(bean)) {
      fail(typeClass.getName(), bean);
    }
  }

  @Override
  public PathRoot<?> getRoot() {

    return this.root;
  }

  /**
   * @return the property
   */
  public ReadableProperty<?> getProperty() {

    return this.property;
  }

  /**
   * @return the parent
   */
  public PathImpl<?> getParent() {

    return this.parent;
  }

  @Override
  public String getParentPath() {

    if (this.parent == null) {
      return this.root.getName();
    } else {
      return this.parent.pojoPath;
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
  public Path<V> getPath() {

    return this;
  }

  @Override
  public String getSegment() {

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

  @Override
  public boolean isConstant() {

    return false;
  }

}
