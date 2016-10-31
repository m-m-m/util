/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl.id;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import net.sf.mmm.util.bean.api.id.GenericId;
import net.sf.mmm.util.bean.api.id.Id;

/**
 * An implementation of {@link JsonDeserializer} for {@link Id}.
 *
 * @author hohwille
 * @since 8.4.0
 */
public class GenericIdJsonDeserializer extends JsonDeserializer<GenericId<?>> {

  /**
   * The constructor.
   */
  public GenericIdJsonDeserializer() {
    super();
  }

  @Override
  public GenericId<?> deserialize(JsonParser p, DeserializationContext ctx)
      throws IOException, JsonProcessingException {

    String idAsString = p.getValueAsString();
    return GenericId.of(null, idAsString);
  }

}
