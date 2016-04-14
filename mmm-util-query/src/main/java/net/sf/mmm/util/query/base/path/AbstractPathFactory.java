/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.path;

import net.sf.mmm.util.bean.api.EntityBean;
import net.sf.mmm.util.property.api.ReadableProperty;
import net.sf.mmm.util.property.api.ReadableSetProperty;
import net.sf.mmm.util.property.api.lang.ReadableBooleanProperty;
import net.sf.mmm.util.property.api.lang.ReadableNumberProperty;
import net.sf.mmm.util.property.api.lang.ReadableStringProperty;
import net.sf.mmm.util.property.api.link.LinkProperty;
import net.sf.mmm.util.property.api.util.ReadableListProperty;
import net.sf.mmm.util.property.api.util.ReadableMapProperty;
import net.sf.mmm.util.query.api.path.BooleanPath;
import net.sf.mmm.util.query.api.path.ListPath;
import net.sf.mmm.util.query.api.path.MapPath;
import net.sf.mmm.util.query.api.path.NumberPath;
import net.sf.mmm.util.query.api.path.Path;
import net.sf.mmm.util.query.api.path.PathFactory;
import net.sf.mmm.util.query.api.path.PathRoot;
import net.sf.mmm.util.query.api.path.SetPath;
import net.sf.mmm.util.query.api.path.StringPath;

/**
 * This is the abstract base implementation of {@link PathFactory}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract class AbstractPathFactory implements PathFactory {

  /**
   * The constructor.
   */
  public AbstractPathFactory() {
    super();
  }

  @Override
  public <V> Path<V> to(ReadableProperty<V> property) {

    if (this instanceof PathImpl) {
      return new PathImpl<>((PathImpl<?>) this, property);
    } else {
      return new PathImpl<>((PathRoot<?>) this, property);
    }
  }

  @Override
  public BooleanPath to(ReadableBooleanProperty property) {

    if (this instanceof PathImpl) {
      return new BooleanPathImpl((PathImpl<?>) this, property);
    } else {
      return new BooleanPathImpl((PathRoot<?>) this, property);
    }
  }

  @Override
  public StringPath to(ReadableStringProperty property) {

    if (this instanceof PathImpl) {
      return new StringPathImpl((PathImpl<?>) this, property);
    } else {
      return new StringPathImpl((PathRoot<?>) this, property);
    }
  }

  @Override
  public <V extends Number & Comparable<?>> NumberPath<V> to(ReadableNumberProperty<V> property) {

    if (this instanceof PathImpl) {
      return new NumberPathImpl<>((PathImpl<?>) this, property);
    } else {
      return new NumberPathImpl<>((PathRoot<?>) this, property);
    }
  }

  @Override
  public <V extends EntityBean<ID>, ID> Path<V> to(LinkProperty<ID, V> property) {

    if (this instanceof PathImpl) {
      return new EntityBeanPathImpl<>((PathImpl<?>) this, property);
    } else {
      return new EntityBeanPathImpl<>((PathRoot<?>) this, property);
    }
  }

  @Override
  public <E> ListPath<E> to(ReadableListProperty<E> property) {

    if (this instanceof PathImpl) {
      return new ListPathImpl<>((PathImpl<?>) this, property);
    } else {
      return new ListPathImpl<>((PathRoot<?>) this, property);
    }
  }

  @Override
  public <E> SetPath<E> to(ReadableSetProperty<E> property) {

    if (this instanceof PathImpl) {
      return new SetPathImpl<>((PathImpl<?>) this, property);
    } else {
      return new SetPathImpl<>((PathRoot<?>) this, property);
    }
  }

  @Override
  public <K, V> MapPath<K, V> to(ReadableMapProperty<K, V> property) {

    if (this instanceof PathImpl) {
      return new MapPathImpl<>((PathImpl<?>) this, property);
    } else {
      return new MapPathImpl<>((PathRoot<?>) this, property);
    }
  }

}
