/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.api;

import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Pattern;

import net.sf.mmm.util.filter.api.CharFilter;
import net.sf.mmm.util.lang.api.function.Function;
import net.sf.mmm.util.lang.api.function.VoidFunction;
import net.sf.mmm.util.pattern.base.GlobPatternCompiler;
import net.sf.mmm.util.scanner.base.CharSequenceScanner;

/**
 * This class represents a path in a file, URL, or the like.
 *
 * @param <D> is the type of the {@link #getData() generic data}. For simple usage just {@link Void}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 7.0.0
 */
public class ResourcePath<D> implements Serializable {

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  /** The path segment indicating the current folder itself. */
  public static final String PATH_SEGMENT_CURRENT = ".";

  /** The path segment indicating the parent folder. */
  public static final String PATH_SEGMENT_PARENT = "..";

  /** The path segment separator. */
  public static final char PATH_SEGMENT_SEPARATOR_CHAR = '/';

  /** The path segment separator. */
  public static final char HOME_PATH_CHAR = '~';

  /** The path segment separator. */
  public static final String PATH_SEGMENT_SEPARATOR = Character.toString(PATH_SEGMENT_SEPARATOR_CHAR);

  /** The prefix of an UNC (Uniform Naming Convention) path (e.g. <code>\\10.0.0.1\share</code>). */
  public static final String UNC_PATH_PREFIX = "\\\\";

  /** The separator infix between scheme and authority in an URL. */
  public static final String URL_SCHEME_AUTHORITY_SEPARATOR = "://";

  /** The suffix of a Windows drive letter. */
  private static final char WINDOWS_DRIVE_LETTER_SUFFIX = ':';

  /** The {@link Void} data function. */
  private static final Function<ResourcePath<Void>, Void> VOID_FUNCTION = VoidFunction
      .<ResourcePath<Void>> getInstance();

  /** The {@link Void} data function. */
  private static final Function<ResourcePath<Pattern>, Pattern> GLOB_PATTERN_FUNCTION = new Function<ResourcePath<Pattern>, Pattern>() {

    public Pattern apply(ResourcePath<Pattern> path) {

      return GlobPatternCompiler.INSTANCE.compile(path.getName());
    };
  };

  /** The {@link #isAbsolute() absolute} {@link #isRoot() root} {@link ResourcePath} (.). */
  public static final ResourcePath<Void> ROOT_ABSOLUTE = new ResourcePathRootAbsolute<Void>(VOID_FUNCTION);

  /** The {@link #isAbsolute() relative} {@link #isRoot() root} {@link ResourcePath} (/). */
  public static final ResourcePath<Void> ROOT_RELATIVE = new ResourcePathRootRelative<Void>(VOID_FUNCTION);

  /** The {@link #isAbsolute() absolute} home {@link #isRoot() root} {@link ResourcePath} (~). */
  public static final ResourcePath<Void> ROOT_HOME = new ResourcePathRootHome<Void>(VOID_FUNCTION);

  /** @see #getParent() */
  private final ResourcePath<D> parent;

  /** @see #getName() */
  private final String name;

  /** @see #getData() */
  private final D data;

  /**
   * The constructor for a root {@link ResourcePath}.
   *
   * @param name - see {@link #getName()}.
   * @param data - see {@link #getData()}.
   */
  protected ResourcePath(String name, D data) {

    super();
    this.parent = null;
    this.name = name;
    this.data = data;
  }

  /**
   * The constructor for a root {@link ResourcePath}.
   *
   * @param name - see {@link #getName()}.
   * @param dataFunction - the {@link Function} to {@link Function#apply(Object) create} the
   *        {@link #getData() data}.
   */
  protected ResourcePath(String name, Function<ResourcePath<D>, D> dataFunction) {

    super();
    this.parent = null;
    this.name = name;
    this.data = dataFunction.apply(this);
  }

  /**
   * The constructor.
   *
   * @param parent - see {@link #getParent()}.
   * @param name - see {@link #getName()}.
   * @param data - see {@link #getData()}.
   */
  protected ResourcePath(ResourcePath<D> parent, String name, D data) {

    super();
    Objects.requireNonNull(parent, "parent");
    Objects.requireNonNull(name, "name");
    if (name.isEmpty()) {
      throw new IllegalArgumentException("name");
    }
    this.parent = parent;
    this.name = name;
    this.data = data;
  }

