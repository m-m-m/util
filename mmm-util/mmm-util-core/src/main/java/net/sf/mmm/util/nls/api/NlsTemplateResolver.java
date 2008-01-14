/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

/**
 * This is the callback interface for translating a text-message to a
 * {@link java.util.Locale}-specific language.<br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface NlsTemplateResolver {

  /**
   * This method is used to create the {@link NlsTemplate} from information that
   * is found by the given <code>internationalizedMessage</code>.<br>
   * This may happen via a reverse lookup from
   * {@link net.sf.mmm.util.nls.AbstractResourceBundle}(s).
   * 
   * @param internationalizedMessage is the
   *        {@link NlsMessage#getInternationalizedMessage() internationalized message}.
   * @return the according {@link NlsTemplate} or <code>null</code> if this
   *         implementation failed to resolve the according template.
   */
  NlsTemplate resolveTemplate(String internationalizedMessage);

}
