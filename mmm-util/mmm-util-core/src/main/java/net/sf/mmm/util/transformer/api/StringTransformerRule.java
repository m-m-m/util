/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.transformer.api;


/**
 * This is the interface for a {@link Transformer} of {@link String}s that is
 * typically used as part of a
 * {@link net.sf.mmm.util.transformer.base.StringTransformerChain}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface StringTransformerRule extends Transformer<String> {

  /**
   * This method determines if this rule will stop further proceeding if it
   * matched. Since the flow of proceeding is performed outside of this
   * transformer rule, the caller needs to identify if this rule matched. This
   * is done via the convention that the rule returns the original value in
   * {@link #transform(String)} if it did NOT match.<br>
   * If you are familiar with apache httpd you can think of this flag as the "L"
   * in a rewrite rule.
   * 
   * @return <code>true</code> if the rule should stop further proceeding after
   *         successful transformation.
   */
  boolean isStopOnMatch();

}
