/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Provides the API for utilities that help with textual operations.
 * <a name="documentation"/><h2>Util Text API</h2>
 * This package contains the API for various textual utilities.
 * {@link net.sf.mmm.util.text.api.Justification} allows simple justification of
 * a short string while {@link net.sf.mmm.util.text.api.LineWrapper} offers
 * justified text-layout with multiple-columns and hyphenation via
 * {@link net.sf.mmm.util.text.api.Hyphenator}.<br/>
 * For unicode support there is {@link net.sf.mmm.util.text.api.UnicodeUtil}
 * and also {@link net.sf.mmm.util.text.api.DiacriticalMark}.
 * The {@link net.sf.mmm.util.text.api.Singularizer} transforms a term in plural
 * form to its according singular form (e.g. <code>"children"</code> to
 * <code>"child"</code>). It is mainly intended for technical reasons (e.g. to
 * determine relations between <code>getChildren()</code> and
 * <code>addChild()</code>) and less for linguistic purposes.
 */
package net.sf.mmm.util.text.api;

