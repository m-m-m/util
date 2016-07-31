/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import net.sf.mmm.util.cli.NlsBundleUtilCliRoot;
import net.sf.mmm.util.cli.api.AbstractMain;
import net.sf.mmm.util.cli.api.AbstractVersionedMain;
import net.sf.mmm.util.cli.api.CliOption;
import net.sf.mmm.util.component.api.IocContainer;
import net.sf.mmm.util.file.api.FileCreationFailedException;
import net.sf.mmm.util.filter.api.Filter;
import net.sf.mmm.util.nls.api.NlsBundle;
import net.sf.mmm.util.nls.api.NlsBundleOptions;
import net.sf.mmm.util.nls.api.NlsBundleWithLookup;
import net.sf.mmm.util.nls.impl.NlsResourceBundleLocator;
import net.sf.mmm.util.nls.impl.NlsResourceBundleLocatorImpl;
import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.reflect.base.AssignableFromFilter;
import net.sf.mmm.util.reflect.base.ReflectionUtilImpl;

/**
 * The abstract base class for a {@link AbstractMain CLI program} to process {@link NlsBundle}s or
 * {@link AbstractResourceBundle}s for localization maintenance.
 *
 * @see ResourceBundleSynchronizer
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 7.3.0
 */
public abstract class AbstractResourceBundleCli extends AbstractVersionedMain {

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

  private static final String DEFAULT_ENCODING = "UTF-8";

  private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss Z";

  @CliOption(name = OPTION_PATH, aliases = "-p", operand = "DIR", //
      usage = NlsBundleUtilCliRoot.MSG_SYNCHRONIZER_USAGE_PATH)
  private String path;

  @CliOption(name = OPTION_ENCODING, aliases = "-e", operand = "ENC", //
      usage = NlsBundleUtilCliRoot.MSG_SYNCHRONIZER_USAGE_ENCODING)
  private String encoding;

  private String newline;

  @CliOption(name = OPTION_DATE_PATTERN, aliases = "-d", operand = "PATTERN", //
      usage = NlsBundleUtilCliRoot.MSG_SYNCHRONIZER_USAGE_DATE_PATTERN)
  private String datePattern;

  @CliOption(name = OPTION_BUNDLE_CLASS, aliases = "-b", operand = "CLASS", //
      usage = NlsBundleUtilCliRoot.MSG_SYNCHRONIZER_USAGE_BUNDLE_CLASS)
  private List<Class<?>> bundleClasses;

  private NlsResourceBundleLocator resourceBundleLocator;

  private ReflectionUtil reflectionUtil;

  private NlsBundleHelper bundleHelper;

