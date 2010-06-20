/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.base;

import net.sf.mmm.util.cli.api.CliArgument;
import net.sf.mmm.util.cli.api.CliOption;
import net.sf.mmm.util.collection.api.CollectionFactoryManager;
import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.api.NlsMessageFactory;
import net.sf.mmm.util.reflect.api.CollectionReflectionUtil;
import net.sf.mmm.util.value.api.GenericValueConverter;

/**
 * This is the default implementation of the
 * {@link net.sf.mmm.util.cli.api.CliParser} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class DefaultCliParser extends AbstractCliParser {

  /**
   * The constructor.
   * 
   * @param state is the {@link #getState() state}.
   * @param cliState is the {@link CliState}.
   * @param stringUtil is the {@link StringUtil} instance.
   * @param collectionReflectionUtil is the {@link CollectionReflectionUtil}
   *        instance.
   * @param collectionFactoryManager is the {@link CollectionFactoryManager}
   *        instance.
   * @param converter is the {@link GenericValueConverter} instance used to
   *        convert values for {@link CliOption options} or {@link CliArgument
   *        arguments}.
   * @param nlsMessageFactory is the {@link NlsMessageFactory} used to create
   *        instances of {@link NlsMessage}.
   */
  public DefaultCliParser(Object state, CliState cliState, CliParserConfiguration configuration) {

    super(state, cliState, configuration);
  }

}
