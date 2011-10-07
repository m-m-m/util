/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.datatype.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import net.sf.mmm.content.datatype.base.AbstractBlob;
import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;
import net.sf.mmm.util.io.api.StreamUtil;

/**
 * This is a simple implementation of
 * {@link net.sf.mmm.content.datatype.api.MutableBlob}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class FileBlob extends AbstractBlob {

  /** UID for serialization. */
  private static final long serialVersionUID = 5975990166524924465L;

  /** @see #getRevision() */
  private int revision;

  /** The underlying {@link File}. */
  private final File file;

  /**
   * The constructor.
   * 
   * @param file is the underlying {@link File}.
   * @param streamUtil is the {@link StreamUtil} instance.
   */
  public FileBlob(File file, StreamUtil streamUtil) {

    super(streamUtil);
    this.file = file;
    this.revision = 0;
  }

  /**
   * {@inheritDoc}
   */
  public OutputStream getWriteAccess(boolean append) {

    try {
      // TODO: Transaction support?
      FileOutputStream result = new FileOutputStream(this.file, append);
      this.revision++;
      return result;
    } catch (FileNotFoundException e) {
      throw new RuntimeIoException(e, IoMode.OPEN);
    }
  }

  /**
   * {@inheritDoc}
   */
  public InputStream getReadAccess() {

    try {
      return new FileInputStream(this.file);
    } catch (FileNotFoundException e) {
      throw new RuntimeIoException(e, IoMode.OPEN);
    }
  }

  /**
   * {@inheritDoc}
   */
  public long getSize() {

    return this.file.length();
  }

  /**
   * {@inheritDoc}
   */
  public int getRevision() {

    return this.revision;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTitle() {

    return "<Blob: " + this.file.getName() + ">";
  }

}
