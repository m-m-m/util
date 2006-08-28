/* $Id$ */
package net.sf.mmm.value.api;

import java.util.Iterator;

import org.w3c.dom.Element;

/**
 * This is the interface for the component that manages the values.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
// @Specification
public interface ValueServiceIF {

    /**
     * This method parses an xml element that represents a value.
     * 
     * @param xmlElement
     *        is the value encoded as XML element.
     * @return the value represented by the xml element.
     * @throws ValueException
     *         if the given xml element is no legal value.
     */
    Object xml2value(Element xmlElement) throws ValueException;

    /**
     * This method gets the value manager for the given value type.
     * 
     * @param valueClass
     *        is the {@link ValueManagerIF#getValueType() "value type"} of the
     *        requested manager. If the
     *        {@link ValueManagerIF#getValueType() "value type"} of a registered
     *        {@link ValueManagerIF manager} is an
     *        {@link Class#isInterface() interface}, also a class implementing
     *        that interface may be given here to retrieve the according manger.
     * @return the manager for the given <code>valueClass</code> or
     *         <code>null</code> if NO such manager is registered. Be aware
     *         that {@link ValueManagerIF#getValueType()} applied to the result
     *         may return a different {@link Class} thant the given
     *         <code>valueClass</code> as described above.
     */
    ValueManagerIF getManager(Class valueClass);

    /**
     * This method gets the value manager for the given name.
     * 
     * @see ValueManagerIF#getName()
     * 
     * @param name
     *        is the {@link ValueManagerIF#getName() name} of the value for
     *        which the manager is requested.
     * @return the manager for the given name or <code>null</code> if no such
     *         manager is registered.
     */
    ValueManagerIF<?> getManager(String name);

    /**
     * This method gets the list of all value managers registered in this
     * service.
     * 
     * @return an iterator of all registered value managers.
     */
    Iterator<ValueManagerIF> getManagers();

}