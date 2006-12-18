/* $Id$ */
package net.sf.mmm.search.parser.impl;

import net.sf.mmm.search.parser.base.AbstractContentParserService;

/**
 * This is the abstract base implemenation of the
 * {@link net.sf.mmm.search.parser.api.ContentParserService} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentParserServiceImpl extends AbstractContentParserService {

  /**
   * The constructor
   */
  public ContentParserServiceImpl() {

    super();
    setGenericParser(new ContentParserGeneric());
    addParser(new ContentParserPdf(), "pdf");
    addParser(new ContentParserHtml(), "html", "htm");
    addParser(new ContentParserDoc(), "doc", "dot");
    addParser(new ContentParserXls(), "xls");
    addParser(new ContentParserPpt(), "ppt");
  }

}
