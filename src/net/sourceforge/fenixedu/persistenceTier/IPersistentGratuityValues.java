/*
 * Created on 9/Jan/2004
 *
 */
package net.sourceforge.fenixedu.persistenceTier;

import net.sourceforge.fenixedu.domain.IGratuityValues;

/**
 * @author T�nia Pous�o
 *  
 */
public interface IPersistentGratuityValues extends IPersistentObject {
    public IGratuityValues readGratuityValuesByExecutionDegree(Integer executionDegreeID)
            throws ExcepcaoPersistencia;
}