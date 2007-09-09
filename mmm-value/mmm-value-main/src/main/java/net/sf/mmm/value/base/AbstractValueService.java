/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.base;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import net.sf.mmm.value.api.ValueException;
import net.sf.mmm.value.api.ValueManager;
import net.sf.mmm.value.api.ValueParseException;
import net.sf.mmm.value.api.ValueService;

/**
 * This is the abstract base implementation of the {@link ValueService}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractValueService implements ValueService, ValueManager<Object> {

  /**
   * maps a {@link ValueManager#getValueClass() value type} to the according
   * manager.
   * 
   * @see #getManager(Class)
   */
  private final Map<Class, ValueManager> class2manager;

  /**
   * maps a {@link ValueManager#getName() name} to the according manager.
   * 
   * @see #getManager(String)
   */
  private final Map<String, ValueManager> name2manager;

  /** @see #getManagers() */
  private final Collection<ValueManager> managers;

  /**
   * The constructor.
   */
  public AbstractValueService() {

    super();
    this.name2manager = new HashMap<String, ValueManager>();
    this.class2manager = new HashMap<Class, ValueManager>();
    this.managers = Collections.unmodifiableCollection(this.class2manager.values());
  }

  /**
   * This method registers a value manager to this service.
   * 
   * @param manager is the value manager to register.
   * @throws ValueException if the a manager is already registered for the same
   *         (case INtensitive) value type.
   */
  public void addValueManager(ValueManager<?> manager) throws ValueException {

    String name = manager.getName();
    if (this.name2manager.containsKey(name)) {
      throw new ValueAlreadyRegisteredException(name);
    }
    Class<?> valueClass = manager.getValueClass();
    if (this.class2manager.containsKey(valueClass)) {
      throw new ValueAlreadyRegisteredException(valueClass);
    }
    if (manager instanceof ValueServiceAware) {
      ((ValueServiceAware) manager).setValueService(this);
    }
    this.name2manager.put(name, manager);
    this.class2manager.put(valueClass, manager);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public <V> ValueManager<V> getManager(Class<V> valueType) {

    return this.class2manager.get(valueType);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public <V> ValueManager<? super V> findManager(Class<V> valueSubType) {

    ValueManager<? super V> manager = getManager(valueSubType);
    if (manager == null) {
      for (ValueManager<?> currentManager : this.class2manager.values()) {
        if (currentManager.getValueClass().isAssignableFrom(valueSubType)) {
          manager = (ValueManager<? super V>) currentManager;
          // TODO: should we cache this mapping to speed up lookups?
          break;
        }
      }
    }
    return manager;
  }

  /**
   * {@inheritDoc}
   */
  public ValueManager<?> getManager(String name) {

    return this.name2manager.get(name);
  }

  /**
   * {@inheritDoc}
   */
  public Collection<ValueManager> getManagers() {

    return this.managers;
  }

  /**
   * {@inheritDoc}
   */
  public ValueManager<Object> getGenericManager() {

    return this;
  }

  /**
   * {@inheritDoc}
   */
  public void toXml(XMLStreamWriter xmlWriter, Object value) throws XMLStreamException {

    if (value == null) {
      xmlWriter.writeStartElement(XML_TAG_VALUE);
      xmlWriter.writeAttribute(XML_ATR_VALUE_NAME, NULL_VALUE_NAME);
      xmlWriter.writeEndElement();
    } else {
      ValueManager manager = findManager(value.getClass());
      manager.toXml(xmlWriter, value);
    }
  }

  /**
   * {@inheritDoc}
   */
  public Object fromXml(XMLStreamReader xmlReader) throws XMLStreamException {

    assert (xmlReader.isStartElement());
    assert (ValueManager.XML_TAG_VALUE.equals(xmlReader.getLocalName()));
    String valueName = xmlReader.getAttributeValue(null, ValueManager.XML_ATR_VALUE_NAME);
    if (valueName == null) {
      // TODO:
      throw new IllegalArgumentException();
    }
    if (ValueManager.NULL_VALUE_NAME.equals(valueName)) {
      // this is the null value...
      int eventCode = xmlReader.nextTag();
      if (XMLStreamConstants.END_ELEMENT != eventCode) {
        // TODO:
        throw new IllegalArgumentException();
      }
      return null;
    }
    ValueManager<?> manager = getManager(valueName);
    if (manager == null) {
      throw new ValueNotRegisteredException(valueName);
    }
    return manager.fromXml(xmlReader);
  }

  /**
   * {@inheritDoc}
   */
  public String toString(Object value) {
  
    if (value == null) {
      return ValueManager.NULL_STRING;
    }
    ValueManager manager = findManager(value.getClass());
    if (manager == null) {
      throw new ValueNotRegisteredException(value.getClass());
    }
    return manager.toString(value);
  }
  
  /**
   * {@inheritDoc}
   */
  public Object fromString(String valueAsString) throws ValueParseException {
  
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public String getName() {
  
    return "Generic";
  }
  
  /**
   * {@inheritDoc}
   */
  public Class<Object> getValueClass() {
  
    return Object.class;
  }

  /**
   * {@inheritDoc}
   */
  public Type getValueType() {
  
    return getValueClass();
  }
  
  /**
   * {@inheritDoc}
   */
  public boolean isEqual(Object value1, Object value2) {

    if (value1 == null) {
      return (value2 == null);
    } else {
      ValueManager manager = findManager(value1.getClass());
      if (manager == null) {
        //return false;
        return value1.equals(value2);
      } else {
        return manager.isEqual(value1, value2);
      }
    }
  }
  
}
