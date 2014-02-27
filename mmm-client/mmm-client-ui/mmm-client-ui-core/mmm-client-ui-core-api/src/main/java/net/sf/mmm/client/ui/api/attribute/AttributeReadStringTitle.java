/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

import net.sf.mmm.util.lang.api.attribute.AttributeReadTitle;
import net.sf.mmm.util.pojo.path.api.TypedProperty;

/**
 * This interface gives read access to the {@link #getTitle() title} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadStringTitle extends AttributeReadTitle<String> {

  /** {@link TypedProperty} for {@link #getTitle()}. */
  TypedProperty<String> PROPERTY_TITLE = new TypedProperty<String>(String.class, "title");

  /**
   * This method gets the title of this object. The detailed meaning of the title depends on the type of
   * object. E.g. a window will have the title in the top-level bar.
   * 
   * @return the title.
   */
  @Override
  String getTitle();

}
