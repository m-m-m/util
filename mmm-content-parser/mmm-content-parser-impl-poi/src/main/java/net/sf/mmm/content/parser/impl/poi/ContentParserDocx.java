/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.impl.poi;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.content.parser.api.ContentParser} interface for MS-Word
 * documents in OOXML format.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Singleton
@Named
public class ContentParserDocx extends AbstractContentParserOoxml {

  /** The mimetype. */
  public static final String KEY_MIMETYPE = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";

  /** The default extension. */
  public static final String KEY_EXTENSION = "docx";

  /** The template mimetype. */
  public static final String KEY_MIMETYPE_TEMPLATE = "application/vnd.openxmlformats-officedocument.wordprocessingml.template";

  /** The template extension. */
  public static final String KEY_EXTENSION_TEMPLATE = "dotx";

  /** The macro mimetype. */
  public static final String KEY_MIMETYPE_WITH_MACROS = "application/vnd.ms-word.document.macroEnabled.12";

  /** The macro extension. */
  public static final String KEY_EXTENSION_WITH_MACROS = "docm";

  /**
   * The constructor.
   */
  public ContentParserDocx() {

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
        KEY_MIMETYPE_WITH_MACROS, KEY_MIMETYPE_GENERIC };
  }

}
