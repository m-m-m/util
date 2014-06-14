/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import java.util.Set;

import net.sf.mmm.util.collection.api.SetFactory;
import net.sf.mmm.util.collection.base.ConcurrentHashSetFactory;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;

/**
 * This is an abstract base implementation of {@link net.sf.mmm.util.value.api.ValueConverter} that extends
 * {@link AbstractValueConverterToCompatiblePojo} and adds tolerance for unmatched properties. Instead of
 * throwing an exception the first usage of an unmatched setter is logged once as warning.
 *
 * @param <SOURCE> is the generic {@link #getSourceType() source-type}.
 * @param <TARGET> is the generic {@link #getTargetType() target-type}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public abstract class AbstractValueConverterToSimilarPojo<SOURCE, TARGET> extends
    AbstractValueConverterToCompatiblePojo<SOURCE, TARGET> {

  /** @see #handleNoGetterForSetter(PojoPropertyAccessorOneArg, Object, Class) */
  private final Set<PojoPropertyAccessorOneArg> unmatchedSetters;

  /**
   * The constructor.
   */
  public AbstractValueConverterToSimilarPojo() {

    this(ConcurrentHashSetFactory.INSTANCE);
  }

  /**
   * The constructor.
   *
   * @param setFactory is the {@link SetFactory} used to create {@link Set} instances.
   */
  protected AbstractValueConverterToSimilarPojo(SetFactory setFactory) {

    super();
    this.unmatchedSetters = setFactory.create();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void handleNoGetterForSetter(PojoPropertyAccessorOneArg setter, Class<?> targetClass, Object sourceObject,
      Class<?> sourceClass) {

    boolean added = this.unmatchedSetters.add(setter);
    if (added) {
      getLogger().warn("Could not set propert {} of {} because source object {} has no such property.",
          setter.getName(), targetClass, sourceClass);
    }
  }
}
