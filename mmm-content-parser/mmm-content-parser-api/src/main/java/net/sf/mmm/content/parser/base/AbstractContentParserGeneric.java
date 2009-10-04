/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.base;

import net.sf.mmm.util.lang.api.StringUtil;

/**
 * This is the abstract base implementation of {@link ContentParserGeneric}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractContentParserGeneric extends AbstractContentParserBase implements
    ContentParserGeneric {

  /**
   * The constructor.
   */
  public AbstractContentParserGeneric() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String[] getRegistryKeys() {

    return StringUtil.EMPTY_STRING_ARRAY;
  }

}
