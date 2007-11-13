/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.file;

import net.sf.mmm.util.filter.CharFilter;
import net.sf.mmm.util.scanner.CharacterSequenceScanner;

/**
 * This class represents the <a
 * href="http://en.wikipedia.org/wiki/File_system_permissions">permissions</a>
 * of a file.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FileAccessPermissions implements Cloneable {

  /** @see #isReadable(FileAccessClass) */
  public static final int MASK_READABLE = 4;

  /** @see #isWritable(FileAccessClass) */
  public static final int MASK_WRITABLE = 2;

  /** @see #isExecutable(FileAccessClass) */
  public static final int MASK_EXECUTABLE = 1;

  /** @see #isSetuid() */
  public static final int MASK_SETUID = 04000;

  /** @see #isSetgid() */
  public static final int MASK_SETGID = 02000;

  /** @see #isSticky() */
  public static final int MASK_STICKY = 01000;

  /** @see #chmod(String) */
  private static final int MASK_USER_FLAGS = 00700;

  /** @see #chmod(String) */
  private static final int MASK_GROUP_FLAGS = 00070;

  /** @see #chmod(String) */
  private static final int MASK_OTHERS_FLAGS = 00007;

  /** @see #createByUmask(int, boolean) */
  private static final int MASK_FULL_FILE_ACCESS = 0666;

  /** @see #createByUmask(int, boolean) */
  private static final int MASK_FULL_DIRECTORY_ACCESS = 0777;

  /** @see #setExecutable(boolean) */
  private static final int MASK_ALL_EXECUTABLE = 0111;

  /** @see #setWritable(boolean) */
  private static final int MASK_ALL_WRITABLE = 0222;

  /** @see #setReadable(boolean) */
  private static final int MASK_ALL_READABLE = 0444;

  /** @see #getMaskBits() */
  private int maskBits;

  /**
   * The constructor.
   */
  public FileAccessPermissions() {

    super();
  }

  /**
   * This method create a new {@link FileAccessPermissions} instance according
   * to the given
   * <code><a href="http://en.wikipedia.org/wiki/Umask">umask</a></code>
   * (user file creation mode mask).
   * 
   * @param umask is the umask.
   * @param isDirectory <code>true</code> if the the
   *        {@link FileAccessPermissions} is to be created for a directory,
   *        <code>false</code> for a regular file.
   * @return the according {@link FileAccessPermissions}.
   */
  public static FileAccessPermissions createByUmask(int umask, boolean isDirectory) {

    int fullAccessMask;
    if (isDirectory) {
      fullAccessMask = MASK_FULL_DIRECTORY_ACCESS;
    } else {
      fullAccessMask = MASK_FULL_FILE_ACCESS;
    }
    int mask = fullAccessMask & ~umask;
    return new FileAccessPermissions(mask);
  }

  /**
   * The constructor.
   * 
   * @param mask is the {@link #getMaskBits() mask}.
   */
  public FileAccessPermissions(int mask) {

    super();
    setMaskBits(mask);
  }

  /**
   * This method gets the {@link FileAccessPermissions} encoded as a single
   * integer value. The value is in the same format as the octal notation for
   * the command <code>chmod</code>. Only the last 12 bits of the mask can be
   * set.
   * 
   * @return the encoded mask.
   */
  public int getMaskBits() {

    return this.maskBits;
  }

  /**
   * This method sets the bitwise encoded {@link #getMaskBits() mask}.
   * 
   * @param mask the mask to set.
   * @throws IllegalArgumentException if the given <code>mask</code> is
   *         negative or greater than <code>07777</code> (==
   *         <code>0xFFF</code> == <code>4095</code>).
   */
  public void setMaskBits(int mask) {

    if ((mask > 07777) || (mask < 0)) {
      throw new IllegalArgumentException("File mode mask out of range: " + mask);
    }
    this.maskBits = mask;
  }

  /**
   * This method determines if this {@link #getMaskBits() mask} is readable for
   * the given <code>fileModeClass</code>.
   * 
   * @param fileModeClass is the class of access (<code>{@link FileAccessClass#USER}</code>,
   *        <code>{@link FileAccessClass#GROUP}</code>, or
   *        <code>{@link FileAccessClass#OTHERS}</code>).
   * @return <code>true</code> if the mask is readable for the given
   *         <code>fileModeClass</code>, <code>false</code> otherwise.
   */
  public boolean isReadable(FileAccessClass fileModeClass) {

    return hasFlag(fileModeClass, MASK_READABLE);
  }

  /**
   * This method sets the {@link #isReadable(FileAccessClass) readable flag} of
   * this this {@link #getMaskBits() mask} for ALL
   * {@link FileAccessClass access-classes} to the given value (<code>readable</code>).
   * 
   * @param readable if <code>true</code> the mask will be readable, if
   *        <code>false</code> it will NOT be readable.
   */
  public void setReadable(boolean readable) {

    setBits(MASK_ALL_READABLE, readable);
  }

  /**
   * This method sets the {@link #isReadable(FileAccessClass) readable flag} of
   * this this {@link #getMaskBits() mask} for the given
   * <code>fileModeClass</code> to the given value (<code>readable</code>).
   * 
   * @param fileModeClass is the class of access (<code>{@link FileAccessClass#USER}</code>,
   *        <code>{@link FileAccessClass#GROUP}</code>, or
   *        <code>{@link FileAccessClass#OTHERS}</code>).
   * @param readable if <code>true</code> the mask will be readable for the
   *        given <code>fileModeClass</code>, if <code>false</code> it will
   *        NOT be readable.
   */
  public void setReadable(FileAccessClass fileModeClass, boolean readable) {

    setFlag(fileModeClass, MASK_READABLE, readable);
  }

  /**
   * This method determines if this {@link #getMaskBits() mask} is writable for
   * the given <code>fileModeClass</code>.
   * 
   * @param fileModeClass is the class of access (<code>{@link FileAccessClass#USER}</code>,
   *        <code>{@link FileAccessClass#GROUP}</code>, or
   *        <code>{@link FileAccessClass#OTHERS}</code>).
   * @return <code>true</code> if the mask is writable for the given
   *         <code>fileModeClass</code>, <code>false</code> otherwise.
   */
  public boolean isWritable(FileAccessClass fileModeClass) {

    return hasFlag(fileModeClass, MASK_WRITABLE);
  }

  /**
   * This method sets the {@link #isWritable(FileAccessClass) writable flag} of
   * this this {@link #getMaskBits() mask} for ALL
   * {@link FileAccessClass access-classes} to the given value (<code>writable</code>).
   * 
   * @param writable if <code>true</code> the mask will be writable, if
   *        <code>false</code> it will NOT be writable.
   */
  public void setWritable(boolean writable) {

    setBits(MASK_ALL_WRITABLE, writable);
  }

  /**
   * This method sets the {@link #isWritable(FileAccessClass) writable flag} of
   * this this {@link #getMaskBits() mask} for the given
   * <code>fileModeClass</code> to the given value (<code>writable</code>).
   * 
   * @param fileModeClass is the class of access (<code>{@link FileAccessClass#USER}</code>,
   *        <code>{@link FileAccessClass#GROUP}</code>, or
   *        <code>{@link FileAccessClass#OTHERS}</code>).
   * @param writable if <code>true</code> the mask will be writable for the
   *        given <code>fileModeClass</code>, if <code>false</code> it will
   *        NOT be writable.
   */
  public void setWritable(FileAccessClass fileModeClass, boolean writable) {

    setFlag(fileModeClass, MASK_WRITABLE, writable);
  }

  /**
   * This method determines if this {@link #getMaskBits() mask} is executable
   * for the given <code>fileModeClass</code>.
   * 
   * @param fileModeClass is the class of access (<code>{@link FileAccessClass#USER}</code>,
   *        <code>{@link FileAccessClass#GROUP}</code>, or
   *        <code>{@link FileAccessClass#OTHERS}</code>).
   * @return <code>true</code> if the mask is executable for the given
   *         <code>fileModeClass</code>, <code>false</code> otherwise.
   */
  public boolean isExecutable(FileAccessClass fileModeClass) {

    return hasFlag(fileModeClass, MASK_EXECUTABLE);
  }

  /**
   * This method sets the {@link #isExecutable(FileAccessClass) executable flag}
   * of this this {@link #getMaskBits() mask} for ALL
   * {@link FileAccessClass access-classes} to the given value (<code>executable</code>).
   * 
   * @param executable if <code>true</code> the mask will be executable, if
   *        <code>false</code> it will NOT be executable.
   */
  public void setExecutable(boolean executable) {

    setBits(MASK_ALL_EXECUTABLE, executable);
  }

  /**
   * This method sets the {@link #isExecutable(FileAccessClass) executable flag}
   * of this this {@link #getMaskBits() mask} for the given
   * <code>fileModeClass</code> to the given value (<code>executable</code>).
   * 
   * @param fileModeClass is the class of access (<code>{@link FileAccessClass#USER}</code>,
   *        <code>{@link FileAccessClass#GROUP}</code>, or
   *        <code>{@link FileAccessClass#OTHERS}</code>).
   * @param executable if <code>true</code> the mask will be executable for
   *        the given <code>fileModeClass</code>, if <code>false</code> it
   *        will NOT be executable.
   */
  public void setExecutable(FileAccessClass fileModeClass, boolean executable) {

    setFlag(fileModeClass, MASK_EXECUTABLE, executable);
  }

  /**
   * This method determines the value of the <em>setuid</em> flag ("set user
   * ID"). If this flag is set and the file is executed, the according process
   * will be started under the user of the file-{@link FileAccessClass#USER owner}
   * instead of the user that performed the execution.
   * 
   * @return <code>true</code> if the flag is set, <code>false</code>
   *         otherwise.
   */
  public boolean isSetuid() {

    return hasBits(MASK_SETUID);
  }

  /**
   * This method sets the {@link #isSetuid() setuid} flag to the given value
   * <code>setuid</code>.
   * 
   * @param setuid - if <code>true</code> the flag will be set, if
   *        <code>false</code> it will be unset.
   */
  public void setSetuid(boolean setuid) {

    setBits(MASK_SETUID, setuid);
  }

  /**
   * This method determines the value of the <em>setgid</em> flag ("set group
   * ID"). If this flag is set and the file is executed, the according process
   * will be started under the
   * {@link FileAccessClass#GROUP group owning the file} instead of the group of
   * the user that performed the execution.
   * 
   * @return <code>true</code> if the flag is set, <code>false</code>
   *         otherwise.
   */
  public boolean isSetgid() {

    return hasBits(MASK_SETGID);
  }

  /**
   * This method sets the {@link #isSetuid() setuid} flag to the given value
   * <code>setuid</code>.
   * 
   * @param setgid - if <code>true</code> the flag will be set, if
   *        <code>false</code> it will be unset.
   */
  public void setSetgid(boolean setgid) {

    setBits(MASK_SETGID, setgid);
  }

  /**
   * This method determines the value of the <em>sticky</em> flag. This flag
   * is mostly obsolete and is NOT used on linux systems.
   * 
   * @return <code>true</code> if the flag is set, <code>false</code>
   *         otherwise.
   */
  public boolean isSticky() {

    return hasBits(MASK_STICKY);
  }

  /**
   * This method sets the {@link #isSticky() sticky} flag to the given value
   * <code>sticky</code>.
   * 
   * @param sticky - if <code>true</code> the flag will be set, if
   *        <code>false</code> it will be unset.
   */
  public void setSticky(boolean sticky) {

    setBits(MASK_STICKY, sticky);
  }

  /**
   * This method shifts the given <code>bitMask</code> according to the given
   * <code>fileModeClass</code>.
   * 
   * @param fileModeClass is the class of access (<code>{@link FileAccessClass#USER}</code>,
   *        <code>{@link FileAccessClass#GROUP}</code>, or
   *        <code>{@link FileAccessClass#OTHERS}</code>).
   * @param bitMask is the bit-mask to shift.
   * @return the shifted <code>bitMask</code>.
   */
  private int shiftMask(FileAccessClass fileModeClass, int bitMask) {

    if (fileModeClass == FileAccessClass.USER) {
      return bitMask << 6;
    } else if (fileModeClass == FileAccessClass.GROUP) {
      return bitMask << 3;
    } else if (fileModeClass == FileAccessClass.OTHERS) {
      return bitMask;
    }
    throw new IllegalArgumentException("Illegal FileModeClass: " + fileModeClass);
  }

  /**
   * This method sets the flag(s) given by <code>bitMask</code> of this this
   * {@link #getMaskBits() mask} for the given <code>fileModeClass</code> to
   * the given value (<code>flag</code>).
   * 
   * 
   * @param fileModeClass is the class of access (<code>{@link FileAccessClass#USER}</code>,
   *        <code>{@link FileAccessClass#GROUP}</code>, or
   *        <code>{@link FileAccessClass#OTHERS}</code>).
   * @param bitMask is the bit-mask of the flag(s) to set.
   * @param flag - if <code>true</code> the flag will be set, if
   *        <code>false</code> it will be unset.
   */
  private void setFlag(FileAccessClass fileModeClass, int bitMask, boolean flag) {

    setBits(shiftMask(fileModeClass, bitMask), flag);
  }

  /**
   * This method determines if the flag(s) given by <code>bitMask</code> is
   * set in this {@link #getMaskBits() mask} for the given
   * <code>fileModeClass</code>.
   * 
   * @param fileModeClass is the class of access (<code>{@link FileAccessClass#USER}</code>,
   *        <code>{@link FileAccessClass#GROUP}</code>, or
   *        <code>{@link FileAccessClass#OTHERS}</code>).
   * @param bitMask is the bit-mask of the flag(s) to get.
   * @return <code>true</code> if the flag is set, <code>false</code>
   *         otherwise.
   */
  private boolean hasFlag(FileAccessClass fileModeClass, int bitMask) {

    return hasBits(shiftMask(fileModeClass, bitMask));
  }

  /**
   * This method determines if the flags given by <code>bitMask</code> are set
   * in this {@link #getMaskBits() mask}.
   * 
   * @param bitMask is the bit-mask of the flag(s) to check.
   * @return <code>true</code> if the flags are set, <code>false</code>
   *         otherwise.
   */
  private boolean hasBits(int bitMask) {

    return ((this.maskBits & bitMask) == bitMask);
  }

  /**
   * This method sets or unsets the flags given by <code>bitMask</code> in
   * this {@link #getMaskBits() mask} according to the given <code>flag</code>.
   * 
   * @param bitMask is the bit-mask of the flag(s) to set or unset.
   * @param set - if <code>true</code> the flag(s) will be set, if
   *        <code>false</code> they will be unset.
   */
  private void setBits(int bitMask, boolean set) {

    if (set) {
      this.maskBits = this.maskBits | bitMask;
    } else {
      this.maskBits = this.maskBits & ~bitMask;
    }
  }

  /**
   * This method parses the <code>ugoa</code> prefix indicating which flags to
   * modify.
   * <ul>
   * <li><code>u</code> indicates that the flags of the
   * {@link FileAccessClass#USER user} should be changed.</li>
   * <li><code>g</code> indicates that the flags of the
   * {@link FileAccessClass#GROUP group} should be changed.</li>
   * <li><code>o</code> indicates that the flags of the
   * {@link FileAccessClass#OTHERS others} should be changed.</li>
   * <li><code>a</code> indicates that the flags of all
   * {@link FileAccessClass classes} should be changed.</li>
   * </ul>
   * 
   * @param parse is the current state of the parser.
   * @return the bit-mask with the UGO-flags.
   */
  private static int parseUGO(CharacterSequenceScanner parse) {

    int ugo = 0;
    while (true) {
      char c = parse.forceNext();
      if (c == 'u') {
        ugo = ugo | 0100;
      } else if (c == 'g') {
        ugo = ugo | 0010;
      } else if (c == 'o') {
        ugo = ugo | 0001;
      } else if (c == 'a') {
        ugo = 0111;
      } else {
        if (ugo == 0) {
          // if none of u/g/o/a was specified, then 'a' is the default
          ugo = 0111;
        }
        if (c != 0) {
          // we read too far
          parse.stepBack();
        }
        return ugo;
      }
    }
  }

  /**
   * This method parses a symbolic-mode segment from <code>parse</code>. It
   * applies the {@link #chmod(String) chmod} of that segment to the given
   * <code>mask</code> and returns the result. The state of this object
   * remains unchanged.
   * 
   * @param parse is the current state of the parser.
   * @param maskBits is the current modifier mask.
   * @return the changed <code>mask</code>.
   */
  private static int parseSymbolicMode(CharacterSequenceScanner parse, int maskBits) {

    int mask = maskBits;
    int ugo = parseUGO(parse);
    char mode = parse.forceNext();
    int flags = 0;
    int template = -1;
    char c = parse.forceNext();
    if (c == 'u') {
      template = (mask & MASK_USER_FLAGS) >> 6;
    } else if (c == 'g') {
      template = (mask & MASK_GROUP_FLAGS) >> 3;
    } else if (c == 'o') {
      template = mask & MASK_OTHERS_FLAGS;
    } else {
      while (c != 0) {
        if (c == 'r') {
          flags = flags | (ugo << 2);
        } else if (c == 'w') {
          flags = flags | (ugo << 1);
        } else if (c == 'x') {
          flags = flags | ugo;
        } else if (c == 's') {
          flags = flags | MASK_SETUID;
        } else if (c == 'S') {
          flags = flags | MASK_SETGID;
        } else if (c == 't') {
          flags = flags | MASK_STICKY;
        } else {
          parse.stepBack();
          break;
        }
        c = parse.forceNext();
      }
    }
    if (template != -1) {
      if ((ugo & 0001) != 0) {
        flags = template;
      }
      if ((ugo & 0010) != 0) {
        flags = flags | (template << 3);
      }
      if ((ugo & 0100) != 0) {
        flags = flags | (template << 6);
      }
    }
    if (mode == '+') {
      mask = mask | flags;
    } else if (mode == '-') {
      mask = mask & ~flags;
    } else if (mode == '=') {
      int clear = ugo | (ugo << 1) | (ugo << 2);
      mask = mask & ~clear;
      mask = mask | flags;
    } else {
      throw new IllegalArgumentException("op: '" + mode + "'");
    }
    return mask;
  }

  /**
   * This method parses the current state of the {@link #chmod(String) chmod}
   * argument given by <code>parse</code> in octal mode. If no digits are
   * detected, <code>parse</code> remains unchanged and <code>-1</code> is
   * returned, else all digits are consumed and the parsed octal-mode is
   * returned.
   * 
   * @param parse is the current parser state of the the
   *        {@link #chmod(String) chmod} argument.
   * @return the parsed octal-mode or <code>-1</code> if <code>parse</code>
   *         does NOT point to a digit.
   * @throws IllegalArgumentException if there are more the 4 digits or
   *         non-octal digits (8 or 9).
   */
  private static int parseOctalMode(CharacterSequenceScanner parse) throws IllegalArgumentException {

    String octals = parse.readWhile(CharFilter.LATIN_DIGIT_FILTER);
    if (octals.length() == 0) {
      return -1;
    }
    if (octals.length() > 4) {
      throw new IllegalArgumentException();
    }
    return Integer.parseInt(octals, 8);
  }

  /**
   * This method allows to change the {@link #getMaskBits() modifiers} by a
   * string expression. It behaves like the GNU command <code>chmod</code>
   * (change modifiers).<br>
   * Examples:<br>
   * <ul>
   * <li><code>0124</code><br>
   * sets the {@link #getMaskBits() modifiers} to <code>--x-w-r--</code></li>
   * <li><code>u=wx,g+w,o-r</code><br>
   * sets the {@link #getMaskBits() modifiers} to <code>-wx?w?-??</code></li>
   * </ul>
   * 
   * @param chmod is the mode-list argument as supplied to the
   *        <code>chmod</code> command. It can either be a single octal-mode
   *        (up to 4 digits of octal number) or a comma-separated list of
   *        symbolic-modes (<code>[ugoa]*([-+=]([rwxXst]*|[ugo]))+</code>).
   */
  public void chmod(String chmod) {

    CharacterSequenceScanner parse = new CharacterSequenceScanner(chmod);
    if (!parse.hasNext()) {
      throw new IllegalArgumentException();
    }
    int mask = this.maskBits;
    try {
      int octal = parseOctalMode(parse);
      if (octal != -1) {
        mask = octal;
        char c = parse.forceNext();
        if (c != 0) {
          throw new IllegalArgumentException("'" + c + "'");
        }
      } else {
        while (parse.hasNext()) {
          mask = parseSymbolicMode(parse, mask);
          char c = parse.forceNext();
          if ((c != ',') && (c != 0)) {
            throw new IllegalArgumentException("'" + c + "'");
          }
        }
      }
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Illegal chmod: " + chmod, e);
    }
    this.maskBits = mask;
  }

  /**
   * {@inheritDoc}
   */
  public int hashCode() {

    return this.maskBits;
  }

  /**
   * {@inheritDoc}
   */
  public boolean equals(Object obj) {

    if (obj == null) {
      return false;
    }
    if (obj == this) {
      return true;
    }
    if (obj.getClass() != FileAccessPermissions.class) {
      return false;
    }
    FileAccessPermissions otherMask = (FileAccessPermissions) obj;
    return (this.maskBits == otherMask.maskBits);
  }

  /**
   * {@inheritDoc}
   */
  public FileAccessPermissions clone() {

    try {
      return (FileAccessPermissions) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new IllegalStateException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  public String toString() {

    String result = Integer.toString(this.maskBits, 8);
    int len = result.length();
    if (len == 1) {
      result = "000" + result;
    } else if (len == 2) {
      result = "00" + result;
    } else if (len == 3) {
      result = "0" + result;
    }
    return result;
  }

}
