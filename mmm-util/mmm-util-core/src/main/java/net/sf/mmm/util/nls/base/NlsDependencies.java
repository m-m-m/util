/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import net.sf.mmm.util.date.api.Iso8601UtilLimited;
import net.sf.mmm.util.nls.api.NlsArgumentParser;
import net.sf.mmm.util.nls.api.NlsMessageFormatterFactory;

/**
 * This interface bundles the dependencies for the native language support (NLS). It contains the required
 * components.
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
   * This method gets the {@link NlsMessageFormatterFactory}.
   * 
   * @return the {@link NlsMessageFormatterFactory}.
   */
  NlsMessageFormatterFactory getMessageFormatterFactory();

  /**
   * This method gets the {@link Iso8601UtilLimited}.
   * 
   * @return the {@link Iso8601UtilLimited}.
   */
  Iso8601UtilLimited getIso8601Util();

}
