/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api.attribute;

/**
 * This is the abstract interface for an object that has an {@link #getTitle() title}.
 * 
 * @param <TITLE> is the generic type of the {@link #getTitle() title}. The type will typically be {@link String}
 *        but can also be something else (e.g. {@link net.sf.mmm.util.nls.api.NlsMessage}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public abstract interface AttributeReadTitle<TITLE> {

  /**
   * This method gets the title of this object.<br>
   * <b>NOTE:</b><br>
   * For generic usage you need to use {@link Object#toString()} of the result if NOT <code>null</code>
   * 
   * @return the title or <code>null</code> if not set. The {@link Object#toString() string-representation} of
   *         the result (if NOT <code>null</code>) needs to be suitable for end-users.
   */
  TITLE getTitle();

}
