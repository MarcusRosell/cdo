/**
 * <copyright>
 * Copyright (c) 2004 - 2008 André Dietisheim, Switzerland.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    André Dietisheim - initial API and implementation
 * </copyright>
 *
 * $Id: ExecutorServiceDefImpl.java,v 1.1 2008-12-31 14:43:36 estepper Exp $
 */
package org.eclipse.net4j.util.defs.impl;

import org.eclipse.net4j.util.defs.ExecutorServiceDef;
import org.eclipse.net4j.util.defs.Net4jUtilDefsPackage;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Executor Service Def</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * </p>
 * 
 * @generated
 */
public abstract class ExecutorServiceDefImpl extends DefImpl implements ExecutorServiceDef
{
  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  protected ExecutorServiceDefImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return Net4jUtilDefsPackage.Literals.EXECUTOR_SERVICE_DEF;
  }

} // ExecutorServiceDefImpl
