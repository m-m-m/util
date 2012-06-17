/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.impl.opendoc;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.content.parser.api.ContentParser} interface for content of
 * the open-document presentation-template format.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Singleton
@Named
public class ContentParserOdp extends AbstractContentParserOpenDoc {

  /** The mimetype. */
  public static final String KEY_MIMETYPE = "application/vnd.oasis.opendocument.presentation";

  /** The default extension. */
  public static final String KEY_EXTENSION = "odp";

  /** The mimetype. */
  public static final String KEY_MIMETYPE_TEMPLATE = "application/vnd.oasis.opendocument.presentation-template";

  /** The default extension. */
  public static final String KEY_EXTENSION_TEMPLATE = "otp";

  /**
   * The constructor.
   */
  public ContentParserOdp() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public String getExtension() {

    return KEY_EXTENSION;
  }

  /**
   * {@inheritDoc}
   */
  public String getMimetype() {

    return KEY_MIMETYPE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String[] getAlternativeKeyArray() {

    return new String[] { KEY_EXTENSION_TEMPLATE, KEY_MIMETYPE_TEMPLATE };
  }

}
