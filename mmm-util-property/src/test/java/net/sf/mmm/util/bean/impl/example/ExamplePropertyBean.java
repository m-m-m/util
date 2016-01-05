/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl.example;

import javafx.geometry.Orientation;
import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.api.BooleanProperty;
import net.sf.mmm.util.property.api.GenericProperty;
import net.sf.mmm.util.property.api.IntegerProperty;
import net.sf.mmm.util.property.api.StringProperty;
import net.sf.mmm.util.property.impl.IntegerPropertyImpl;
import net.sf.mmm.util.validation.base.Mandatory;

/**
 * TODO: this class ...
 *
 * @author hohwille
 * @since 7.1.0
 */
public interface ExamplePropertyBean extends Bean {

  CountryCodeProperty CountryCode();

  @Mandatory
  StringProperty Name();

  default IntegerProperty Age() {

    return new IntegerPropertyImpl(null, null).withValdidator().mandatory().size(0, 200).and().build();
  }

  BooleanProperty Friend();

  GenericProperty<Orientation> Orientation();

}
