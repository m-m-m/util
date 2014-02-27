/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

import net.sf.mmm.util.lang.api.attribute.AttributeReadId;
import net.sf.mmm.util.pojo.path.api.TypedProperty;

/**
 * This interface gives read access to a {@link #getId() ID}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadHtmlId extends AttributeReadId<String> {

  /** {@link TypedProperty} for {@link #getId()}. */
  TypedProperty<String> PROPERTY_ID = new TypedProperty<String>(String.class, "id");

  /** The name of the <code>id</code> attribute. */
  String HTML_ATTRIBUTE_ID = "id";

  /** Use this character to compose hierarchical {@link #getId() IDs}. */
  String ID_SEPARATOR = "_";

  /**
   * This method gets the unique identifier of this object.<br/>
   * <b>ATTENTION:</b><br>
   * In order to be compliant with all possible UI toolkit implementations, a valid ID has to fulfill
   * ECMA-262, Section 7.6. It is recommended to use IDs of the form <code>[a-zA-Z][a-zA-Z0-9_$]*</code>.
   * Invalid IDs (e.g. containing colon, hash or period) are not accepted to prevent you from later having
   * problems with CSS selectors.
   * 
   * @see #ID_SEPARATOR
   * 
   * @return the ID of this object.
   */
  @Override
  String getId();

}
