/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

import java.io.File;
import java.util.List;

import net.sf.mmm.util.file.api.FileType;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * This is the test-case for {@link CliConstraintNumber},
 * {@link CliConstraintContainer} and {@link CliConstraintFile}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
@SuppressWarnings("all")
public class CliConstraintTest {

  /**
   * This method runs {@link TestMain} with all constraints fulfilled and checks
   * that all values have been assigned properly.
   */
  @Test
  public void testConstraints() {

    TestMain main = new TestMain();
    int exitCode = main.run("--port", "8080", "--weigth", "0.9999", "--home",
        System.getProperty("user.home"), "--peak", "55", "--peak", "66", "--peak", "77");
    Assert.assertEquals(0, exitCode);
    Assert.assertEquals(8080, main.port);
    Assert.assertEquals(0.9999, main.fuzzyWeigth, 0.0);
    Assert.assertEquals(System.getProperty("user.home"), main.homeDirectory.getPath());
    Assert.assertEquals(3, main.peaks.size());
    Assert.assertEquals(55, main.peaks.get(0).intValue());
    Assert.assertEquals(66, main.peaks.get(1).intValue());
    Assert.assertEquals(77, main.peaks.get(2).intValue());
  }

  /**
   * This method runs {@link TestMain} with all constraints fulfilled and checks
   * that all values have been assigned properly.
   */
  @Test
  @Ignore("Validation should be ported to JSR 303")
  public void testConstraintNumberFailure() {

    TestMain main = new TestMain();
    int exitCode = main.run("--port", "443", "--weigth", "0.9999", "--home",
        System.getProperty("user.home"), "--peak", "55", "--peak", "66", "--peak", "77");
    Assert.assertEquals(-1, exitCode);
  }

  @CliClass(usage = TestMain.USAGE)
  @CliMode(id = CliMode.ID_DEFAULT)
  private static class TestMain extends AbstractVersionedMain {

    private static final String USAGE = "This program is used for tests only";

    @CliOption(name = "--port", usage = "The port number.")
    @CliConstraintNumber(min = 8080, max = 9090)
    private int port;

    @CliOption(name = "--weigth", usage = "A fuzzy weigth.")
    @CliConstraintNumber(min = -1, max = 1)
    private Double fuzzyWeigth;

    @CliOption(name = "--home", usage = "The home directory.")
    @CliConstraintFile(type = FileType.DIRECTORY, exists = true)
    private File homeDirectory;

    @CliOption(name = "--peak", usage = "The peaks.")
    @CliConstraintContainer(min = 3, max = 5)
    private List<Integer> peaks;

    /** @see #handleError(Exception, CliParser) */
    private CliConstraintIllegalException constraintException;

    /**
     * {@inheritDoc}
     */
    @Override
    protected int run(CliModeObject mode) throws Exception {

      if (CliMode.ID_DEFAULT.equals(mode.getId())) {
        return EXIT_CODE_OK;
      }
      return super.run(mode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected int handleError(Exception exception, CliParser parser) {

      if (exception instanceof CliConstraintIllegalException) {
        this.constraintException = (CliConstraintIllegalException) exception;
        return EXIT_CODE_ILLEGAL_SYNTAX;
      }
      return super.handleError(exception, parser);
    }
  }
}
