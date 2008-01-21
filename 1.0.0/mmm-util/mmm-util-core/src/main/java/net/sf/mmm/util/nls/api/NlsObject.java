/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

/**
 * This is the interface for an object with native language support. Such object
 * be can {@link #toNlsMessage() converted} to an
 * {@link NlsMessage i18n-message} describing the object analog to its
 * {@link Object#toString() string representation}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface NlsObject {

  /**
   * This method is the equivalent to {@link Object#toString()} with native
   * language support.
   * 
   * @return an nls message representing this object.
   */
  NlsMessage toNlsMessage();

}
