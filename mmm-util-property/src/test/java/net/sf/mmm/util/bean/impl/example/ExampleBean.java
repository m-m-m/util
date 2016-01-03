/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl.example;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.impl.BeanFactoryImpl;

/**
 * This is a {@link Bean} with {@link ExamplePropertyBean property accessors} as well as {@link ExamplePojo POJO getters
 * and setters}. Further it contains {@link #create()} as fabrication method.
 *
 * @author hohwille
 */
public interface ExampleBean extends ExamplePojoBean, ExamplePropertyBean {

  static ExampleBean create() {

    return BeanFactoryImpl.getInstance().create(ExampleBean.class);
  }

}
