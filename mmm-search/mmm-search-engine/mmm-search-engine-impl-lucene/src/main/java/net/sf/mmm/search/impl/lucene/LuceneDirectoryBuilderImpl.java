/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.impl.lucene;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.search.api.config.SearchIndexConfiguration;
import net.sf.mmm.util.component.base.AbstractComponent;
import net.sf.mmm.util.file.api.FileUtil;
import net.sf.mmm.util.file.base.FileUtilImpl;
import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.store.SimpleFSDirectory;

/**
 * This is the implementation of the {@link LuceneDirectoryBuilder} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Singleton
@Named
public class LuceneDirectoryBuilderImpl extends AbstractComponent implements LuceneDirectoryBuilder {

  /** @see #setFileUtil(FileUtil) */
  private FileUtil fileUtil;

  /**
   * The constructor.
   */
  public LuceneDirectoryBuilderImpl() {

    super();
  }

  /**
   * @param fileUtil is the fileUtil to set
   */
  @Inject
  public void setFileUtil(FileUtil fileUtil) {

    getInitializationState().requireNotInitilized();
    this.fileUtil = fileUtil;
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
   * {@inheritDoc}
   */
  public Directory createDirectory(SearchIndexConfiguration configuration) {

    return createDirectory(configuration.getLocation());
  }

  /**
   * {@inheritDoc}
   */
  public Directory createDirectory(String directory) {

    String location = this.fileUtil.normalizePath(directory);
    File path = new File(location);
    boolean windows = true; // System.getProperty("os");
    try {
      if (windows) {
        return new SimpleFSDirectory(path);
      } else {
        return new NIOFSDirectory(path);
      }
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.READ);
    }
  }
}
