/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.base.jaxb;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import net.sf.mmm.util.lang.api.attribute.AttributeReadId;

/**
 * This is an implementation of {@link XmlAdapter} for mapping a {@link Map}. It
 * uses a {@link java.util.List} of {@link AttributeReadId}-objects that can be
 * handled by JAXB.
 * 
 * @param <K> is the generic type of the {@link AttributeReadId#getId() ID}s
 *        used as {@link Map#get(Object) map-key}.
 * @author hohwille
 * @since 2.0.0
 */
public class XmlAdapterMap<K> extends XmlAdapter<Object[], Map<K, AttributeReadId<K>>> {

  /**
   * The constructor.
   */
  public XmlAdapterMap() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object[] marshal(Map<K, AttributeReadId<K>> map) throws Exception {

    Object[] result = null;
    if (map != null) {
      Collection<AttributeReadId<K>> values = map.values();
      if (values.size() > 0) {
        result = (AttributeReadId[]) Array.newInstance(values.iterator().next().getClass(),
            values.size());
        result = values.toArray(result);
      }
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings("unchecked")
  public Map<K, AttributeReadId<K>> unmarshal(Object[] list) throws Exception {

    Map<K, AttributeReadId<K>> result = null;
    if (list != null) {
      result = new HashMap<K, AttributeReadId<K>>();
      for (Object e : list) {
        AttributeReadId<K> entry = (AttributeReadId<K>) e;
        result.put(entry.getId(), entry);
      }
    }
    return result;
  }

}
