/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.api.NlsMessageFactory;
import net.sf.mmm.util.nls.api.NlsTemplate;
import net.sf.mmm.util.reflect.api.ReflectionUtilLimited;

/**
 * This is the implementation of the {@link NlsMessageFactory} interface.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public abstract class AbstractNlsMessageFactory extends AbstractLoggableComponent implements NlsMessageFactory {

  /**
   * The constructor.
   */
  public AbstractNlsMessageFactory() {

    super();
  }

  @Override
  public NlsMessage create(String internationalizedMessage, Object... messageArguments) {

    Map<String, Object> arguments = new HashMap<>();
    assert ((messageArguments.length == 0) || !(messageArguments[0] instanceof Map<?, ?>));
    for (int i = 0; i < messageArguments.length; i++) {
      arguments.put(Integer.toString(i), messageArguments[i]);
    }
    return create(internationalizedMessage, arguments);
  }

  @Override
  public NlsMessage create(NlsTemplate template, Object... messageArguments) {

    Map<String, Object> arguments = new HashMap<>();
    assert ((messageArguments.length == 0) || !(messageArguments[0] instanceof Map<?, ?>));
    for (int i = 0; i < messageArguments.length; i++) {
      arguments.put(Integer.toString(i), messageArguments[i]);
    }
    return create(template, arguments);
  }

  // CHECKSTYLE:OFF (many arguments for convenience)
  @Override
  public NlsMessage create(NlsTemplate template, String key1, Object argument1, String key2, Object argument2,
      String key3, Object argument3, String key4, Object argument4) {

    // CHECKSTYLE:ON

    Map<String, Object> arguments = new HashMap<>();
    arguments.put(key1, argument1);
    arguments.put(key2, argument2);
    arguments.put(key3, argument3);
    arguments.put(key4, argument4);
    return create(template, arguments);
  }

  @Override
  public NlsMessage create(NlsTemplate template, String key1, Object argument1, String key2, Object argument2,
      String key3, Object argument3) {

    Map<String, Object> arguments = new HashMap<>();
    arguments.put(key1, argument1);
    arguments.put(key2, argument2);
    arguments.put(key3, argument3);
    return create(template, arguments);
  }

  @Override
  public NlsMessage create(NlsTemplate template, String key1, Object argument1, String key2, Object argument2) {

    Map<String, Object> arguments = new HashMap<>();
    arguments.put(key1, argument1);
    arguments.put(key2, argument2);
    return create(template, arguments);
  }

  @Override
  public NlsMessage create(NlsTemplate template, String key1, Object argument1) {

    Map<String, Object> arguments = new HashMap<>();
    arguments.put(key1, argument1);
    return create(template, arguments);
  }

  // CHECKSTYLE:OFF (many arguments for convenience)
  @Override
  public NlsMessage create(String internationalizedMessage, String key1, Object argument1, String key2,
      Object argument2, String key3, Object argument3, String key4, Object argument4) {

    // CHECKSTYLE:ON

    Map<String, Object> arguments = new HashMap<>();
    arguments.put(key1, argument1);
    arguments.put(key2, argument2);
    arguments.put(key3, argument3);
    arguments.put(key4, argument4);
    return create(internationalizedMessage, arguments);
  }

  @Override
  public NlsMessage create(String internationalizedMessage, String key1, Object argument1, String key2,
      Object argument2, String key3, Object argument3) {

    Map<String, Object> arguments = new HashMap<>();
    arguments.put(key1, argument1);
    arguments.put(key2, argument2);
    arguments.put(key3, argument3);
    return create(internationalizedMessage, arguments);
  }

  @Override
  public NlsMessage create(String internationalizedMessage, String key1, Object argument1, String key2,
      Object argument2) {

    Map<String, Object> arguments = new HashMap<>();
    arguments.put(key1, argument1);
    arguments.put(key2, argument2);
    return create(internationalizedMessage, arguments);
  }

  @Override
  public NlsMessage create(String internationalizedMessage, String key1, Object argument1) {

    Map<String, Object> arguments = new HashMap<>();
    arguments.put(key1, argument1);
    return create(internationalizedMessage, arguments);
  }

  @Override
  public NlsMessage create(NlsTemplate template) {

    return create(template, ReflectionUtilLimited.NO_ARGUMENTS);
  }

  @Override
  public NlsMessage create(String internationalizedMessage) {

    return create(internationalizedMessage, Collections.<String, Object> emptyMap());
  }

  @Override
  public NlsMessage createDirect(String bundleName, String key) {

    return createDirect(bundleName, key, Collections.<String, Object> emptyMap());
  }

}