  /**
   * The constructor.
   *
   * @param parent - see {@link #getParent()}.
   * @param name - see {@link #getName()}.
   * @param dataFunction - the {@link Function} to {@link Function#apply(Object) create} the
   *        {@link #getData() data}.
   */
  protected ResourcePath(ResourcePath<D> parent, String name, Function<ResourcePath<D>, D> dataFunction) {

    super();
    Objects.requireNonNull(parent, "parent");
    Objects.requireNonNull(name, "name");
    if (name.isEmpty()) {
      throw new IllegalArgumentException("name");
    }
    this.parent = parent;
    this.name = name;
    this.data = dataFunction.apply(this);
  }

  /**
   * @return the parent {@link ResourcePath} or <code>null</code> if this is the {@link #isRoot()} segment.
   */
  public ResourcePath<D> getParent() {

    return this.parent;
  }

  /**
   * @return the name of this {@link ResourcePath} as segment (this "folder" or "file").
   */
  public String getName() {

    return this.name;
  }

  /**
   * @return the optional generic data. E.g. the {@link #getName() name} compiled as
   *         {@link java.util.regex.Pattern}.
   */
  public D getData() {

    return this.data;
  }

  /**
   * @return <code>true</code> if this path is absolute, <code>false</code> if it is relative.
   */
  public boolean isAbsolute() {

    // ROOT is always a subclass that overrides this method...
    return this.parent.isAbsolute();
  }

  /**
   * @return <code>true</code> if this is the root {@link ResourcePath}, <code>false</code> otherwise.
   */
  public boolean isRoot() {

    return (this.parent == null);
  }

  /**
   * @return <code>true</code> if {@link #toString(StringBuilder, char)} needs to append a separator after
   *         this segment, <code>false</code> otherwise.
   */
  protected boolean isAppendSeparator() {

    return true;
  }

  /**
   * Checks if this {@link ResourcePath} matches to the given {@link #createPattern(String) pattern path}.
   *
   * @param patternPath the {@link #createPattern(String) pattern path} to match.
   * @return <code>true</code> if this path matches the given the given {@code patternPath}.
   */
  public boolean matches(ResourcePath<Pattern> patternPath) {

    // currently does not support ** segments like in Ant
    if (isRoot()) {
      if (!patternPath.isRoot()) {
        return false;
      }
    } else if (patternPath.isRoot()) {
      return false;
    } else if (!this.parent.matches(patternPath.parent)) {
      return false;
    }
    if (patternPath.data == null) {
      return patternPath.name.equals(this.name);
    } else {
      return patternPath.data.matcher(this.name).matches();
    }
  }

  /**
   * @param buffer is the {@link StringBuilder} where to append the string representation to.
   * @param separator is the character to separate the {@link ResourcePath} segments. Typically
   *        {@value #PATH_SEGMENT_SEPARATOR_CHAR}.
   */
  protected void toString(StringBuilder buffer, char separator) {

    if (this.parent != null) {
      this.parent.toString(buffer, separator);
      if (this.parent.isAppendSeparator()) {
        buffer.append(separator);
      }
    }
    buffer.append(this.name);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return toString(PATH_SEGMENT_SEPARATOR_CHAR);
  }

  /**
   * @param separator is the character to separate the {@link ResourcePath} segments. Typically
   *        {@value #PATH_SEGMENT_SEPARATOR_CHAR}.
   * @return the {@link #toString() string representation}.
   */
  public String toString(char separator) {

    StringBuilder buffer = new StringBuilder();
    toString(buffer, separator);
    return buffer.toString();
  }

