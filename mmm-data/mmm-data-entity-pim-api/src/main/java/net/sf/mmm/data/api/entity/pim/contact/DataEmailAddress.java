/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.pim.contact;

import net.sf.mmm.data.api.entity.DataEntityClassified;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DataEmailAddress extends DataEntityClassified {

  /**
   * This method will return the same result as {@link #getEmailAddress()}.<br/>
   * 
   * {@inheritDoc}
   */
  String getTitle();

  /**
   * This method gets the email address.
   * 
   * @return the email address or <code>null</code> if undefined.
   */
  String getEmailAddress();

}
