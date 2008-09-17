/**
 * <copyright>
 * </copyright>
 *
 * $Id: ImplSingleRefNonContainerImpl.java,v 1.2.8.2 2008-09-17 12:15:13 estepper Exp $
 */
package org.eclipse.emf.cdo.tests.model4.impl;

import org.eclipse.emf.cdo.tests.model4.ImplSingleRefNonContainer;
import org.eclipse.emf.cdo.tests.model4.model4Package;
import org.eclipse.emf.cdo.tests.model4interfaces.ISingleRefNonContainedElement;
import org.eclipse.emf.cdo.tests.model4interfaces.model4interfacesPackage;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Impl Single Ref Non Container</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.cdo.tests.model4.impl.ImplSingleRefNonContainerImpl#getElement <em>Element</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class ImplSingleRefNonContainerImpl extends CDOObjectImpl implements ImplSingleRefNonContainer
{
  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  protected ImplSingleRefNonContainerImpl()
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
    return model4Package.Literals.IMPL_SINGLE_REF_NON_CONTAINER;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  protected int eStaticFeatureCount()
  {
    return 0;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public ISingleRefNonContainedElement getElement()
  {
    return (ISingleRefNonContainedElement)eGet(model4interfacesPackage.Literals.ISINGLE_REF_NON_CONTAINER__ELEMENT,
        true);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void setElement(ISingleRefNonContainedElement newElement)
  {
    eSet(model4interfacesPackage.Literals.ISINGLE_REF_NON_CONTAINER__ELEMENT, newElement);
  }

} // ImplSingleRefNonContainerImpl
