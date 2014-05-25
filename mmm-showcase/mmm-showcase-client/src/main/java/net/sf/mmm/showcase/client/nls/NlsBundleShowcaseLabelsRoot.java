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
@SuppressWarnings("javadoc")
public interface NlsBundleShowcaseLabelsRoot extends NlsBundleWithLookup {

  @NlsBundleMessage("Vorname")
  NlsMessage firstName();

  @NlsBundleMessage("Nachname")
  NlsMessage lastName();

  @NlsBundleMessage("Email-Adresse")
  NlsMessage email();

  @NlsBundleMessage("Telefon-Nummer")
  NlsMessage phone();

}
