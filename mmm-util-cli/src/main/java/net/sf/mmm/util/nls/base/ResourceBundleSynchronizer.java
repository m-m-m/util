/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
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
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

import net.sf.mmm.util.cli.NlsBundleUtilCliRoot;
import net.sf.mmm.util.cli.api.AbstractVersionedMain;
import net.sf.mmm.util.cli.api.CliClass;
import net.sf.mmm.util.cli.api.CliMode;
import net.sf.mmm.util.cli.api.CliOption;
import net.sf.mmm.util.component.api.IocContainer;
import net.sf.mmm.util.file.api.FileCreationFailedException;
import net.sf.mmm.util.filter.api.Filter;
import net.sf.mmm.util.nls.api.NlsBundle;
import net.sf.mmm.util.nls.api.NlsBundleMessage;
import net.sf.mmm.util.nls.api.NlsBundleOptions;
import net.sf.mmm.util.nls.api.NlsBundleWithLookup;
import net.sf.mmm.util.nls.impl.NlsResourceBundleLocator;
import net.sf.mmm.util.nls.impl.NlsResourceBundleLocatorImpl;
import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.reflect.base.AssignableFromFilter;
import net.sf.mmm.util.reflect.base.ReflectionUtilImpl;

/**
 * This class can be used to create and update the localized bundles (properties) from an {@link AbstractResourceBundle}
 * . <br>
 * It is a main-program. Simply call it with the parameter "--help" to get help. <b>ATTENTION:</b><br>
 * This class only works with java 6 or above.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@CliClass(usage = NlsBundleUtilCliRoot.MSG_SYNCHRONIZER_USAGE)
@CliMode(id = CliMode.ID_DEFAULT, title = NlsBundleUtilCliRoot.INF_MAIN_MODE_DEFAULT, //
usage = NlsBundleUtilCliRoot.MSG_SYNCHRONIZER_USAGE_MODE_DEFAULT)
public class ResourceBundleSynchronizer extends AbstractVersionedMain {

  /**
   * The command-line option to {@link #setDatePattern(String) set the date-pattern}.
   */
  public static final String OPTION_DATE_PATTERN = "--date-pattern";

  /** The command-line option to {@link #setEncoding(String) set the encoding}. */
  public static final String OPTION_ENCODING = "--encoding";

  /** The command-line option to {@link #setPath(String) set the path}. */
  public static final String OPTION_PATH = "--path";

  /** The command-line option to set the bundle-class. */
  public static final String OPTION_BUNDLE_CLASS = "--bundle";

  /** The command-line option to set the locales. */
  public static final String OPTION_LOCALE = "--locale";

  /** @see #getPath() */
  protected static final String DEFAULT_BASE_PATH = "src/main/resources";

  /** @see #getEncoding() */
  private static final String DEFAULT_ENCODING = "UTF-8";

  /** @see #getEncoding() */
  private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss Z";

  /** @see #getPath() */
  @CliOption(name = OPTION_PATH, aliases = "-p", operand = "DIR", //
  usage = NlsBundleUtilCliRoot.MSG_SYNCHRONIZER_USAGE_PATH)
  private String path;

  /** @see #getEncoding() */
  @CliOption(name = OPTION_ENCODING, aliases = "-e", operand = "ENC", //
  usage = NlsBundleUtilCliRoot.MSG_SYNCHRONIZER_USAGE_ENCODING)
  private String encoding;

  /** @see #getNewline() */
  private String newline;

  /** @see #getDatePattern() */
  @CliOption(name = OPTION_DATE_PATTERN, aliases = "-d", operand = "PATTERN", //
  usage = NlsBundleUtilCliRoot.MSG_SYNCHRONIZER_USAGE_DATE_PATTERN)
  private String datePattern;

  /** @see #getLocales() */
  @CliOption(name = OPTION_LOCALE, aliases = "-l", operand = "LOCALE", //
  required = true, usage = NlsBundleUtilCliRoot.MSG_SYNCHRONIZER_USAGE_LOCALES)
  private String[] locales;

  /** @see #getBundleClasses() */
  @CliOption(name = OPTION_BUNDLE_CLASS, aliases = "-b", operand = "CLASS", //
  usage = NlsBundleUtilCliRoot.MSG_SYNCHRONIZER_USAGE_BUNDLE_CLASS)
  private List<Class<?>> bundleClasses;

  /** @see #getResourceBundleLocator() */
  private NlsResourceBundleLocator resourceBundleLocator;

  /** @see #getReflectionUtil() */
  private ReflectionUtil reflectionUtil;

  /** @see #getBundleHelper() */
  private NlsBundleHelper bundleHelper;

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
   * This method gets the locales of the bundles that should be {@link #synchronize(NlsBundleContainer) synchronized}.
   * Examples for locales (entries of the returned array) are <code>""</code>, <code></code>
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
   * This method gets the base-path where the bundles are written to. They will appear there under their appropriate
   * classpath. The default is {@link #DEFAULT_BASE_PATH}.
   *
   * @return the basePath is the base path where the resource bundles are written to.
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
   * This method gets the encoding used to read and write the bundles. The default is <code>UTF-8</code>.
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
   * This method sets the newline string used to terminate a line in the resource bundle. The default is LF (
   * <code>\n</code>).
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
   * This method gets the {@link Class} reflecting the {@link ResourceBundle} to synchronize.
   *
   * @return the bundle-class.
   */
  public List<Class<?>> getBundleClasses() {

    return this.bundleClasses;
  }

  /**
   * This method sets the {@link #getBundleClasses() bundle-classes}.
   *
   * @param bundleClasses is the {@link List} of bundle-classes to set
   */
  public void setBundleClasses(List<Class<?>> bundleClasses) {

    this.bundleClasses = bundleClasses;
  }

  /**
   * This method gets the {@link NlsResourceBundleLocator}.
   *
   * @return the {@link NlsResourceBundleLocator}.
   */
  public NlsResourceBundleLocator getResourceBundleLocator() {

    if (this.resourceBundleLocator == null) {
      IocContainer container = getIocContainer();
      if (container == null) {
        NlsResourceBundleLocatorImpl impl = new NlsResourceBundleLocatorImpl();
        impl.initialize();
        this.resourceBundleLocator = impl;
      } else {
        this.resourceBundleLocator = container.get(NlsResourceBundleLocator.class);
      }
    }
    return this.resourceBundleLocator;
  }

  /**
   * @param resourceBundleFinder is the resourceBundleFinder to set
   */
  public void setResourceBundleLocator(NlsResourceBundleLocator resourceBundleFinder) {

    this.resourceBundleLocator = resourceBundleFinder;
  }

  /**
   * This method gets the {@link ReflectionUtil}.
   *
   * @return the {@link ReflectionUtil}.
   */
  public ReflectionUtil getReflectionUtil() {

    if (this.reflectionUtil == null) {
      IocContainer container = getIocContainer();
      if (container == null) {
        this.reflectionUtil = ReflectionUtilImpl.getInstance();
      } else {
        this.reflectionUtil = container.get(ReflectionUtil.class);
      }
    }
    return this.reflectionUtil;
  }

  /**
   * @param reflectionUtil is the {@link ReflectionUtil}.
   */
  public void setReflectionUtil(ReflectionUtil reflectionUtil) {

    this.reflectionUtil = reflectionUtil;
  }

  /**
   * @return the {@link NlsBundleHelper}.
   */
  public NlsBundleHelper getBundleHelper() {

    if (this.bundleHelper == null) {
      this.bundleHelper = NlsBundleHelper.getInstance();
    }
    return this.bundleHelper;
  }

  /**
   * @param bundleHelper is the {@link NlsBundleHelper}.
   */
  public void setBundleHelper(NlsBundleHelper bundleHelper) {

    this.bundleHelper = bundleHelper;
  }

  /**
   * This method synchronizes (creates or updates) the localized bundles (properties). If a bundle already exists, it
   * will NOT just be overwritten but the missing keys are appended to the end of the file. If no keys are missing, the
   * existing file remains untouched.
   *
   * @param bundle is the bundle instance as java object.
   * @throws IOException if the operation failed with an input/output error.
   */
  public void synchronize(NlsBundleContainer bundle) throws IOException {

    PrintWriter out = getStandardOutput();
    if (bundle.getProperties().isEmpty()) {
      out.println(bundle.getClass().getName() + " is empty - noting to do!");
      return;
    }
    SimpleDateFormat sdf = new SimpleDateFormat(this.datePattern);
    String date = sdf.format(new Date());
    String propertyPath = this.path + File.separatorChar + bundle.getName().replace('.', File.separatorChar);
    File directory = new File(propertyPath).getParentFile();
    if (!directory.exists()) {
      boolean success = directory.mkdirs();
      if (!success) {
        throw new FileCreationFailedException(directory);
      }
    }
    // synchronize(bundle, "", propertyPath, date);
    for (String locale : this.locales) {
      synchronize(bundle, locale, propertyPath, date);
    }
  }

  /**
   * Like {@link #synchronize(NlsBundleContainer)} but for a single {@link Locale}.
   *
   * @param bundle is the bundle instance as java object.
   * @param locale is the locale to synchronize as string.
   * @param propertyPath is the path to the property-file excluding locale-suffix.
   * @param date is the current date as string.
   * @throws IOException if an I/O problem occurred.
   */
  protected void synchronize(NlsBundleContainer bundle, String locale, String propertyPath, String date)
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
    Map<String, String> bundleProperties = bundle.getProperties();
    for (Entry<String, String> entry : bundleProperties.entrySet()) {
      String key = entry.getKey();
      if (!existingBundle.containsKey(key)) {
        String value = entry.getValue();
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
  protected int runDefaultMode() throws Exception {

    if (this.bundleClasses == null) {
      List<ResourceBundle> bundleList = getResourceBundleLocator().findBundles();
      for (ResourceBundle resourceBundle : bundleList) {
        if (isProductive(resourceBundle.getClass())) {
          synchronize(new NlsBundleContainer(resourceBundle));
        }
      }
      Set<String> allClasses = getReflectionUtil().findClassNames("", true);
      Filter<? super Class<?>> filter = new AssignableFromFilter(NlsBundle.class, true);
      @SuppressWarnings({ "unchecked", "rawtypes" })
      Set<Class<? extends NlsBundle>> nlsBundleClasses = (Set) getReflectionUtil().loadClasses(allClasses, filter);
      for (Class<? extends NlsBundle> bundleClass : nlsBundleClasses) {
        if (bundleClass != NlsBundleWithLookup.class) {
          if (isProductive(bundleClass)) {
            synchronize(new NlsBundleContainer(bundleClass));
          }
        }
      }
    } else {
      for (Class<?> bundleClass : this.bundleClasses) {
        NlsBundleContainer bundle;
        if (ResourceBundle.class.isAssignableFrom(bundleClass)) {
          ResourceBundle resourceBundle = (ResourceBundle) bundleClass.newInstance();
          bundle = new NlsBundleContainer(resourceBundle);
        } else if (NlsBundle.class.isAssignableFrom(bundleClass)) {
          @SuppressWarnings("unchecked")
          Class<? extends NlsBundle> bundleInterface = (Class<? extends NlsBundle>) bundleClass;
          bundle = new NlsBundleContainer(bundleInterface);
        } else {
          throw new IllegalArgumentException(bundleClass.getName());
        }
        synchronize(bundle);
      }
    }
    return EXIT_CODE_OK;
  }

  /**
   * Determines if the given {@code bundleClass} is {@link NlsBundleOptions#productive() productive}.
   *
   * @param bundleClass is the {@link Class} to test.
   * @return <code>true</code> if {@link NlsBundleOptions#productive() productive}, <code>false</code> otherwise.
   */
  private boolean isProductive(Class<?> bundleClass) {

    NlsBundleOptions options = bundleClass.getAnnotation(NlsBundleOptions.class);
    if (options != null) {
      return options.productive();
    }
    return true;
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

  /**
   * This inner class is a container for {@link ResourceBundle} or {@link NlsBundle}.
   */
  public class NlsBundleContainer {

    /** The {@link ResourceBundle} or <code>null</code> if {@link #nlsBundleInterface} is given. */
    private final ResourceBundle resourceBundle;

    /** The {@link NlsBundle}-interface or <code>null</code> if {@link #resourceBundle} is given. */
    private final Class<? extends NlsBundle> nlsBundleInterface;

    /** @see #getProperties() */
    private Map<String, String> properties;

    /**
     * The constructor.
     *
     * @param resourceBundle is the {@link ResourceBundle}.
     */
    public NlsBundleContainer(ResourceBundle resourceBundle) {

      super();
      this.resourceBundle = resourceBundle;
      this.nlsBundleInterface = null;
    }

    /**
     * The constructor.
     *
     * @param nlsBundleInterface is the {@link NlsBundle} interface.
     */
    public NlsBundleContainer(Class<? extends NlsBundle> nlsBundleInterface) {

      super();
      this.nlsBundleInterface = nlsBundleInterface;
      this.resourceBundle = null;
    }

    /**
     * @return the fully qualified name of the bundle in java class notation.
     */
    public String getName() {

      if (this.nlsBundleInterface != null) {
        return getBundleHelper().getQualifiedLocation(this.nlsBundleInterface).getName();
      }
      return this.resourceBundle.getClass().getName();
    }

    /**
     * @return the properties
     */
    public Map<String, String> getProperties() {

      if (this.properties == null) {
        this.properties = new HashMap<>();
        if (this.resourceBundle == null) {
          for (Method method : this.nlsBundleInterface.getMethods()) {
            if (getBundleHelper().isNlsBundleMethod(method, false)) {
              String key = getBundleHelper().getKey(method);
              String message = "";
              NlsBundleMessage messageAnnotation = method.getAnnotation(NlsBundleMessage.class);
              if (messageAnnotation != null) {
                message = messageAnnotation.value();
              }
              this.properties.put(key, message);
            }
          }
        } else {
          Enumeration<String> keyEnum = this.resourceBundle.getKeys();
          while (keyEnum.hasMoreElements()) {
            String key = keyEnum.nextElement();
            this.properties.put(key, this.resourceBundle.getString(key));
          }
        }
      }
      return this.properties;
    }
  }

}
