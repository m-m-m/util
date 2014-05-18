/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.showcase.client.nls;

import net.sf.mmm.util.nls.api.NlsBundleMessage;
import net.sf.mmm.util.nls.api.NlsBundleWithLookup;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * This is a demo bundle for the current state.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface NlsBundleShowcaseLabelsRoot extends NlsBundleWithLookup {

  /**
   * @return the label for the first name field.
   */
  @NlsBundleMessage("Vorname1")
  NlsMessage firstName();

  /**
   * @return the label for the last name field.
   */
  @NlsBundleMessage("Nachname1")
  NlsMessage lastName();

}