  /**
   * Creates a normalized {@link ResourcePath}. Here are some examples:
   *
   * <table border="1">
   * <tr>
   * <th>{@code path}</th>
   * <th colspan="3">{@link ResourcePath}.{@link #create(String) create}({@code path})</th>
   * </tr>
   * <tr>
   * <th>&nbsp;</th>
   * <th>{@link #toString()}</th>
   * <th>{@link #isAbsolute()}</th>
   * <th>{@link #getName()}</th>
   * </tr>
   * <tr>
   * <td>"/foo//bar/"</td>
   * <td>"/foo/bar"</td>
   * <td>true</td>
   * <td>"bar"</td>
   * </tr>
   * <tr>
   * <td>"/foo/bar"</td>
   * <td>"/foo/bar"</td>
   * <td>true</td>
   * <td>"bar"</td>
   * </tr>
   * <tr>
   * <td>"a/../b/./c"</td>
   * <td>"b/c"</td>
   * <td>false</td>
   * <td>"c"</td>
   * </tr>
   * <tr>
   * <td>"a/../../"</td>
   * <td>".."</td>
   * <td>false</td>
   * <td>".."</td>
   * </tr>
   * <tr>
   * <td>"a/.."</td>
   * <td>""</td>
   * <td>false</td>
   * <td>""</td>
   * </tr>
   * <tr>
   * <td>"../a"</td>
   * <td>"../a"</td>
   * <td>false</td>
   * <td>"a"</td>
   * </tr>
   * <tr>
   * <td>"/../a"</td>
   * <td>"/a"</td>
   * <td>true</td>
   * <td>"a"</td>
   * </tr>
   * <tr>
   * <td>"~/.ssh"</td>
   * <td>"~/.ssh"</td>
   * <td>true</td>
   * <td>".ssh"</td>
   * </tr>
   * <tr>
   * <td>"~admin/../"</td>
   * <td>"~admin/.."</td>
   * <td>true</td>
   * <td>".."</td>
   * </tr>
   * <tr>
   * <td>"/"</td>
   * <td>""</td>
   * <td>true</td>
   * <td>"/"</td>
   * </tr>
   * <tr>
   * <td>""</td>
   * <td>""</td>
   * <td>false</td>
   * <td>""</td>
   * </tr>
   * <tr>
   * <td>null</td>
   * <td>- (null)</td>
   * <td>- (null)</td>
   * <td>- (null)</td>
   * </tr>
   * </table>
   *
   * @param path is the path to parse (e.g. "/foo/bar.txt" or "a/../b/./c").
   * @return the parsed {@code path} as {@link ResourcePath} or <code>null</code> if empty.
   */
  public static ResourcePath<Void> create(String path) {

    return create(path, VOID_FUNCTION);
  }

  /**
   * Calls {@link #create(String, Function)} with {@link #getData() data} function delegating to
   * {@link GlobPatternCompiler}.
   *
   * @see #create(String)
   * @see #matches(ResourcePath)
   *
   * @param path - see {@link #create(String)}.
   * @return the parsed {@link ResourcePath}.
   */
  public static ResourcePath<Pattern> createPattern(String path) {

    return create(path, GLOB_PATTERN_FUNCTION);
  }