  /**
   * The constructor.
   */
  public AbstractResourceBundleCli() {

    super();
    this.path = DEFAULT_BASE_PATH;
    this.datePattern = DEFAULT_DATE_PATTERN;
    this.encoding = DEFAULT_ENCODING;
    this.newline = "\n";
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
   * This method gets the locales of the bundles that should be {@link #synchronize(NlsBundleDescriptor) synchronized}.
   * Examples for locales (entries of the returned array) are {@code ""}, {@code "en"}, or {@code "en_GB"}.
   *
   * @return the locales to create/update.
   */
  public abstract String[] getLocales();

  /**
   * This method sets the {@link #getLocales() locales}.
   *
   * @param locales are the locales to set
   */
  public abstract void setLocales(String[] locales);

  /**
   * This method sets the {@link #getLocales() locales}.
   *
   * @param locales are the locales to set
   */
  public void setLocales(Locale... locales) {

    String[] array = new String[locales.length];
    for (int i = 0; i < locales.length; i++) {
      array[i] = locales[i].toString();
    }
    setLocales(array);
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
   * This method gets the encoding used to read and write the bundles. The default is {@code UTF-8}.
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
   * This method sets the newline string used to terminate a line in the resource bundle. The default is LF ({@code \n}
   * ).
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
  public void synchronize(NlsBundleDescriptor bundle) throws IOException {

    PrintWriter out = getStandardOutput();
    if (bundle.getMessages().isEmpty()) {
      out.println(bundle.getQualifiedName() + " is empty - noting to do!");
      return;
    }
    List<String> locales = getLocales(bundle);
    if (locales.isEmpty()) {
      getStandardError().println("No localized bundles for " + bundle.getQualifiedName() + " on your classpath!");
      return;
    }
    SimpleDateFormat sdf = new SimpleDateFormat(this.datePattern);
    String date = sdf.format(new Date());
    for (String locale : locales) {
      File targetFile = getTargetFileMkdirs(bundle, locale);
      synchronize(bundle, locale, targetFile, date);
    }
  }

  protected File getTargetFileMkdirs(NlsBundleDescriptor bundle, String locale) {

    File targetFile = getTargetFile(bundle, locale);
    File directory = targetFile.getParentFile();
    if (!directory.exists()) {
      boolean success = directory.mkdirs();
      if (!success) {
        throw new FileCreationFailedException(directory);
      }
    }
    return targetFile;
  }

  protected abstract File getTargetFile(NlsBundleDescriptor bundle, String locale);

  /**
   * @param bundle the {@link NlsBundleDescriptor}.
   * @return the {@link #getLocales() locales} to process for the bundle.
   */
  protected List<String> getLocales(NlsBundleDescriptor bundle) {

    return Arrays.asList(getLocales());
  }

  /**
   * Like {@link #synchronize(NlsBundleDescriptor)} but for a single {@link Locale}.
   *
   * @param bundle the bundle instance as java object.
   * @param locale the locale to synchronize as string.
   * @param targetFile the {@link File} to write to. May not yet exists but parent folder exists.
   * @param date is the current date as string.
   * @throws IOException if an I/O problem occurred.
   */
  protected abstract void synchronize(NlsBundleDescriptor bundle, String locale, File targetFile, String date)
      throws IOException;

  @Override
  protected int runDefaultMode() throws Exception {

    if (this.bundleClasses == null) {
      List<ResourceBundle> bundleList = getResourceBundleLocator().findBundles();
      for (ResourceBundle resourceBundle : bundleList) {
        if (isProductive(resourceBundle.getClass())) {
          synchronize(new NlsBundleDescriptor(resourceBundle));
        }
      }
      Set<String> allClasses = getReflectionUtil().findClassNames("", true);
      Filter<? super Class<?>> filter = new AssignableFromFilter(NlsBundle.class, true);
      @SuppressWarnings({ "unchecked", "rawtypes" })
      Set<Class<? extends NlsBundle>> nlsBundleClasses = (Set) getReflectionUtil().loadClasses(allClasses, filter);
      for (Class<? extends NlsBundle> bundleClass : nlsBundleClasses) {
        if (bundleClass != NlsBundleWithLookup.class) {
          if (isProductive(bundleClass)) {
            synchronize(new NlsBundleDescriptor(bundleClass));
          }
        }
      }
    } else {
      for (Class<?> bundleClass : this.bundleClasses) {
        NlsBundleDescriptor bundle;
        if (ResourceBundle.class.isAssignableFrom(bundleClass)) {
          ResourceBundle resourceBundle = (ResourceBundle) bundleClass.newInstance();
          bundle = new NlsBundleDescriptor(resourceBundle);
        } else if (NlsBundle.class.isAssignableFrom(bundleClass)) {
          @SuppressWarnings("unchecked")
          Class<? extends NlsBundle> bundleInterface = (Class<? extends NlsBundle>) bundleClass;
          bundle = new NlsBundleDescriptor(bundleInterface);
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
   * @return {@code true} if {@link NlsBundleOptions#productive() productive}, {@code false} otherwise.
   */
  private boolean isProductive(Class<?> bundleClass) {

    NlsBundleOptions options = bundleClass.getAnnotation(NlsBundleOptions.class);
    if (options != null) {
      return options.productive();
    }
    return true;
  }

}
