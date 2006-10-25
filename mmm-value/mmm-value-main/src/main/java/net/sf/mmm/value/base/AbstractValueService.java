/* $Id$ */
package net.sf.mmm.value.base;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

import net.sf.mmm.value.api.ValueException;
import net.sf.mmm.value.api.ValueManagerIF;
import net.sf.mmm.value.api.ValueParseException;
import net.sf.mmm.value.api.ValueServiceIF;

/**
 * This is the abstract base implemenation of the ValueServiceIF interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractValueService implements ValueServiceIF {

  /**
   * maps a {@link ValueManagerIF#getValueType() value type} to the according
   * manager.
   * 
   * @see #getManager(Class)
   */
  private final Map<Class, ValueManagerIF> class2manager;

  /**
   * maps a {@link ValueManagerIF#getName() name} to the according manager.
   * 
   * @see #getManager(String)
   */
  private final Map<String, ValueManagerIF> name2manager;

  /** @see #getManagers() */
  private final Collection<ValueManagerIF> managers;

  /**
   * The constructor.
   */
  public AbstractValueService() {

    super();
    this.name2manager = new HashMap<String, ValueManagerIF>();
    this.class2manager = new HashMap<Class, ValueManagerIF>();
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
  public void addManager(ValueManagerIF<?> manager) throws ValueException {

    String name = manager.getName();
    if (this.name2manager.containsKey(name)) {
      throw new ValueAlreadyRegisteredException(name);
    }
    Class<?> valueClass = manager.getValueType();
    if (this.class2manager.containsKey(valueClass)) {
      throw new ValueAlreadyRegisteredException(valueClass);
    }
    this.name2manager.put(name, manager);
    this.class2manager.put(valueClass, manager);
  }

  /**
   * @see net.sf.mmm.value.api.ValueServiceIF#getManager(java.lang.Class)
   * 
   */
  @SuppressWarnings("unchecked")
  public <V> ValueManagerIF<V> getManager(Class<V> valueType) {

    return this.class2manager.get(valueType);
  }

  /**
   * @see net.sf.mmm.value.api.ValueServiceIF#findManager(java.lang.Class)
   */
  @SuppressWarnings("unchecked")
  public <V> ValueManagerIF<? super V> findManager(Class<V> valueSubType) {

    ValueManagerIF<? super V> manager = getManager(valueSubType);
    if (manager == null) {
      for (ValueManagerIF<?> currentManager : this.class2manager.values()) {
        if (currentManager.getValueType().isAssignableFrom(valueSubType)) {
          manager = (ValueManagerIF<? super V>) currentManager;
          // TODO: should we cache this mapping to speed up lookups?
          break;
        }
      }
    }
    return manager;
  }

  /**
   * @see net.sf.mmm.value.api.ValueServiceIF#getManager(java.lang.String)
   */
  public ValueManagerIF<?> getManager(String name) {

    return this.name2manager.get(name);
  }

  /**
   * @see net.sf.mmm.value.api.ValueServiceIF#getManagers()
   */
  public Collection<ValueManagerIF> getManagers() {

    return this.managers;
  }

  /**
   * @see net.sf.mmm.value.api.ValueServiceIF#xml2value(org.w3c.dom.Element)
   */
  public Object xml2value(Element xmlElement) throws ValueException {

    if (!xmlElement.hasAttribute(ValueManagerIF.XML_ATR_VALUE_NAME)) {
      throw new ValueParseException(null);
    }
    String valueName = xmlElement.getAttribute(ValueManagerIF.XML_ATR_VALUE_NAME);
    ValueManagerIF<?> manager = getManager(valueName);
    if (manager == null) {
      throw new ValueNotRegisteredException(valueName);
    }
    return manager.parse(xmlElement);
  }

}
