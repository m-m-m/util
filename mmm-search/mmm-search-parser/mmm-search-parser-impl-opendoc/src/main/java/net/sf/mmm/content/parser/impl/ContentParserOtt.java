/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.impl;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.content.parser.api.ContentParser} interface for content of
 * the open-document text-template format (content with the mimetypes
 * "application/vnd.oasis.opendocument.text-template").
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentParserOtt extends AbstractContentParserOpenDoc {

  /** The mimetype. */
  public static final String KEY_MIMETYPE = "application/vnd.oasis.opendocument.text-template";

  /** The default extension. */
  public static final String KEY_EXTENSION = "ott";

  /**
   * The constructor.
   */
  public ContentParserOtt() {

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
