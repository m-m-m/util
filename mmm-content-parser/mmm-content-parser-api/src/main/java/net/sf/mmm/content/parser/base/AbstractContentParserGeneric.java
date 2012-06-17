/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.base;


/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.content.parser.api.ContentParserService#getGenericParser()
 * generic} {@link net.sf.mmm.content.parser.api.ContentParser}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractContentParserGeneric extends AbstractContentParser {

  /**
   * The constructor.
   */
  public AbstractContentParserGeneric() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public final String getExtension() {

    return null;
  }

  /**
   * {@inheritDoc}
   */
  public final String getMimetype() {

    return null;
  }

}