  /**
   * Flexible implementation of {@link #create(String)} to custom data function.
   *
   * @param <D> is the type of the {@link #getData() generic data}. For simple usage just {@link Void}.
   * @param path is the path to parse.
   * @param dataFunction - the {@link Function} to {@link Function#apply(Object) create} the
   *        {@link #getData() data}.
   * @return the parsed {@code path}.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static <D> ResourcePath<D> create(String path, Function<ResourcePath<D>, D> dataFunction) {

    if (path == null) {
      return null;
    }
    if (path.isEmpty()) {
      if (((Function) dataFunction) == VOID_FUNCTION) {
        return (ResourcePath<D>) ROOT_RELATIVE;
      } else {
        return new ResourcePathRootRelative<D>(dataFunction);
      }
    }
    char[] chars = path.toCharArray();
    // normalize slashes: replace \ with /
    for (int i = 0; i < chars.length; i++) {
      if (chars[i] == '\\') {
        chars[i] = PATH_SEGMENT_SEPARATOR_CHAR;
      }
    }
    CharSequenceScanner scanner = new CharSequenceScanner(chars);
    ResourcePath<D> result = createRootPathSegment(scanner, path, dataFunction);
    while (scanner.hasNext()) {
      String segment = scanner.readUntil('/', true);
      // ignore double slashes ("//") and segment "."
      if ((segment.length() > 0) && (!PATH_SEGMENT_CURRENT.equals(segment))) {
        if (PATH_SEGMENT_PARENT.equals(segment)) {
          // ".." --> remove last segment...
          if (result.isRoot()) {
            if (!result.isAbsolute() || (result instanceof ResourcePathRootHome)) {
              // ".." --> ".."
              result = new ResourcePath<D>(result, segment, dataFunction);
            }
          } else if (PATH_SEGMENT_PARENT.equals(result.getName())) {
            // "../.." --> "../.."
            result = new ResourcePath<D>(result, segment, dataFunction);
          } else {
            result = result.parent;
          }
        } else {
          result = new ResourcePath<D>(result, segment, dataFunction);
        }
      }
    }
    return result;
  }

  /**
   * @see ResourcePath#create(String)
   * @see ResourcePath#isRoot()
   *
   * @param <D> is the type of the {@link #getData() generic data}. For simple usage just {@link Void}.
   * @param scanner the fresh {@link CharSequenceScanner} for the entire path (normalized from of
   *        {@code originalPath}). Only the root segment shall be consumed.
   * @param originalPath is the original path without normalization of slashed (may contain backslashes).
   * @param dataFunction - the {@link Function} to {@link Function#apply(Object) create} the
   *        {@link #getData() data}.
   * @return the {@link ResourcePath#isRoot() root path segment}.
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  private static <D> ResourcePath<D> createRootPathSegment(CharSequenceScanner scanner, String originalPath,
      Function<ResourcePath<D>, D> dataFunction) {

    char first = scanner.peek();
    if (first == PATH_SEGMENT_SEPARATOR_CHAR) {
      if (originalPath.startsWith(UNC_PATH_PREFIX)) {
        scanner.setCurrentIndex(UNC_PATH_PREFIX.length());
        String uncSegment = scanner.readUntil(CharFilter.FILE_SEPARATOR_FILTER, true);
        return new ResourcePathRootUnc<D>(uncSegment, dataFunction);
      }
      if (((Function) dataFunction) == VOID_FUNCTION) {
        return (ResourcePath<D>) ROOT_ABSOLUTE;
      } else {
        return new ResourcePathRootAbsolute<D>(dataFunction);
      }
    } else {
      if (first == HOME_PATH_CHAR) {
        // home root
        scanner.setCurrentIndex(1);
        String user = scanner.readUntil(PATH_SEGMENT_SEPARATOR_CHAR, true);
        if (user.isEmpty()) {
          if (((Function) dataFunction) == VOID_FUNCTION) {
            return (ResourcePath<D>) ROOT_HOME;
          } else {
            return new ResourcePathRootHome<D>(dataFunction);
          }
        }
        return new ResourcePathRootHome<D>(user, dataFunction);
      } else if (CharFilter.ASCII_LETTER_FILTER.accept(first)) {
        // is windows drive letter?
        int length = scanner.getLength();
        if ((length > 1) && (scanner.charAt(1) == WINDOWS_DRIVE_LETTER_SUFFIX)) {
          if ((length == 2) || (scanner.charAt(2) == PATH_SEGMENT_SEPARATOR_CHAR)) {
            scanner.setCurrentIndex(2);
            return new ResourcePathRootWindows(originalPath.substring(0, 1), dataFunction);
          }
        }
      }
      int authorityIndex = originalPath.indexOf(URL_SCHEME_AUTHORITY_SEPARATOR);
      if (authorityIndex > 0) {
        String scheme = scanner.read(authorityIndex);
        scanner.require(URL_SCHEME_AUTHORITY_SEPARATOR, false);
        String authority = scanner.readUntil(PATH_SEGMENT_SEPARATOR_CHAR, true);
        return new ResourcePathRootUrl(scheme, authority, dataFunction);
      }
      if (((Function) dataFunction) == VOID_FUNCTION) {
        return (ResourcePath<D>) ROOT_RELATIVE;
      } else {
        return new ResourcePathRootRelative<D>(dataFunction);
      }
    }
  }

  /**
   * Implementation of Unix/Linux absolute root.
   *
   * @param <D> is the type of the {@link #getData() generic data}. For simple usage just {@link Void}.
   */
  public static final class ResourcePathRootAbsolute<D> extends ResourcePath<D> {

    /** UID for serialization. */
    private static final long serialVersionUID = 1L;

