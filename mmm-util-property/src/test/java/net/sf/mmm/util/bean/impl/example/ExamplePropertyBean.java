/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl.example;

import javax.inject.Named;

import javafx.geometry.Orientation;
import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.api.IntegerProperty;
import net.sf.mmm.util.property.api.WritableBooleanProperty;
import net.sf.mmm.util.property.api.WritableIntegerProperty;
import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.property.api.WritableStringProperty;
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

  default WritableIntegerProperty Age() {

    return new IntegerProperty(null, null).withValdidator().mandatory().size(0, 200).and().build();
  }

  WritableBooleanProperty Friend();

  WritableProperty<Orientation> Orientation();

}
