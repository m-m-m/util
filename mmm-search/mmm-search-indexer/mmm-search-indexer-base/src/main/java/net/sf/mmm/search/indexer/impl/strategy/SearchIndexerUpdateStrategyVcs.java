/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.impl.strategy;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PreDestroy;
import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.search.indexer.api.EntryUpdateVisitor;
import net.sf.mmm.search.indexer.api.ResourceSearchIndexer;
import net.sf.mmm.search.indexer.api.SearchIndexer;
import net.sf.mmm.search.indexer.api.config.SearchIndexerDataLocation;
import net.sf.mmm.search.indexer.api.config.SearchIndexerSource;
import net.sf.mmm.search.indexer.api.strategy.UpdateStrategyArguments;
import net.sf.mmm.search.indexer.base.strategy.BasicSearchIndexerUpdateStrategy;
import net.sf.mmm.util.component.api.ResourceMissingException;
import net.sf.mmm.util.event.api.ChangeType;
import net.sf.mmm.util.nls.api.IllegalCaseException;
import net.sf.mmm.util.nls.api.NlsIllegalStateException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;
import net.sf.mmm.util.resource.api.DataResource;

import org.apache.maven.scm.ScmException;
import org.apache.maven.scm.ScmFile;
import org.apache.maven.scm.ScmFileSet;
import org.apache.maven.scm.ScmFileStatus;
import org.apache.maven.scm.command.update.UpdateScmResult;
import org.apache.maven.scm.command.update.UpdateScmResultWithRevision;
import org.apache.maven.scm.manager.NoSuchScmProviderException;
import org.apache.maven.scm.manager.ScmManager;
import org.apache.maven.scm.repository.ScmRepository;
import org.apache.maven.scm.repository.UnknownRepositoryStructure;
import org.codehaus.plexus.PlexusContainerException;
import org.codehaus.plexus.component.repository.exception.ComponentLookupException;
import org.codehaus.plexus.embed.Embedder;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.search.indexer.api.strategy.SearchIndexerUpdateStrategy}
 * for {@link SearchIndexerSource#UPDATE_STRATEGY_LAST_MODIFIED}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Singleton
@Named
public class SearchIndexerUpdateStrategyVcs extends BasicSearchIndexerUpdateStrategy {

  /** @see #getScmManager() */
  private ScmManager scmManager;

  /** @see #createScmManager() */
  private Embedder plexus;

  /** @see #detectVcsType(File) */
  private static final Map<String, String> FOLDER2VCS_MAP;

  static {
    FOLDER2VCS_MAP = new HashMap<String, String>();
    FOLDER2VCS_MAP.put(".svn", SearchIndexerDataLocation.UPDATE_STRATEGY_VCS_VARIANT_SVN);
    FOLDER2VCS_MAP.put(".bzr", SearchIndexerDataLocation.UPDATE_STRATEGY_VCS_VARIANT_BAZAAR);
    FOLDER2VCS_MAP.put(".hg", SearchIndexerDataLocation.UPDATE_STRATEGY_VCS_VARIANT_MERCURIAL);
    FOLDER2VCS_MAP.put(".git", SearchIndexerDataLocation.UPDATE_STRATEGY_VCS_VARIANT_GIT);
    FOLDER2VCS_MAP.put("CVS", SearchIndexerDataLocation.UPDATE_STRATEGY_VCS_VARIANT_CVS);
    // FOLDER2VCS_MAP.put(".p4",
    // SearchIndexerDataLocation.UPDATE_STRATEGY_VCS_VARIANT_PERFORCE);
  }

  /**
   * The constructor.
   */
  public SearchIndexerUpdateStrategyVcs() {

    super(SearchIndexerSource.UPDATE_STRATEGY_VCS);
  }

  /**
   * This method gets the {@link ScmManager} used to access the
   * version-control-system (VCS).
   * 
   * @return the {@link ScmManager}.
   */
  protected ScmManager getScmManager() {

    return this.scmManager;
  }

  /**
   * @param scmManager is the scmManager to set
   */
  // @Inject
  public void setScmManager(ScmManager scmManager) {

    getInitializationState().requireNotInitilized();
    this.scmManager = scmManager;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.scmManager == null) {
      this.scmManager = createScmManager();
    }
  }

  /**
   * This method is called when this component shall be destroyed.
   */
  @PreDestroy
  public void dispose() {

    if (this.plexus != null) {
      this.plexus.stop();
      this.plexus = null;
    }
  }

  /**
   * This method creates the {@link ScmManager} instance manually. This method
   * is called during {@link #initialize() initialization} if the
   * {@link ScmManager} has NOT been {@link #setScmManager(ScmManager) set}
   * (injected) otherwise.
   * 
   * @return the created {@link ScmManager}.
   */
  protected ScmManager createScmManager() {

    try {
      getLogger().debug("Starting plexus for getting " + ScmManager.class.getSimpleName() + "...");
      this.plexus = new Embedder();
      this.plexus.start();
      ScmManager result = (ScmManager) this.plexus.lookup(ScmManager.ROLE);
      NlsNullPointerException.checkNotNull(ScmManager.class, result);
      getLogger().debug("Plexus started successfully.");
      return result;
    } catch (PlexusContainerException e) {
      throw new NlsIllegalStateException(e);
    } catch (ComponentLookupException e) {
      throw new NlsIllegalStateException(e);
    }
  }

  /**
   * This method tries to detect the VCS type for the given
   * <code>location</code>.
   * 
   * @param location is the location for which the VCS-type is requested.
   * @return the detected VCS-type.
   * @throws RuntimeException if the VCS-type could NOT be detected.
   */
  protected String detectVcsType(File location) throws RuntimeException {

    if (location.isDirectory()) {
      for (File child : location.listFiles()) {
        if (child.isDirectory()) {
          String childName = child.getName();
          String vcsType = FOLDER2VCS_MAP.get(childName);
          if (vcsType != null) {
            return vcsType;
          }
        }
      }
    }
    // TODO create own exception
    throw new ObjectNotFoundException("vcs-type");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings("unchecked")
  public void index(UpdateStrategyArguments arguments, SearchIndexerDataLocation location,
      EntryUpdateVisitor entryUpdateVisitor) {

    File locationDirectory = new File(location.getLocationUri());
    String vcsType = location.getUpdateStrategyVariant();
    SearchIndexer indexer = arguments.getIndexer();
    ResourceSearchIndexer resourceSearchIndexer = getIndexerDependencies()
        .getResourceSearchIndexer();
    // if
    // (SearchIndexerDataLocation.UPDATE_STRATEGY_VCS_VARIANT_RSYNC.equals(variant))
    // {
    // TODO
    // } else {
    if (vcsType == null) {
      vcsType = detectVcsType(locationDirectory);
    }
    try {
      ScmRepository scmRepository = this.scmManager.makeProviderScmRepository(vcsType,
          locationDirectory);
      ScmFileSet fileSet = new ScmFileSet(locationDirectory);
      UpdateScmResult result = this.scmManager.update(scmRepository, fileSet);
      List<ScmFile> updatedFiles = result.getUpdatedFiles();
      for (ScmFile changedFile : updatedFiles) {
        String path = changedFile.getPath();
        DataResource resource = getIndexerDependencies().getBrowsableResourceFactory()
            .createDataResource(path);
        ScmFileStatus status = changedFile.getStatus();
        ChangeType changeType;
        if (status == ScmFileStatus.ADDED) {
          changeType = ChangeType.ADD;
        } else if (status == ScmFileStatus.UPDATED) {
          changeType = ChangeType.UPDATE;
        } else if (status == ScmFileStatus.DELETED) {
          changeType = ChangeType.REMOVE;
        } else if (status == ScmFileStatus.MODIFIED) {
          changeType = ChangeType.ADD;
        } else if (status == ScmFileStatus.CONFLICT) {
          // TODO: delete file and update again?
          changeType = ChangeType.ADD;
        } else {
          throw new IllegalCaseException(status.toString());
        }
        resourceSearchIndexer.index(indexer, resource, changeType, location);
      }
      if (result instanceof UpdateScmResultWithRevision) {
        UpdateScmResultWithRevision resultWithRevision = (UpdateScmResultWithRevision) result;
        String revision = resultWithRevision.getRevision();
        arguments.getSourceState().getLocationState(location.getLocationUri())
            .setRevision(revision);
      }
      // }
    } catch (NoSuchScmProviderException e) {
      throw new ResourceMissingException(vcsType, e);
    } catch (ScmException e) {
      // TODO
      throw new NlsIllegalStateException(e);
    } catch (UnknownRepositoryStructure e) {
      // TODO
      throw new NlsIllegalStateException(e);
    }
  }

}
