/*
 * Created on 12/Jan/2005
 *
 */
package DataBeans.student;

import DataBeans.InfoExecutionYear;
import DataBeans.InfoObject;
import DataBeans.InfoPerson;

/**
 * @author Ricardo Rodrigues
 *  
 */
public class InfoRegistrationDeclaration extends InfoObject {

    private String degreeName;

    private InfoExecutionYear infoExecutionYear;

    private InfoPerson infoPerson;

    /**
     *  
     */
    public InfoRegistrationDeclaration() {
    }

    /**
     * 
     * @param infoStudentCurricularPlan
     * @param infoExecutionYear
     * @param infoPerson
     */
    public InfoRegistrationDeclaration(String degreeName,
            InfoExecutionYear infoExecutionYear, InfoPerson infoPerson) {
        
        setDegreeName(degreeName);
        setInfoExecutionYear(infoExecutionYear);
        setInfoPerson(infoPerson);
    }

    /**
     * @return Returns the infoExecutionYear.
     */
    public InfoExecutionYear getInfoExecutionYear() {
        return infoExecutionYear;
    }

    /**
     * @param infoExecutionYear
     *            The infoExecutionYear to set.
     */
    public void setInfoExecutionYear(InfoExecutionYear infoExecutionYear) {
        this.infoExecutionYear = infoExecutionYear;
    }

    /**
     * @return Returns the infoPerson.
     */
    public InfoPerson getInfoPerson() {
        return infoPerson;
    }

    /**
     * @param infoPerson
     *            The infoPerson to set.
     */
    public void setInfoPerson(InfoPerson infoPerson) {
        this.infoPerson = infoPerson;
    }

    /**
     * @return Returns the infoStudentCurricularPlan.
     */
    public String getDegreeName() {
        return degreeName;
    }

    /**
     * @param infoStudentCurricularPlan
     *            The infoStudentCurricularPlan to set.
     */
    public void setDegreeName(String degreeName) {
        this.degreeName = degreeName;
    }
}
