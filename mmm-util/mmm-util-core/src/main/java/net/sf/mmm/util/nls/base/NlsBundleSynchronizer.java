/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

/**
 * This class holds the internationalized messages for <code>util-misc</code>.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class NlsBundleSynchronizer extends AbstractResourceBundle {

  /** @see net.sf.mmm.util.nls.base.ResourceBundleSynchronizer */
  public static final String MSG_SYNCHRONIZER_USAGE = "Usage: {mainClass} [<option>*] <bundle-class> [<locale>*]\n\n"
      + "Create and/or update resource bundle properties from <bundle-class>\n"
      + "for the given locales (including the root locale).\n\n"
      + "Options:\n"
      + "\t--help             print this help.\n"
      + "\t--encoding <enc>   use the specified encoding (Default is \"{encoding}\").\n"
      + "\t--path <path>      use the specified base path (Default is \"{path}\").\n"
      + "\t--date-pattern <p> use the specified date pattern (Default is \"{datePattern}\").\n\n"
      + "Example:\n" + "{mainClass} {exampleClass} de de_DE en en_US en_GB fr zh ja_JP zh_TW\n";

}