    /**
     * The constructor.
     *
     * @param dataFunction - the {@link Function} to {@link Function#apply(Object) create} the
     *        {@link #getData() data}.
     */
    private ResourcePathRootAbsolute(Function<ResourcePath<D>, D> dataFunction) {

      super(ResourcePath.PATH_SEGMENT_SEPARATOR, dataFunction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAbsolute() {

      return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isAppendSeparator() {

      return false;
    }
  }

  /**
   * Implementation of Windows absolute root drive.
   *
   * @param <D> is the type of the {@link #getData() generic data}. For simple usage just {@link Void}.
   */
  public static class ResourcePathRootWindows<D> extends ResourcePath<D> {

    /** UID for serialization. */
    private static final long serialVersionUID = 1L;

    /**
     * The constructor.
     *
     * @param drive is the Windows drive letter (e.g. "C" for "C:\\").
     * @param dataFunction - the {@link Function} to {@link Function#apply(Object) create} the
     *        {@link #getData() data}.
     */
    public ResourcePathRootWindows(String drive, Function<ResourcePath<D>, D> dataFunction) {

      super(drive + WINDOWS_DRIVE_LETTER_SUFFIX, dataFunction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAbsolute() {

      return true;
    }
  }

  /**
   * Implementation of relative root.
   *
   * @param <D> is the type of the {@link #getData() generic data}. For simple usage just {@link Void}.
   */
  public static final class ResourcePathRootRelative<D> extends ResourcePath<D> {

    /** UID for serialization. */
    private static final long serialVersionUID = 1L;

    /**
     * The constructor.
     *
     * @param dataFunction - the {@link Function} to {@link Function#apply(Object) create} the
     *        {@link #getData() data}.
     */
    private ResourcePathRootRelative(Function<ResourcePath<D>, D> dataFunction) {

      super("", dataFunction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAbsolute() {

      return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isAppendSeparator() {

      return false;
    }
  }

  /**
   * Implementation of home absolute root (~ or ~login).
   *
   * @param <D> is the type of the {@link #getData() generic data}. For simple usage just {@link Void}.
   */
  public static class ResourcePathRootHome<D> extends ResourcePath<D> {

    /** UID for serialization. */
    private static final long serialVersionUID = 1L;

    /**
     * The constructor.
     *
     * @param dataFunction - the {@link Function} to {@link Function#apply(Object) create} the
     *        {@link #getData() data}.
     */
    public ResourcePathRootHome(Function<ResourcePath<D>, D> dataFunction) {

      this("", dataFunction);
    }

    /**
     * The constructor.
     *
     * @param user the name of the user.
     * @param dataFunction - the {@link Function} to {@link Function#apply(Object) create} the
     *        {@link #getData() data}.
     */
    public ResourcePathRootHome(String user, Function<ResourcePath<D>, D> dataFunction) {

      super(HOME_PATH_CHAR + user, dataFunction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAbsolute() {

      return true;
    }
  }

  /**
   * Implementation of UNC absolute root.
   *
   * @param <D> is the type of the {@link #getData() generic data}. For simple usage just {@link Void}.
   */
  public static class ResourcePathRootUnc<D> extends ResourcePath<D> {

    /** UID for serialization. */
    private static final long serialVersionUID = 1L;

    /**
     * The constructor.
     *
     * @param uncAuthority is the authority or host of the UNC root.
     * @param dataFunction - the {@link Function} to {@link Function#apply(Object) create} the
     *        {@link #getData() data}.
     */
    public ResourcePathRootUnc(String uncAuthority, Function<ResourcePath<D>, D> dataFunction) {

      super(UNC_PATH_PREFIX + uncAuthority, dataFunction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAbsolute() {

      return true;
    }
  }

  /**
   * Implementation of Unix/Linux absolute root.
   *
   * @param <D> is the type of the {@link #getData() generic data}. For simple usage just {@link Void}.
   */
  public static class ResourcePathRootUrl<D> extends ResourcePath<D> {

    /** UID for serialization. */
    private static final long serialVersionUID = 1L;

    /** @see #getScheme() */
    private final String scheme;

    /** @see #getAuthority() */
    private final String authority;

    /**
     * The constructor.
     *
     * @param scheme - see {@link #getScheme()}.
     * @param authority - see {@link #getAuthority()}.
     * @param dataFunction - the {@link Function} to {@link Function#apply(Object) create} the
     *        {@link #getData() data}.
     */
    public ResourcePathRootUrl(String scheme, String authority, Function<ResourcePath<D>, D> dataFunction) {

      super(scheme + URL_SCHEME_AUTHORITY_SEPARATOR + authority, dataFunction);
      this.scheme = scheme;
      this.authority = authority;
    }

    /**
     * @return the URL scheme, e.g. "http", "https", "ftp", etc.
     */
    public String getScheme() {

      return this.scheme;
    }

    /**
     * @return the authority, e.g. "github.com", "localhost:8080", "127.0.0.1:8443", "[::1]:8080".
     */
    public String getAuthority() {

      return this.authority;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAbsolute() {

      return true;
    }
  }

}
