/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.impl.text;

import javax.inject.Singleton;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.content.parser.api.ContentParser} interface for text
 * (content with the mimetype "text/plain").
 * 
 * @see ContentParserTextMarkupAware
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Singleton
// @Named
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
  public String[] getRegistryKeysPrimary() {

    return new String[] { KEY_EXTENSION, KEY_MIMETYPE };
  }

}
