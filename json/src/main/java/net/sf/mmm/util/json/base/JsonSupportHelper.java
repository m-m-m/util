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
 * Helper class to implement {@link JsonSupport} in case extending {@link AbstractJsonSupport} is not suitable.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 7.6.0
 */
public class JsonSupportHelper {

  /**
   * @param jsonSupport the {@link JsonSupport} to {@link JsonSupport#toJson() get as JSON from}.
   * @return the {@link JsonSupport#toJson() JSON} as {@link String}.
   */
  public static String toJson(JsonSupport jsonSupport) {

    StringWriter writer = new StringWriter();
    JsonGenerator json = Json.createGenerator(writer);
    json.writeStartObject();
    jsonSupport.toJson(json);
    json.writeEnd();
    json.close();
    return writer.toString();
  }

  /**
   * @param json the JSON as {@link String}.
   * @param jsonSupport the {@link JsonSupport}.
   */
  public static void fromJson(String json, JsonSupport jsonSupport) {

    StringReader reader = new StringReader(json);
    JsonParser parser = Json.createParser(reader);
    Event event = parser.next();
    if (event != Event.START_OBJECT) {
      throw new ObjectMismatchException(event, Event.START_OBJECT);
    }
    jsonSupport.fromJson(parser);
    reader.close();
  }

}
