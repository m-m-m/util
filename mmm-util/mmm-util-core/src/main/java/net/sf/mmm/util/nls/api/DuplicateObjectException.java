/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import java.util.Map;

import net.sf.mmm.util.NlsBundleUtilCoreRoot;

/**
 * An {@link DuplicateObjectException} is thrown if an object was rejected because it is a duplicate. This
 * typically happens if objects are registered (e.g. in a {@link java.util.Map}) and two objects should be
 * associated with the same key.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public class DuplicateObjectException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = -1754790439556739544L;

  /**
   * The constructor.
   * 
   * @param object is the object that was rejected because it is a duplicate.
   */
  public DuplicateObjectException(Object object) {

    super(createBundle(NlsBundleUtilCoreRoot.class).errorDuplicateObject(object));
  }

  /**
   * The constructor.
   * 
   * @param object is the object that was rejected because it is a duplicate.
   * @param key is the key the object could NOT be associated with because it already leads to another object.
   */
  public DuplicateObjectException(Object object, Object key) {

    super(createBundle(NlsBundleUtilCoreRoot.class).errorDuplicateObjectWithKey(object, key));
  }

  /**
   * The constructor.
   * 
   * @param object is the object that was rejected because it is a duplicate.
   * @param key is the key the object could NOT be associated with because it already leads to another object.
   * @param existing is the object that is already registered for the given <code>key</code>.
   * @since 2.0.2
   */
  public DuplicateObjectException(Object object, Object key, Object existing) {

    super(createBundle(NlsBundleUtilCoreRoot.class).errorDuplicateObjectWithKeyAndExisting(object, key, existing));
  }

  /**
   * This method {@link Map#put(Object, Object) puts} the given <code>value</code> into the given
   * <code>map</code> using the given <code>key</code>.
   * 
   * @param <KEY> is the generic type of the <code>key</code>.
   * @param <VALUE> is the generic type of the <code>value</code>.
   * @param map is the {@link Map}.
   * @param key is the {@link Map#get(Object) key}.
   * @param value is the value to {@link Map#put(Object, Object) put}.
   * @throws DuplicateObjectException if the given <code>map</code> already contains a value for the given
   *         <code>key</code> that is NOT equal to the given <code>value</code>.
   * @since 2.0.2
   */
  public static <KEY, VALUE> void put(Map<KEY, VALUE> map, KEY key, VALUE value) throws DuplicateObjectException {

    VALUE old = map.get(key);
    if ((old != null) && (!old.equals(value))) {
      throw new DuplicateObjectException(value, key, old);
    }
    map.put(key, value);
  }

}
