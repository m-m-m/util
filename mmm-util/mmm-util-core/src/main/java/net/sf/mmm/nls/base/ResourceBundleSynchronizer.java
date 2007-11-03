/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.nls.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import net.sf.mmm.nls.NlsBundleNlsCore;
import net.sf.mmm.nls.api.NlsTranslator;

/**
 * This class can be used to create and update the localized bundles
 * (properties) from an {@link AbstractResourceBundle}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ResourceBundleSynchronizer {

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
      System.out.println(bundle.getClass().getName() + " is empty - noting to do!");
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
      Properties existingBundle = new Properties();
      boolean update = file.exists();
      if (update) {
        System.out.println("Updating " + file.getPath());
        FileInputStream in = new FileInputStream(file);
        try {
          Reader reader = new InputStreamReader(in, this.encoding);
          existingBundle.load(reader);
        } finally {
          in.close();
        }
      } else {
        System.out.println("Creating " + file.getPath());
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
          value = value.replace("\n", "\\n\\");
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
   * 
   * @param code is the exit-code.
   */
  public static void usage(int code) {

    NlsMessageImpl message = new NlsMessageImpl(NlsBundleNlsCore.MSG_SYNCHRONIZER_USAGE,
        ResourceBundleSynchronizer.class.getName(), DEFAULT_ENCODING, DEFAULT_BASE_PATH,
        DEFAULT_DATE_PATTERN, NlsBundleNlsCore.class.getName());
    NlsTranslator nationalizer = new SimpleStringTranslator(new NlsBundleNlsCore(), Locale
        .getDefault());
    System.out.println(message.getLocalizedMessage(nationalizer));
    System.exit(code);
  }

  /**
   * This is the main method used to run this class as application.
   * 
   * @param arguments are the commandline arguments.
   * @throws Exception if the operation fails for arbitrary reasons.
   */
  public static void main(String[] arguments) throws Exception {

    ResourceBundleSynchronizer synchronizer = new ResourceBundleSynchronizer();
    String bundleClassName = null;
    List<String> locales = new ArrayList<String>();
    locales.add("");
    for (int argIndex = 0; argIndex < arguments.length; argIndex++) {
      String arg = arguments[argIndex];
      if ((arg.length() > 0) && (arg.charAt(0) == '-')) {
        if ("--help".equals(arg)) {
          usage(0);
        }
        argIndex++;
        if ((bundleClassName != null) || (argIndex < arguments.length)) {
          usage(-1);
        }
        String value = arguments[argIndex];
        if ("--encoding".equals(arg)) {
          synchronizer.setEncoding(value);
        } else if ("--path".equals(arg)) {
          synchronizer.setBasePath(value);
        } else if ("--date-pattern".equals(arg)) {
          synchronizer.setDatePattern(value);
        } else {
          System.out.println("Error: unknown option '" + arg + "'!");
          usage(-1);
        }
      } else if (bundleClassName == null) {
        bundleClassName = arg;
      } else {
        locales.add(arg);
      }
    }
    if (bundleClassName == null) {
      System.out.println("Error: <bundle-class> not specified!");
      usage(-1);
    }
    String[] localeArray = locales.toArray(new String[locales.size()]);
    synchronizer.setLocales(localeArray);
    Class<?> bundleClass = Class.forName(bundleClassName);
    if (!ResourceBundle.class.isAssignableFrom(bundleClass)) {
      throw new IllegalArgumentException("Given class '" + bundleClassName + "' does NOT extend '"
          + ResourceBundle.class.getName() + "'!");
    }
    ResourceBundle bundle = (ResourceBundle) bundleClass.newInstance();
    synchronizer.synchronize(bundle);
  }

}
