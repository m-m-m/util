/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 Contains support for generic filtering.
 <h2>Filters</h2>
 To compile a {@link java.util.regex.Pattern} from a string you can use
 a {@link net.sf.mmm.util.pattern.PatternCompiler} from this package.
 Besides the {@link net.sf.mmm.util.pattern.RegexPatternCompiler} that only 
 delegates to {@link java.util.regex.Pattern#compile(String)} there is 
 also {@link net.sf.mmm.util.pattern.GlobPatternCompiler}
 and {@link net.sf.mmm.util.pattern.PathPatternCompiler}.
 */
package net.sf.mmm.util.filter;