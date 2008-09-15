/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.impl;

import java.lang.reflect.TypeVariable;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.logging.LogFactory;

/**
 * This class contains common {@link TypeVariable}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("unchecked")
public final class CommonTypeVariables {

  /**
   * The forbidden constructor.
   */
  private CommonTypeVariables() {

    super();
  }

  /**
   * The {@link TypeVariable} <code>E</code> of <code>Collection&lt;E&gt;</code>
   * .
   */
  public static final TypeVariable<Class<Collection>> TYPE_VARIABLE_COLLECTION_ELEMENT;

  /** The {@link TypeVariable} <code>K</code> of <code>Map&lt;K, V&gt;</code>. */
  public static final TypeVariable<Class<Map>> TYPE_VARIABLE_MAP_KEY;

  /** The {@link TypeVariable} <code>V</code> of <code>Map&lt;K, V&gt;</code>. */
  public static final TypeVariable<Class<Map>> TYPE_VARIABLE_MAP_VALUE;

  static {
    TypeVariable<Class<Collection>> collectionElement = null;
    try {
      collectionElement = Collection.class.getTypeParameters()[0];
    } catch (RuntimeException e) {
      LogFactory.getLog(CommonTypeVariables.class).error(e);
    }
    TYPE_VARIABLE_COLLECTION_ELEMENT = collectionElement;

    TypeVariable<Class<Map>> mapKey = null;
    TypeVariable<Class<Map>> mapValue = null;
    try {
      TypeVariable<Class<Map>>[] mapParameters = Map.class.getTypeParameters();
      mapKey = mapParameters[0];
      mapValue = mapParameters[1];
    } catch (RuntimeException e) {
      LogFactory.getLog(CommonTypeVariables.class).error(e);
    }
    TYPE_VARIABLE_MAP_KEY = mapKey;
    TYPE_VARIABLE_MAP_VALUE = mapValue;
  }

}
