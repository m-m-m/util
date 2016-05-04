/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.path.base;

import net.sf.mmm.util.collection.base.AbstractSimpleMap;
import net.sf.mmm.util.pojo.path.api.PojoPathContext;
import net.sf.mmm.util.pojo.path.api.PojoPathMode;
import net.sf.mmm.util.pojo.path.api.PojoPathNavigator;

/**
 * This is represents a given {@link net.sf.mmm.util.pojo.api.Pojo} as {@link java.util.Map} where the key is
 * a {@link net.sf.mmm.util.pojo.path.api.PojoPath}-String.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.1
 */
public class PojoPathMap extends AbstractSimpleMap<String, Object> {

  private  final PojoPathNavigator pojoPathNavigator;

  private  final Object pojo;

  private  final PojoPathMode mode;

  private  final PojoPathContext context;

  /**
   * The constructor.
   * 
   * @param pojoPathNavigator the {@link PojoPathNavigator} to use.
   * @param pojo the {@link net.sf.mmm.util.pojo.api.Pojo} to represent as {@link java.util.Map}.
   */
  public PojoPathMap(PojoPathNavigator pojoPathNavigator, Object pojo) {

    this(pojoPathNavigator, pojo, new PojoPathContextBean());
  }

  /**
   * The constructor.
   * 
   * @param pojoPathNavigator the {@link PojoPathNavigator} to use.
   * @param pojo the {@link net.sf.mmm.util.pojo.api.Pojo} to represent as {@link java.util.Map}.
   * @param context is the {@link PojoPathContext}.
   */
  public PojoPathMap(PojoPathNavigator pojoPathNavigator, Object pojo, PojoPathContext context) {

    this(pojoPathNavigator, pojo, context, PojoPathMode.RETURN_IF_NULL);
  }

  /**
   * The constructor.
   * 
   * @param pojoPathNavigator the {@link PojoPathNavigator} to use.
   * @param pojo the {@link net.sf.mmm.util.pojo.api.Pojo} to represent as {@link java.util.Map}.
   * @param context is the {@link PojoPathContext}.
   * @param mode is the {@link PojoPathMode}.
   */
  public PojoPathMap(PojoPathNavigator pojoPathNavigator, Object pojo, PojoPathContext context, PojoPathMode mode) {

    super();
    this.pojoPathNavigator = pojoPathNavigator;
    this.pojo = pojo;
    this.context = context;
    this.mode = mode;
  }

  @Override
  public Object get(Object key) {

    String pojoPath = (String) key;
    return this.pojoPathNavigator.get(this.pojo, pojoPath, this.mode, this.context);
  }

}
