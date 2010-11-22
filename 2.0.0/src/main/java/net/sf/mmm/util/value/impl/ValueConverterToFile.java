/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.file.api.FileUtil;
import net.sf.mmm.util.file.base.FileUtilImpl;
import net.sf.mmm.util.value.base.AbstractSimpleValueConverter;

/**
 * This is an implementation of the
 * {@link net.sf.mmm.util.value.api.ValueConverter} interface that converts an
 * {@link Object} to a {@link Number}. It supports objects given as
 * {@link CharSequence} (e.g. {@link String}) or {@link Number}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
@Singleton
@Named
public class ValueConverterToFile extends AbstractSimpleValueConverter<CharSequence, File> {

  /** @see #getFileUtil() */
  private FileUtil fileUtil;

  /**
   * The constructor.
   */
  public ValueConverterToFile() {

    super();
  }

  /**
   * This method gets the {@link FileUtil} to use.
   * 
   * @return the {@link FileUtil} instance.
   */
  protected FileUtil getFileUtil() {

    return this.fileUtil;
  }

  /**
   * This method set the {@link FileUtil} to use.
   * 
   * @param fileUtil is the {@link FileUtil} instance.
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
  public Class<CharSequence> getSourceType() {

    return CharSequence.class;
  }

  /**
   * {@inheritDoc}
   */
  public Class<File> getTargetType() {

    return File.class;
  }

  /**
   * {@inheritDoc}
   */
  public File convert(CharSequence value, Object valueSource, Class<? extends File> targetClass) {

    if (value == null) {
      return null;
    }
    String filename = this.fileUtil.normalizePath(value.toString());
    return new File(filename);
  }

}
