/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Provides the API for a pattern compiler.
 * <a name="documentation"/><h2>Pattern-Util API</h2>
 * To compile a {@link java.util.regex.Pattern} from a string you can use
 * a {@link net.sf.mmm.util.pattern.api.PatternCompiler} instead of
 * {@link java.util.regex.Pattern#compile(String)}. This allows to exchange the 
 * implementation and e.g. support a different pattern syntax such as the common 
 * glob-patterns while still providing a Java regex-{@link java.util.regex.Pattern}
 * (and NOT some non-JDK object such as ORO or whatever).
 */
package net.sf.mmm.util.pattern.api;

