/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl.id;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import net.sf.mmm.util.bean.api.id.Id;

/**
 * An implementation of {@link JsonSerializer} for {@link Id}.
 *
 * @author hohwille
 * @since 8.4.0
 */
public class IdJsonSerializer extends JsonSerializer<Id<?>> {

  /**
   * The constructor.
   */
  public IdJsonSerializer() {
    super();
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public Class<Id<?>> handledType() {

    return (Class) Id.class;
  }

  @Override
  public void serialize(Id<?> id, JsonGenerator gen, SerializerProvider serializers)
      throws IOException, JsonProcessingException {

    if (id == null) {
      return;
    }
    String idString = id.toString();
    gen.writeString(idString);
  }

}
