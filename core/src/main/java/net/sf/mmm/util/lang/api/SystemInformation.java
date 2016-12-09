/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

/**
 * This is the interface for a data-object containing structured of an operating system.
 *
 * @see SystemUtil#getSystemInformation()
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public interface SystemInformation {

  /**
   * The {@link #getSystemType() system-type} for a variant of Microsoft Windows (including {@link #isLimitedDevice()
   * mobile variants}).
   */
  String SYSTEM_TYPE_WINDOWS = "Windows";

  /** The {@link #getSystemType() system-type} for <em>Windows Phone</em> (Windows mobile OS). */
  String SYSTEM_TYPE_WINDOWS_PHONE = "Windows Phone";

  /**
   * The {@link #getSystemType() system-type} for a variant of Apples operating system ("Mac OS", "Mac OS X" or any
   * variant of "iOS").
   *
   * @deprecated use {@link #SYSTEM_TYPE_MAC_OS}
   * @deprecated use {@link #SYSTEM_TYPE_IOS}
   */
  @Deprecated
  String SYSTEM_TYPE_MAC_IOS = "Mac/iOS";

  /** The {@link #getSystemType() system-type} for <em>Mac OS</em> (Apple Macintosh). */
  String SYSTEM_TYPE_MAC_OS = "Mac OS";

  /** The {@link #getSystemType() system-type} for <em>iOS</em> (Apple Mobile OS for iPhone, iPad, etc.). */
  String SYSTEM_TYPE_IOS = "iOS";

  /** The {@link #getSystemType() system-type} for <em>tvOS</em> (for AppleTV). */
  String SYSTEM_TYPE_TV_OS = "tvOS";

  /**
   * The {@link #getSystemType() system-type} for a distribution of Linux (android, ubuntu, kubuntu, debian, openSUSE,
   * gentoo, fedora, mandriva, etc.).
   */
  String SYSTEM_TYPE_LINUX = "Linux";

  /**
   * The {@link #getSystemType() system-type} for Unix explicitly excluding {@link #SYSTEM_TYPE_MAC_IOS Mac OS},
   * {@link #SYSTEM_TYPE_BSD BSD-systems} and {@link #SYSTEM_TYPE_LINUX Linux}. This means operating systems like
   * "[Open]Solaris", "HP-UX", "AIX", "DG/UX", "IRIX" are considered of this type.
   */
  String SYSTEM_TYPE_UNIX = "UNIX";

  /**
   * The {@link #getSystemType() system-type} for a <em>BSD</em> (Berkeley Software Distribution) operating system
   * (freeBSD, openBSD, etc.).
   */
  String SYSTEM_TYPE_BSD = "BSD";

  /**
   * The {@link #getSystemType() system-type} for operating systems dedicated for mainframe machines ([Open]VMS, z/OS,
   * AIX, BS2000, OS/360, OS/390, etc.). A mainframe is a (traditional) high-end server.
   */
  String SYSTEM_TYPE_MAINFRAIME = "Mainfraime";

  /** The {@link #getSystemType() system-type} for <em>Android</em>. */
  String SYSTEM_TYPE_ANDROID = "Android";

  /** The {@link #getSystemType() system-type} for <em>Nintendo</em>. */
  String SYSTEM_TYPE_NINTENDO = "Nintendo";

  /** The {@link #getSystemType() system-type} for <em>PlayStation</em>. */
  String SYSTEM_TYPE_PLAYSTATION = "PlayStation";

  /** The {@link #getSystemType() system-type} for <em>Xbox</em>. */
  String SYSTEM_TYPE_XBOX = "Xbox";

  /** The {@link #getSystemType() system-type} for <em>Fire OS</em> (from Amazon for Kindle, Fire Phone, or Fire TV). */
  String SYSTEM_TYPE_FIRE_OS = "Fire OS";

  /** The {@link #getSystemType() system-type} for <em>Bada</em> (from Samsung Electronics for Smartphone). */
  String SYSTEM_TYPE_BADA = "Bada";

  /** The {@link #getSystemType() system-type} for <em>Sailfish</em> (Linux based mobile OS). */
  String SYSTEM_TYPE_SAILFISH = "Sailfish";

  /**
   * The result for any of the {@link SystemInformation} segments (especially {@link #getSystemType() system-type}
   * potentially also {@link #getSystemName() system-name}, {@link #getSystemVersion() system-version}, or
   * {@link #getSystemArchitecture() system-architecture}) if the value is unknown. Please note that in new releases of
   * this project, the classification and detection can be improved so segments that used to be {@link #UNKNOWN} will
   * then have proper values.
   *
   * @since 7.4.0
   */
  String UNKNOWN = "unknown";

  /**
   * The {@link #getSystemType() system-type} for anything else that is not further classified.
   *
   * @deprecated replaced by {@link #UNKNOWN}.
   */
  @Deprecated
  String SYSTEM_TYPE_OTHER = UNKNOWN;

  /**
   * The {@link #getSystemArchitecture() architecture} <em>arm</em> (Advanced RISC Machine) used especially for
   * {@link #isLimitedDevice() mobile and embedded devices}.
   *
   * @since 7.4.0
   */
  String SYSTEM_ARCHITECTURE_ARM = "arm";

  /**
   * The {@link #getSystemArchitecture() architecture} <em>x86</em> (80x86 CPU Series) unlike.
   *
   * @since 7.4.0
   */
  String SYSTEM_ARCHITECTURE_X86 = "x86";

  /**
   * The {@link #getSystemArchitecture() architecture} <em>x64</em> for {@link #SYSTEM_ARCHITECTURE_X86 80x86 CPUs} with
   * 64-BIT.
   *
   * @since 7.4.0
   */
  String SYSTEM_ARCHITECTURE_X64 = "x64";

  /**
   * This method gets the name of the operating system. Here is an incomplete list of possible results:
   * <ul>
   * <li>Windows 95</li>
   * <li>Windows 98</li>
   * <li>Windows NT</li>
   * <li>Windows 2000</li>
   * <li>Windows CE</li>
   * <li>Windows Me</li>
   * <li>Windows XP</li>
   * <li>Windows Vista</li>
   * <li>Windows 7</li>
   * <li>Mac OS</li>
   * <li>Mac OS X</li>
   * <li>Linux</li>
   * <li>FreeBSD</li>
   * <li>Solaris</li>
   * <li>Digital Unix</li>
   * <li>HP UX</li>
   * <li>AIX</li>
   * <li>Irix</li>
   * <li>MPE/iX</li>
   * <li>Netware 4.11</li>
   * <li>OS/2</li>
   * <li>OpenVMS</li>
   * <li>NetWare</li>
   * <li>OFS1</li>
   * <li>OS/390</li>
   * </ul>
   *
   * @see SystemUtil#PROPERTY_OS_NAME
   *
   * @return the OS name.
   */
  String getSystemName();

  /**
   * This method gets the architecture of the operating system. Typical results are:
   * <ul>
   * <li>x86</li>
   * <li>x86_64</li>
   * <li>i386</li>
   * <li>i686</li>
   * <li>ppc</li>
   * <li>ppc64</li>
   * <li>PowerPc</li>
   * <li>arm</li>
   * <li>armv41</li>
   * <li>sparc</li>
   * <li>mips</li>
   * <li>alpha</li>
   * <li>PA-RISC</li>
   * </ul>
   *
   * @see SystemUtil#PROPERTY_OS_NAME
   *
   * @return the OS name.
   */
  String getSystemArchitecture();

  /**
   * This method gets the version of the operating system in lower-case.
   *
   * @see SystemUtil#PROPERTY_OS_VERSION
   *
   * @return the OS name.
   */
  String getSystemVersion();

  /**
   * This method gets the type (or family) of the operating system. The type is derived from the {@link #getSystemName()
   * system-name} to make detection easier.
   *
   * @see #SYSTEM_TYPE_WINDOWS
   * @see #SYSTEM_TYPE_MAC_IOS
   * @see #SYSTEM_TYPE_LINUX
   * @see #SYSTEM_TYPE_BSD
   * @see #SYSTEM_TYPE_UNIX
   * @see #UNKNOWN
   *
   * @return the type of the operating system as one of the {@code SYSTEM_TYPE_*} constants defined in this interface.
   */
  String getSystemType();

  /**
   * This method determines if the operating system is from a limited device. Here, limited means limited computing
   * power or limited display resolution (typically both). Limited devices are mobile or embedded devices as well as
   * most tablets (but not a regular laptop even with touch screen). Regular devices that are not limited are
   * full-fledged PC or server machines. Please note that the flag returned by this method is just an indicator and NOT
   * a guarantee.
   *
   * @return {@code true} if operating system is for limited device, {@code false} otherwise.
   */
  boolean isLimitedDevice();

  /**
   * @return {@code true} if the {@link #getSystemArchitecture() architecture} supports 64-Bit (both by CPU and OS),
   *         {@code false} otherwise. Please note that this is just a good guess based on the information available from
   *         the {@link String} provided by {@link #getSystemArchitecture()}.
   * @since 7.4.0
   */
  boolean is64Bit();

  /**
   * @return {@code true} if the {@link #getSystemArchitecture() architecture} indicates an
   *         {@link #SYSTEM_ARCHITECTURE_X86 80x86 CPU} ("x86", "x86_64", "x64", "amd64", "ia-64", "i686", etc.),
   *         {@code false} otherwise. The result is independent from the number of bits such as 16, 32, or
   *         {@link #is64Bit() 64}.
   * @since 7.4.0
   */
  boolean isX86();

}
