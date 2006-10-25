/* $Id$ */
package net.sf.mmm.value.api;

import java.util.Collection;

import javax.annotation.Resource;

import org.w3c.dom.Element;

/**
 * This is the interface for the component that manages the values.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Resource
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
   * This method gets the {@link ValueManagerIF value manager} for the given
   * {@link ValueManagerIF#getValueType() valueType}.
   * 
   * @param <V>
   *        is the templated type of the value.
   * @param valueType
   *        is the {@link ValueManagerIF#getValueType() value type} of the
   *        requested manager.
   * @return the manager for the given <code>valueClass</code> or
   *         <code>null</code> if NO such manager is registered.
   */
  <V> ValueManagerIF<V> getManager(Class<V> valueType);

  /**
   * This method gets the {@link ValueManagerIF value manager} for the given
   * <code>valueSubType</code>. Use this method instead of
   * {@link #getManager(Class)} if you do NOT know the precise value type.<br>
   * E.g. if you have {@link ValueManagerIF value-managers} with an abstract
   * {@link ValueManagerIF#getValueType() value-type} (such as
   * {@link org.w3c.dom.Document}) and you have an instance of a value you can
   * use this method to find the manager for the {@link Object#getClass() class}
   * of the value.
   * 
   * @param <V>
   *        is the templated type of the value.
   * @param valueSubType
   *        is a sup-type of the requested manager's
   *        {@link ValueManagerIF#getValueType() value type}.
   * @return the manager for the given <code>valueClass</code> or
   *         <code>null</code> if NO such manager could be found. Be aware
   *         that the {@link ValueManagerIF#getValueType() value-type} of the
   *         result may return a super-type of the given
   *         <code>valueSubType</code>.
   */
  <V> ValueManagerIF<? super V> findManager(Class<V> valueSubType);

  /**
   * This method gets the value manager for the given name.
   * 
   * @see ValueManagerIF#getName()
   * 
   * @param name
   *        is the {@link ValueManagerIF#getName() name} of the value for which
   *        the manager is requested.
   * @return the manager for the given name or <code>null</code> if no such
   *         manager is registered.
   */
  ValueManagerIF<?> getManager(String name);

  /**
   * This method gets all value {@link ValueManagerIF value-managers} registered
   * by this service.
   * 
   * @return a collection containing all registered value managers.
   */
  Collection<ValueManagerIF> getManagers();

}
