/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * This is the abstract base class for {@link ResourceBundle} implementations using this NLS support. <br/>
 * <b>ATTENTION:</b><br/>
 * The preferred approach to define messages for the root locale is via
 * {@link net.sf.mmm.util.nls.api.NlsBundle} instead of using this class.<br/>
 * <br/>
 * Create your {@link ResourceBundle}s by sub-classing this class and simply define some public static final
 * fields that will be automatically added to the bundle using reflection (only from constructor).<br/>
 * Please note that your sub-class must also be public or you need to set privileges in the security manager
 * to allow this class reading the fields via reflection.<br/>
 * Please also follow the convention using the following prefixes followed by a suffix that properly explains
 * what the text is about:
 * <table border="1">
 * <tr>
 * <th>Prefix</th>
 * <th>Comment</th>
 * <th>Example</th>
 * </tr>
 * <tr>
 * <td>ERR_</td>
 * <td>Text for an exception message.</td>
 * <td>
 * <code>ERR_VALUE_NOT_SET = "The value \"{value}\" is not set!"</code></td>
 * </tr>
 * <tr>
 * <td>MSG_</td>
 * <td>Text for a complete information message.</td>
 * <td>
 * <code>MSG_MAIN_OPTION_VERSION_USAGE = "Print the program-version."</code></td>
 * </tr>
 * <tr>
 * <td>INF_</td>
 * <td>Text for a single information term.</td>
 * <td>
 * <code>INF_MAIN_MODE_DEFAULT = "default"</code></td>
 * </tr>
 * <tr>
 * <td>INT_</td>
 * <td>Reserved for internal constants not to be localized.</td>
 * <td>
 * <code>INT_MAIN_OPTION_NAME_VERSION = "--version"</code></td>
 * </tr>
 * </table>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractResourceBundle extends ResourceBundle {

  /**
   * Fields that start with this prefix ({@value} ), will be ignored as {@link #getString(String) bundle
   * properties}.
   */
  private static final String PREFIX_INTERNAL_FIELD = "INT_";

  /**
   * The key value pairs; maps keys (String) to values (Object). No Map because Enumeration is required...
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
        if (Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers) && !Modifier.isPrivate(modifiers)) {
          if (fields[i].getType() == String.class) {
            String key = fields[i].getName();
            if (!key.startsWith(PREFIX_INTERNAL_FIELD)) {
              Object value = fields[i].get(null);
              this.bundle.put(key, value);
              this.reverse.put(value, key);
            }
          }
        }
      }
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("Failed to initialize " + getClass().getName(), e);
    } catch (RuntimeException e) {
      throw new IllegalStateException("Failed to initialize " + getClass().getName(), e);
    }
  }

  /**
   * This method is the inverse of {@link #getObject(String)}.
   * 
   * @param object is the object (potentially) retrieved via {@link #getObject(String)}.
   * @return the key for the given <code>object</code> or <code>null</code> if it was NOT retrieved via
   *         {@link #getObject(String)} from this instance.
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
  public boolean containsKey(String key) {

    return this.bundle.containsKey(key);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<String> keySet() {

    return this.bundle.keySet();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Object handleGetObject(String key) {

    return this.bundle.get(key);
  }

}
