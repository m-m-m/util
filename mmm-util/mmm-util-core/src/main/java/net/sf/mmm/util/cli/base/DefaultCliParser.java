/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.base;

import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.nls.api.NlsMessageFactory;
import net.sf.mmm.util.value.api.GenericValueConverter;

/**
 * This is the default implementation of the
 * {@link net.sf.mmm.util.cli.api.CliParser} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.2
 */
public class DefaultCliParser extends AbstractCliParser {

  /**
   * The constructor.
   * 
   * @param state is the {@link #getState() state}.
   * @param cliState is the {@link CliState}.
   * @param stringUtil is the {@link StringUtil} instance.
   * @param converter is the {@link GenericValueConverter} instance.
   * @param nlsMessageFactory is the {@link NlsMessageFactory} instance.
   */
  public DefaultCliParser(Object state, CliState cliState, StringUtil stringUtil,
      GenericValueConverter<Object> converter, NlsMessageFactory nlsMessageFactory) {

    super(state, cliState, stringUtil, converter, nlsMessageFactory);
  }

}
