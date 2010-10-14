/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.base.config;

import java.io.File;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.search.engine.api.config.SearchEngineConfiguration;
import net.sf.mmm.search.engine.api.config.SearchEngineConfigurationReader;
import net.sf.mmm.util.file.api.FileUtil;
import net.sf.mmm.util.file.base.FileUtilImpl;
import net.sf.mmm.util.xml.base.jaxb.XmlBeanMapper;

/**
 * This is the implementation of the {@link SearchEngineConfigurationReader}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Singleton
@Named
public class SearchEngineConfigurationReaderImpl extends
    XmlBeanMapper<SearchEngineConfigurationBean> implements SearchEngineConfigurationReader {

  /** @see #getFileUtil() */
  private FileUtil fileUtil;

  /**
   * The constructor.
   */
  public SearchEngineConfigurationReaderImpl() {

    super(SearchEngineConfigurationBean.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.fileUtil == null) {
      this.fileUtil = FileUtilImpl.getInstance();
    }
  }

  /**
   * @return the fileUtil
   */
  public FileUtil getFileUtil() {

    return this.fileUtil;
  }

  /**
   * @param fileUtil is the fileUtil to set
   */
  public void setFileUtil(FileUtil fileUtil) {

    getInitializationState().requireNotInitilized();
    this.fileUtil = fileUtil;
  }

  /**
   * {@inheritDoc}
   */
  public SearchEngineConfiguration readConfiguration(String location) {

    String normalizedPath = this.fileUtil.normalizePath(location);
    return loadXml(new File(normalizedPath));
  }

}
