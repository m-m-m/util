/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import net.sf.mmm.util.date.api.Iso8601Util;
import net.sf.mmm.util.nls.api.NlsArgumentParser;

/**
 * This interface bundles the dependencies for the native language support
 * (NLS). It contains the required components.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public interface NlsDependencies {

  /**
   * This method gets the {@link NlsArgumentParser}.
   * 
   * @return the {@link NlsArgumentParser}.
   */
  NlsArgumentParser getArgumentParser();

  /**
   * This method gets the {@link NlsArgumentFormatter}.
   * 
   * @return the {@link NlsArgumentFormatter}.
   */
  NlsArgumentFormatter getArgumentFormatter();

  /**
   * This method gets the {@link Iso8601Util}.
   * 
   * @return the {@link Iso8601Util}.
   */
  Iso8601Util getIso8601Util();

}
