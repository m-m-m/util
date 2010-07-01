/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import net.sf.mmm.util.NlsBundleUtilCore;
import net.sf.mmm.util.cli.api.AbstractVersionedMain;
import net.sf.mmm.util.cli.api.CliArgument;
import net.sf.mmm.util.cli.api.CliClass;
import net.sf.mmm.util.cli.api.CliMode;
import net.sf.mmm.util.cli.api.CliModeObject;
import net.sf.mmm.util.cli.api.CliOption;
import net.sf.mmm.util.io.api.StreamUtil;
import net.sf.mmm.util.io.base.StreamUtilImpl;

/**
 * This class can be used to create and update the localized bundles
 * (properties) from an {@link AbstractResourceBundle}.<br>
 * It is a main-program. Simply call it with the parameter "--help" to get help.
 * <b>ATTENTION:</b><br>
 * This class only works with java 6 or above.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@CliClass(usage = NlsBundleUtilCore.MSG_SYNCHRONIZER_USAGE)
public class ResourceBundleSynchronizer extends AbstractVersionedMain {

  /**
   * The command-line option to {@link #setDatePattern(String) set the
   * date-pattern}.
   */
  public static final String OPTION_DATE_PATTERN = "--date-pattern";

  /** The command-line option to {@link #setEncoding(String) set the encoding}. */
  public static final String OPTION_ENCODING = "--encoding";

  /** The command-line option to {@link #setPath(String) set the path}. */
  public static final String OPTION_PATH = "--path";

  /** The command-line option to set the bundle-class. */
  public static final String OPTION_BUNDLE_CLASS = "--bundle";

  /** @see #getPath() */
  private static final String DEFAULT_BASE_PATH = "src/main/resources";

  /** @see #getEncoding() */
  private static final String DEFAULT_ENCODING = "UTF-8";

  /** @see #getEncoding() */
  private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss Z";

  /** @see #getLocales() */
  @CliArgument(name = "locales", addNextTo = CliArgument.NAME_LAST)
  private String[] locales;

  /** @see #getPath() */
  @CliOption(name = OPTION_PATH, usage = NlsBundleUtilCore.MSG_SYNCHRONIZER_USAGE_PATH, operand = "DIR", aliases = "-p")
  private String path;

  /** @see #getEncoding() */
  @CliOption(name = OPTION_ENCODING, usage = NlsBundleUtilCore.MSG_SYNCHRONIZER_USAGE_ENCODING, operand = "ENC", aliases = "-e")
  private String encoding;

  /** @see #getNewline() */
  private String newline;

  /** @see #getDatePattern() */
  @CliOption(name = OPTION_DATE_PATTERN, usage = NlsBundleUtilCore.MSG_SYNCHRONIZER_USAGE_DATE_PATTERN, operand = "PATTERN", aliases = "-d")
  private String datePattern;

  /** @see #getBundleClass() */
  @CliOption(name = OPTION_BUNDLE_CLASS, usage = NlsBundleUtilCore.MSG_SYNCHRONIZER_USAGE_BUNDLE_CLASS, operand = "CLASS", aliases = "-b", required = true)
  private Class<? extends ResourceBundle> bundleClass;

  /** @see #getStreamUtil() */
  private StreamUtil streamUtil;

  /**
   * The constructor.
   */
  public ResourceBundleSynchronizer() {

    super();
    this.path = DEFAULT_BASE_PATH;
    this.datePattern = DEFAULT_DATE_PATTERN;
    this.encoding = DEFAULT_ENCODING;
    this.newline = "\n";
    this.locales = null;
  }

  /**
   * This method gets the pattern used to format the date comment.
   * 
   * @see SimpleDateFormat
   * 
   * @return the date pattern.
   */
  public String getDatePattern() {

    return this.datePattern;
  }

  /**
   * @param datePattern the datePattern to set
   */
  public void setDatePattern(String datePattern) {

    this.datePattern = datePattern;
  }

  /**
   * This method gets the locales of the bundles that should be
   * {@link #synchronize(ResourceBundle) synchronized}. Examples for locales
   * (entries of the returned array) are <code>""</code>, <code></code>
   * 
   * @return the locales to create/update.
   */
  public String[] getLocales() {

    return this.locales;
  }

  /**
   * This method sets the {@link #getLocales() locales}.
   * 
   * @param locales are the locales to set
   */
  public void setLocales(String[] locales) {

    this.locales = locales;
  }

  /**
   * This method sets the {@link #getLocales() locales}.
   * 
   * @param locales are the locales to set
   */
  public void setLocales(Locale... locales) {

    this.locales = new String[locales.length];
    for (int i = 0; i < locales.length; i++) {
      this.locales[i] = locales[i].toString();
    }
  }

  /**
   * This method gets the base-path where the bundles are written to. They will
   * appear there under their appropriate classpath. The default is
   * {@link #DEFAULT_BASE_PATH}.
   * 
   * @return the basePath is the base path where the resource bundles are
   *         written to.
   */
  public String getPath() {

    return this.path;
  }

  /**
   * This method sets the {@link #getPath() base-path}.
   * 
   * @param basePath the basePath to set
   */
  public void setPath(String basePath) {

    this.path = basePath;
  }

  /**
   * This method gets the encoding used to read and write the bundles. The
   * default is <code>UTF-8</code>.
   * 
   * @return the encoding.
   */
  public String getEncoding() {

    return this.encoding;
  }

  /**
   * This method sets the {@link #getEncoding() encoding}.
   * 
   * @param encoding the encoding to set
   */
  public void setEncoding(String encoding) {

    this.encoding = encoding;
  }

  /**
   * This method sets the newline string used to terminate a line in the
   * resource bundle. The default is LF (<code>\n</code>).
   * 
   * @return the newline
   */
  public String getNewline() {

    return this.newline;
  }

  /**
   * @param newline the newline to set
   */
  public void setNewline(String newline) {

    this.newline = newline;
  }

  /**
   * This method gets the {@link Class} reflecting the {@link ResourceBundle} to
   * synchronize.
   * 
   * @return the bundle-class.
   */
  public Class<? extends ResourceBundle> getBundleClass() {

    return this.bundleClass;
  }

  /**
   * This method sets the {@link #getBundleClass() bundle-class}.
   * 
   * @param bundleClass is the bundle-class to set
   */
  public void setBundleClass(Class<? extends ResourceBundle> bundleClass) {

    this.bundleClass = bundleClass;
  }

  /**
   * This method gets the {@link StreamUtilImpl} instance to use.
   * 
   * @return the {@link StreamUtilImpl}.
   */
  @Override
  public StreamUtil getStreamUtil() {

    if (this.streamUtil == null) {
      this.streamUtil = StreamUtilImpl.getInstance();
    }
    return this.streamUtil;
  }

  /**
   * This method sets the {@link #getStreamUtil() StreamUtil}.
   * 
   * @param streamUtil is the {@link StreamUtilImpl} instance to use.
   */
  public void setStreamUtil(StreamUtil streamUtil) {

    this.streamUtil = streamUtil;
  }

  /**
   * This method synchronizes (creates or updates) the localized bundles
   * (properties). If a bundle already exists, it will NOT just be overwritten
   * but the missing keys are appended to the end of the file. If no keys are
   * missing, the existing file remains untouched.
   * 
   * @param bundle is the bundle instance as java object.
   * @throws IOException if the operation failed with an input/output error.
   */
  public void synchronize(ResourceBundle bundle) throws IOException {

    PrintWriter out = getStandardOutput();
    if (bundle.keySet().isEmpty()) {
      out.println(bundle.getClass().getName() + " is empty - noting to do!");
      return;
    }
    SimpleDateFormat sdf = new SimpleDateFormat(this.datePattern);
    String date = sdf.format(new Date());
    String propertyPath = this.path + File.separatorChar
        + bundle.getClass().getName().replace('.', File.separatorChar);
    new File(propertyPath).getParentFile().mkdirs();
    synchronize(bundle, "", propertyPath, date);
    for (String locale : this.locales) {
      synchronize(bundle, locale, propertyPath, date);
    }
  }

  /**
   * Like {@link #synchronize(ResourceBundle)} but for a single {@link Locale}.
   * 
   * @param bundle is the bundle instance as java object.
   * @param locale is the locale to synchronize as string.
   * @param propertyPath is the path to the property-file excluding
   *        locale-suffix.
   * @param date is the current date as string.
   * @throws IOException if an I/O problem occurred.
   */
  protected void synchronize(ResourceBundle bundle, String locale, String propertyPath, String date)
      throws IOException {

    PrintWriter out = getStandardOutput();
    StringBuffer pathBuffer = new StringBuffer(propertyPath);
    if (locale.length() > 0) {
      pathBuffer.append('_');
      pathBuffer.append(locale);
    }
    pathBuffer.append(".properties");
    File file = new File(pathBuffer.toString());
    Properties existingBundle;
    boolean update = file.exists();
    if (update) {
      out.println("Updating " + file.getPath());
      FileInputStream in = new FileInputStream(file);
      Reader reader = new InputStreamReader(in, this.encoding);
      existingBundle = getStreamUtil().loadProperties(reader);
    } else {
      out.println("Creating " + file.getPath());
      existingBundle = new Properties();
    }
    StringBuffer buffer = new StringBuffer();
    for (String key : bundle.keySet()) {
      if (!existingBundle.containsKey(key)) {
        String value = bundle.getString(key);
        buffer.append(key);
        buffer.append(" = ");
        if (locale.length() > 0) {
          buffer.append("TODO(");
          buffer.append(locale);
          buffer.append("):");
        }
        // escape newlines for properties-syntax
        value = value.replace("\r", "");
        value = value.replace("\n", "\\n");
        buffer.append(value);
        buffer.append(this.newline);
      }
    }
    if (buffer.length() > 0) {
      OutputStream outStream = new FileOutputStream(file, update);
      try {
        Writer writer = new OutputStreamWriter(outStream, this.encoding);
        try {
          if (update) {
            writer.append("# Updated ");
          } else {
            writer.append("# Generated ");
          }
          writer.append(date);
          writer.append(this.newline);
          writer.write(buffer.toString());
          writer.flush();
        } finally {
          writer.close();
        }
      } finally {
        outStream.close();
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected int run(CliModeObject mode) throws Exception {

    if (CliMode.MODE_DEFAULT.equals(mode.getId())) {
      ResourceBundle bundle = this.bundleClass.newInstance();
      synchronize(bundle);
      return EXIT_CODE_OK;
    } else {
      return super.run(mode);
    }
  }

  /**
   * This is the main method used to run this class as application.
   * 
   * @param arguments are the command-line arguments.
   */
  public static void main(String[] arguments) {

    ResourceBundleSynchronizer synchronizer = new ResourceBundleSynchronizer();
    int exitCode = synchronizer.run(arguments);
    // CHECKSTYLE:OFF (OK for main methods)
    System.exit(exitCode);
    // CHECKSTYLE:ON
  }

}
