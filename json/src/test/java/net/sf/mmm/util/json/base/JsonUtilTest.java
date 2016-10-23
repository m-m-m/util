/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.json.base;

import javax.json.stream.JsonParser.Event;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import net.sf.mmm.util.exception.api.ObjectMismatchException;
import net.sf.mmm.util.json.api.JsonUtil;

/**
 * This is the test-case for {@link JsonUtil}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class JsonUtilTest extends Assertions {

  /**
   * @return the {@link JsonUtil} to test.
   */
  protected JsonUtil getJsonUtil() {

    return JsonUtilImpl.getInstance();
  }

  /** Test of basic functionality */
  @Test
  public void testTrivials() {

    JsonUtil jsonUtil = getJsonUtil();
    jsonUtil.expectEvent(Event.KEY_NAME, Event.KEY_NAME);
    jsonUtil.expectEvent(Event.VALUE_STRING, Event.VALUE_STRING);
    try {
      jsonUtil.expectEvent(Event.KEY_NAME, Event.VALUE_STRING);
      failBecauseExceptionWasNotThrown(ObjectMismatchException.class);
    } catch (ObjectMismatchException e) {
      assertThat(e).hasMessageContaining(Event.KEY_NAME.name());
      assertThat(e.getNlsMessage().getArgument("expected")).isEqualTo(Event.VALUE_STRING);
    }
  }

}
