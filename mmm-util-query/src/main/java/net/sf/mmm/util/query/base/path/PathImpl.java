/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.path;

import java.util.function.Function;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.BeanAccess;
import net.sf.mmm.util.bean.api.link.Link;
import net.sf.mmm.util.lang.api.attribute.AttributeReadName;
import net.sf.mmm.util.property.api.ReadableProperty;
import net.sf.mmm.util.property.api.lang.IntegerProperty;
import net.sf.mmm.util.query.api.path.EntityAlias;
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

  private static final Bean AGGREGATE_FUNCTION_BEAN = new Bean() {
    @Override
    public BeanAccess access() {

      throw new UnsupportedOperationException();
    }
  };

  /** The property for the size aggregate function on a {@link java.util.Collection}. */
  protected static final IntegerProperty SIZE_PROPERTY = new IntegerProperty("size", AGGREGATE_FUNCTION_BEAN);

  /** The property for the avg aggregate function to calculate the average value. */
  protected static final IntegerProperty AVG_PROPERTY = new IntegerProperty("avg", AGGREGATE_FUNCTION_BEAN);

  /** The property for the max aggregate function to calculate the maximum value. */
  protected static final IntegerProperty MAX_PROPERTY = new IntegerProperty("max", AGGREGATE_FUNCTION_BEAN);

  /** The property for the min aggregate function to calculate the minimum value. */
  protected static final IntegerProperty MIN_PROPERTY = new IntegerProperty("min", AGGREGATE_FUNCTION_BEAN);

  /** The property for the size aggregate function on a {@link java.util.Collection}. */
  protected static final IntegerProperty SUM_PROPERTY = new IntegerProperty("sum", AGGREGATE_FUNCTION_BEAN);

  /** The character that separates the segments of a {@link #getPath() path}. */
  private static char SEPARATOR = '.';

  private final PathRoot<?> root;

  private final PathImpl<?> parent;

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

    throw new IllegalArgumentException("Path " + getParentName(false) + " leads to type " + expectedType
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
   * @return the {@link ReadableProperty property}.
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
  public void format(Function<AttributeReadName, String> segmentFormatter, StringBuilder buffer) {

    int len = buffer.length();
    if (this.parent != null) {
      this.parent.format(segmentFormatter, buffer);
    } else {
      buffer.append(segmentFormatter.apply(this.root));
    }
    String propertyName = segmentFormatter.apply(this.property);
    if (isEmpty(propertyName)) {
      return;
    }
    if (buffer.length() > len) {
      buffer.append(SEPARATOR);
    }
    buffer.append(propertyName);
  }

  private static boolean isEmpty(String string) {

    return ((string == null) || (string.isEmpty()));
  }

  /**
   * @param omitRootAlias - {@code true} if the {@link #getRoot() root} shall be omitted in case it is an
   *        {@link EntityAlias}, {@code false} otherwise (the default for {@link #getName()}).
   * @return the {@link #getName() name} of {@link #getParent() parent} or {@link #getRoot() root}.
   */
  public String getParentName(boolean omitRootAlias) {

    if (this.parent == null) {
      if (omitRootAlias && (this.root instanceof EntityAlias)) {
        return "";
      }
      String rootName = this.root.getName();
      if (rootName == null) {
        return "";
      }
      return rootName;
    } else {
      return this.parent.getName();
    }
  }

  @Override
  public Path<V> getValuePath() {

    return this;
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
