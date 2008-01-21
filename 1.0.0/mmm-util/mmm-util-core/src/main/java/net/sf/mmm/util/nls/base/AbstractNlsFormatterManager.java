/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import net.sf.mmm.util.nls.api.NlsFormatter;
import net.sf.mmm.util.nls.api.NlsFormatterManager;

/**
 * This is the abstract base implementation of the {@link NlsFormatterManager}
 * interface.<br>
 * You should extend this class rather than directly implementing the
 * {@link NlsFormatterManager} interface to gain compatibility with further
 * releases.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractNlsFormatterManager implements NlsFormatterManager {

  /**
   * The constructor.
   */
  public AbstractNlsFormatterManager() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public NlsFormatter<Object> getFormatter() {

    return getFormatter(null);
  }

  /**
   * {@inheritDoc}
   */
  public NlsFormatter<Object> getFormatter(String formatType) {

    return getFormatter(formatType, null);
  }

}
