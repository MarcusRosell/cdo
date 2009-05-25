/**
 * Copyright (c) 2004 - 2009 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.tests.db;

import org.eclipse.emf.cdo.server.IRepository;
import org.eclipse.emf.cdo.server.db.CDODBUtil;
import org.eclipse.emf.cdo.server.db.mapping.IMappingStrategy;
import org.eclipse.emf.cdo.tests.AllTestsAllConfigs;
import org.eclipse.emf.cdo.tests.bugzilla.Bugzilla_266982_Test;
import org.eclipse.emf.cdo.tests.config.impl.ConfigTest;
import org.eclipse.emf.cdo.tests.db.bundle.OM;
import org.eclipse.emf.cdo.tests.db.verifier.DBStoreVerifier;

import org.eclipse.net4j.db.DBUtil;
import org.eclipse.net4j.db.IDBAdapter;
import org.eclipse.net4j.db.hsqldb.HSQLDBAdapter;
import org.eclipse.net4j.db.hsqldb.HSQLDBDataSource;

import javax.sql.DataSource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Eike Stepper
 */
public class AllTestsDBHsqldb extends AllTestsAllConfigs
{
  public static Test suite()
  {
    return new AllTestsDBHsqldb().getTestSuite("CDO Tests (DBStoreRepositoryConfig Hsql Horizontal)");
  }

  @Override
  protected void initConfigSuites(TestSuite parent)
  {
    addScenario(parent, COMBINED, AllTestsDBHsqldb.Hsqldb.INSTANCE, TCP, NATIVE);
  }

  @Override
  protected void initTestClasses(List<Class<? extends ConfigTest>> testClasses)
  {
    super.initTestClasses(testClasses);

    // this takes ages ...
    testClasses.remove(Bugzilla_266982_Test.class);
  }

  /**
   * @author Eike Stepper
   */
  public static class Hsqldb extends DBStoreRepositoryConfig
  {
    private static final long serialVersionUID = 1L;

    public static final AllTestsDBHsqldb.Hsqldb INSTANCE = new Hsqldb("HSQLDB");

    public static boolean USE_VERIFIER = false;

    private transient HSQLDBDataSource dataSource;

    public Hsqldb(String name)
    {
      super(name);
    }

    @Override
    protected IMappingStrategy createMappingStrategy()
    {
      return CDODBUtil.createHorizontalMappingStrategy(true);
    }

    @Override
    protected IDBAdapter createDBAdapter()
    {
      return new HSQLDBAdapter();
    }

    @Override
    protected DataSource createDataSource()
    {
      dataSource = new HSQLDBDataSource();
      dataSource.setDatabase("jdbc:hsqldb:mem:dbtest");
      dataSource.setUser("sa");

      try
      {
        dataSource.setLogWriter(new PrintWriter(System.err));
      }
      catch (SQLException ex)
      {
        OM.LOG.warn(ex.getMessage());
      }

      return dataSource;
    }

    @Override
    public void tearDown() throws Exception
    {
      try
      {
        if (USE_VERIFIER)
        {
          IRepository testRepository = getRepository(REPOSITORY_NAME);
          if (testRepository != null)
          {
            getVerifier(testRepository).verify();
          }
        }
      }
      finally
      {
        try
        {
          super.tearDown();
        }
        finally
        {
          shutDownHsqldb();
        }
      }
    }

    protected DBStoreVerifier getVerifier(IRepository repository)
    {
      return new DBStoreVerifier.Audit(repository);
    }

    private void shutDownHsqldb() throws SQLException
    {
      if (dataSource != null)
      {
        Connection connection = null;
        Statement statement = null;

        try
        {
          connection = dataSource.getConnection();
          statement = connection.createStatement();
          statement.execute("SHUTDOWN");
        }
        finally
        {
          DBUtil.close(statement);
          DBUtil.close(connection);
          dataSource = null;
        }
      }
    }
  }
}
