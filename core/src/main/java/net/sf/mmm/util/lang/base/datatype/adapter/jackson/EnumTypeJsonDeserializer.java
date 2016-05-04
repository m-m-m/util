/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base.datatype.adapter.jackson;

import java.io.IOException;

import javax.inject.Inject;

import net.sf.mmm.util.lang.api.EnumProvider;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * This is a generic implementation of {@link JsonDeserializer} based on {@link EnumProvider} so every
 * {@link Enum} and {@link net.sf.mmm.util.lang.api.EnumType} supported by {@link EnumProvider} can be
 * deserialized from JSON.
 *
 * @see DatatypeJsonSerializer
 *
 * @param <T> is the generic type of the {@link net.sf.mmm.util.lang.api.Datatype} to serialize.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 6.0.0
 */
public class EnumTypeJsonDeserializer<T> extends JsonDeserializer<T> {

  /** The class reflecting the datatype to deserialize. */
  private final Class<T> enumType;

  private  EnumProvider enumProvider;

  /**
   * The constructor.
   *
   * @param datatype is the class reflecting the datatype to deserialize.
   */
  public EnumTypeJsonDeserializer(Class<T> datatype) {

    super();
    this.enumType = datatype;
  }

  /**
   * The constructor.
   *
   * @param datatype is the class reflecting the datatype to deserialize.
   * @param enumProvider is the {@link EnumProvider} instance to use.
   */
  public EnumTypeJsonDeserializer(Class<T> datatype, EnumProvider enumProvider) {

    super();
    this.enumType = datatype;
    this.enumProvider = enumProvider;
  }

  /**
   * @param enumProvider is the {@link EnumProvider} to {@link Inject}.
   */
  @Inject
  public void setEnumProvider(EnumProvider enumProvider) {

    this.enumProvider = enumProvider;
  }

  @Override
  public T deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {

    String value = parser.getText();
    return this.enumProvider.getEnumValue(this.enumType, value, true);
  }

}
