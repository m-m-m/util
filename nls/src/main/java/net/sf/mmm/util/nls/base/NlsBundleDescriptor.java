/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import net.sf.mmm.util.nls.api.NlsBundle;
import net.sf.mmm.util.nls.api.NlsBundleKey;

/**
 * This is the descriptor of an {@link NlsBundle} or {@link ResourceBundle}.
 *
 * @author hohwille
 * @since 7.3.0
 */
public class NlsBundleDescriptor {

  /** The {@link ResourceBundle} or {@code null} if {@link #nlsBundleInterface} is given. */
  private final ResourceBundle resourceBundle;

  /** The {@link NlsBundle}-interface or {@code null} if {@link #resourceBundle} is given. */
  private final Class<? extends NlsBundle> nlsBundleInterface;

  private Map<String, NlsMessageDescriptor> properties;

  private final String qualifiedName;

  private final String packageName;

  private final String packagePath;

  private final String qualifiedNamePath;

  private final String name;

  private final NlsBundleHelper bundleHelper;

  /**
   * The constructor.
   *
   * @param resourceBundle is the {@link ResourceBundle}.
   */
  public NlsBundleDescriptor(ResourceBundle resourceBundle) {

    this(null, resourceBundle);
  }

  /**
   * The constructor.
   *
   * @param nlsBundleInterface is the {@link NlsBundle} interface.
   */
  public NlsBundleDescriptor(Class<? extends NlsBundle> nlsBundleInterface) {

    this(nlsBundleInterface, null);
  }

  private NlsBundleDescriptor(Class<? extends NlsBundle> nlsBundleInterface, ResourceBundle resourceBundle) {

    this(nlsBundleInterface, resourceBundle, NlsBundleHelper.getInstance());
  }

  private NlsBundleDescriptor(Class<? extends NlsBundle> nlsBundleInterface, ResourceBundle resourceBundle, NlsBundleHelper bundleHelper) {

    super();
    this.bundleHelper = bundleHelper;
    this.nlsBundleInterface = nlsBundleInterface;
    this.resourceBundle = resourceBundle;
    if (this.nlsBundleInterface != null) {
      this.qualifiedName = this.bundleHelper.getQualifiedLocation(this.nlsBundleInterface);
    } else {
      this.qualifiedName = this.resourceBundle.getClass().getName();
    }
    int lastDot = this.qualifiedName.lastIndexOf('.');
    if (lastDot > 0) {
      this.packageName = this.qualifiedName.substring(0, lastDot);
      this.name = this.qualifiedName.substring(lastDot + 1);
    } else {
      this.packageName = "";
      this.name = this.qualifiedName;
    }
    this.qualifiedNamePath = this.qualifiedName.replace('.', '/');
    this.packagePath = this.packageName.replace('.', '/');
  }

  /**
   * @return the fully qualified name of the bundle in java class notation.
   */
  public String getQualifiedName() {

    return this.qualifiedName;
  }

  /**
   * @return the {@link #getQualifiedName() qualified name} as path (e.g. "net/sf/mmm/util/cli/NlsBundleUtilCli" for
   *         name "net.sf.mmm.util.cli.NlsBundleUtilCli").
   */
  public String getQualifiedNamePath() {

    return this.qualifiedNamePath;
  }

  /**
   * @return the name
   */
  public String getName() {

    return this.name;
  }

  /**
   * @return the {@link Package} name.
   */
  public String getPackageName() {

    return this.packageName;
  }

  /**
   * @return the {@link #getPackageName() package name} as path (e.g. "net/sf/mmm/util/cli" for name
   *         "net.sf.mmm.util.cli").
   */
  public String getPackagePath() {

    return this.packagePath;
  }

  /**
   * @return the properties as key/value {@link Map}.
   */
  public Map<String, NlsMessageDescriptor> getMessages() {

    if (this.properties == null) {
      this.properties = new HashMap<>();
      if (this.resourceBundle == null) {
        for (Method method : this.nlsBundleInterface.getMethods()) {
          if (this.bundleHelper.isNlsBundleMethod(method, false)) {
            NlsMessageDescriptor descriptor = this.bundleHelper.getDescriptor(method);
            this.properties.put(descriptor.getKey(), descriptor);
          }
        }
      } else {
        Enumeration<String> keyEnum = this.resourceBundle.getKeys();
        while (keyEnum.hasMoreElements()) {
          String key = keyEnum.nextElement();
          String message = this.resourceBundle.getString(key);
          NlsArgumentDescriptor[] arguments = getArguments(message);
          NlsMessageDescriptor descriptor = new NlsMessageDescriptor(key, message, arguments);
          this.properties.put(key, descriptor);
        }
      }
    }
    return this.properties;
  }

  /**
   * @param key the {@link NlsBundleKey#value() key} of the requested message.
   * @return the {@link NlsMessageDescriptor} for the given {@code key} or {@code null} if not present.
   */
  public NlsMessageDescriptor getMessage(String key) {

    return getMessages().get(key);
  }

  /**
   * @param key the {@link NlsBundleKey#value() key} of the requested message.
   * @return the {@link NlsMessageDescriptor} for the given {@code key} or {@code null} if not present.
   */
  public NlsMessageDescriptor getMessageRequired(String key) {

    NlsMessageDescriptor message = getMessage(key);
    if (message == null) {
      throw new IllegalArgumentException(key);
    }
    return message;
  }

  private NlsArgumentDescriptor[] getArguments(String message) {

    return null;
  }
}
