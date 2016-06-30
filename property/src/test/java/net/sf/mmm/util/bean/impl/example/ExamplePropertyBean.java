/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl.example;

import javax.inject.Inject;
import javax.inject.Named;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.lang.api.Orientation;
import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.property.api.lang.BooleanProperty;
import net.sf.mmm.util.property.api.lang.WritableStringProperty;
import net.sf.mmm.util.property.api.math.IntegerProperty;
import net.sf.mmm.util.property.api.math.WritableIntegerProperty;
import net.sf.mmm.util.validation.base.Mandatory;

/**
 * TODO: this class ...
 *
 * @author hohwille
 */
@Named("RenamedBean")
public interface ExamplePropertyBean extends Bean {

  CountryCodeProperty CountryCode();

  @Mandatory
  @Named("Alias")
  WritableStringProperty Name();

  @Inject
  default WritableIntegerProperty Age() {

    return new IntegerProperty(null, null).withValdidator().mandatory().range(0, 200).and().build();
  }

  BooleanProperty Friend();

  WritableProperty<Orientation> Orientation();

}
