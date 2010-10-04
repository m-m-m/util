/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.impl.opendoc;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.content.parser.api.ContentParser} interface for content of
 * the open-document graphics-template format (content with the mimetypes
 * "application/vnd.oasis.opendocument.graphics-template").
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Singleton
@Named
public class ContentParserOtg extends AbstractContentParserOpenDoc {

  /** The mimetype. */
  public static final String KEY_MIMETYPE = "application/vnd.oasis.opendocument.graphics-template";

  /** The default extension. */
  public static final String KEY_EXTENSION = "otg";

  /**
   * The constructor.
   */
  public ContentParserOtg() {

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
