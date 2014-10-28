/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base.datatype.adapter;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.math.BigDecimal;
import java.util.Currency;

import net.sf.mmm.util.component.api.IocContainer;
import net.sf.mmm.util.component.impl.SpringContainerPool;
import net.sf.mmm.util.lang.api.Datatype;
import net.sf.mmm.util.lang.api.Direction;
import net.sf.mmm.util.lang.api.Password;
import net.sf.mmm.util.lang.api.SimpleDatatype;
import net.sf.mmm.util.lang.base.datatype.adapter.jackson.DatatypeObjectMapperFactory;

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
   * Test of customized {@link ObjectMapper} for {@link SimpleDatatype}s.
   *
   * @throws IOException if something goes wrong.
   */
  @Test
  public void testJsonSupportSimpleDatatype() throws IOException {

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

  /**
   * Test of customized {@link ObjectMapper} for standard {@link Enum} (that does not implement
   * {@link SimpleDatatype}).
   *
   * @throws IOException if something goes wrong.
   */
  @Test
  public void testJsonSupportStandardEnum() throws IOException {

    ObjectMapper mapper = getObjectMapper();
    assertSame(ElementType.ANNOTATION_TYPE, mapper.readValue("\"AnnotationType\"", ElementType.class));
    assertEquals("\"LocalVariable\"", mapper.writeValueAsString(ElementType.LOCAL_VARIABLE));
  }

  /**
   * Test of customized {@link ObjectMapper} for composite {@link Datatype}s.
   *
   * @throws IOException if something goes wrong.
   */
  @Test
  public void testJsonSupportCompositeDatatype() throws IOException {

    ObjectMapper mapper = getObjectMapper();
    String amountString = "42.42";
    BigDecimal amount = new BigDecimal(amountString);
    String currencyString = "EUR";
    Currency currency = Currency.getInstance(currencyString);
    SampleComposedDatatype datatype = new SampleComposedDatatype(amount, currency);
    String json = "{\"amount\":" + amountString + ",\"currency\":\"" + currencyString + "\"}";
    assertEquals(json, mapper.writeValueAsString(datatype));
    assertEquals(datatype, mapper.readValue(json, SampleComposedDatatype.class));
  }

}
