/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.impl;

import java.lang.reflect.TypeVariable;
import java.util.Collection;
import java.util.Map;

import org.slf4j.LoggerFactory;

/**
 * This class contains common {@link TypeVariable}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
@SuppressWarnings("rawtypes")
public final class CommonTypeVariables {

  /**
   * The {@link TypeVariable} {@code E} of {@code Collection<E>}.
   */
  public static final TypeVariable<Class<Collection>> TYPE_VARIABLE_COLLECTION_ELEMENT;

  /** The {@link TypeVariable} {@code K} of {@code Map<K, V>}. */
  public static final TypeVariable<Class<Map>> TYPE_VARIABLE_MAP_KEY;

  /** The {@link TypeVariable} {@code V} of {@code Map<K, V>}. */
  public static final TypeVariable<Class<Map>> TYPE_VARIABLE_MAP_VALUE;

  static {
    TypeVariable<Class<Collection>> collectionElement = null;
    try {
      collectionElement = Collection.class.getTypeParameters()[0];
    } catch (RuntimeException e) {
      LoggerFactory.getLogger(CommonTypeVariables.class).error("Internal Error!", e);
    }
    TYPE_VARIABLE_COLLECTION_ELEMENT = collectionElement;

    TypeVariable<Class<Map>> mapKey = null;
    TypeVariable<Class<Map>> mapValue = null;
    try {
      TypeVariable<Class<Map>>[] mapParameters = Map.class.getTypeParameters();
      mapKey = mapParameters[0];
      mapValue = mapParameters[1];
    } catch (RuntimeException e) {
      LoggerFactory.getLogger(CommonTypeVariables.class).error("Internal Error!", e);
    }
    TYPE_VARIABLE_MAP_KEY = mapKey;
    TYPE_VARIABLE_MAP_VALUE = mapValue;
  }

  /**
   * The forbidden constructor.
   */
  private CommonTypeVariables() {

    super();
  }

}
