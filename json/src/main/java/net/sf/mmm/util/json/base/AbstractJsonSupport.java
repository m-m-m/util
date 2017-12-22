/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.json.base;

import net.sf.mmm.util.json.api.JsonSupport;

/**
 * This is the abstract base implementation of {@link JsonSupport}.
 *
 * @author hohwille
 * @since 7.4.0
 */
public abstract class AbstractJsonSupport implements JsonSupport {

  @Override
  public String toJson() {

    return JsonSupportHelper.toJson(this);
  }

  @Override
  public void fromJson(String json) {

    JsonSupportHelper.fromJson(json, this);
  }

  @Override
  public String toString() {

    return toJson();
  }

}
