/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.base;

import java.util.ArrayList;

import net.sf.mmm.util.reflect.api.CollectionReflectionUtil;

/**
 * This is an implementation of {@link CliValueContainer} for an array. It uses
 * a {@link ArrayList} that is dynamically converted to an array if
 * {@link #getValue()} gets called.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class CliValueContainerArray extends CliValueContainerCollection {

  /** @see #getValue() */
  private final CollectionReflectionUtil collectionReflectionUtil;

  /** @see #getValue() */
  private final Class<?> componentType;

  /**
   * The constructor.
   * 
   * @param collectionReflectionUtil is the {@link CollectionReflectionUtil}
   *        instance.
   * @param componentType is the {@link Class#getComponentType() component-type}
   *        of the array.
   */
  public CliValueContainerArray(CollectionReflectionUtil collectionReflectionUtil,
      Class<?> componentType) {

    super(new ArrayList<Object>());
    this.collectionReflectionUtil = collectionReflectionUtil;
    this.componentType = componentType;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getValue() {

    return this.collectionReflectionUtil.toArray(getCollection(), this.componentType);
  }
}
