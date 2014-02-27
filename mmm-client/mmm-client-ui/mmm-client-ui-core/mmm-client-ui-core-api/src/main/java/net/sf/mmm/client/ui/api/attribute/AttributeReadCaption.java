/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

import net.sf.mmm.util.pojo.path.api.TypedProperty;

/**
 * This interface gives read access to the {@link #getCaption() caption} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadCaption {

  /** {@link TypedProperty} for {@link #getCaption()}. */
  TypedProperty<String> PROPERTY_CAPTION = new TypedProperty<String>(String.class, "caption");

  /**
   * This method gets the <em>caption</em> of this object. A caption is similar to a
   * {@link AttributeReadStringTitle#getTitle() title}. In our APIs we prefer to use the
   * {@link AttributeReadStringTitle#getTitle() title} but in specific contexts we use caption for technical
   * reasons - e.g. if the term is predefined by technology such as HTML.
   * 
   * @return the caption text. Will be the empty string if no label has been set.
   */
  String getCaption();

}
