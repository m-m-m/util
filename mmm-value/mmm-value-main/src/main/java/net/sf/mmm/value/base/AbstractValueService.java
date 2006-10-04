/* $Id$ */
package net.sf.mmm.value.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;

import net.sf.mmm.value.api.ValueException;
import net.sf.mmm.value.api.ValueManagerIF;
import net.sf.mmm.value.api.ValueParseException;
import net.sf.mmm.value.api.ValueServiceIF;
import net.sf.mmm.value.impl.GenericValueManager;

/**
 * This is the abstract base implemenation of the ValueServiceIF interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractValueService implements ValueServiceIF {

    /**
     * maps a {@link ValueManagerIF#getValueType() "value type"} to the
     * according manager
     */
    private final Map<Class, ValueManagerIF> class2manager;

    /** maps a {@link ValueManagerIF#getName() name} to the according manager */
    private final Map<String, ValueManagerIF> name2manager;

    /** maps a value interface to the according manager */
    private final List<Class> interfaces;

    /**
     * The constructor.
     */
    public AbstractValueService() {

        super();
        this.name2manager = new HashMap<String, ValueManagerIF>();
        this.class2manager = new HashMap<Class, ValueManagerIF>();
        this.interfaces = new ArrayList<Class>();
    }

    /**
     * This method registers a value manager to this service.
     * 
     * @param <V>
     *        is the value type to register.
     * @param valueClass
     *        is the implementation of the value. It should have a String arg
     *        constructor and a compliant {@link Object#toString()} method.
     * @param name
     *        is the {@link ValueManagerIF#getName() "logical name"} of the
     *        value.
     * @throws ValueException
     *         if the registration fails (e.g. value for this name is already
     *         registered).
     */
    public <V> void addValue(Class<V> valueClass, String name) throws ValueException {

        registerManager(new GenericValueManager<V>(valueClass, name));
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
    public void registerManager(ValueManagerIF<?> manager) throws ValueException {

        String name = manager.getName();
        if (this.name2manager.containsKey(name)) {
            throw new ValueAlreadyRegisteredException(name);
        }
        Class valueClass = manager.getValueType();
        if (this.class2manager.containsKey(valueClass)) {
            throw new ValueAlreadyRegisteredException(valueClass);
        }
        this.name2manager.put(name, manager);
        this.class2manager.put(valueClass, manager);
        if (valueClass.isInterface()) {
            this.interfaces.add(valueClass);
        }
    }

    /**
     * @see net.sf.mmm.value.api.ValueServiceIF#getManager(java.lang.Class)
     * 
     */
    @SuppressWarnings("unchecked")
    public ValueManagerIF getManager(Class valueClass) {

        ValueManagerIF manager = this.class2manager.get(valueClass);
        if (manager == null) {
            int ifCount = this.interfaces.size();
            for (int ifIndex = 0; ifIndex < ifCount; ifIndex++) {
                Class ifClass = this.interfaces.get(ifIndex);
                if (ifClass.isAssignableFrom(valueClass)) {
                    manager = this.class2manager.get(ifClass);
                    // TODO: should we add this mapping (valueClass -> manager)
                    // to class2manger map? - could cause concurrency problem?
                }
            }
        }
        return manager;
    }

    /**
     * @see net.sf.mmm.value.api.ValueServiceIF#getManager(java.lang.String)
     * 
     */
    public ValueManagerIF<?> getManager(String name) {

        return this.name2manager.get(name);
    }

    /**
     * @see net.sf.mmm.value.api.ValueServiceIF#getManagers()
     * 
     */
    public Iterator<ValueManagerIF> getManagers() {

        return this.name2manager.values().iterator();
    }

    /**
     * @see net.sf.mmm.value.api.ValueServiceIF#xml2value(org.w3c.dom.Element)
     * 
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