/* $Id$ */
package net.sf.mmm.configuration.base.path;

import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.configuration.api.ConfigurationException;

/**
 * This is the manager where all available
 * {@link net.sf.mmm.configuration.base.path.ComparatorIF comparators} are
 * registered.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ComparatorManager {

    /** the singleton instance */
    private static ComparatorManager INSTANCE = null;

    /** the registered comparators */
    private final Map<String, ComparatorIF> comparators;

    /**
     * The constructor.
     */
    public ComparatorManager() {

        super();
        this.comparators = new HashMap<String, ComparatorIF>();
        ComparatorIF eq = new EqualsComparator();
        this.comparators.put("=", eq);
        register(eq);
        register(new NotEqualsComparator());
    }

    /**
     * This method gets the comparator for the given
     * {@link ComparatorIF#getSymbol() symbol}.
     * 
     * @param symbol
     *        is the {@link ComparatorIF#getSymbol() symbol} of the requested
     *        comparator.
     * @return the comparator for the given symbol.
     */
    public final ComparatorIF getComparator(String symbol) {

        ComparatorIF result = this.comparators.get(symbol);
        if (result == null) {
            throw new ConfigurationException("Undefined comparator symbol " + symbol);
        }
        return result;
    }

    /**
     * 
     * @param comparator
     */
    protected final void register(ComparatorIF comparator) {

        String symbol = comparator.getSymbol();
        if (this.comparators.containsKey(symbol)) {
            throw new ConfigurationException("Duplicate symbol!");
        }
    }

    /**
     * This method gets the singleton instance of the comparator manager.
     * 
     * @return the singleton instance.
     */
    public static ComparatorManager getInstance() {

        if (INSTANCE == null) {
            synchronized (ComparatorManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ComparatorManager();
                }
            }
        }
        return INSTANCE;
    }

}
