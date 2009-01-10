/***************************************************************************
 * Copyright (c) 2004 - 2008 Eike Stepper, Germany.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Simon McDuff - initial API and implementation
 **************************************************************************/
package org.eclipse.emf.cdo.internal.server.protocol;

import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.io.CDODataInput;
import org.eclipse.emf.cdo.common.io.CDODataOutput;
import org.eclipse.emf.cdo.common.protocol.CDOProtocolConstants;
import org.eclipse.emf.cdo.server.IView;

import org.eclipse.net4j.util.concurrent.RWLockManager;

import java.io.IOException;

/**
 * @author Simon McDuff
 */
public class ObjectLockedIndication extends CDOReadIndication
{
  private boolean isLocked;

  public ObjectLockedIndication(CDOServerProtocol protocol)
  {
    super(protocol, CDOProtocolConstants.SIGNAL_OBJECT_LOCKED);
  }

  @Override
  protected void indicating(CDODataInput in) throws IOException
  {
    int viewID = in.readInt();
    IView view = getSession().getView(viewID);

    RWLockManager.LockType lockType = in.readCDOLockType();
    CDOID id = in.readCDOID();
    isLocked = getRepository().getLockManager().hasLock(lockType, view, id);
  }

  @Override
  protected void responding(CDODataOutput out) throws IOException
  {
    out.writeBoolean(isLocked);
  }
}
