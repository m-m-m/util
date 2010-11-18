/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.impl.poi;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.content.parser.api.ContentParser} interface for
 * MS-Powerpoint documents in OOXML format.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Singleton
@Named
public class ContentParserPptx extends AbstractContentParserOoxml {

  /** The mimetype. */
  public static final String KEY_MIMETYPE = "application/vnd.openxmlformats-officedocument.presentationml.presentation";

  /** The default extension. */
  public static final String KEY_EXTENSION = "pptx";

  /** The template mimetype. */
  public static final String KEY_MIMETYPE_TEMPLATE = "application/vnd.openxmlformats-officedocument.presentationml.template";

  /** The template extension. */
  public static final String KEY_EXTENSION_TEMPLATE = "potx";

  /** The macro mimetype. */
  public static final String KEY_MIMETYPE_WITH_MACROS = "application/vnd.ms-powerpoint.template.macroEnabled.12";

  /** The macro extension. */
  public static final String KEY_EXTENSION_WITH_MACROS = "potm";

  /** The mimetype. */
  public static final String KEY_MIMETYPE_SLIDESHOW = "application/vnd.openxmlformats-officedocument.presentationml.slideshow";

  /** The default extension. */
  public static final String KEY_EXTENSION_SLIDESHOW = "ppsx";

  /** The mimetype. */
  public static final String KEY_MIMETYPE_SLIDESHOW_WITH_MACROS = "application/vnd.ms-powerpoint.slideshow.macroEnabled.12";

  /** The default extension. */
  public static final String KEY_EXTENSION_SLIDESHOW_MACROS = "ppsm";

  /**
   * The constructor.
   */
  public ContentParserPptx() {

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

    return new String[] { KEY_EXTENSION_TEMPLATE, KEY_MIMETYPE_TEMPLATE, KEY_EXTENSION_WITH_MACROS,
        KEY_MIMETYPE_WITH_MACROS, KEY_EXTENSION_SLIDESHOW, KEY_MIMETYPE_SLIDESHOW };
  }

}
