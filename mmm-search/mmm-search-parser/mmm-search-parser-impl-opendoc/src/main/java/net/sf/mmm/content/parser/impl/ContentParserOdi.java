/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.impl;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.content.parser.api.ContentParser} interface for content of
 * the open-document image format (content with the mimetypes
 * "application/vnd.oasis.opendocument.image").
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentParserOdi extends AbstractContentParserOpenDoc {

  /** The mimetype. */
  public static final String KEY_MIMETYPE = "application/vnd.oasis.opendocument.image";

  /** The default extension. */
  public static final String KEY_EXTENSION = "odi";

  /**
   * The constructor.
   */
  public ContentParserOdi() {

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
