/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import net.sf.mmm.util.lang.api.SystemInformation;
import net.sf.mmm.util.lang.api.SystemUtil;

/**
 * This is the implementation of the {@link SystemInformation} interface.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class SystemInformationImpl implements SystemInformation {

  private static final Set<String> X86_ARCHITECTURES = new HashSet<>(Arrays.asList(SYSTEM_ARCHITECTURE_X86, SYSTEM_ARCHITECTURE_X64, "i286", "i386", "i486",
      "i568", "i686", "amd64", "pentium", "ia-16", "ia-32", "ia-64", "ia16", "ia32", "ia64"));

  private final String systemName;

  private final String systemVersion;

  private final String systemArchitecture;

  private final String systemType;

  private final boolean limitedDevice;

  /**
   * The constructor.
   */
  public SystemInformationImpl() {

    super();
    this.systemName = System.getProperty(SystemUtil.PROPERTY_OS_NAME);
    this.systemVersion = System.getProperty(SystemUtil.PROPERTY_OS_VERSION);
    this.systemArchitecture = System.getProperty(SystemUtil.PROPERTY_OS_ARCHITECTURE);
    this.systemType = detectSystemType(null, this.systemName, true);
    this.limitedDevice = detectLimitedDevice(this.systemName, this.systemArchitecture, true);
  }

  /**
   * The constructor.
   *
   * @param systemName - see {@link #getSystemName()}.
   * @param systemVersion - see {@link #getSystemVersion()}.
   * @param systemArchitecture - see {@link #getSystemArchitecture()}.
   */
  public SystemInformationImpl(String systemName, String systemVersion, String systemArchitecture) {

    this(systemName, systemVersion, systemArchitecture, null, null);
  }

  /**
   * The constructor.
   *
   * @param systemName - see {@link #getSystemName()}.
   * @param systemVersion - see {@link #getSystemVersion()}.
   * @param systemArchitecture - see {@link #getSystemArchitecture()}.
   * @param systemType - see {@link #getSystemType()}.
   * @since 7.4.0
   */
  public SystemInformationImpl(String systemName, String systemVersion, String systemArchitecture, String systemType) {

    this(systemName, systemVersion, systemArchitecture, systemType, null);
  }

  /**
   * The constructor.
   *
   * @param systemName - see {@link #getSystemName()}.
   * @param systemVersion - see {@link #getSystemVersion()}.
   * @param systemArchitecture - see {@link #getSystemArchitecture()}.
   * @param systemType - see {@link #getSystemType()}.
   * @param limitedDevice - see {@link #isLimitedDevice()}.
   */
  public SystemInformationImpl(String systemName, String systemVersion, String systemArchitecture, String systemType, boolean limitedDevice) {

    this(systemName, systemVersion, systemArchitecture, systemType, Boolean.valueOf(limitedDevice));
  }

  /**
   * The constructor.
   *
   * @param systemName - see {@link #getSystemName()}.
   * @param systemVersion - see {@link #getSystemVersion()}.
   * @param systemArchitecture - see {@link #getSystemArchitecture()}.
   * @param systemType - see {@link #getSystemType()}.
   * @param limitedDevice - see {@link #isLimitedDevice()}.
   */
  private SystemInformationImpl(String systemName, String systemVersion, String systemArchitecture, String systemType, Boolean limitedDevice) {

    super();
    this.systemName = getNotEmpty(systemName);
    this.systemVersion = getNotEmpty(systemVersion);
    this.systemType = detectSystemType(systemType, this.systemName, false);
    this.systemArchitecture = detectArchitecture(systemArchitecture, this.systemName, this.systemVersion, this.systemType);
    if (limitedDevice == null) {
      this.limitedDevice = detectLimitedDevice(this.systemName, this.systemArchitecture, false);
    } else {
      this.limitedDevice = limitedDevice.booleanValue();
    }
  }

  private static String getNotEmpty(String string) {

    if (isEmpty(string)) {
      return UNKNOWN;
    }
    return string;
  }

  private static boolean isEmpty(String string) {

    return (string == null) || string.isEmpty() || string.equals(UNKNOWN);
  }

  private static String detectArchitecture(String systemArchitecture, String systemName, String systemVersion, String systemType) {

    if (!isEmpty(systemArchitecture)) {
      return systemArchitecture;
    }
    if (systemType.equals(SYSTEM_TYPE_IOS)) {
      return SYSTEM_ARCHITECTURE_ARM;
    }
    return UNKNOWN;
  }

  /**
   * @see #getSystemType()
   *
   * @param osName - see {@link #getSystemName()}.
   * @param currentSystem - {@code true} if the value should be determined for the system and JVM currently running (in
   *        this case additional system-properties might be evaluated), {@code false} otherwise.
   * @return the value for {@link #isLimitedDevice()}.
   */
  private static String detectSystemType(String systemType, String osName, boolean currentSystem) {

    if (!isEmpty(systemType)) {
      return systemType;
    }
    if (isEmpty(osName)) {
      return UNKNOWN;
    }
    String os = osName.toLowerCase(Locale.US).trim();
    if (os.startsWith("windows")) {
      return SYSTEM_TYPE_WINDOWS;
    } else if (os.startsWith("mac")) {
      return SYSTEM_TYPE_MAC_OS;
    } else if (os.startsWith("ios")) {
      return SYSTEM_TYPE_IOS;
    } else if (os.contains("linux")) {
      return SYSTEM_TYPE_LINUX;
    } else if (os.contains("bsd")) {
      return SYSTEM_TYPE_BSD;
    } else if (os.contains("solaris") || os.contains("hp-ux") || os.contains("nix") || os.contains("aix") || os.contains("nextstep") || os.contains("sorix")
        || os.contains("irix")) {
      return SYSTEM_TYPE_UNIX;
    } else if (os.startsWith("z/") || os.startsWith("os/360") || os.startsWith("os/390") || os.startsWith("os/400") || os.startsWith("bs2000")
        || os.startsWith("mvs") || os.startsWith("aix") || os.startsWith("tpf") || os.equals("cms")) {
      return SYSTEM_TYPE_MAINFRAIME;
    } else {
      return UNKNOWN;
    }
  }

  /**
   * @see #isLimitedDevice()
   *
   * @param osName - see {@link #getSystemName()}.
   * @param osArchitecture - see {@link #getSystemArchitecture()}.
   * @param currentSystem - {@code true} if the value should be determined for the system and JVM currently running (in
   *        this case additional system-properties might be evaluated), {@code false} otherwise.
   * @return the value for {@link #isLimitedDevice()}.
   */
  private static boolean detectLimitedDevice(String osName, String osArchitecture, boolean currentSystem) {

    String os = osName.toLowerCase(Locale.US).trim();
    if (os.contains("windows ce")) {
      return true;
    } else if (os.contains("darvin")) {
      return true;
    } else if (os.contains("android")) {
      return true;
    } else if (os.contains("phone")) {
      return true;
    } else if (os.contains("firefox os")) {
      return true;
    } else if (os.contains("bada")) {
      return true;
    } else if (os.contains("sailfish")) {
      return true;
    } else if (os.contains("tvos")) {
      return true;
    } else if (os.startsWith("ios")) {
      return true;
    } else if (os.contains("iphone")) {
      return true;
    } else if (os.contains("nintendo")) {
      return true;
    } else if (os.contains("wii")) {
      return true;
    } else if (os.contains("xbox")) {
      return true;
    } else if (os.contains("playstation")) {
      return true;
    } else if (os.startsWith("rim ")) {
      return true;
    }
    String arch = osArchitecture.toLowerCase(Locale.US).trim();
    if (arch.equals("arm")) {
      return true;
    }
    if (currentSystem) {
      String vmName = System.getProperty(SystemUtil.PROPERTY_JAVA_VM_NAME).toLowerCase(Locale.US);
      if (vmName.contains("dalvik")) {
        return true;
      }
    }
    return false;
  }

  @Override
  public String getSystemName() {

    return this.systemName;
  }

  @Override
  public String getSystemArchitecture() {

    return this.systemArchitecture;
  }

  @Override
  public String getSystemVersion() {

    return this.systemVersion;
  }

  @Override
  public String getSystemType() {

    return this.systemType;
  }

  @Override
  public boolean isLimitedDevice() {

    return this.limitedDevice;
  }

  @Override
  public boolean isX86() {

    if (X86_ARCHITECTURES.contains(this.systemArchitecture)) {
      return true;
    }
    for (String indicator : X86_ARCHITECTURES) {
      if (this.systemArchitecture.contains(indicator)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean is64Bit() {

    return this.systemArchitecture.endsWith("64");
  }

}
