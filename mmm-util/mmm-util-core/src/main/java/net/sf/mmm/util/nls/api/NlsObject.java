/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

/**
 * This is the interface for an object with native language support. Such object
 * be can {@link #toNlsMessage() converted} to an {@link NlsMessage
 * i18n-message} describing the object analog to its {@link Object#toString()
 * string representation}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface NlsObject {

  /** Key for the {@link NlsMessage#getArgument(String) argument} {@value}. */
  String KEY_OBJECT = "object";

  /** Key for the {@link NlsMessage#getArgument(String) argument} {@value}. */
  String KEY_KEY = "key";

  /** Key for the {@link NlsMessage#getArgument(String) argument} {@value}. */
  String KEY_ID = "id";

  /** Key for the {@link NlsMessage#getArgument(String) argument} {@value}. */
  String KEY_VALUE = "value";

  /** Key for the {@link NlsMessage#getArgument(String) argument} {@value}. */
  String KEY_TYPE = "type";

  /** Key for the {@link NlsMessage#getArgument(String) argument} {@value}. */
  String KEY_SOURCE = "source";

  /** Key for the {@link NlsMessage#getArgument(String) argument} {@value}. */
  String KEY_MIN = "min";

  /** Key for the {@link NlsMessage#getArgument(String) argument} {@value}. */
  String KEY_MAX = "max";

  /** Key for the {@link NlsMessage#getArgument(String) argument} {@value}. */
  String KEY_RESOURCE = "resource";

  /** Key for the {@link NlsMessage#getArgument(String) argument} {@value}. */
  String KEY_SIZE = "size";

  /** Key for the {@link NlsMessage#getArgument(String) argument} {@value}. */
  String KEY_CAPACITY = "capacity";

  /** Key for the {@link NlsMessage#getArgument(String) argument} {@value}. */
  String KEY_PROPERTY = "property";

  /** Key for the {@link NlsMessage#getArgument(String) argument} {@value}. */
  String KEY_PATH = "path";

  /** Key for the {@link NlsMessage#getArgument(String) argument} {@value}. */
  String KEY_NAME = "name";

  /** Key for the {@link NlsMessage#getArgument(String) argument} {@value}. */
  String KEY_OPTION = "option";

  /** Key for the {@link NlsMessage#getArgument(String) argument} {@value}. */
  String KEY_MODE = "mode";

  /** Key for the {@link NlsMessage#getArgument(String) argument} {@value}. */
  String KEY_FILE = "file";

  /** Key for the {@link NlsMessage#getArgument(String) argument} {@value}. */
  String KEY_OPERAND = "operand";

  /** Key for the {@link NlsMessage#getArgument(String) argument} {@value}. */
  String KEY_DEFAULT = "default";

  /** Key for the {@link NlsMessage#getArgument(String) argument} {@value}. */
  String KEY_ARGUMENT = "argument";

  /**
   * This method is the equivalent to {@link Object#toString()} with native
   * language support.
   * 
   * @return an nls message representing this object.
   */
  NlsMessage toNlsMessage();

}
