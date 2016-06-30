/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl;

import java.io.StringWriter;
import java.lang.reflect.Method;

import javax.json.Json;
import javax.json.stream.JsonGenerator;

import net.sf.mmm.util.bean.api.Bean;

/**
 * Operation for {@link Bean#toString()}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class BeanPrototypeOperationToString extends BeanPrototypeOperation {

  /**
   * The constructor.
   *
   * @param prototype the {@link BeanAccessPrototype}.
   * @param method the {@link Method}.
   */
  public BeanPrototypeOperationToString(BeanAccessPrototype<?> prototype, Method method) {
    super(prototype, method);
  }

  @Override
  public Object invoke(BeanAccessBase<?> access, Object[] args) {

    StringWriter writer = new StringWriter();
    JsonGenerator json = Json.createGenerator(writer);
    json.writeStartObject();
    access.toJson(json);
    json.writeEnd();
    json.close();
    return writer.toString();
  }

}
