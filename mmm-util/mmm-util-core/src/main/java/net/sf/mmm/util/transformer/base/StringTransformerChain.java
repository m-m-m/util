/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.transformer.base;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;

import net.sf.mmm.util.transformer.api.StringTransformerRule;
import net.sf.mmm.util.transformer.api.Transformer;

/**
 * This class represents a {@link Transformer} for {@link String}s that is build
 * out of a list of {@link StringTransformerRule rules}. It performs its
 * {@link #transform(String) transformation} by passing the given value to the
 * first rule and its result to the next rule and so on. If a rule matched, it
 * can stop further proceeding via the
 * {@link StringTransformerRule#isStopOnMatch() stop-on-match} flag and cause
 * its result to be returned immediately. Otherwise the result of the last rule
 * in the chain is returned (like a left associative operator).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class StringTransformerChain implements Transformer<String> {

  /** @see #getId() */
  @XmlID
  @XmlAttribute(name = "id")
  private String id;

  /** The parent that is extended by this cain or <code>null</code>. */
  @XmlIDREF
  private StringTransformerChain parent;

  /** The rules of this chain. */
  @XmlElement(name = "regex", type = RegexStringTransformerRule.class)
  private StringTransformerRule[] rules;

  /**
   * The non-arg constructor.<br>
   * <b>NOTE:</b><br>
   * This constructor should not be called directly! It is only intended for
   * reflective access (e.g. for JAXB).
   */
  public StringTransformerChain() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param rules are the rules of this chain.
   */
  public StringTransformerChain(StringTransformerRule... rules) {

    super();
    this.rules = rules;
  }

  /**
   * The constructor.
   * 
   * @param id the {@link #getId() ID} of this chain.
   * @param parent is the parent {@link StringTransformerChain chain} to extend
   *        or <code>null</code> for a root-chain.
   * @param rules are the rules of this chain.
   */
  public StringTransformerChain(String id, StringTransformerChain parent,
      StringTransformerRule... rules) {

    super();
    this.id = id;
    this.parent = parent;
    this.rules = rules;
  }

  /**
   * {@inheritDoc}
   */
  public String transform(String original) {

    return transformRecursive(original, new State());
  }

  /**
   * This method implements {@link #transform(String)} recursively.
   * 
   * @param original is the original value.
   * @param state is the {@link State} used to indicate if a
   *        {@link StringTransformerRule rule} causes the chain to
   *        {@link State#stop}.
   * @return the transformed result.
   */
  private String transformRecursive(String original, State state) {

    String value = original;
    if (this.parent != null) {
      value = this.parent.transformRecursive(original, state);
      if (state.stop) {
        return value;
      }
    }
    for (StringTransformerRule rule : this.rules) {
      String transformed = rule.transform(value);
      if ((transformed != value) && (rule.isStopOnMatch())) {
        state.stop = true;
        return transformed;
      }
      value = transformed;
    }
    return value;
  }

  /**
   * This method gets the ID used to identify this rule.
   * 
   * @return the ID or <code>null</code> if undefined.
   */
  public String getId() {

    return this.id;
  }

  /**
   * This method extends this chain with <code>additionalRules</code>.<br>
   * <b>ATTENTION:</b><br>
   * If you want to be able to marshall the chain with JAXB, you have to use
   * {@link StringTransformerChain#StringTransformerChain(String, StringTransformerChain, StringTransformerRule...)}
   * instead.
   * 
   * @param additionalRules are the rules to add.
   * @return the chain that also checks the <code>additionalRules</code> if none
   *         of this rules match.
   */
  public StringTransformerChain extend(StringTransformerRule... additionalRules) {

    StringTransformerRule[] newRules = new StringTransformerRule[this.rules.length
        + additionalRules.length];
    System.arraycopy(this.rules, 0, newRules, 0, this.rules.length);
    System.arraycopy(additionalRules, 0, newRules, this.rules.length, additionalRules.length);
    return new StringTransformerChain(newRules);
  }

  /**
   * This inner class contains the state of a transformation.
   * 
   * @since 1.1.2
   */
  protected static class State {

    /**
     * The constructor.
     */
    protected State() {

      super();
      this.stop = false;
    }

    /**
     * <code>true</code> if the chain should be stopped, <code>false</code>
     * otherwise.
     */
    private boolean stop;
  }

}
