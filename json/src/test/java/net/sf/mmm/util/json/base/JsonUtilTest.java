/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.json.base;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import net.sf.mmm.util.json.api.JsonUtil;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.base.GenericTypeBuilder;

/**
 * This is the test-case for {@link JsonUtil}.
 */
public class JsonUtilTest extends Assertions {

  /**
   * @return the {@link JsonUtil} to test.
   */
  protected JsonUtil getJsonUtil() {

    return JsonUtilImpl.getInstance();
  }

  /** Test of {@link JsonUtil#expectEvent(Event, Event)}. */
  @Test
  public void testExpectEvent() {

    JsonUtil jsonUtil = getJsonUtil();
    jsonUtil.expectEvent(Event.KEY_NAME, Event.KEY_NAME);
    jsonUtil.expectEvent(Event.VALUE_STRING, Event.VALUE_STRING);
    try {
      jsonUtil.expectEvent(Event.KEY_NAME, Event.VALUE_STRING);
      failBecauseExceptionWasNotThrown(IllegalStateException.class);
    } catch (IllegalStateException e) {
      assertThat(e).hasMessageContaining(Event.KEY_NAME.name()).hasMessageContaining(Event.VALUE_STRING.toString());
    }
  }

  /**
   * Test of {@link JsonUtil} when reading (parsing) a {@literal Map<Long, List<String>>} from JSON and
   * writing back to JSON.
   */
  @Test
  public void testMapLong2ListString() {

    // given
    JsonUtil jsonUtil = getJsonUtil();
    String json = "{\"4711\":[\"foo\",\"bar\",\"zonk\"],\"42\":[\"abc\"]}";
    GenericType<Map<Long, List<String>>> type = new GenericTypeBuilder<Map<Long, List<String>>>() {}.build();

    // when
    JsonParser jsonParser = Json.createParser(new StringReader(json));
    Map<Long, List<String>> value = jsonUtil.read(jsonParser, type);

    // then
    assertThat(value).hasSize(2);
    List<String> list42 = value.get(Long.valueOf(42));
    assertThat(list42).containsExactly("abc");
    List<String> list4711 = value.get(Long.valueOf(4711));
    assertThat(list4711).containsExactly("foo", "bar", "zonk");

    // and given
    StringWriter writer = new StringWriter();
    JsonGenerator jsonGenerator = Json.createGenerator(writer);

    // and when
    jsonUtil.write(jsonGenerator, null, value);
    jsonGenerator.close();

    // and then
    String newJSon = writer.toString();
    assertThat(newJSon).isEqualTo(json);
  }

}
