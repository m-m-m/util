/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.path;

import net.sf.mmm.util.property.api.ReadableProperty;
import net.sf.mmm.util.property.api.lang.ReadableBooleanProperty;
import net.sf.mmm.util.property.api.lang.ReadableNumberProperty;
import net.sf.mmm.util.property.api.lang.ReadableStringProperty;
import net.sf.mmm.util.query.api.path.BooleanPath;
import net.sf.mmm.util.query.api.path.NumberPath;
import net.sf.mmm.util.query.api.path.Path;
import net.sf.mmm.util.query.api.path.PathFactory;
import net.sf.mmm.util.query.api.path.PathRoot;
import net.sf.mmm.util.query.api.path.StringPath;

/**
 * TODO: this class ...
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
  public <T> Path<T> to(ReadableProperty<T> property) {

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
  public <N extends Number & Comparable<?>> NumberPath<N> to(ReadableNumberProperty<N> property) {

    if (this instanceof PathImpl) {
      return new NumberPathImpl<>((PathImpl<?>) this, property);
    } else {
      return new NumberPathImpl<>((PathRoot<?>) this, property);
    }
  }

}
