/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

/**
 * This is the interface for a data-object containing structured of an operating
 * system.
 * 
 * @see SystemUtil#getSystemInformation()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public interface SystemInformation {

  /**
   * The {@link #getSystemType() system-type} for a variant of Microsoft Windows
   * (including {@link #isLimitedDevice() mobile variants}).
   */
  String SYSTEM_TYPE_WINDOWS = "Windows";

  /**
   * The {@link #getSystemType() system-type} for a variant of Apples operating
   * system ("Mac OS", "Mac OS X" or any variant of "iOS").
   */
  String SYSTEM_TYPE_MAC_IOS = "Mac/iOS";

  /**
   * The {@link #getSystemType() system-type} for a distribution of Linux
   * (android, ubuntu, kubuntu, debian, openSUSE, gentoo, fedora, mandriva,
   * etc.).
   */
  String SYSTEM_TYPE_LINUX = "Linux";

  /**
   * The {@link #getSystemType() system-type} for Unix explicitly excluding
   * {@link #SYSTEM_TYPE_MAC_IOS Mac OS}, {@link #SYSTEM_TYPE_BSD BSD-systems}
   * and {@link #SYSTEM_TYPE_LINUX Linux}. This means operating systems like
   * "[Open]Solaris", "HP-UX", "AIX", "DG/UX", "IRIX" are considered of this
   * type.
   */
  String SYSTEM_TYPE_UNIX = "UNIX";

  /**
   * The {@link #getSystemType() system-type} for a BSD operating system
   * (freeBSD, openBSD, etc.).
   */
  String SYSTEM_TYPE_BSD = "BSD";

  /**
   * The {@link #getSystemType() system-type} for operating systems dedicated
   * for mainframe machines ([Open]VMS, z/OS, BS2000, OS/360, OS/390, etc.). A
   * mainframe is a (traditional) high-end server.
   */
  String SYSTEM_TYPE_MAINFRAIME = "Mainfraime";

  /**
   * The {@link #getSystemType() system-type} for anything else that is not
   * further classified. Please note that in new releases of this project, a new
   * classification can be added and systems that have been in
   * {@link #SYSTEM_TYPE_OTHER} are then of a new type.
   */
  String SYSTEM_TYPE_OTHER = "Other";

  /**
   * This method gets the name of the operating system running this java virtual
   * machine. Here is an incomplete list of possible results:
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
   * @see SystemUtil#SYSTEM_PROPERTY_OS_NAME
   * 
   * @return the OS name.
   */
  String getSystemName();

  /**
   * This method gets the architecture of the operating system running this java
   * virtual machine. Typical results are:
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
   * @see SystemUtil#SYSTEM_PROPERTY_OS_NAME
   * 
   * @return the OS name.
   */
  String getSystemArchitecture();

  /**
   * This method gets the version of the operating system running this java
   * virtual machine in lower-case.
   * 
   * @see SystemUtil#SYSTEM_PROPERTY_OS_VERSION
   * 
   * @return the OS name.
   */
  String getSystemVersion();

  /**
   * This method gets the type of the operating system running this java virtual
   * machine. The type is not something directly provided by the JRE. It is
   * defined by this API and is derived from the {@link #getSystemName()
   * system-name} to make things easier for you.
   * 
   * @see #SYSTEM_TYPE_WINDOWS
   * @see #SYSTEM_TYPE_MAC_IOS
   * @see #SYSTEM_TYPE_LINUX
   * @see #SYSTEM_TYPE_BSD
   * @see #SYSTEM_TYPE_UNIX
   * @see #SYSTEM_TYPE_OTHER
   * 
   * @return the type of the operating system as one of the
   *         <code>SYSTEM_TYPE_*</code> constants defined in this interface.
   */
  String getSystemType();

  /**
   * This method determines if the operating system running this java virtual
   * machine is for mobile or embedded devices or maybe tablets but not for
   * full-fledged PC or server machines. In such case you can assume that you
   * are running on a mobile or embedded device. Please note that this is NOT a
   * guarantee.
   * 
   * @return <code>true</code> if operating system is for limited device,
   *         <code>false</code> otherwise.
   */
  boolean isLimitedDevice();

}
