/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.base.jaxb;

import java.util.Locale;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import net.sf.mmm.util.nls.api.ObjectNotFoundException;

/**
 * This is an implementation of {@link XmlAdapter} for mapping {@link Enum}s to {@link #normalize(String)
 * nice} names.
 * 
 * @param <E> is the type of the enum.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public abstract class XmlAdapterEnum<E extends Enum<E>> extends XmlAdapter<String, E> {

  /**
   * The constructor.
   */
  public XmlAdapterEnum() {

    super();
  }

  /**
   * This method gets the {@link Class} reflecting the enum.
   * 
   * @return the enum type.
   */
  protected abstract Class<E> getEnumClass();

  /**
   * {@inheritDoc}
   */
  @Override
  public String marshal(E value) throws Exception {

    if (value == null) {
      return null;
    } else {
      return normalize(value.name());
    }
  }

  /**
   * This method normalizes the constant {@link Enum#name() name} of the {@link Enum}.
   * 
   * @param enumName is the {@link Enum#name() name}.
   * @return the normalized name.
   */
  protected String normalize(String enumName) {

    return enumName.toLowerCase(Locale.US).replace('_', '-');
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public E unmarshal(String value) throws Exception {

    if (value == null) {
      return null;
    } else {
      String normalizedValue = normalize(value);
      for (E e : getEnumClass().getEnumConstants()) {
        if (normalizedValue.equals(normalize(e.name()))) {
          return e;
        }
      }
      throw new ObjectNotFoundException(getEnumClass().getSimpleName(), value);
    }
  }
}
