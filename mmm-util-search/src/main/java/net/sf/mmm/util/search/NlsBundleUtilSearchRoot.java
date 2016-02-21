/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.search;

import net.sf.mmm.util.nls.api.NlsBundle;
import net.sf.mmm.util.nls.api.NlsBundleMessage;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * This interface holds the {@link NlsBundle internationalized messages} for this module.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public interface NlsBundleUtilSearchRoot extends NlsBundle {

  /**
   * @see net.sf.mmm.util.search.api.SearchTimeoutException
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Your search query was canceled because it exceeded a given timeout! Please try to simplify, "
      + "specialize to match less hits, or try again later.")
  NlsMessage errorSearchTimeout();

}
