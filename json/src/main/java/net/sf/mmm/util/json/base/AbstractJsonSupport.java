/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.json.base;

import java.io.StringReader;
import java.io.StringWriter;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import net.sf.mmm.util.exception.api.ObjectMismatchException;
import net.sf.mmm.util.json.api.JsonSupport;

/**
 * This is the abstract base implementation of {@link JsonSupport}.
 *
 * @author hohwille
 * @since 7.4.0
 */
public abstract class AbstractJsonSupport implements JsonSupport {

  @Override
  public String toJson() {

    StringWriter writer = new StringWriter();
    JsonGenerator json = Json.createGenerator(writer);
    json.writeStartObject();
    toJson(json);
    json.writeEnd();
    json.close();
    return writer.toString();
  }

  @Override
  public void fromJson(String json) {

    StringReader reader = new StringReader(json);
    JsonParser parser = Json.createParser(reader);
    Event event = parser.next();
    if (event != Event.START_OBJECT) {
      throw new ObjectMismatchException(event, Event.START_OBJECT);
    }
    fromJson(parser);
    reader.close();
  }

  @Override
  public String toString() {

    return toJson();
  }

}
