/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl.example;

import java.util.Objects;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.CustomEquals;
import net.sf.mmm.util.bean.api.CustomHashCode;
import net.sf.mmm.util.property.api.lang.IntegerProperty;

/**
 * TODO: this class ...
 *
 * @author hohwille
 */
public interface CustomEqHcBean extends Bean, CustomHashCode, CustomEquals<CustomEqHcBean> {

  IntegerProperty Value();

  @Override
  default int hash() {

    return -Value().get();
  }

  @Override
  default boolean isEqualTo(CustomEqHcBean other) {

    if (other == null) {
      return false;
    }
    return Objects.equals(Value().getValue(), other.Value().getValue());
  }

}
