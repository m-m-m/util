/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Contains utilities for textual operations.
 * <h2>Text Utilities</h2>
 * The {@link net.sf.mmm.util.lang.base.AbstractCharSequence} helps to write 
 * implementations of the interface {@link java.lang.CharSequence} easily.
 * The {@link net.sf.mmm.util.text.api.Singularizer} transforms a term in plural 
 * form to its according singular form (e.g. <code>"children"</code> to 
 * <code>"child"</code>). It is mainly intended for technical reasons (e.g. to
 * determine relations between <code>getChildren()</code> and 
 * <code>addChild()</code>) and less for linguistic purposes.
 */
package net.sf.mmm.util.text;

