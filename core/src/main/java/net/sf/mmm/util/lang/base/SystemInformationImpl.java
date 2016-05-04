/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import java.util.Locale;

import net.sf.mmm.util.lang.api.SystemInformation;
import net.sf.mmm.util.lang.api.SystemUtil;

/**
 * This is the implementation of the {@link SystemInformation} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class SystemInformationImpl implements SystemInformation {

  private  final String systemName;

  private  final String systemVersion;

  private  final String systemArchitecture;

  private  final String systemType;

  private  final boolean limitedDevice;

  /**
   * The constructor.
   */
  public SystemInformationImpl() {

    super();
    this.systemName = System.getProperty(SystemUtil.PROPERTY_OS_NAME);
    this.systemVersion = System.getProperty(SystemUtil.PROPERTY_OS_VERSION);
    this.systemArchitecture = System.getProperty(SystemUtil.PROPERTY_OS_ARCHITECTURE);
    this.systemType = detectSystemType(this.systemName, true);
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

    super();
    this.systemName = systemName;
    this.systemVersion = systemVersion;
    this.systemArchitecture = systemArchitecture;
    this.systemType = detectSystemType(this.systemName, false);
    this.limitedDevice = detectLimitedDevice(this.systemName, this.systemArchitecture, false);
  }

  /**
   * The constructor.
   * 
   * @param systemName - see {@link #getSystemName()}.
   * @param systemVersion - see {@link #getSystemVersion()}.
   * @param systemArchitecture - see {@link #getSystemArchitecture()}.
   * @param systemType - see {@link #getSystemType()}.
   * @param mobileDevice - see {@link #isLimitedDevice()}.
   */
  public SystemInformationImpl(String systemName, String systemVersion, String systemArchitecture, String systemType,
      boolean mobileDevice) {

    super();
    this.systemName = systemName;
    this.systemVersion = systemVersion;
    this.systemArchitecture = systemArchitecture;
    this.systemType = systemType;
    this.limitedDevice = mobileDevice;
  }

  /**
   * @see #getSystemType()
   * 
   * @param osName - see {@link #getSystemName()}.
   * @param currentSystem - {@code true} if the value should be determined for the system and JVM
   *        currently running (in this case additional system-properties might be evaluated),
   *        {@code false} otherwise.
   * @return the value for {@link #isLimitedDevice()}.
   */
  private static String detectSystemType(String osName, boolean currentSystem) {

    if (osName == null) {
      return SYSTEM_TYPE_OTHER;
    }
    String os = osName.toLowerCase(Locale.US).trim();
    if (os.startsWith("windows")) {
      return SYSTEM_TYPE_WINDOWS;
    } else if (os.startsWith("mac") || os.startsWith("ios")) {
      return SYSTEM_TYPE_MAC_IOS;
    } else if (os.contains("linux")) {
      return SYSTEM_TYPE_LINUX;
    } else if (os.contains("bsd")) {
      return SYSTEM_TYPE_BSD;
    } else if (os.contains("solaris") || os.contains("hp-ux") || os.contains("nix") || os.contains("aix")
        || os.contains("nextstep") || os.contains("sorix") || os.contains("irix")) {
      return SYSTEM_TYPE_UNIX;
    } else if (os.startsWith("z/") || os.startsWith("os/360") || os.startsWith("os/390") || os.startsWith("os/400")
        || os.startsWith("bs2000") || os.startsWith("mvs") || os.startsWith("tpf") || os.equals("cms")) {
      return SYSTEM_TYPE_MAINFRAIME;
    } else {
      return SYSTEM_TYPE_OTHER;
    }
  }

  /**
   * @see #isLimitedDevice()
   * 
   * @param osName - see {@link #getSystemName()}.
   * @param osArchitecture - see {@link #getSystemArchitecture()}.
   * @param currentSystem - {@code true} if the value should be determined for the system and JVM
   *        currently running (in this case additional system-properties might be evaluated),
   *        {@code false} otherwise.
   * @return the value for {@link #isLimitedDevice()}.
   */
  private static boolean detectLimitedDevice(String osName, String osArchitecture, boolean currentSystem) {

    String os = osName.toLowerCase(Locale.US).trim();
    if (os.contains("windows ce")) {
      return true;
    }
    if (os.contains("darvin")) {
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

}
