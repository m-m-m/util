/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.parser.impl;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.search.parser.api.ContentParser} interface for content of
 * the open-document text-master format (content with the mimetypes
 * "application/vnd.oasis.opendocument.text-master").
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentParserOdm extends AbstractContentParserOpenDoc {

  /** The mimetype. */
  public static final String KEY_MIMETYPE = "application/vnd.oasis.opendocument.text-master";

  /** The default extension. */
  public static final String KEY_EXTENSION = "odm";

  /**
   * The constructor.
   */
  public ContentParserOdm() {

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
