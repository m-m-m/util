/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl;

import org.junit.Test;

import net.sf.mmm.util.bean.api.BeanFactory;
import net.sf.mmm.util.bean.api.BeanPrototypeBuilder;
import net.sf.mmm.util.bean.impl.example.ExampleBean;
import net.sf.mmm.util.bean.impl.example.ExtendedExampleBean;
import net.sf.mmm.util.property.api.lang.StringProperty;
import net.sf.mmm.util.property.api.lang.WritableStringProperty;

/**
 * This is the test-case for {@link BeanPrototypeBuilderImpl}.
 *
 * @author hohwille
 */
public class BeanPrototypeBuilderTest extends AbstractBeanTest {

  @Test
  public void testExampleBean() {

    BeanFactory beanFactory = getBeanFactory();
    BeanPrototypeBuilder builder = beanFactory.createPrototypeBuilder(true);
    ExampleBean exampleBeanPrototype = builder.getOrCreatePrototype(ExampleBean.class);
    ExampleBean exampleBean = builder.create(exampleBeanPrototype);
    verifyExamplePropertyBean(exampleBean, builder);
    assertThat(exampleBean.access().isDynamic()).isTrue();
    ExtendedExampleBean extendedExampleBeanPrototype = builder.getOrCreatePrototype(ExtendedExampleBean.class);
    ExtendedExampleBean extendedExampleBean = builder.create(extendedExampleBeanPrototype);
    verifyExamplePropertyBean(extendedExampleBean, builder);
    assertThat(extendedExampleBean.access().isDynamic()).isTrue();

    String propertyName = "newProperty";
    WritableStringProperty stringProperty = exampleBeanPrototype.access().createProperty(propertyName,
        WritableStringProperty.class);
    assertThat(stringProperty).isInstanceOf(StringProperty.class);
    assertThat(exampleBeanPrototype.access().getProperty(propertyName)).isSameAs(stringProperty);
    assertThat(exampleBean.access().getProperty(propertyName)).isEqualTo(stringProperty);

    assertThat(extendedExampleBeanPrototype.access().getProperty(propertyName)).isEqualTo(stringProperty);
    assertThat(extendedExampleBean.access().getProperty(propertyName)).isEqualTo(stringProperty);
  }

}
