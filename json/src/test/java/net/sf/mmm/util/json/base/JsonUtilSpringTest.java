/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.json.base;

import javax.inject.Inject;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.sf.mmm.util.json.api.JsonUtil;
import net.sf.mmm.util.json.impl.spring.UtilJsonSpringConfig;

/**
 * This is the test of {@link JsonUtil} using spring.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UtilJsonSpringConfig.class })
public class JsonUtilSpringTest extends JsonUtilTest {

  @Inject
  private JsonUtil jsonUtil;

  @Override
  protected JsonUtil getJsonUtil() {

    return this.jsonUtil;
  }

}
