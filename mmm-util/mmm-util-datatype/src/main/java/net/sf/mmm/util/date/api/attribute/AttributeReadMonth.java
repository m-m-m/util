/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.date.api.attribute;

import net.sf.mmm.util.date.api.Month;

/**
 * This interface gives read access to the {@link #getMonth() month} attribute of an object related to date.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.1
 */
public interface AttributeReadMonth {

  /**
   * @return the {@link Month} of the year.
   */
  Month getMonth();

}
