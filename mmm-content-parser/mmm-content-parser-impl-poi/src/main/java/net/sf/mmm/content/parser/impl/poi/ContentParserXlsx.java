/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.impl.poi;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.content.parser.api.ContentParser} interface for MS-Excel
 * documents in OOXML format.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Singleton
@Named
public class ContentParserXlsx extends AbstractContentParserOoxml {

  /** The mimetype. */
  public static final String KEY_MIMETYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

  /** The default extension. */
  public static final String KEY_EXTENSION = "xlsx";

  /** The template mimetype. */
  public static final String KEY_MIMETYPE_TEMPLATE = "application/vnd.openxmlformats-officedocument.spreadsheetml.template";

  /** The template extension. */
  public static final String KEY_EXTENSION_TEMPLATE = "xltx";

  /** The macro mimetype. */
  public static final String KEY_MIMETYPE_WITH_MACROS = "application/vnd.ms-excel.sheet.macroEnabled.12";

  /** The macro extension. */
  public static final String KEY_EXTENSION_WITH_MACROS = "xlsm";

  /** The macro template mimetype. */
  public static final String KEY_MIMETYPE_TEMPLATE_WITH_MACROS = "application/vnd.ms-excel.template.macroEnabled.12";

  /** The macro template extension. */
  public static final String KEY_EXTENSION_TEMPLATE_WITH_MACROS = "xltm";

  /** The macro template mimetype. */
  public static final String KEY_MIMETYPE_BINARY_TEMPLATE_WITH_MACROS = "application/vnd.ms-excel.sheet.binary.macroEnabled.12";

  /** The macro template extension. */
  public static final String KEY_EXTENSION_BINARY_TEMPLATE_WITH_MACROS = "xlsb";

  /**
   * The constructor.
   */
  public ContentParserXlsx() {

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
        KEY_MIMETYPE_WITH_MACROS, KEY_EXTENSION_TEMPLATE_WITH_MACROS,
        KEY_MIMETYPE_TEMPLATE_WITH_MACROS, KEY_EXTENSION_BINARY_TEMPLATE_WITH_MACROS,
        KEY_MIMETYPE_BINARY_TEMPLATE_WITH_MACROS };
  }

}
