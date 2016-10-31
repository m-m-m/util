/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl.id;

import java.io.IOException;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import net.sf.mmm.util.bean.impl.example.ExamplePojoBean;
import net.sf.mmm.util.data.api.id.GenericId;
import net.sf.mmm.util.data.api.id.Id;

/**
 * Test-case of {@link IdJsonSerializer} and {@link GenericIdJsonDeserializer}.
 *
 * @author hohwille
 */
public class GenericIdJacksonTest extends Assertions {

  protected ObjectMapper getObjectMapper() {

    ObjectMapper mapper = new ObjectMapper();
    SimpleModule module = new SimpleModule("test");
    module.addSerializer(new IdJsonSerializer());
    GenericIdJsonDeserializer idDeserializer = new GenericIdJsonDeserializer();
    module.addDeserializer(Id.class, idDeserializer);
    module.addDeserializer(GenericId.class, idDeserializer);
    mapper.registerModule(module);
    return mapper;
  }

  /**
   * Test serialize {@link GenericId} to and from JSON.
   */
  @Test
  public void testJson() throws IOException {

    // given
    long id = 4711;
    long version = 1;
    GenericId<?> genericId = GenericId.of(MyBean.class, id, version);
    MyBean bean = new MyBean();
    bean.setId(genericId);
    long fkId = 4712;
    GenericId<ExamplePojoBean> foreignKey = GenericId.of(ExamplePojoBean.class, fkId);
    bean.setForeignKey(foreignKey);

    ObjectMapper mapper = getObjectMapper();

    // when
    String json = mapper.writeValueAsString(bean);
    MyBean beanFromJson = mapper.readValue(json, MyBean.class);

    // then
    assertThat(json).isEqualTo("{\"id\":\"4711@1\",\"foreignKey\":\"4712\"}");
    assertThat(beanFromJson.getId()).isEqualTo(genericId);
    assertThat(beanFromJson.getForeignKey()).isEqualTo(foreignKey);
  }

  @SuppressWarnings("javadoc")
  public static class MyBean {

    private Id<?> id;

    private Id<ExamplePojoBean> foreignKey;

    public Id<?> getId() {

      return this.id;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void setId(Id<?> id) {

      this.id = ((Id) id).withType(getClass());
    }

    public Id<ExamplePojoBean> getForeignKey() {

      return this.foreignKey;
    }

    public void setForeignKey(Id<ExamplePojoBean> foreignKey) {

      this.foreignKey = foreignKey.withType(ExamplePojoBean.class);
    }

  }

}
