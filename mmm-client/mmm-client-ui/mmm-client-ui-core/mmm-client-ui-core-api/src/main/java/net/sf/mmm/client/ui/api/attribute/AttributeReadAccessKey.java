/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

import net.sf.mmm.util.lang.api.concern.Accessibility;

/**
 * This interface gives read access to the {@link #getAccessKey() accessKey} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadAccessKey extends Accessibility {

  /** The name of the <code>accesskey</code> attribute. */
  String HTML_ATTRIBUTE_ACCESS_KEY = "accesskey";

  /** The {@link #getAccessKey() access key} value if no access key is defined. */
  char ACCESS_KEY_NONE = '\0';

  /**
   * This method gets the <em>access key</em> of this object. If the access key is pressed together with the
   * [shift] and [alt] keys this object will be invoked. Here <em>invoked</em> means the same as if the
   * end-user would click on the object. In case of an action item (button, link, etc.) it is invoked,
   * otherwise it will get the {@link AttributeReadFocused#isFocused() focus}.
   * 
   * @return the access key. Will be {@link #ACCESS_KEY_NONE} if no access key is defined.
   */
  char getAccessKey();

}
