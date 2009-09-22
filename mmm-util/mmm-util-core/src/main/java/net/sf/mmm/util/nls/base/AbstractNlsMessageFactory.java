/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.api.NlsMessageFactory;
import net.sf.mmm.util.nls.api.NlsTemplate;

/**
 * This is the implementation of the {@link NlsMessageFactory} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public abstract class AbstractNlsMessageFactory implements NlsMessageFactory {

  /**
   * The constructor.
   */
  public AbstractNlsMessageFactory() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public NlsMessage create(String internationalizedMessage, Object... messageArguments) {

    Map<String, Object> arguments = new HashMap<String, Object>();
    assert ((messageArguments.length == 0) || !(messageArguments[0] instanceof Map<?, ?>));
    for (int i = 0; i < messageArguments.length; i++) {
      arguments.put(Integer.toString(i), messageArguments[i]);
    }
    return create(internationalizedMessage, arguments);
  }

  /**
   * {@inheritDoc}
   */
  public NlsMessage create(NlsTemplate template, Object... messageArguments) {

    Map<String, Object> arguments = new HashMap<String, Object>();
    assert ((messageArguments.length == 0) || !(messageArguments[0] instanceof Map<?, ?>));
    for (int i = 0; i < messageArguments.length; i++) {
      arguments.put(Integer.toString(i), messageArguments[i]);
    }
    return create(template, arguments);
  }

  /**
   * {@inheritDoc}
   */
  public NlsMessage create(NlsTemplate template, String key1, Object argument1, String key2,
      Object argument2, String key3, Object argument3) {

    Map<String, Object> arguments = new HashMap<String, Object>();
    arguments.put(key1, argument1);
    arguments.put(key2, argument2);
    arguments.put(key3, argument3);
    return create(template, arguments);
  }

  /**
   * {@inheritDoc}
   */
  public NlsMessage create(NlsTemplate template, String key1, Object argument1, String key2,
      Object argument2) {

    Map<String, Object> arguments = new HashMap<String, Object>();
    arguments.put(key1, argument1);
    arguments.put(key2, argument2);
    return create(template, arguments);
  }

  /**
   * {@inheritDoc}
   */
  public NlsMessage create(NlsTemplate template, String key1, Object argument1) {

    Map<String, Object> arguments = new HashMap<String, Object>();
    arguments.put(key1, argument1);
    return create(template, arguments);
  }

  /**
   * {@inheritDoc}
   */
  public NlsMessage create(String internationalizedMessage, String key1, Object argument1,
      String key2, Object argument2, String key3, Object argument3) {

    Map<String, Object> arguments = new HashMap<String, Object>();
    arguments.put(key1, argument1);
    arguments.put(key2, argument2);
    arguments.put(key3, argument3);
    return create(internationalizedMessage, arguments);
  }

  /**
   * {@inheritDoc}
   */
  public NlsMessage create(String internationalizedMessage, String key1, Object argument1,
      String key2, Object argument2) {

    Map<String, Object> arguments = new HashMap<String, Object>();
    arguments.put(key1, argument1);
    arguments.put(key2, argument2);
    return create(internationalizedMessage, arguments);
  }

  /**
   * {@inheritDoc}
   */
  public NlsMessage create(String internationalizedMessage, String key1, Object argument1) {

    Map<String, Object> arguments = new HashMap<String, Object>();
    arguments.put(key1, argument1);
    return create(internationalizedMessage, arguments);
  }

  /**
   * {@inheritDoc}
   */
  public NlsMessage create(NlsTemplate template) {

    return create(template, Collections.emptyMap());
  }

  /**
   * {@inheritDoc}
   */
  public NlsMessage create(String internationalizedMessage) {

    return create(internationalizedMessage, Collections.<String, Object> emptyMap());
  }

}
