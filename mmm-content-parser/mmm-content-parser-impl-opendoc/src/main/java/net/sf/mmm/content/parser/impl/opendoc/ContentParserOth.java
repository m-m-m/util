/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.impl.opendoc;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.content.parser.api.ContentParser} interface for content of
 * the open-document text-web (HTML Document Template) format (content with the
 * mimetypes "application/vnd.oasis.opendocument.text-web").
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Singleton
@Named
public class ContentParserOth extends AbstractContentParserOpenDoc {

  /** The mimetype. */
  public static final String KEY_MIMETYPE = "application/vnd.oasis.opendocument.text-web";

  /** The default extension. */
  public static final String KEY_EXTENSION = "oth";

  /**
   * The constructor.
   */
  public ContentParserOth() {

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
