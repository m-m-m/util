/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.json.api;

import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;

/**
 * This is the interface for an object that supports to {@link #toJson(JsonGenerator) serialize} and
 * {@link #fromJson(JsonParser) deserialize} itself to JSON.
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface JsonSupport {

  /** The JSON property name for the type information for polymorphic mapping of {@link #getClass()}. */
  String PROPERTY_TYPE = "@type";

  /**
   * Serializes this object as JSON (JavaScript-Object-Notation).
   *
   * @see JsonUtil#write(JsonGenerator, String, Object)
   *
   * @param json the {@link JsonGenerator} where to write this object to. Has to be in
   *        {@link JsonGenerator#writeStartObject()} state.
   */
  void toJson(JsonGenerator json);

  /**
   * Deserializes this object from JSON (JavaScript-Object-Notation). This operation is typically NOT symmetric to
   * {@link #toJson(JsonGenerator)} - e.g. {@link #toJson(JsonGenerator)} might write a property with its value while
   * {@link #fromJson(JsonParser)} may only deserialze the value as the parent object has to handle the property names
   * that may appear in any order.
   *
   * @param json the {@link JsonParser} to read from.
   */
  void fromJson(JsonParser json);

}
