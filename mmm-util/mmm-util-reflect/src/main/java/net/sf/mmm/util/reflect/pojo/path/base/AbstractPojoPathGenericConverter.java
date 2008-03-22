/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.path.base;

import java.lang.reflect.Type;

import net.sf.mmm.util.reflect.ClassHierarchieMapper;
import net.sf.mmm.util.reflect.pojo.path.api.PojoPath;
import net.sf.mmm.util.reflect.pojo.path.api.PojoPathConverter;
import net.sf.mmm.util.reflect.pojo.path.api.PojoPathGenericConverter;

/**
 * This is the abstract base implementation of the interface
 * {@link PojoPathGenericConverter}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractPojoPathGenericConverter implements PojoPathGenericConverter {

  /**
   * The constructor.
   */
  public AbstractPojoPathGenericConverter() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public Object convert(Object pojo, Class<? extends Object> targetClass, Type targetType,
      PojoPath path) {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public Class<Object> getSourceType() {

    return Object.class;
  }

  /**
   * {@inheritDoc}
   */
  public Class<Object> getTargetType() {

    return Object.class;
  }

  /**
   * This inner class contains
   */
  protected static class SourceTypeConverterMap extends ClassHierarchieMapper<PojoPathConverter> {

    @Override
    protected Class<?> getClass(PojoPathConverter element) {

      return element.getSourceType();
    }

    @Override
    protected boolean isPreferable(PojoPathConverter element, Class<?> elementType,
        PojoPathConverter existing, Class<?> currentType) {

      if (existing.getTargetType().isAssignableFrom(element.getTargetType())) {

      }
      return super.isPreferable(element, elementType, existing, currentType);
    }

  }

}
