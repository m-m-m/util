/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.impl;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import net.sf.mmm.content.model.api.ClassModifiers;
import net.sf.mmm.content.model.api.FieldModifiers;
import net.sf.mmm.content.model.api.Modifiers;
import net.sf.mmm.content.value.api.ContentId;
import net.sf.mmm.content.value.api.MutableMetaData;
import net.sf.mmm.util.collection.SizedIterable;
import net.sf.mmm.util.reflect.ClassResolver;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class DummyClassResolver implements ClassResolver {

  private final Map<String, Class> classesMap;

  /**
   * The constructor.
   */
  public DummyClassResolver() {

    super();
    this.classesMap = new HashMap<String, Class>();
  }

  protected void register(String name, Class clazz) {
    
    this.classesMap.put(name, clazz);
  }
  
  protected void register(Class clazz) {
    
    register(clazz.getSimpleName(), clazz);
  }
  
  /**
   * This method initializes this class. It has to be called after construction
   * and injection is completed.
   */
  @PostConstruct
  public void initialize() {

    register(String.class);
    register(Boolean.class);
    register(Integer.class);
    register(Long.class);
    register(Double.class);
    register(Date.class);
    // unnecessary lang values
    register(Float.class);
    register(Short.class);
    register(Byte.class);
    // collections
    register(List.class);
    register(Set.class);
    register(Collection.class);
    register(Map.class);
    register(SizedIterable.class);
    // mmm values
    register(Modifiers.class);
    register(FieldModifiers.class);
    register(ClassModifiers.class);
    register(ContentId.VALUE_NAME, ContentId.class);
    register(MutableMetaData.VALUE_NAME, MutableMetaData.class);
 }

  /**
   * {@inheritDoc}
   */
  public Class resolveClass(String name) throws ClassNotFoundException {

    Class result = this.classesMap.get(name);
    if (result == null) {
      result = Class.forName(name);
    }
    return result;
  }

}
