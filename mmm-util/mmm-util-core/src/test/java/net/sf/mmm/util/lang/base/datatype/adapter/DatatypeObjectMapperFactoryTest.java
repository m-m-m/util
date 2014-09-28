/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base.datatype.adapter;

import java.io.IOException;

import net.sf.mmm.util.component.api.IocContainer;
import net.sf.mmm.util.component.impl.SpringContainerPool;
import net.sf.mmm.util.lang.api.Direction;
import net.sf.mmm.util.lang.api.Password;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This is the test-case for {@link DatatypeObjectMapperFactory}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 6.0.0
 */
public class DatatypeObjectMapperFactoryTest extends Assert {

  /**
   * @return an instance of {@link ObjectMapper} properly configured via {@link DatatypeObjectMapperFactory}.
   */
  protected ObjectMapper getObjectMapper() {

    IocContainer container = SpringContainerPool
        .getInstance("/net/sf/mmm/util/lang/base/datatype/adapter/beans-datatype-adapter-test.xml");
    return container.get(ObjectMapper.class);
  }

  /**
   * Test of customized {@link ObjectMapper}.
   *
   * @throws IOException if something goes wrong.
   */
  @Test
  public void testDatatypeJsonSupport() throws IOException {

    ObjectMapper mapper = getObjectMapper();
    assertSame(Direction.SOUTH, mapper.readValue("\"S\"", Direction.class));
    assertEquals("\"SW\"", mapper.writeValueAsString(Direction.SOUTH_WEST));
    // should typically not use Password in such context...
    String secret = "$ecreT42";
    Password password = new Password(secret);
    String json = '"' + secret + '"';
    assertEquals(password, mapper.readValue(json, Password.class));
    assertEquals(json, mapper.writeValueAsString(password));

  }

}
