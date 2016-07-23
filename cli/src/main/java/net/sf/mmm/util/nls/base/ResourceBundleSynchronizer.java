/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import net.sf.mmm.util.cli.NlsBundleUtilCliRoot;
import net.sf.mmm.util.cli.api.CliClass;
import net.sf.mmm.util.cli.api.CliMode;
import net.sf.mmm.util.cli.api.CliOption;
import net.sf.mmm.util.nls.api.NlsBundle;

/**
 * This class can be used to create and update the localized bundles (properties) from an {@link NlsBundle} or
 * {@link AbstractResourceBundle}. If you do not explicitly specify the bundle to synchronize as commandline option
 * (using {@code --bundle}) this {@link ResourceBundleSynchronizer} will automatically scan your classpath for
 * {@link NlsBundle}s and synchronize all of them.<br>
 * This is a main-program to execute via command-line. Simply call it with the parameter {@code --help} to get help.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@CliClass(usage = NlsBundleUtilCliRoot.MSG_SYNCHRONIZER_USAGE)
@CliMode(id = CliMode.ID_DEFAULT, title = NlsBundleUtilCliRoot.INF_MAIN_MODE_DEFAULT, //
    usage = NlsBundleUtilCliRoot.MSG_SYNCHRONIZER_USAGE_MODE_DEFAULT)
public class ResourceBundleSynchronizer extends AbstractResourceBundleCli {

  @CliOption(name = OPTION_LOCALE, aliases = "-l", operand = "LOCALE", //
      required = true, usage = NlsBundleUtilCliRoot.MSG_SYNCHRONIZER_USAGE_LOCALES)
  private String[] locales;

  /**
   * The constructor.
   */
  public ResourceBundleSynchronizer() {

    super();
    this.locales = null;
  }

  @Override
  public String[] getLocales() {

    return this.locales;
  }

  @Override
  public void setLocales(String[] locales) {

    this.locales = locales;
  }

  @Override
  protected File getTargetFile(NlsBundleContainer bundle, String locale) {

    StringBuilder pathBuffer = new StringBuilder(getPath());
    pathBuffer.append('/');
    pathBuffer.append(bundle.getQualifiedNamePath());
    if (locale.length() > 0) {
      pathBuffer.append('_');
      pathBuffer.append(locale);
    }
    pathBuffer.append(".properties");
    File file = new File(pathBuffer.toString());
    return file;
  }

  @Override
  protected void synchronize(NlsBundleContainer bundle, String locale, File targetFile, String date)
      throws IOException {

    PrintWriter out = getStandardOutput();
    boolean update = targetFile.exists();
    Properties existingBundle = loadOrCreatePropertiesFile(out, targetFile, update);
    StringBuffer buffer = new StringBuffer();
    synchronizeProperties(bundle, locale, existingBundle, buffer);
    writeProperties(date, targetFile, update, buffer);
  }

  private Properties loadOrCreatePropertiesFile(PrintWriter out, File file, boolean update)
      throws FileNotFoundException, UnsupportedEncodingException {

    Properties existingBundle;
    if (update) {
      out.println("Updating " + file.getPath());
      FileInputStream in = new FileInputStream(file);
      Reader reader = new InputStreamReader(in, getEncoding());
      existingBundle = getStreamUtil().loadProperties(reader);
    } else {
      out.println("Creating " + file.getPath());
      existingBundle = new Properties();
    }
    return existingBundle;
  }

  private void synchronizeProperties(NlsBundleContainer bundle, String locale, Properties existingBundle,
      StringBuffer buffer) {

    Map<String, String> bundleProperties = bundle.getProperties();
    for (Entry<String, String> entry : bundleProperties.entrySet()) {
      String key = entry.getKey();
      if (!existingBundle.containsKey(key)) {
        String value = entry.getValue();
        buffer.append(key);
        buffer.append('=');
        if (locale.length() > 0) {
          buffer.append("TODO(");
          buffer.append(locale);
          buffer.append("):");
        }
        // escape newlines for properties-syntax
        value = value.replace("\r", "");
        value = value.replace("\n", "\\n");
        buffer.append(value);
        buffer.append(getNewline());
      }
    }
  }

  private void writeProperties(String date, File file, boolean update, StringBuffer buffer)
      throws FileNotFoundException, UnsupportedEncodingException, IOException {

    if (buffer.length() > 0) {
      OutputStream outStream = new FileOutputStream(file, update);
      try {
        Writer writer = new OutputStreamWriter(outStream, getEncoding());
        try {
          if (update) {
            writer.append("# Updated ");
          } else {
            writer.append("# Generated ");
          }
          writer.append(date);
          writer.append(getNewline());
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
   * This is the main method used to run this class as application.
   *
   * @param arguments are the command-line arguments.
   */
  public static void main(String[] arguments) {

    ResourceBundleSynchronizer synchronizer = new ResourceBundleSynchronizer();
    synchronizer.runAndExit(arguments);
  }

}
