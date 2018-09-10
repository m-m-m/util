/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.mmm.util.text.api.Singularizer;

/**
 * This is the implementation of the {@link Singularizer} interface for English language. It aims for
 * simplicity rather than linguistic perfection.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class EnglishSingularizer extends AbstractTextTransformer implements Singularizer {

  /** The singleton instance. */
  public static final EnglishSingularizer INSTANCE = new EnglishSingularizer();

  private static final List<TransformerRule> RULES;

  static {
    List<TransformerRule> rules = new ArrayList<>();

    // SECTION: rules for complete words (excuses)

    rules.add(new SuffixTransformerRule("amountis", "amount"));
    // regular but conflicts with "tis" rule: e.g. "bronchitis"
    rules.add(new SuffixTransformerRule("antis", "anti"));
    rules.add(new SuffixTransformerRule("automata", "automaton"));
    rules.add(new SuffixTransformerRule("axes", "axis"));
    rules.add(new SuffixTransformerRule("blues", "blues"));
    // regular but conflicts with "nis" rule: e.g. "tennis"
    rules.add(new SuffixTransformerRule("bikinis", "bikini"));
    // regular but conflicts with "ies" rule: e.g. "bodies" --> "body"
    rules.add(new SuffixTransformerRule("birdies", "birdie"));
    rules.add(new SuffixTransformerRule("canoes", "canoe"));
    rules.add(new SuffixTransformerRule("children", "child"));
    rules.add(new SuffixTransformerRule("crises", "crisis"));
    rules.add(new SuffixTransformerRule("criteria", "criterion"));
    rules.add(new SuffixTransformerRule("dice", "die"));
    rules.add(new SuffixTransformerRule("feet", "foot"));
    rules.add(new SuffixTransformerRule("geese", "goose"));
    rules.add(new SuffixTransformerRule("lice", "louse"));
    rules.add(new SuffixTransformerRule("media", "medium"));
    rules.add(new SuffixTransformerRule("memoranda", "memorandum"));
    rules.add(new SuffixTransformerRule("men", "man"));
    rules.add(new SuffixTransformerRule("mice", "mouse"));
    rules.add(new SuffixTransformerRule("people", "person"));
    rules.add(new SuffixTransformerRule("phenomena", "phenomenon"));
    rules.add(new SuffixTransformerRule("polyhedra", "polyhedron"));
    // regular but conflicts with "bis" rule: e.g. "cannabis"
    rules.add(new SuffixTransformerRule("rabbis", "rabbi"));
    rules.add(new SuffixTransformerRule("staves", "staff"));
    rules.add(new SuffixTransformerRule("teeth", "tooth"));
    rules.add(new SuffixTransformerRule("testes", "testis"));

    // SECTION: not plural / already singular
    rules.add(new SuffixTransformerRule("abbacies", "abbacies"));
    rules.add(new SuffixTransformerRule("species", "species"));
    rules.add(new SuffixTransformerRule("series", "series"));
    // darkness, witness, faithless, ...
    rules.add(new SuffixTransformerRule("ss", "ss"));
    // cannabis, ibis, pubis, ...
    rules.add(new SuffixTransformerRule("bis", "bis"));
    // his, this, ...
    rules.add(new SuffixTransformerRule("his", "his"));
    // e.g. tennis
    rules.add(new SuffixTransformerRule("nis", "nis"));
    // iris, paris, tigris, ...
    rules.add(new SuffixTransformerRule("ris", "ris"));
    // crisis, analysis, ...
    rules.add(new SuffixTransformerRule("sis", "sis"));
    // bronchitis, gratis, ...
    rules.add(new SuffixTransformerRule("tis", "tis"));
    // axis, prophylaxis, ...
    rules.add(new SuffixTransformerRule("xis", "xis"));

    // SECTION: regular rules

    // interfaCES --> interfaCE
    // plaCES --> plaCE
    rules.add(new SuffixTransformerRule("ces", "ce"));
    // tyPES --> tyPE
    // apES --> aPE
    // scoPES --> scoPE
    rules.add(new SuffixTransformerRule("pes", "pe"));
    // actreSSES --> actreSS
    // maSSES --> maSS
    rules.add(new SuffixTransformerRule("sses", "ss"));
    // houSES --> houSE
    rules.add(new SuffixTransformerRule("ses", "se"));
    // kniVES --> kniFE
    // calVES --> calf
    rules.add(new SuffixTransformerRule("ves", "f"));
    // maTRICES --> maTRIX
    rules.add(new SuffixTransformerRule("trices", "trix"));
    // indICES --> indEX
    // vertICES --> vertEX
    rules.add(new SuffixTransformerRule("ices", "ex"));
    // french: *eaux --> *eau
    // beaux --> beau
    // bureaux --> bureau
    rules.add(new SuffixTransformerRule("eaux", "eau"));
    // ITALIEN: *zzi --> *zzo
    // paparazzi --> paparazzo
    rules.add(new SuffixTransformerRule("zzi", "zzo"));
    // utilitIES --> utilitY
    // ATTENTION:
    // serIES --> serIES
    // specIES --> specIES
    rules.add(new SuffixTransformerRule("ies", "y"));
    rules.add(new SuffixTransformerRule("ues", "ue"));
    rules.add(new SuffixTransformerRule("ees", "ee"));
    // pages -> page (and not pag, see following rule)
    rules.add(new SuffixTransformerRule("ges", "ge"));
    // bunchES --> bunch
    // classES --> class
    // EXCUSE: canOES --> canOE, *UES->*UE
    rules.add(new SuffixTransformerRule("es", ""));
    // serviceS --> service
    rules.add(new SuffixTransformerRule("s", ""));
    RULES = Collections.unmodifiableList(rules);
  }

  /**
   * The constructor.
   */
  protected EnglishSingularizer() {

    super();
  }

  @Override
  protected List<TransformerRule> getRules() {

    return RULES;
  }

}
