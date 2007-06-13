/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.nls.base;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * This is the abstract base class for {@link ResourceBundle} implementations
 * using this NLS support. Create your {@link ResourceBundle}s by sub-classing
 * this class and simply define some public static final fields that will be
 * automatically added to the bundle using reflection (only from constructor).
 * Please note that your sub-class must also be public or you need to set
 * privileges in the security manager to allow this class reading the fields via
 * reflection.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractResourceBundle extends ResourceBundle {

  /**
   * The key value pairs; maps keys (String) to values (Object). No Map because
   * Enumeration is required...
   */
  private Hashtable<String, Object> bundle;

  /** the inverse map of {@link #bundle} */
  private Map<Object, String> reverse;

  /**
   * The constructor.
   */
  public AbstractResourceBundle() {

    super();
    try {
      Field[] fields = getClass().getFields();
      this.bundle = new Hashtable<String, Object>(fields.length);
      this.reverse = new HashMap<Object, String>(fields.length);
      for (int i = 0; i < fields.length; i++) {
        int modifiers = fields[i].getModifiers();
        if (Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers)
            && !Modifier.isPrivate(modifiers)) {
          if (fields[i].getType() == String.class) {
            String key = fields[i].getName();
            Object value = fields[i].get(null);
            this.bundle.put(key, value);
            this.reverse.put(value, key);
          }
        }
      }
    } catch (Exception e) {
      throw new IllegalStateException("Failed to initialize " + getClass().getName(), e);
    }
  }

  /**
   * This method is the inverse of {@link #getObject(String)}.
   * 
   * @param object is the object (potentially) retrieved via
   *        {@link #getObject(String)}.
   * @return the key for the given <code>object</code> or <code>null</code>
   *         if it was NOT retrieved via {@link #getObject(String)} from this
   *         instance.
   */
  public String getKey(Object object) {

    return this.reverse.get(object);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Enumeration<String> getKeys() {

    return this.bundle.keys();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Object handleGetObject(String key) {

    return this.bundle.get(key);
  }

}
