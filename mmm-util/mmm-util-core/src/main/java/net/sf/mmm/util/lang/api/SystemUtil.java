/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import net.sf.mmm.util.component.base.ComponentSpecification;

/**
 * This is the interface for a collection of utility functions that help to deal with things specific for the
 * operating system running this java virtual machine.<br>
 * <b>ATTENTION:</b><br>
 * A key-feature of Java is the great portability of the platform. Please use this util to make your
 * application even more portable rather than restrict your code to platform specific circumstances.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
@ComponentSpecification
public interface SystemUtil {

  /**
   * The name of the {@link System#getProperty(String) system-property} for
   * {@link SystemInformation#getSystemName()}.
   */
  String PROPERTY_OS_NAME = "os.name";

  /**
   * The name of the {@link System#getProperty(String) system-property} for
   * {@link SystemInformation#getSystemArchitecture()}.
   */
  String PROPERTY_OS_ARCHITECTURE = "os.arch";

  /**
   * The name of the {@link System#getProperty(String) system-property} for
   * {@link SystemInformation#getSystemVersion()}.
   */
  String PROPERTY_OS_VERSION = "os.version";

  /**
   * The name of the {@link System#getProperty(String) system-property} for the name of the JVM.
   */
  String PROPERTY_JAVA_VM_NAME = "java.vm.name";

  /**
   * This method gets the {@link SystemInformation} for the operating system running this java
   * virtual-machine. It lets you determine specific things (e.g. if you are running on "MS Windows" or on a
   * "mobile device").
   * 
   * @return the {@link SystemInformation}.
   */
  SystemInformation getSystemInformation();

}
