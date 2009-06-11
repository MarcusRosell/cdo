/***************************************************************************
 * Copyright (c) 2004 - 2009 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Andre Dietisheim - initial API and implementation
 *    Eike Stepper - maintenance
 **************************************************************************/
package org.eclipse.emf.cdo.common.internal.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.MessageFormat;

import org.eclipse.emf.cdo.common.model.CDOModelConstants;
import org.eclipse.emf.cdo.common.revision.CDORevision;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDORevision;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.net4j.db.DBException;
import org.eclipse.net4j.util.CheckUtil;

/**
 * @author Andre Dietisheim
 */
public class DBRevisionCacheUtils
{

  public static void mandatoryInsertUpdate(PreparedStatement preparedStatement) throws SQLException
  {
    insertUpdate(preparedStatement);
    if (preparedStatement.getUpdateCount() == 0)
    {
      rollback(preparedStatement.getConnection());
      throw new DBException(MessageFormat.format("No row inserted by statement \"{0}\"", preparedStatement));
    }
  }

  public static void insertUpdate(PreparedStatement preparedStatement) throws SQLException
  {
    if (preparedStatement.execute())
    {
      rollback(preparedStatement.getConnection());
      throw new DBException("No result set expected");
    }
    commit(preparedStatement.getConnection());
  }

  public static void rollback(Connection connection) throws SQLException
  {
    assertIsNotNull(connection);
    connection.rollback();
  }

  public static void commit(Connection connection) throws SQLException
  {
    assertIsNotNull(connection);
    connection.commit();
  }

  /**
   * Asserts the given {@link CDORevision} is <tt>null</tt>.
   * 
   * @param cdoRevision
   *          the cdo revision
   * @param message
   *          the message to use when throwing the {@link DBException}
   * @throws DBException
   *           if the given CDORevision's not <tt>null</tt>
   */
  public static void assertIsNull(CDORevision cdoRevision, String message)
  {
    if (cdoRevision != null)
    {
      throw new DBException(message);
    }
  }

  /**
   * Asserts the given {@link Connection} is not <tt>null</tt>.
   * 
   * @param connection
   *          the connection to check
   * @return the connection
   * @throws DBException
   *           if the given connection's <tt>null</tt>
   */
  public static Connection assertIsNotNull(Connection connection)
  {
    if (connection == null)
    {
      throw new DBException("connection is null!");
    }
    else
    {
      return connection;
    }
  }

  public static String getResourceNodeName(CDORevision revision)
  {
    CheckUtil.checkArg(revision.isResourceNode(), "the revision is not a resource node!");
    EStructuralFeature feature = revision.getEClass().getEStructuralFeature(
        CDOModelConstants.RESOURCE_NODE_NAME_ATTRIBUTE);
    return (String)((InternalCDORevision)revision).getValue(feature);
  }
}