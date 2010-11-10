/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.base;

import net.sf.mmm.content.parser.api.ContentParser;
import net.sf.mmm.util.component.base.ComponentSpecification;

/**
 * This is the interface used to tag the
 * {@link AbstractContentParserService#setGenericParser(ContentParserGeneric)
 * generic} {@link ContentParser}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ComponentSpecification
public interface ContentParserGeneric extends ContentParser {

}
