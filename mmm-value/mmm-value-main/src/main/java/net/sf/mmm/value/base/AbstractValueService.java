/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.base;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

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
public abstract class AbstractValueService implements ValueService {

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
   * @param manager
   *        is the value manager to register.
   * @throws ValueException
   *         if the a manager is already registered for the same (case
   *         INtensitive) value type.
   */
  public void addManager(ValueManager<?> manager) throws ValueException {

    String name = manager.getName();
    if (this.name2manager.containsKey(name)) {
      throw new ValueAlreadyRegisteredException(name);
    }
    Class<?> valueClass = manager.getValueClass();
    if (this.class2manager.containsKey(valueClass)) {
      throw new ValueAlreadyRegisteredException(valueClass);
    }
    this.name2manager.put(name, manager);
    this.class2manager.put(valueClass, manager);
  }

  /**
   * @see net.sf.mmm.value.api.ValueService#getManager(java.lang.Class)
   */
  @SuppressWarnings("unchecked")
  public <V> ValueManager<V> getManager(Class<V> valueType) {

    return this.class2manager.get(valueType);
  }

  /**
   * @see net.sf.mmm.value.api.ValueService#findManager(java.lang.Class)
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
   * @see net.sf.mmm.value.api.ValueService#getManager(java.lang.String)
   */
  public ValueManager<?> getManager(String name) {

    return this.name2manager.get(name);
  }

  /**
   * @see net.sf.mmm.value.api.ValueService#getManagers()
   */
  public Collection<ValueManager> getManagers() {

    return this.managers;
  }

  /**
   * @see net.sf.mmm.value.api.ValueService#xml2value(org.w3c.dom.Element)
   */
  public Object xml2value(Element xmlElement) throws ValueException {

    if (!xmlElement.hasAttribute(ValueManager.XML_ATR_VALUE_NAME)) {
      throw new ValueParseException(null);
    }
    String valueName = xmlElement.getAttribute(ValueManager.XML_ATR_VALUE_NAME);
    ValueManager<?> manager = getManager(valueName);
    if (manager == null) {
      throw new ValueNotRegisteredException(valueName);
    }
    return manager.parse(xmlElement);
  }

}
