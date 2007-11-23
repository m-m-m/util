/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This is the singularizer for English language.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class EnglishSingularizer extends AbstractSingularizer {

  /** The singleton instance. */
  public static final EnglishSingularizer INSTANCE = new EnglishSingularizer();

  /** @see #getRules() */
  private static final List<SingularizerRule> RULES;

  static {
    List<SingularizerRule> rules = new ArrayList<SingularizerRule>();

    // SECTION: rules for complete words (excuses)

    rules.add(new SimpleSingularizerRule("amountis", "amount"));
    // regular but conflicts with "tis" rule: e.g. "bronchitis"
    rules.add(new SimpleSingularizerRule("antis", "anit"));
    rules.add(new SimpleSingularizerRule("automata", "automaton"));
    rules.add(new SimpleSingularizerRule("axes", "axis"));
    rules.add(new SimpleSingularizerRule("blues", "blues"));
    // regular but conflicts with "nis" rule: e.g. "tennis"
    rules.add(new SimpleSingularizerRule("bikinis", "bikini"));
    // regular but conflicts with "ies" rule: e.g. "bodies" --> "body"
    rules.add(new SimpleSingularizerRule("birdies", "birdie"));
    rules.add(new SimpleSingularizerRule("canoes", "canoe"));
    rules.add(new SimpleSingularizerRule("children", "child"));
    rules.add(new SimpleSingularizerRule("crises", "crisis"));
    rules.add(new SimpleSingularizerRule("criteria", "criterion"));
    rules.add(new SimpleSingularizerRule("dice", "die"));
    rules.add(new SimpleSingularizerRule("feet", "foot"));
    rules.add(new SimpleSingularizerRule("geese", "goose"));
    rules.add(new SimpleSingularizerRule("lice", "louse"));
    rules.add(new SimpleSingularizerRule("media", "medium"));
    rules.add(new SimpleSingularizerRule("memoranda", "memorandum"));
    rules.add(new SimpleSingularizerRule("men", "man"));
    rules.add(new SimpleSingularizerRule("mice", "mouse"));
    rules.add(new SimpleSingularizerRule("people", "person"));
    rules.add(new SimpleSingularizerRule("phenomena", "phenomenon"));
    rules.add(new SimpleSingularizerRule("polyhedra", "polyhedron"));
    // regular but conflicts with "bis" rule: e.g. "cannabis"
    rules.add(new SimpleSingularizerRule("rabbis", "rabbi"));
    rules.add(new SimpleSingularizerRule("staves", "staff"));
    rules.add(new SimpleSingularizerRule("teeth", "tooth"));
    rules.add(new SimpleSingularizerRule("testes", "testis"));

    // SECTION: not plural / already singular
    rules.add(new SimpleSingularizerRule("abbacies", "abbacies"));
    rules.add(new SimpleSingularizerRule("species", "species"));
    rules.add(new SimpleSingularizerRule("series", "series"));
    // darkness, witness, faithless, ...
    rules.add(new SimpleSingularizerRule("ss", "ss"));
    // cannabis, ibis, pubis, ...
    rules.add(new SimpleSingularizerRule("bis", "bis"));
    // his, this, ...
    rules.add(new SimpleSingularizerRule("his", "his"));
    // e.g. tennis
    rules.add(new SimpleSingularizerRule("nis", "nis"));
    // iris, paris, tigris, ...
    rules.add(new SimpleSingularizerRule("ris", "ris"));
    // crisis, analysis, ...
    rules.add(new SimpleSingularizerRule("sis", "sis"));
    // bronchitis, gratis, ...
    rules.add(new SimpleSingularizerRule("tis", "tis"));
    // axis, prophylaxis, ...
    rules.add(new SimpleSingularizerRule("xis", "xis"));

    // SECTION: regular rules

    // interfaCES --> interfaCE
    // plaCES --> plaCE
    rules.add(new SimpleSingularizerRule("ces", "ce"));
    // tyPES --> tyPE
    // apES --> aPE
    // scoPES --> scoPE
    rules.add(new SimpleSingularizerRule("pes", "pe"));
    // actreSSES --> actreSS
    // maSSES --> maSS
    rules.add(new SimpleSingularizerRule("sses", "ss"));
    // houSES --> houSE
    rules.add(new SimpleSingularizerRule("ses", "se"));
    // kniVES --> kniFE
    // calVES --> calf
    rules.add(new SimpleSingularizerRule("ves", "f"));
    // maTRICES --> maTRIX
    rules.add(new SimpleSingularizerRule("trices", "trix"));
    // indICES --> indEX
    // vertICES --> vertEX
    rules.add(new SimpleSingularizerRule("ices", "ex"));
    // french: *eaux --> *eau
    // beaux --> beau
    // bureaux --> bureau
    rules.add(new SimpleSingularizerRule("eaux", "eau"));
    // ITALIEN: *zzi --> *zzo
    // paparazzi --> paparazzo
    rules.add(new SimpleSingularizerRule("zzi", "zzo"));
    // utilitIES --> utilitY
    // ATTENTION:
    // serIES --> serIES
    // specIES --> specIES
    rules.add(new SimpleSingularizerRule("ies", "y"));
    // bunchES --> bunch
    // classES --> class
    // EXCUSE: canOES --> canOE
    rules.add(new SimpleSingularizerRule("es", ""));
    // serviceS --> service
    rules.add(new SimpleSingularizerRule("s", ""));
    RULES = Collections.unmodifiableList(rules);
  }

  /**
   * The constructor.
   */
  protected EnglishSingularizer() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected List<SingularizerRule> getRules() {

    return RULES;
  }

}
