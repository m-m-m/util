/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl.id;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import net.sf.mmm.util.data.api.id.Id;
import net.sf.mmm.util.data.api.id.LongId;

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
  public void serialize(Id<?> id, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {

    if (id == null) {
      return;
    }
    long version = id.getVersion();
    if (version == Id.VERSION_LATEST) {
      writeId(id, gen);
    } else {
      gen.writeStartObject();
      gen.writeFieldName(Id.PROPERTY_ID);
      writeId(id, gen);
      gen.writeNumberField(Id.PROPERTY_VERSION, version);
      gen.writeEndObject();
    }
  }

  private void writeId(Id<?> id, JsonGenerator gen) throws IOException {

    if (id instanceof LongId) {
      gen.writeNumber(((LongId<?>) id).getIdAsLong());
    } else {
      gen.writeString(id.getId().toString());
    }
  }

}
