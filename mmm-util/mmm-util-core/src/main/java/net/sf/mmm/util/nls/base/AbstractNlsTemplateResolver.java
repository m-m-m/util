/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import net.sf.mmm.util.nls.api.NlsTemplateResolver;

/**
 * This is the abstract base implementation of the {@link NlsTemplateResolver}
 * interface.<br>
 * You should extend this class rather than directly implementing the
 * {@link NlsTemplateResolver} interface to gain compatibility with further
 * releases.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractNlsTemplateResolver implements NlsTemplateResolver {

  /**
   * The constructor.<br>
   */
  public AbstractNlsTemplateResolver() {

    super();
  }

}
