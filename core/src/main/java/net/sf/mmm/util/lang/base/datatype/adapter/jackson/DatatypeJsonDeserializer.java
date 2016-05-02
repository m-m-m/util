/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base.datatype.adapter.jackson;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Currency;
import java.util.List;

import javax.inject.Inject;

import net.sf.mmm.util.lang.api.DatatypeDescriptor;
import net.sf.mmm.util.lang.api.DatatypeDescriptorManager;
import net.sf.mmm.util.lang.api.DatatypeSegmentDescriptor;
import net.sf.mmm.util.value.api.ValueNotSetException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * This is a generic implementation of {@link JsonDeserializer} based on {@link DatatypeDescriptorManager} so
 * every {@link net.sf.mmm.util.lang.api.Datatype} supported by {@link DatatypeDescriptorManager} can be
 * deserialized from JSON.
 *
 * @see DatatypeJsonSerializer
 *
 * @param <T> is the generic type of the {@link net.sf.mmm.util.lang.api.Datatype} to serialize.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 6.0.0
 */
public class DatatypeJsonDeserializer<T> extends JsonDeserializer<T> {

  /** The class reflecting the datatype to deserialize. */
  private final Class<T> datatype;

  /** @see #setDatatypeDescriptorManager(DatatypeDescriptorManager) */
  private DatatypeDescriptorManager datatypeDescriptorManager;

  /**
   * The constructor.
   *
   * @param datatype is the class reflecting the datatype to deserialize.
   */
  public DatatypeJsonDeserializer(Class<T> datatype) {

    super();
    this.datatype = datatype;
  }

  /**
   * The constructor.
   *
   * @param datatype is the class reflecting the datatype to deserialize.
   * @param datatypeDescriptorManager is the {@link DatatypeDescriptorManager} instance to use.
   */
  public DatatypeJsonDeserializer(Class<T> datatype, DatatypeDescriptorManager datatypeDescriptorManager) {

    super();
    this.datatype = datatype;
    this.datatypeDescriptorManager = datatypeDescriptorManager;
  }

  /**
   * @param datatypeDescriptorManager is the {@link DatatypeDescriptorManager} to {@link Inject}.
   */
  @Inject
  public void setDatatypeDescriptorManager(DatatypeDescriptorManager datatypeDescriptorManager) {

    this.datatypeDescriptorManager = datatypeDescriptorManager;
  }

  /**
   * Gets and converts the specified value from the given {@link JsonNode}.
   *
   * @param <V> is the generic type of the requested value.
   * @param node is the {@link JsonNode} containing the value as child.
   * @param fieldName is the {@link JsonNode#get(String) field name} of the requested value.
   * @param type is the {@link Class} reflecting the requested type to convert to.
   * @param required - {@code true} if the value is mandatory and an exception will be thrown if the
   *        value is not present, {@code false} otherwise.
   * @return the requested and converted value. May be {@code null} if not set and {@code required}
   *         is {@code false}.
   */
  protected <V> V getValue(JsonNode node, String fieldName, Class<V> type, boolean required) {

    JsonNode childNode = node.get(fieldName);
    if ((childNode == null) || childNode.isNull()) {
      if (required) {
        throw new ValueNotSetException(fieldName);
      } else {
        return null;
      }
    }
    return getValue(childNode, type);
  }

