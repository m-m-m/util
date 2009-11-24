/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base.config;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since X 12.10.2009
 */
public class SSBTest {

  /**
   * TODO: javadoc
   * 
   * @param args
   */
  public static void main(String[] args) throws Exception {

    JAXBContext context = JAXBContext.newInstance(SearchSourceBean.class, SearchConfigurationBean.class);
    SearchConfigurationBean list = new SearchConfigurationBean();
    List<SearchSourceBean> sourceList = new ArrayList<SearchSourceBean>();
    list.setSources(sourceList);
    SearchSourceBean source = new SearchSourceBean();
    source.setId("SVN");
    source.setTitle("Subversion");
    source.setUrlPrefix("http://foo.org/svn/trunk");
    sourceList.add(source);
    source = new SearchSourceBean();
    source.setId("Wiki");
    source.setTitle("Wiki");
    source.setUrlPrefix("http://foo.org/twiki/");
    sourceList.add(source);
    context.createMarshaller().marshal(list, System.out);
  }

}
