/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import java.util.HashMap;
import java.util.Map;

/**
 * This is an abstract base implementation of a checked exception with real
 * <em>native language support</em> (NLS).<br>
 * <b>ATTENTION:</b><br>
 * Checked exceptions should be used for business errors and should only occur
 * in unexpected situations.
 * 
 * @see net.sf.mmm.util.nls.api.NlsThrowable
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class NlsException extends AbstractNlsException {

  /** UID for serialization. */
  private static final long serialVersionUID = 234915625705566691L;

  /**
   * The constructor.<br>
   * 
   * @param internationalizedMessage is a short description of the problem. It
   *        is used for
   *        {@link net.sf.mmm.util.nls.api.NlsMessage#getInternationalizedMessage()
   *        internationalization} and should be in English language.
   * @param arguments are the arguments filled into the
   *        <code>internaitionalizedMessage</code> after nationalization. May be
   *        empty if no variable arguments exist.
   * @deprecated use {@link #NlsException(String, Map)} instead.
   */
  @Deprecated
  public NlsException(String internationalizedMessage, Object... arguments) {

    super(NlsAccess.getFactory().create(internationalizedMessage, arguments));
  }

  /**
   * The constructor.<br>
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param internationalizedMessage is a short description of the problem. It
   *        is used for
   *        {@link net.sf.mmm.util.nls.api.NlsMessage#getInternationalizedMessage()
   *        internationalization} and should be in English language.
   * @param arguments are the arguments filled into the
   *        <code>internaitionalizedMessage</code> after nationalization. May be
   *        empty if no variable arguments exist.
   * @deprecated use {@link #NlsException(String, Map)} instead.
   */
  @Deprecated
  public NlsException(Throwable nested, String internationalizedMessage, Object... arguments) {

    super(nested, NlsAccess.getFactory().create(internationalizedMessage, arguments));
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
  public NlsException(String internationalizedMessage, Map<String, Object> arguments) {

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
  public NlsException(Throwable nested, String internationalizedMessage,
      Map<String, Object> arguments) {

    super(nested, NlsAccess.getFactory().create(internationalizedMessage, arguments));
  }

  /**
   * The constructor.
   * 
   * @param message the {@link #getNlsMessage() message} describing the problem
   *        briefly.
   */
  public NlsException(NlsMessage message) {

    super(message);
  }

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param message the {@link #getNlsMessage() message} describing the problem
   *        briefly.
   */
  public NlsException(Throwable nested, NlsMessage message) {

    super(nested, message);
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
    result.put(key, value);
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
    map.put(key2, value2);
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
    map.put(key2, value2);
    map.put(key3, value3);
    return map;
  }

}
