/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.parser.impl;

import net.sf.mmm.search.parser.base.AbstractContentParserService;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.search.parser.api.ContentParserService} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentParserServiceImpl extends AbstractContentParserService {

  /**
   * The constructor.
   */
  public ContentParserServiceImpl() {

    super();
    setGenericParser(new ContentParserGeneric());
    addParser(new ContentParserPdf(), "pdf");
    addParser(new ContentParserHtml(), "html", "htm");
    addParser(new ContentParserDoc(), "doc", "dot");
    addParser(new ContentParserXls(), "xls");
    addParser(new ContentParserPpt(), "ppt");
    addParser(new ContentParserXml(), "xml");
    // even *.txt files may contain markup (e.g. twiki data files).
    addParser(new ContentParserTextMarkupAware(), "txt", "php", "jsp", "jinc", "asp");
    addParser(new ContentParserText(), "asc", "apt", "csv", "sql", "vbs", "bat", "sh", "c", "h",
        "cpp", "c++", "cs", "net", "pl", "py", "js");
    addParser(new ContentParserJava(), "java");
  }

}
