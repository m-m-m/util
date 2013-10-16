/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.binding;

import net.sf.mmm.util.nls.api.NlsBundleLocation;
import net.sf.mmm.util.nls.api.NlsBundleMessage;
import net.sf.mmm.util.nls.api.NlsBundleWithLookup;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @deprecated - we need to have a general component in UiConfiguration or the like that is used to lookup
 *             labels.
 */
@Deprecated
@NlsBundleLocation(bundleName = "NlsBundleLabels")
public interface NlsBundleLabelsRoot extends NlsBundleWithLookup {

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
