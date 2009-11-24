/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base;

import javax.xml.bind.annotation.XmlAttribute;

import net.sf.mmm.util.filter.base.FilterRuleChain;
import net.sf.mmm.util.transformer.api.Transformer;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class SearchConfigurationDirectoryBean {

  private String path;

  private String source;

  private String encoding;

  @XmlAttribute(name = "filter")
  private String filterName;

  @XmlAttribute(name = "uri-transformer")
  private String uriTransformerName;

  private transient FilterRuleChain<String> filter;

  private transient Transformer<String> uriTransformer;

  /**
   * The constructor.
   */
  public SearchConfigurationDirectoryBean() {

    super();
  }

}
