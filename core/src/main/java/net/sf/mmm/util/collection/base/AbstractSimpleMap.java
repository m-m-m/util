/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * This is the abstract base implementation for a <em>simple</em> {@link Map}. <br>
 * <b>ATTENTION:</b><br>
 * Here <em>simple</em> means that subclasses of this class only guarantee that the method {@link #get(Object)} and
 * {@link #containsKey(Object)} is properly implemented.
 *
 * @param <KEY> the type of keys maintained by this map.
 * @param <VALUE> the type of mapped values.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.1
 */
public abstract class AbstractSimpleMap<KEY, VALUE> implements Map<KEY, VALUE> {

  /**
   * The constructor.
   */
  public AbstractSimpleMap() {

    super();
  }

  @Override
  public void clear() {

    throw new UnsupportedOperationException();
  }

  @Override
  public boolean containsKey(Object key) {

    return (get(key) != null);
  }

  @Override
  public boolean containsValue(Object value) {

    throw new UnsupportedOperationException();
  }

  @Override
  public Set<Entry<KEY, VALUE>> entrySet() {

    throw new UnsupportedOperationException();
  }

  @Override
  public boolean isEmpty() {

    return false;
  }

  @Override
  public Set<KEY> keySet() {

    throw new UnsupportedOperationException();
  }

  @Override
  public VALUE put(KEY key, VALUE value) {

    throw new UnsupportedOperationException();
  }

  @Override
  public void putAll(Map<? extends KEY, ? extends VALUE> m) {

    throw new UnsupportedOperationException();
  }

  @Override
  public VALUE remove(Object key) {

    throw new UnsupportedOperationException();
  }

  @Override
  public int size() {

    throw new UnsupportedOperationException();
  }

  @Override
  public Collection<VALUE> values() {

    throw new UnsupportedOperationException();
  }

}
