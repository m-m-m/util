/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl;

import org.junit.Test;

import net.sf.mmm.test.ObjectHelper;
import net.sf.mmm.util.bean.api.BeanFactory;
import net.sf.mmm.util.bean.api.CustomEquals;
import net.sf.mmm.util.bean.api.CustomHashCode;
import net.sf.mmm.util.bean.impl.example.CustomEqHcBean;
import net.sf.mmm.util.bean.impl.example.ExampleBean;
import net.sf.mmm.util.bean.impl.example.ExamplePojoBean;
import net.sf.mmm.util.bean.impl.example.ExamplePropertyBean;
import net.sf.mmm.util.bean.impl.example.ExtendedExampleBean;

/**
 * This is the test-case for {@link BeanFactoryImpl}.
 *
 * @author hohwille
 */
public class BeanFactoryImplTest extends AbstractBeanTest {

  @Test
  public void testExamplePropertyBean() {

    BeanFactory beanFactory = getBeanFactory();
    ExamplePropertyBean bean = beanFactory.create(ExamplePropertyBean.class);
    verifyBean(bean, beanFactory);
    verifyExamplePropertyBean(bean, beanFactory);
    assertThat(bean.access().isDynamic()).isFalse();
    assertThat(bean.access().getBeanClass()).isEqualTo(ExamplePropertyBean.class);
    assertThat(bean.access().getSimpleName()).isEqualTo("RenamedBean");
    assertThat(bean.access().getDeclaredPropertyNames()).containsOnly("CountryCode", "Name", "Age", "Friend",
        "Orientation");
  }

  @Test
  public void testExamplePojoBean() {

    BeanFactory beanFactory = getBeanFactory();
    ExamplePojoBean bean = beanFactory.create(ExamplePojoBean.class);
    verifyBean(bean, beanFactory);
    verifyExamplePojoBean(bean);
  }

  @Test
  public void testExtendedExampleBean() {

    BeanFactory beanFactory = getBeanFactory();
    ExtendedExampleBean bean = beanFactory.create(ExtendedExampleBean.class);
    verifyBean(bean, beanFactory);
    verifyExamplePojoBean(bean);
    verifyExamplePropertyBean(bean, beanFactory);
    assertThat(bean.access().isDynamic()).isFalse();
    assertThat(bean.access().getDeclaredPropertyNames()).containsOnly("ExtraProperty");
  }

  @Test
  public void testExampleBean() {

    BeanFactory beanFactory = getBeanFactory();
    ExampleBean bean = beanFactory.create(ExampleBean.class);
    verifyBean(bean, beanFactory);
    verifyExamplePojoBean(bean);
    verifyExamplePropertyBean(bean, beanFactory);
    assertThat(bean.access().isDynamic()).isFalse();

    assertThat(bean.self()).isSameAs(bean);
    assertThat(bean.sayHi("Peter")).isEqualTo("Hi Peter");
  }

  /**
   * Test of {@link CustomEquals} and {@link CustomHashCode}.
   */
  @Test
  public void testCustomEqualsAndHashCode() {

    BeanFactory beanFactory = getBeanFactory();
    CustomEqHcBean bean1 = beanFactory.create(CustomEqHcBean.class);
    int magic = 42;
    bean1.Value().set(magic);
    assertThat(bean1.hashCode()).isEqualTo(-magic).isEqualTo(bean1.hash());
    CustomEqHcBean bean2 = beanFactory.copy(bean1);
    ObjectHelper.checkEqualsAndHashCode(bean1, bean2, true);
  }

}
