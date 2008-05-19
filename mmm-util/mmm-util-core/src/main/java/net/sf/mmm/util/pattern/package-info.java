/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 Contains compilers for creating java regex patterns for different syntax
 such as glob- and path-patterns.
 <h2>Pattern Compilers</h2>
 To compile a {@link java.util.regex.Pattern} from a string you can use
 a {@link net.sf.mmm.util.pattern.api.PatternCompiler} from this package.
 Besides the {@link net.sf.mmm.util.pattern.base.RegexPatternCompiler} that only 
 delegates to {@link java.util.regex.Pattern#compile(String)} there is 
 also {@link net.sf.mmm.util.pattern.base.GlobPatternCompiler}
 and {@link net.sf.mmm.util.pattern.base.PathPatternCompiler}.
 */
package net.sf.mmm.util.pattern;

