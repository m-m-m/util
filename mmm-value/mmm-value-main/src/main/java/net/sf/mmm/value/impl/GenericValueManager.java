/* $Id$ */
package net.sf.mmm.value.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import net.sf.mmm.value.NlsResourceBundle;
import net.sf.mmm.value.api.ValueException;
import net.sf.mmm.value.api.ValueParseException;
import net.sf.mmm.value.api.ValueParseStringException;
import net.sf.mmm.value.base.BasicValueManager;

/**
 * This is a generic implementation of the
 * {@link net.sf.mmm.value.api.ValueManager}interface.
 * 
 * @param <V>
 *        is the managed value type.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class GenericValueManager<V> extends BasicValueManager<V> {

  /**
   * the constructor to create value from string or <code>null</code> if no
   * such constructor available
   */
  private final Constructor<V> stringConstructor;

  /**
   * The constructor.
   * 
   * @param valueClass
   *        is the class implementing the managed value type.
   */
  public GenericValueManager(Class<V> valueClass) {

    this(valueClass, valueClass.getSimpleName());
  }

  /**
   * The constructor.
   * 
   * @param valueClass
   *        is the {@link #getValueType() implementation} of the managed value
   *        type.
   * @param typeName
   *        is the {@link #getName() "logical name"} of the managed value
   *        type.
   */
  public GenericValueManager(Class<V> valueClass, String typeName) {

    super(valueClass, typeName);
    if (Modifier.isAbstract(this.valueType.getModifiers())
        || Modifier.isInterface(this.valueType.getModifiers())) {
      throw new ValueException(NlsResourceBundle.ERR_GENERIC_CLASS_ABSTRACT, valueClass);
    }
    try {
      this.stringConstructor = valueClass.getConstructor(new Class[] {String.class});
      if (!Modifier.isPublic(this.stringConstructor.getModifiers())) {
        throw new ValueException(NlsResourceBundle.ERR_GENERIC_CLASS_NOT_ACCESSIBLE, valueClass);
      }
    } catch (SecurityException e) {
      throw new ValueException(e, NlsResourceBundle.ERR_GENERIC_CLASS_SECURITY, valueClass);
    } catch (NoSuchMethodException e) {
      // TODO: maybe search for static parse(String) method...
      throw new ValueException(e, NlsResourceBundle.ERR_GENERIC_CLASS_STRING, valueClass);
    }
  }

  /**
   * @see net.sf.mmm.value.api.ValueManager#parse(java.lang.String)
   */
  public V parse(String valueAsString) throws ValueParseException {

    try {
      return this.stringConstructor.newInstance(new Object[] {valueAsString});
    } catch (IllegalArgumentException e) {
      throw new ValueParseStringException(valueAsString, this.valueType, this.name, e);
    } catch (InstantiationException e) {
      throw new ValueParseStringException(valueAsString, this.valueType, this.name, e);
    } catch (IllegalAccessException e) {
      throw new ValueParseStringException(valueAsString, this.valueType, this.name, e);
    } catch (InvocationTargetException e) {
      throw new ValueParseStringException(valueAsString, this.valueType, this.name, e);
    }
  }

}
