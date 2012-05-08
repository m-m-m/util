/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import net.sf.mmm.util.nls.api.NlsTemplate;
import net.sf.mmm.util.nls.api.NlsTemplateResolver;

/**
 * This is a dummy implementation of the {@link NlsTemplateResolver} interface that performs no resolving
 * (always returns <code>null</code> for {@link #resolveTemplate(String)}).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class NoTemplateResolver extends AbstractNlsTemplateResolver {

  /** The singleton instance. */
  public static final NlsTemplateResolver INSTANCE = new NoTemplateResolver();

  /**
   * The constructor.
   */
  public NoTemplateResolver() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public NlsTemplate resolveTemplate(String internationalizedMessage) {

    return null;
  }

}
