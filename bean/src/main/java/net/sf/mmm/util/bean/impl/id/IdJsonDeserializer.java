/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl.id;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import net.sf.mmm.util.data.api.id.Id;
import net.sf.mmm.util.data.api.id.LongId;
import net.sf.mmm.util.data.api.id.StringId;
import net.sf.mmm.util.exception.api.NlsParseException;

/**
 * An implementation of {@link JsonDeserializer} for {@link Id}.
 *
 * @author hohwille
 * @since 8.4.0
 */
public class IdJsonDeserializer extends JsonDeserializer<Id<?>> {

  /**
   * The constructor.
   */
  public IdJsonDeserializer() {
    super();
  }

  @Override
  public Id<?> deserialize(JsonParser p, DeserializationContext ctx) throws IOException, JsonProcessingException {

    if (p.getCurrentToken() == JsonToken.VALUE_STRING) {
      return createId(p.nextTextValue(), Id.VERSION_LATEST);
    } else if (p.getCurrentToken() == JsonToken.VALUE_NUMBER_INT) {
      return createId(Long.valueOf(p.getLongValue()), Id.VERSION_LATEST);
    } else if (p.getCurrentToken() == JsonToken.START_OBJECT) {
      JsonToken token = p.nextToken();
      Object id = null;
      long version = Id.VERSION_LATEST;
      while (token == JsonToken.FIELD_NAME) {
        String property = p.getCurrentName();
        token = p.nextToken();
        if ("id".equals(property)) {
          if (token.isNumeric()) {
            id = Long.valueOf(p.getLongValue());
          } else {
            id = p.getText();
          }
        } else if ("version".equals(property)) {
          version = p.getLongValue();
        }
        token = p.nextToken();
      }
      if (id != null) {
        return createId(id, version);
      }
    }
    throw new NlsParseException("JSON", Id.class);
  }

  private Id<?> createId(Object id, long version) {

    if (id instanceof Long) {
      return LongId.of(null, (Long) id, version);
    } else {
      return StringId.of(null, (String) id, version);
    }
  }

}
