/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

import net.sf.mmm.util.lang.api.concern.Accessibility;
import net.sf.mmm.util.pojo.path.api.TypedProperty;

/**
 * This interface gives read access to the {@link #getSummary() summary} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadSummary extends Accessibility {

  /** {@link TypedProperty} for {@link #getSummary()}. */
  TypedProperty<String> PROPERTY_SUMMARY = new TypedProperty<String>(String.class, "summary");

  /**
   * This method gets the <em>summary</em> of this object. It describes the main purpose of this object in a
   * short and compact way. A summary might not be visible but is honored by assistive technology such as
   * screen-readers.
   * 
   * @return the text. Will be the empty string if no text has been set.
   */
  String getSummary();

}
