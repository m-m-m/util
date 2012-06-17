/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.api.config;

import net.sf.mmm.util.xml.base.jaxb.JaxbBeanHolder;

/**
 * This is the interface for a container {@link #getBean() holding} the {@link SearchConfiguration}.
 * 
 * @see JaxbBeanHolder
 * 
 * @param <T> is the generic type of the {@link #getBean() configuration}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface SearchConfigurationHolder<T extends SearchConfiguration> extends JaxbBeanHolder<T> {

  // nothing to add...

}
