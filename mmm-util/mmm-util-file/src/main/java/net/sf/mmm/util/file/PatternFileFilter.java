/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.file;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

import net.sf.mmm.util.pattern.GlobPatternCompiler;

/**
 * This is the implementation of a {@link FileFilter} that filters using a
 * {@link java.util.regex.Pattern pattern}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PatternFileFilter implements FileFilter {

  /**
   * the pattern that must match in order to {@link #accept(File) accept} a file
   * by its {@link File#getName() name}.
   */
  private final Pattern pattern;

  /**
   * The type of the {@link #accept(File) acceptable} files or <code>null</code>
   * if any type is acceptable.
   */
  private final FileType fileType;

  /**
   * The constructor.
   * 
   * @param filenamePattern is the pattern that must match in order to
   *        {@link #accept(File) accept} a file by its
   *        {@link File#getName() name}.
   */
  public PatternFileFilter(Pattern filenamePattern) {

    this(filenamePattern, null);
  }

  /**
   * The constructor.
   * 
   * @param filenamePattern is the pattern that must match in order to
   *        {@link #accept(File) accept} a file by its
   *        {@link File#getName() name}.
   * @param type the type of the {@link #accept(File) acceptable} files or
   *        <code>null</code> if any type is acceptable.
   */
  public PatternFileFilter(Pattern filenamePattern, FileType type) {

    super();
    this.pattern = filenamePattern;
    this.fileType = type;
  }

  /**
   * The constructor.
   * 
   * @param filenamePattern is the {@link GlobPatternCompiler glob-pattern} that
   *        must match in order to {@link #accept(File) accept} a file by its
   *        {@link File#getName() name}.
   * @param type the type of the {@link #accept(File) acceptable} files or
   *        <code>null</code> if any type is acceptable.
   */
  public PatternFileFilter(String filenamePattern, FileType type) {

    this(new GlobPatternCompiler().compile(filenamePattern), type);
  }

  /**
   * {@inheritDoc}
   */
  public boolean accept(File file) {

    if (this.fileType != null) {
      if (this.fileType != FileType.getType(file)) {
        return false;
      }
    }
    return this.pattern.matcher(file.getName()).matches();
  }

}
