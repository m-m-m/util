/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.base;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.sf.mmm.content.api.ContentException;
import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.model.api.ContentAccessor;
import net.sf.mmm.content.model.api.ContentModelException;
import net.sf.mmm.content.security.api.PermissionDeniedException;
import net.sf.mmm.util.reflect.ReflectionUtil;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class MethodContentAccessor implements ContentAccessor {

  /** @see #get(ContentObject) */
  private final Method getter;

  /** @see #set(ContentObject, Object) */
  private final Method setter;

  /**
   * The constructor.
   * 
   * @param getter
   */
  public MethodContentAccessor(Method getter) {

    this(getter, null);
  }

  /**
   * The constructor.
   * 
   * @param getter
   * @param setter
   */
  public MethodContentAccessor(Method getter, Method setter) {

    super();
    this.getter = getter;
    this.setter = setter;
  }

  /**
   * {@inheritDoc}
   */
  public Object get(ContentObject object) throws ContentModelException {

    try {
      return this.getter.invoke(object, ReflectionUtil.NO_ARGUMENTS);
    } catch (InvocationTargetException e) {
      // TODO: message and NLS
      throw new ContentModelException(e.getCause(), "Error");
    } catch (Exception e) {
      // TODO: message and NLS
      throw new ContentModelException(e, "Error");
    }
  }

  /**
   * {@inheritDoc}
   */
  public void set(ContentObject object, Object value) throws ContentException {

    if (this.setter == null) {
      // TODO:
      throw new PermissionDeniedException("", "write", "" + object);
    }
    try {
      this.setter.invoke(object, new Object[] { value });
    } catch (InvocationTargetException e) {
      // TODO: message and NLS
      throw new ContentModelException(e.getCause(), "Error");
    } catch (Exception e) {
      // TODO: message and NLS
      throw new ContentModelException(e, "Error");
    }
    
  }

}
