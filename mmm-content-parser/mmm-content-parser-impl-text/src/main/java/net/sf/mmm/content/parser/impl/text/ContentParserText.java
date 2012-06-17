/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
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

  /**
   * The constructor.
   */
  public ContentParserText() {

    super();
  }

}
