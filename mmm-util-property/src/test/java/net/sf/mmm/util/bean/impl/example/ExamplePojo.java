/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl.example;

import net.sf.mmm.util.lang.api.Orientation;
import net.sf.mmm.util.validation.base.Mandatory;

/**
 * TODO: this class ...
 *
 * @author hohwille
 * @since 7.1.0
 */
public interface ExamplePojo {

  String getCountryCode();

  void setCountryCode(String countryCode);

  String getName();

  void setName(String name);

  boolean isFriend();

  void setFriend(boolean friend);

  @Mandatory
  Orientation getOrientation();

  void setOrientation(Orientation orientation);

}
