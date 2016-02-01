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
package org.eclipse.emf.cdo.view;

import org.eclipse.emf.cdo.common.util.CDOException;

import org.eclipse.net4j.util.container.IContainer;

import org.eclipse.emf.ecore.EObject;

/**
 * @author Eike Stepper
 * @since 4.5
 */
public interface CDOUnitManager extends IContainer<CDOUnit>
{
  public CDOView getView();

  public boolean isUnit(EObject root);

  public CDOUnit createUnit(EObject root) throws UnitExistsException;

  public CDOUnit openUnit(EObject root) throws UnitNotFoundException;

  public CDOUnit getOpenUnit(EObject object);

  public CDOUnit[] getOpenUnits();

  /**
   * @author Eike Stepper
   */
  public static final class UnitExistsException extends CDOException
  {
    private static final long serialVersionUID = 1L;
  }

  /**
   * @author Eike Stepper
   */
  public static final class UnitNotFoundException extends CDOException
  {
    private static final long serialVersionUID = 1L;
  }
}