  /**
   * Gets and converts the value of the given {@link JsonNode}.
   *
   * @param <V> is the generic type of the requested value.
   * @param valueNode is the {@link JsonNode} containing the value.
   * @param type is the {@link Class} reflecting the requested type to convert to.
   * @return the requested and converted value. May be {@code null} if not set.
   */
  @SuppressWarnings("unchecked")
  protected <V> V getValue(JsonNode valueNode, Class<V> type) {

    Object value = null;
    try {
      if (type == String.class) {
        value = valueNode.asText();
      } else if (type == BigDecimal.class) {
        if (valueNode.isNumber()) {
          value = valueNode.decimalValue();
        } else {
          value = new BigDecimal(valueNode.asText());
        }
      } else if (type == BigInteger.class) {
        if (valueNode.isNumber()) {
          value = valueNode.bigIntegerValue();
        } else {
          value = new BigInteger(valueNode.asText());
        }
      } else if (type == Boolean.class) {
        if (valueNode.isBoolean()) {
          value = Boolean.valueOf(valueNode.booleanValue());
        } else {
          value = Boolean.valueOf(valueNode.asText());
        }
      } else if (type == Integer.class) {
        if (valueNode.isInt()) {
          value = Integer.valueOf(valueNode.intValue());
        } else {
          value = Integer.valueOf(valueNode.asText());
        }
      } else if (type == Long.class) {
        if (valueNode.isLong()) {
          value = Long.valueOf(valueNode.longValue());
        } else {
          value = Long.valueOf(valueNode.asText());
        }
      } else if (type == Double.class) {
        if (valueNode.isDouble()) {
          value = Double.valueOf(valueNode.doubleValue());
        } else {
          value = Double.valueOf(valueNode.asText());
        }
      } else if (type == Float.class) {
        if (valueNode.isFloat()) {
          value = Float.valueOf(valueNode.floatValue());
        } else {
          value = Float.valueOf(valueNode.asText());
        }
      } else if (type == Short.class) {
        if (valueNode.isShort()) {
          value = Short.valueOf(valueNode.shortValue());
        } else {
          value = Short.valueOf(valueNode.asText());
        }
      } else if (type == Character.class) {
        String text = valueNode.asText();
        if (text.length() > 1) {
          text = text.trim();
        }
        if (text.length() == 1) {
          value = Character.valueOf(text.charAt(0));
        }
      } else if (type == Currency.class) {
        value = Currency.getInstance(valueNode.asText());
      } else {
        throw new IllegalArgumentException("Unsupported value type " + type.getName());
      }
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Failed to convert value to type " + type.getName(), e);
    }
    return (V) value;
  }

  /**
   * Generic and recursive implementation of {@link #deserialize(JsonParser, DeserializationContext)}.
   *
   * @param <V> is the generic type of the expected value to deserialize.
   * @param parser is the {@link JsonParser}.
   * @param context is the {@link DeserializationContext}.
   * @param type is the {@link Class} reflecting the expected type of the value to deserialize.
   * @param parentNode is the {@link JsonNode} that has already been parsed (recursive call) or
   *        {@code null} if the value has to be read from {@link JsonParser}.
   * @param segment is the {@link DatatypeSegmentDescriptor} with the segment to deserialize as child of
   *        parentNode (recursive call) or {@code null} if the value has to be read from
   *        {@link JsonParser}.
   * @return the deserialized value.
   * @throws IOException if reading from {@link JsonParser} causes an I/O problem.
   */
  public <V> V deserializeGeneric(JsonParser parser, DeserializationContext context, Class<V> type,
      JsonNode parentNode, DatatypeSegmentDescriptor<?, ?> segment) throws IOException {

    DatatypeDescriptor<V> descriptor = this.datatypeDescriptorManager.getDatatypeDescriptor(type);
    List<DatatypeSegmentDescriptor<V, ?>> segmentList = descriptor.getSegmentDescriptors();

    int segmentCount = segmentList.size();
    if (segmentCount == 0) {
      if (parentNode != null) {
        return getValue(parentNode, segment.getName(), type, !segment.isOptional());
      } else {
        return parser.readValueAs(type);
      }
    } else if (segmentCount == 1) {
      DatatypeSegmentDescriptor<V, ?> childSegment = segmentList.get(0);
      Object value = deserializeGeneric(parser, context, childSegment.getType(), parentNode, segment);
      return descriptor.create(value);
    } else {
      JsonNode node;
      if (parentNode == null) {
        node = parser.getCodec().readTree(parser);
      } else {
        node = parentNode.get(segment.getName());
      }
      Object[] values = new Object[segmentCount];
      for (int i = 0; i < segmentCount; i++) {
        DatatypeSegmentDescriptor<V, ?> childSegment = segmentList.get(i);
        values[i] = deserializeGeneric(parser, context, childSegment.getType(), node, childSegment);
      }
      return descriptor.create(values);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public T deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {

    return deserializeGeneric(parser, context, this.datatype, null, null);
  }

}
