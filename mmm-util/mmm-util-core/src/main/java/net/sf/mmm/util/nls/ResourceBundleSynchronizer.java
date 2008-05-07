/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import net.sf.mmm.util.io.StreamUtil;
import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.api.NlsTemplateResolver;
import net.sf.mmm.util.nls.base.NlsBundleSynchronizer;
import net.sf.mmm.util.nls.impl.NlsTemplateResolverImpl;

/**
 * This class can be used to create and update the localized bundles
 * (properties) from an {@link AbstractResourceBundle}.<br>
 * <b>ATTENTION:</b><br>
 * This class only works with java 6 or above.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ResourceBundleSynchronizer {

  /**
   * The commandline option to
   * {@link #setDatePattern(String) set the date-pattern}.
   */
  public static final String OPTION_DATE_PATTERN = "--date-pattern";

  /** The commandline option to {@link #setEncoding(String) set the encoding}. */
  public static final String OPTION_ENCODING = "--encoding";

  /** The commandline option to {@link #setBasePath(String) set the path}. */
  public static final String OPTION_PATH = "--path";

  /** @see #getBasePath() */
  private static final String DEFAULT_BASE_PATH = "src/main/resources";

  /** @see #getEncoding() */
  private static final String DEFAULT_ENCODING = "UTF-8";

  /** @see #getEncoding() */
  private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss Z";

  /** @see #getLocales() */
  private String[] locales;

  /** @see #getBasePath() */
  private String basePath;

  /** @see #getEncoding() */
  private String encoding;

  /** @see #getNewline() */
  private String newline;

  /** @see #getDatePattern() */
  private String datePattern;

  /** @see #getStreamUtil() */
  private StreamUtil streamUtil;

  /** @see #getOut() */
  private PrintStream out;

  /**
   * The constructor.
   */
  public ResourceBundleSynchronizer() {

    super();
    this.basePath = DEFAULT_BASE_PATH;
    this.datePattern = DEFAULT_DATE_PATTERN;
    this.encoding = DEFAULT_ENCODING;
    this.newline = "\n";
    this.locales = new String[] { "" };
    this.out = System.out;
  }

  /**
   * @return the out-stream where to print information to.
   */
  public PrintStream getOut() {

    return this.out;
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
  public void setLocales(Locale[] locales) {

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
  public String getBasePath() {

    return this.basePath;
  }

  /**
   * This method sets the {@link #getBasePath() base-path}.
   * 
   * @param basePath the basePath to set
   */
  public void setBasePath(String basePath) {

    this.basePath = basePath;
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
   * This method gets the {@link StreamUtil} instance to use.
   * 
   * @return the {@link StreamUtil}.
   */
  public StreamUtil getStreamUtil() {

    if (this.streamUtil == null) {
      this.streamUtil = StreamUtil.getInstance();
    }
    return this.streamUtil;
  }

  /**
   * This method sets the {@link #getStreamUtil() StreamUtil}.
   * 
   * @param streamUtil is the {@link StreamUtil} instance to use.
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

    if (bundle.keySet().isEmpty()) {
      this.out.println(bundle.getClass().getName() + " is empty - noting to do!");
      return;
    }
    SimpleDateFormat sdf = new SimpleDateFormat(this.datePattern);
    String date = sdf.format(new Date());
    String path = this.basePath + File.separatorChar
        + bundle.getClass().getName().replace('.', File.separatorChar);
    new File(path).getParentFile().mkdirs();
    for (String locale : this.locales) {
      StringBuffer pathBuffer = new StringBuffer(path);
      if (locale.length() > 0) {
        pathBuffer.append('_');
        pathBuffer.append(locale);
      }
      pathBuffer.append(".properties");
      File file = new File(pathBuffer.toString());
      Properties existingBundle;
      boolean update = file.exists();
      if (update) {
        this.out.println("Updating " + file.getPath());
        FileInputStream in = new FileInputStream(file);
        Reader reader = new InputStreamReader(in, this.encoding);
        existingBundle = getStreamUtil().loadProperties(reader);
      } else {
        this.out.println("Creating " + file.getPath());
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
          if (update) {
            writer.append("# Updated ");
          } else {
            writer.append("# Generated ");
          }
          writer.append(date);
          writer.append(this.newline);
          writer.write(buffer.toString());
          writer.flush();
          writer.close();
          outStream = null;
        } finally {
          if (outStream != null) {
            outStream.close();
          }
        }
      }

    }
  }

  /**
   * This method prints the usage of this class.
   */
  public void usage() {

    NlsMessage message = NlsAccess.getFactory().create(
        NlsBundleSynchronizer.MSG_SYNCHRONIZER_USAGE, ResourceBundleSynchronizer.class.getName(),
        DEFAULT_ENCODING, DEFAULT_BASE_PATH, DEFAULT_DATE_PATTERN,
        NlsBundleSynchronizer.class.getName());
    NlsTemplateResolver nationalizer = new NlsTemplateResolverImpl(new NlsBundleSynchronizer());
    this.out.println(message.getLocalizedMessage(Locale.getDefault(), nationalizer));
  }

  /**
   * The non-static version of the {@link #main(String[]) main-method}.
   * 
   * @param arguments are the commandline arguments.
   * @return the exit-code. <code>0</code> for success, anything else if
   *         something went wrong.
   * @throws Exception if the operation failed.
   */
  public int run(String[] arguments) throws Exception {

    String bundleClassName = null;
    List<String> currentLocales = new ArrayList<String>();
    currentLocales.add("");
    for (int argIndex = 0; argIndex < arguments.length; argIndex++) {
      String arg = arguments[argIndex];
      if ((arg.length() > 0) && (arg.charAt(0) == '-')) {
        if ("--help".equals(arg)) {
          usage();
          return 0;
        }
        argIndex++;
        if ((bundleClassName != null) || (argIndex >= arguments.length)) {
          usage();
          return -1;
        }
        String value = arguments[argIndex];
        if (OPTION_ENCODING.equals(arg)) {
          setEncoding(value);
        } else if (OPTION_PATH.equals(arg)) {
          setBasePath(value);
        } else if (OPTION_DATE_PATTERN.equals(arg)) {
          setDatePattern(value);
        } else {
          this.out.println("Error: unknown option '" + arg + "'!");
          usage();
          return -1;
        }
      } else if (bundleClassName == null) {
        bundleClassName = arg;
      } else {
        currentLocales.add(arg);
      }
    }
    if (bundleClassName == null) {
      this.out.println("Error: <bundle-class> not specified!");
      usage();
      return -1;
    }
    String[] localeArray = currentLocales.toArray(new String[currentLocales.size()]);
    setLocales(localeArray);
    Class<?> bundleClass;
    try {
      bundleClass = Class.forName(bundleClassName);
      if (!ResourceBundle.class.isAssignableFrom(bundleClass)) {
        throw new IllegalArgumentException("Given class '" + bundleClassName
            + "' does NOT extend '" + ResourceBundle.class.getName() + "'!");
      }
      ResourceBundle bundle = (ResourceBundle) bundleClass.newInstance();
      synchronize(bundle);
      return 0;
    } catch (Throwable e) {
      this.out.println("Error loading class '" + bundleClassName + "': " + e.getMessage());
      usage();
      return -1;
    }
  }

  /**
   * This is the main method used to run this class as application.
   * 
   * @param arguments are the commandline arguments.
   */
  public static void main(String[] arguments) {

    ResourceBundleSynchronizer synchronizer = new ResourceBundleSynchronizer();
    int exitCode;
    try {
      exitCode = synchronizer.run(arguments);
      System.exit(exitCode);
    } catch (Exception e) {
      e.printStackTrace(System.out);
      System.exit(-1);
    }
  }

}
