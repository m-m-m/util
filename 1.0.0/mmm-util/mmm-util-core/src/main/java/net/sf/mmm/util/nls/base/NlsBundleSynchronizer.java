/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import net.sf.mmm.util.nls.AbstractResourceBundle;

/**
 * This class holds the internationalized messages for <code>util-misc</code>.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class NlsBundleSynchronizer extends AbstractResourceBundle {

  /** @see net.sf.mmm.util.nls.ResourceBundleSynchronizer */
  public static final String MSG_SYNCHRONIZER_USAGE = "Usage: {0} [<option>*] <bundle-class> [<locale>*]\n\n"
      + "Create and/or update resource bundle properties from <bundle-class>\n"
      + "for the given locales (including the root locale).\n\n"
      + "Options:\n"
      + "\t--help             print this help.\n"
      + "\t--encoding <enc>   use the specified encoding (Default is \"{1}\").\n"
      + "\t--path <path>      use the specified base path (Default is \"{2}\").\n"
      + "\t--date-pattern <p> use the specified date pattern (Default is \"{3}\").\n\n"
      + "Example:\n" + "{0} {4} de de_DE en en_US en_GB fr zh ja_JP zh_TW\n";

}
