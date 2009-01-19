/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.parser.impl;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.search.parser.api.ContentParser} interface for text
 * (content with the mimetype "text/plain").
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentParserText extends AbstractContentParserText {

  /** The mimetype. */
  public static final String KEY_MIMETYPE = "text/plain";

  /** The default extension. */
  public static final String KEY_EXTENSION = "txt";

  /**
   * The constructor.
   */
  public ContentParserText() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String[] getRegistryKeys() {

    return new String[] { KEY_EXTENSION, KEY_MIMETYPE };
  }

}
