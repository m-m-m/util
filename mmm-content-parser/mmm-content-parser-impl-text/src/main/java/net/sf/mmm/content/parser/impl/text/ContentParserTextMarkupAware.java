/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.impl.text;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.content.parser.api.ContentParser} interface for text that
 * may contain markup. It should be used in advance to {@link ContentParserText}
 * as even "*.txt" files can contain markup (e.g. data-files from
 * TWiki/FosWiki).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Singleton
@Named
public class ContentParserTextMarkupAware extends AbstractContentParserTextMarkupAware {

  /**
   * The constructor.
   */
  public ContentParserTextMarkupAware() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String[] getAlternativeKeyArray() {

    return new String[] { "asc" };
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String[] getSecondaryKeyArray() {

    return new String[] { "jinc", "asp", "java", "cpp", "c", "h", "c++", "js", "css", "apt", "csv",
        "sql", "bat", "sh", "net", "pl", "py" };
  }

}
