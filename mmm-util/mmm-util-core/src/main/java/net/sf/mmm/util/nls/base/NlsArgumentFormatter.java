/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import net.sf.mmm.util.component.base.ComponentSpecification;
import net.sf.mmm.util.nls.api.NlsArgument;
import net.sf.mmm.util.nls.api.NlsFormatter;

/**
 * The {@link NlsArgumentFormatter} is an {@link NlsFormatter} for an actual {@link NlsArgument}. It performs
 * the higher-level formatting with {@link NlsArgument#getJustification() justification} delegating the
 * lower-level formatting to the {@link NlsArgument#getFormatter() according sub-formatter} (typically a
 * {@link net.sf.mmm.util.nls.api.NlsFormatterPlugin}).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
@ComponentSpecification
public interface NlsArgumentFormatter extends NlsFormatter<NlsArgument> {

  // nothing to add, just bound generic

}
