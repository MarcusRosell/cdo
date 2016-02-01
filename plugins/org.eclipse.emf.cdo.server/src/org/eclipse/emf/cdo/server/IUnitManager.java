/*
 * Copyright (c) 2016 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.server;

import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.revision.CDORevisionHandler;

import org.eclipse.net4j.util.container.IContainer;

/**
 * @author Eike Stepper
 * @since 4.5
 */
public interface IUnitManager extends IContainer<IUnit>
{
  public IRepository getRepository();

  public IUnit createUnit(CDOID rootID, IView view, CDORevisionHandler revisionHandler);

  public boolean isUnit(CDOID rootID);

  public IUnit getUnit(CDOID rootID);

  public IUnit[] getUnits();
}