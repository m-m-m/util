/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import java.util.HashMap;
import java.util.Map;

/**
 * This the base class for all runtime exceptions of the project.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class NlsRuntimeException extends AbstractNlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = 6002426164465970398L;

  /**
   * The constructor.
   * 
   * @param internationalizedMessage is a short description of the problem. It
   *        is used for internationalization and should be in English language.
   * @param arguments are the arguments filled into the
   *        <code>internationalizedMessage</code> after nationalization.
   * @deprecated use {@link #NlsRuntimeException(String, Map)} instead.
   */
  @Deprecated
  public NlsRuntimeException(String internationalizedMessage, Object... arguments) {

    super(NlsAccess.getFactory().create(internationalizedMessage, arguments));
  }

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param internationalizedMessage is a short description of the problem. It
   *        is used for internationalization and should be in English language.
   * @param arguments are the arguments filled into the
   *        <code>internationalizedMessage</code> after nationalization.
   * @deprecated use {@link #NlsRuntimeException(Throwable, String, Map)}
   *             instead.
   */
  @Deprecated
  public NlsRuntimeException(Throwable nested, String internationalizedMessage, Object... arguments) {

    super(nested, NlsAccess.getFactory().create(internationalizedMessage, arguments));
  }

  /**
   * The constructor.
   * 
   * @param internationalizedMessage is a short description of the problem. It
   *        is used for {@link NlsMessage#getInternationalizedMessage()
   *        internationalization} and should be in English language.
   */
  public NlsRuntimeException(String internationalizedMessage) {

    super(NlsAccess.getFactory().create(internationalizedMessage));
  }

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param internationalizedMessage is a short description of the problem. It
   *        is used for {@link NlsMessage#getInternationalizedMessage()
   *        internationalization} and should be in English language.
   */
  public NlsRuntimeException(Throwable nested, String internationalizedMessage) {

    super(nested, NlsAccess.getFactory().create(internationalizedMessage));
  }

  /**
   * The constructor.
   * 
   * @param internationalizedMessage is a short description of the problem. It
   *        is used for {@link NlsMessage#getInternationalizedMessage()
   *        internationalization} and should be in English language.
   * @param arguments are the {@link NlsMessage#getArgument(String) arguments}
   *        to be {@link NlsMessage#getInternationalizedMessage() filled into
   *        <code>internationalizedMessage</code>}.
   */
  public NlsRuntimeException(String internationalizedMessage, Map<String, Object> arguments) {

    super(NlsAccess.getFactory().create(internationalizedMessage, arguments));
  }

  /**
   * The constructor.
   * 
   * @see #toMap(String, Object, String, Object)
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param internationalizedMessage is a short description of the problem. It
   *        is used for {@link NlsMessage#getInternationalizedMessage()
   *        internationalization} and should be in English language.
   * @param arguments are the {@link NlsMessage#getArgument(String) arguments}
   *        to be {@link NlsMessage#getInternationalizedMessage() filled into
   *        <code>internationalizedMessage</code>}.
   */
  public NlsRuntimeException(Throwable nested, String internationalizedMessage,
      Map<String, Object> arguments) {

    super(nested, NlsAccess.getFactory().create(internationalizedMessage, arguments));
  }

  /**
   * This method adds the given arguments to the given {@link Map}.
   * 
   * @param map is the {@link Map} to use or <code>null</code> to create a new
   *        {@link Map}.
   * @param key is the {@link Map#keySet() key} of the entry to add.
   * @param value is the {@link Map#get(Object) value} of the entry to add.
   * @return a the given <code>map</code> (or a new one if <code>map</code> was
   *         <code>null</code>) after the given entry was added.
   */
  protected static Map<String, Object> addToMap(Map<String, Object> map, String key, Object value) {

    Map<String, Object> result;
    if (map == null) {
      result = new HashMap<String, Object>();
    } else {
      result = map;
    }
    Object duplicate = map.put(key, value);
    assert (duplicate == null);
    return result;
  }

  /**
   * This method create a {@link Map} for the given arguments.
   * 
   * @param key1 is the {@link Map#keySet() key} of the first map entry.
   * @param value1 is the {@link Map#get(Object) value} of the first map entry.
   * @return a {@link Map} containing only the given arguments.
   */
  protected static Map<String, Object> toMap(String key1, Object value1) {

    Map<String, Object> map = new HashMap<String, Object>();
    map.put(key1, value1);
    return map;
  }

  /**
   * This method create a {@link Map} for the given arguments.
   * 
   * @param key1 is the {@link Map#keySet() key} of the first map entry.
   * @param value1 is the {@link Map#get(Object) value} of the first map entry.
   * @param key2 is the {@link Map#keySet() key} of the second map entry.
   * @param value2 is the {@link Map#get(Object) value} of the second map entry.
   * @return a {@link Map} containing only the given arguments.
   */
  protected static Map<String, Object> toMap(String key1, Object value1, String key2, Object value2) {

    Map<String, Object> map = new HashMap<String, Object>();
    map.put(key1, value1);
    Object duplicate = map.put(key2, value2);
    assert (duplicate == null);
    return map;
  }

  /**
   * This method create a {@link Map} for the given arguments.
   * 
   * @param key1 is the {@link Map#keySet() key} of the first map entry.
   * @param value1 is the {@link Map#get(Object) value} of the first map entry.
   * @param key2 is the {@link Map#keySet() key} of the second map entry.
   * @param value2 is the {@link Map#get(Object) value} of the second map entry.
   * @param key3 is the {@link Map#keySet() key} of the third map entry.
   * @param value3 is the {@link Map#get(Object) value} of the third map entry.
   * @return a {@link Map} containing only the given arguments.
   */
  protected static Map<String, Object> toMap(String key1, Object value1, String key2,
      Object value2, String key3, Object value3) {

    Map<String, Object> map = new HashMap<String, Object>();
    map.put(key1, value1);
    Object duplicate = map.put(key2, value2);
    assert (duplicate == null);
    duplicate = map.put(key3, value3);
    assert (duplicate == null);
    return map;
  }

}
