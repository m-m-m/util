/* $Id$ */
package net.sf.mmm.util.pojo.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.util.pojo.api.PojoDescriptor;
import net.sf.mmm.util.pojo.api.PojoPropertyAccessor;
import net.sf.mmm.util.pojo.api.PojoPropertyDescriptor;
import net.sf.mmm.util.pojo.api.PojoPropertyNotFoundException;
import net.sf.mmm.util.reflect.ReflectionUtil;

/**
 * A {@link PojoDescriptor} is used to reflect the
 * {@link #getPropertyDescriptors() property descriptors} of a
 * {@link #getPojoType() pojo}.
 * 
 * @param
 * <P>
 * is the templated type of the {@link #getPojoType() POJO}.
 * 
 * @see PojoPropertyDescriptorImpl
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoDescriptorImpl<P> implements PojoDescriptor<P> {

  /** method name prefix for classic getter */
  private static final String METHOD_PREFIX_GET = "get";

  /** method name prefix for classic setter */
  private static final String METHOD_PREFIX_SET = "set";

  /** alternative method name prefix for boolean getter */
  private static final String METHOD_PREFIX_IS = "is";

  /** alternative method name prefix for boolean getter */
  private static final String METHOD_PREFIX_HAS = "has";

  /** alternative method name prefix for boolean add-method */
  private static final String METHOD_PREFIX_ADD = "add";

  /** @see #getPojoType() */
  private final Class<P> pojoType;

  /** @see #getPropertyDescriptor(String) */
  private final Map<String, PojoPropertyDescriptorImpl> propertyMap;

  /** @see #getPropertyDescriptor(String) */
  private final Collection<PojoPropertyDescriptorImpl> propertyList;

  /**
   * The constructor.
   * 
   * @param pojoClass
   *        is the {@link #getPojoType() type} reflecting the POJO of this
   *        descriptor.
   */
  public PojoDescriptorImpl(Class<P> pojoClass) {

    super();
    this.pojoType = pojoClass;
    this.propertyMap = new HashMap<String, PojoPropertyDescriptorImpl>();
    this.propertyList = Collections.unmodifiableCollection(this.propertyMap.values());
    Method[] methods = this.pojoType.getMethods();
    for (Method method : methods) {
      String methodName = method.getName();
      Class<?>[] argumentTypes = method.getParameterTypes();
      PojoPropertyDescriptorImpl descriptor;
      PojoPropertyAccessorImpl accessor;

      if (argumentTypes.length == 1) {
        // is property write method (setter)?
        Type[] genericArgTypes = method.getGenericParameterTypes();
        if (methodName.startsWith(METHOD_PREFIX_SET)) {
          // found compliant setter
          String propertyName = getPropertyName(methodName, METHOD_PREFIX_SET.length());
          if (propertyName != null) {
            accessor = new PojoPropertyAccessorImpl(propertyName, method, genericArgTypes[0],
                argumentTypes[0]);
            descriptor = getOrCreateProperty(propertyName);
            descriptor.write = accessor;
          }
        } else if (methodName.startsWith(METHOD_PREFIX_ADD)) {
          // found compliant setter
          String propertyName = getPropertyName(methodName, METHOD_PREFIX_SET.length());
          if (propertyName != null) {
            accessor = new PojoPropertyAccessorImpl(propertyName, method, genericArgTypes[0],
                argumentTypes[0]);
            descriptor = getOrCreateProperty(propertyName);
            descriptor.add = accessor;
          }
        }
      } else if (argumentTypes.length == 0) {
        Class<?> propertyClass = method.getReturnType();
        String propertyName = null;
        if (propertyClass != Void.class) {
          // is property read method?
          if (methodName.startsWith(METHOD_PREFIX_GET)) {
            propertyName = getPropertyName(methodName, METHOD_PREFIX_GET.length());
          } else if ((propertyClass == boolean.class) || (propertyClass == Boolean.class)) {
            // boolean getters may be is* or has* ...
            if (methodName.startsWith(METHOD_PREFIX_IS)) {
              propertyName = getPropertyName(methodName, METHOD_PREFIX_IS.length());
            } else if (methodName.startsWith(METHOD_PREFIX_HAS)) {
              propertyName = getPropertyName(methodName, METHOD_PREFIX_HAS.length());
            }
          }
          if (propertyName != null) {
            accessor = new PojoPropertyAccessorImpl(propertyName, method,
                method.getGenericReturnType(), propertyClass);
            descriptor = getOrCreateProperty(accessor.getName());
            descriptor.read = accessor;
          }
        }
      }
    }
  }

  /**
   * This method gets the property-descriptor for the given
   * <code>propertyName</code>.
   * 
   * @param propertyName
   *        is the name of the requested property-descriptor.
   * @return the requested property-descriptor or <code>null</code> if NO
   *         property exists with the given <code>propertyName</code>.
   */
  private PojoPropertyDescriptorImpl getOrCreateProperty(String propertyName) {

    PojoPropertyDescriptorImpl descriptor = this.propertyMap.get(propertyName);
    if (descriptor == null) {
      descriptor = new PojoPropertyDescriptorImpl(propertyName);
      this.propertyMap.put(propertyName, descriptor);
    }
    return descriptor;
  }

  /**
   * This method gets the according
   * {@link PojoPropertyDescriptor#getName() property-name} for the given
   * <code>methodName</code>.<br>
   * This is the un-capitalized substring of the <code>methodName</code> after
   * the prefix (given via <code>prefixLength</code>).
   * 
   * @param methodName
   *        is the {@link Method#getName() name} of the
   *        {@link PojoPropertyAccessor#getMethod() accessor-method}.
   * @param prefixLength
   *        is the length of the method prefix (e.g. 3 for "get"/"set" or 2 for
   *        "is").
   * @return the requested property-name or <code>null</code> if NOT available
   *         <br> (<code>methodName</code>.{@link String#length() length()}
   *         &lt;= <code>prefixLength</code>).
   */
  private static String getPropertyName(String methodName, int prefixLength) {

    String methodSuffix = methodName.substring(prefixLength);
    if (methodSuffix.length() > 0) {
      StringBuffer sb = new StringBuffer(methodSuffix);
      sb.setCharAt(0, Character.toLowerCase(methodSuffix.charAt(0)));
      return sb.toString();
    }
    return null;
  }

  /**
   * @see net.sf.mmm.util.pojo.api.PojoDescriptor#getPojoType()
   */
  public Class<P> getPojoType() {

    return this.pojoType;
  }

  /**
   * This method gets the {@link PojoPropertyAccessorImpl accessor} used to read the
   * property identified by the given <code>propertyName</code>.
   * 
   * @param propertyName
   *        is the name of the requested property.
   * @return the accessor used to read the property identified by the given
   *         <code>propertyName</code> or <code>null</code> if no public
   *         read-{@link PojoPropertyAccessorImpl#getMethod() method} exists for
   *         the property in the according {@link #getPojoType() POJO}.
   */
  public PojoPropertyAccessor getReadAccess(String propertyName) {

    PojoPropertyAccessor accessor = null;
    PojoPropertyDescriptor descriptor = this.propertyMap.get(propertyName);
    if (descriptor != null) {
      accessor = descriptor.getReadAccess();
    }
    return accessor;
  }

  /**
   * This method gets the {@link PojoPropertyAccessorImpl accessor} used to write
   * the property identified by the given <code>propertyName</code>.
   * 
   * @param propertyName
   *        is the name of the requested property.
   * @return the accessor used to write the property identified by the given
   *         <code>propertyName</code> or <code>null</code> if no public
   *         write-{@link PojoPropertyAccessorImpl#getMethod() method} exists for
   *         the property in the according {@link #getPojoType() POJO}.
   */
  public PojoPropertyAccessor getWriteAccess(String propertyName) {

    PojoPropertyAccessor accessor = null;
    PojoPropertyDescriptor descriptor = this.propertyMap.get(propertyName);
    if (descriptor != null) {
      accessor = descriptor.getWriteAccess();
    }
    return accessor;
  }

  /**
   * @see net.sf.mmm.util.pojo.api.PojoDescriptor#getPropertyDescriptor(java.lang.String)
   */
  public PojoPropertyDescriptor getPropertyDescriptor(String propertyName) {

    return this.propertyMap.get(propertyName);
  }

  /**
   * This method gets the {@link PojoPropertyDescriptorImpl descriptor}s of all
   * properties of the according {@link #getPojoType() pojo}.
   * 
   * @return a collection with all
   *         {@link PojoPropertyDescriptorImpl property descriptor}s
   */
  public Collection<? extends PojoPropertyDescriptor> getPropertyDescriptors() {

    return this.propertyList;
  }

  /**
   * This inner class is a simple container for different property accessors.
   */
  private static class PojoPropertyDescriptorImpl implements PojoPropertyDescriptor {

    /** @see #getName() */
    private final String name;

    /** @see #getReadAccess() */
    private PojoPropertyAccessorImpl read;

    /** @see #getWriteAccess() */
    private PojoPropertyAccessorImpl write;

    /** @see #getAddAccess() */
    private PojoPropertyAccessorImpl add;

    /**
     * The constructor.
     * 
     * @param propertyName
     *        is the {@link #getName() name} of the property.
     */
    public PojoPropertyDescriptorImpl(String propertyName) {

      super();
      this.name = propertyName;
      this.read = null;
      this.write = null;
      this.add = null;
    }

    /**
     * This method gets the programmatic (technical) name of this property.
     * 
     * @see java.beans.PropertyDescriptor#getName()
     * 
     * @return the property name.
     */
    public String getName() {

      return this.name;
    }

    /**
     * @see net.sf.mmm.util.pojo.api.PojoPropertyDescriptor#getAddAccess()
     */
    public PojoPropertyAccessor getAddAccess() {

      return this.add;
    }

    /**
     * @see net.sf.mmm.util.pojo.api.PojoPropertyDescriptor#getReadAccess()
     */
    public PojoPropertyAccessor getReadAccess() {

      return this.read;
    }

    /**
     * @see net.sf.mmm.util.pojo.api.PojoPropertyDescriptor#getWriteAccess()
     */
    public PojoPropertyAccessor getWriteAccess() {

      return this.write;
    }

  }

  /**
   * @see net.sf.mmm.util.pojo.api.PojoDescriptor#getProperty(java.lang.Object,
   *      java.lang.String)
   */
  public Object getProperty(P pojoInstance, String propertyName)
      throws PojoPropertyNotFoundException, IllegalAccessException, InvocationTargetException {

    PojoPropertyAccessor readAccess = getReadAccess(propertyName);
    if (readAccess == null) {
      throw new PojoPropertyNotFoundException(getPojoType(), propertyName);
    }
    return readAccess.getMethod().invoke(pojoInstance, ReflectionUtil.NO_ARGUMENTS);
  }

  /**
   * @see net.sf.mmm.util.pojo.api.PojoDescriptor#setProperty(java.lang.Object,
   *      java.lang.String, java.lang.Object)
   */
  public void setProperty(P pojoInstance, String propertyName, Object value)
      throws PojoPropertyNotFoundException, IllegalAccessException, InvocationTargetException {

    PojoPropertyAccessor writeAccess = getWriteAccess(propertyName);
    if (writeAccess == null) {
      throw new PojoPropertyNotFoundException(getPojoType(), propertyName);
    }
    writeAccess.getMethod().invoke(pojoInstance, value);
  }

}
