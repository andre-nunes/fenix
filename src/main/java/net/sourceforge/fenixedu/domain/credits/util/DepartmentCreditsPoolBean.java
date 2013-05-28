package net.sourceforge.fenixedu.domain.credits.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import net.sourceforge.fenixedu.domain.Department;
import net.sourceforge.fenixedu.domain.DepartmentCreditsPool;
import net.sourceforge.fenixedu.domain.ExecutionCourse;
import net.sourceforge.fenixedu.domain.ExecutionSemester;
import net.sourceforge.fenixedu.domain.ExecutionYear;
import net.sourceforge.fenixedu.domain.Professorship;
import net.sourceforge.fenixedu.domain.credits.AnnualCreditsState;
import net.sourceforge.fenixedu.domain.exceptions.DomainException;
import net.sourceforge.fenixedu.domain.teacher.DegreeTeachingService;
import net.sourceforge.fenixedu.domain.teacher.DegreeTeachingServiceCorrection;
import pt.ist.fenixWebFramework.services.Service;

public class DepartmentCreditsPoolBean implements Serializable {

    protected Department department;

    protected DepartmentCreditsPool departmentCreditsPool;

    protected AnnualCreditsState annualCreditsState;

    private SortedSet<DepartmentExecutionCourse> departmentSharedExecutionCourses;

    private SortedSet<DepartmentExecutionCourse> departmentExecutionCourses;

    protected BigDecimal availableCredits;

    protected BigDecimal assignedCredits;

    public DepartmentCreditsPoolBean(DepartmentCreditsBean departmentCreditsBean) {
        setDepartment(departmentCreditsBean.getDepartment());
        ExecutionYear executionYear = departmentCreditsBean.getExecutionYear();
        setDepartmentCreditsPool(DepartmentCreditsPool.getDepartmentCreditsPool(getDepartment(), executionYear));
        setAnnualCreditsState(AnnualCreditsState.getAnnualCreditsState(executionYear));
        setValues();
    }

    protected void setValues() {
        departmentSharedExecutionCourses = new TreeSet<DepartmentExecutionCourse>();
        departmentExecutionCourses = new TreeSet<DepartmentExecutionCourse>();
        availableCredits = BigDecimal.ZERO;
        assignedCredits = BigDecimal.ZERO;
        for (ExecutionSemester executionSemester : getAnnualCreditsState().getExecutionYear().getExecutionPeriods()) {
            for (ExecutionCourse executionCourse : executionSemester.getAssociatedExecutionCourses()) {
                if (!(executionCourse.isDissertation() || executionCourse.getProjectTutorialCourse())) {
                    if (executionCourse.getDepartments().contains(getDepartment())) {
                        DepartmentExecutionCourse departmentExecutionCourse = new DepartmentExecutionCourse(executionCourse);
                        if (isSharedExecutionCourse(executionCourse)) {
                            departmentSharedExecutionCourses.add(departmentExecutionCourse);
                        } else {
                            departmentExecutionCourses.add(departmentExecutionCourse);
                        }
                        assignedCredits =
                                assignedCredits.add(departmentExecutionCourse.getDepartmentEffectiveLoad().multiply(
                                        executionCourse.getUnitCreditValue()));
                    }
                }
            }
        }
        availableCredits = departmentCreditsPool.getCreditsPool().subtract(assignedCredits);
    }

    private boolean isSharedExecutionCourse(ExecutionCourse executionCourse) {
        Department otherProfessorshipDepartment = null;
        for (Professorship professorship : executionCourse.getProfessorships()) {
            Department professorshipDepartment =
                    professorship.getTeacher().getLastWorkingDepartment(
                            executionCourse.getExecutionPeriod().getBeginDateYearMonthDay(),
                            executionCourse.getExecutionPeriod().getEndDateYearMonthDay());
            if (professorshipDepartment != null) {
                if (otherProfessorshipDepartment == null) {
                    otherProfessorshipDepartment = professorshipDepartment;
                } else if (!professorshipDepartment.equals(otherProfessorshipDepartment)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean getCanEditSharedUnitCredits() {
        return getAnnualCreditsState().getSharedUnitCreditsInterval() != null
                && getAnnualCreditsState().getSharedUnitCreditsInterval().containsNow();
    }

    public boolean getCanEditUnitCredits() {
        return getAnnualCreditsState().getUnitCreditsInterval() != null
                && getAnnualCreditsState().getUnitCreditsInterval().containsNow();
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public AnnualCreditsState getAnnualCreditsState() {
        return annualCreditsState;
    }

    public void setAnnualCreditsState(AnnualCreditsState annualCreditsState) {
        this.annualCreditsState = annualCreditsState;
    }

    public DepartmentCreditsPool getDepartmentCreditsPool() {
        return departmentCreditsPool;
    }

    public void setDepartmentCreditsPool(DepartmentCreditsPool departmentCreditsPool) {
        this.departmentCreditsPool = departmentCreditsPool;
    }

    public Set<DepartmentExecutionCourse> getDepartmentSharedExecutionCourses() {
        return departmentSharedExecutionCourses;
    }

    public Set<DepartmentExecutionCourse> getDepartmentExecutionCourses() {
        return departmentExecutionCourses;
    }

    public BigDecimal getAvailableCredits() {
        return availableCredits;
    }

    public void setAvailableCredits(BigDecimal availableCredits) {
        this.availableCredits = availableCredits;
    }

    public BigDecimal getAssignedCredits() {
        return assignedCredits;
    }

    public void setAssignedCredits(BigDecimal assignedCredits) {
        this.assignedCredits = assignedCredits;
    }

    public class DepartmentExecutionCourse implements Serializable, Comparable<DepartmentExecutionCourse> {
        protected ExecutionCourse executionCourse;
        protected BigDecimal departmentEffectiveLoad = BigDecimal.ZERO;
        protected BigDecimal totalEffectiveLoad = BigDecimal.ZERO;
        protected BigDecimal unitCreditValue = BigDecimal.ZERO;

        public DepartmentExecutionCourse(ExecutionCourse executionCourse) {
            super();
            this.executionCourse = executionCourse;
            setUnitCreditValue(getExecutionCourse().getUnitCreditValue());
            setEfectiveLoads();
        }

        public ExecutionCourse getExecutionCourse() {
            return executionCourse;
        }

        public void setExecutionCourse(ExecutionCourse executionCourse) {
            this.executionCourse = executionCourse;
        }

        public BigDecimal getDepartmentEffectiveLoad() {
            return departmentEffectiveLoad;
        }

        public BigDecimal getTotalEffectiveLoad() {
            return totalEffectiveLoad;
        }

        public BigDecimal getUnitCreditValue() {
            return unitCreditValue;
        }

        public void setUnitCreditValue(BigDecimal unitCreditValue) {
            this.unitCreditValue = unitCreditValue;
        }

        public void setEfectiveLoads() {
            for (Professorship professorship : getExecutionCourse().getProfessorships()) {
                ExecutionSemester executionPeriod = getExecutionCourse().getExecutionPeriod();
                Department lastWorkingDepartment =
                        professorship.getTeacher().getLastWorkingDepartment(executionPeriod.getBeginDateYearMonthDay(),
                                executionPeriod.getEndDateYearMonthDay());
                for (DegreeTeachingService degreeTeachingService : professorship.getDegreeTeachingServices()) {
                    double efectiveLoad = degreeTeachingService.getEfectiveLoad();
                    if (lastWorkingDepartment != null && lastWorkingDepartment.equals(getDepartment())) {
                        this.departmentEffectiveLoad = this.departmentEffectiveLoad.add(new BigDecimal(efectiveLoad));
                    }
                    this.totalEffectiveLoad = this.totalEffectiveLoad.add(new BigDecimal(efectiveLoad));
                }
                for (DegreeTeachingServiceCorrection degreeTeachingServiceCorrection : professorship
                        .getDegreeTeachingServiceCorrections()) {
                    BigDecimal efectiveLoad = degreeTeachingServiceCorrection.getCorrection();
                    if (lastWorkingDepartment != null && lastWorkingDepartment.equals(getDepartment())) {
                        this.departmentEffectiveLoad = this.departmentEffectiveLoad.add(efectiveLoad);
                    }
                    this.totalEffectiveLoad = this.totalEffectiveLoad.add(efectiveLoad);
                }
            }
            this.departmentEffectiveLoad = this.departmentEffectiveLoad.setScale(2, BigDecimal.ROUND_HALF_UP);
            this.totalEffectiveLoad = this.totalEffectiveLoad.setScale(2, BigDecimal.ROUND_HALF_UP);
        }

        @Override
        public int compareTo(DepartmentExecutionCourse o) {
            if (o == null) {
                return -1;
            }
            int compareTo = getExecutionCourse().getName().compareTo(o.getExecutionCourse().getName());
            if (compareTo == 0) {
                compareTo =
                        getExecutionCourse().getDegreePresentationString().compareTo(
                                o.getExecutionCourse().getDegreePresentationString());
            }
            return compareTo == 0 ? getExecutionCourse().getExternalId().compareTo(o.getExecutionCourse().getExternalId()) : compareTo;
        }
    }

    @Service
    public void editUnitCredits() {
        BigDecimal newAssignedCredits = BigDecimal.ZERO;
        for (DepartmentExecutionCourse departmentExecutionCourse : departmentSharedExecutionCourses) {
            newAssignedCredits =
                    setExecutionCourseUnitCredit(departmentExecutionCourse, getCanEditSharedUnitCredits(), newAssignedCredits);
        }
        for (DepartmentExecutionCourse departmentExecutionCourse : departmentExecutionCourses) {
            newAssignedCredits =
                    setExecutionCourseUnitCredit(departmentExecutionCourse, getCanEditUnitCredits(), newAssignedCredits);
        }
        if (newAssignedCredits.compareTo(getDepartmentCreditsPool().getCreditsPool()) > 0) {
            throw new DomainException("label.excededDepartmentCreditsPool", getDepartmentCreditsPool().getCreditsPool()
                    .toString(), newAssignedCredits.toString());
        }
        setValues();
    }

    protected BigDecimal setExecutionCourseUnitCredit(DepartmentExecutionCourse departmentExecutionCourse,
            boolean canEditUnitValue, BigDecimal newAssignedCredits) {
        if (canEditUnitValue) {
            BigDecimal newExecutionCourseCLE =
                    departmentExecutionCourse.getDepartmentEffectiveLoad().multiply(
                            departmentExecutionCourse.getUnitCreditValue());
            newAssignedCredits = newAssignedCredits.add(newExecutionCourseCLE);
            departmentExecutionCourse.executionCourse.setUnitCreditValue(departmentExecutionCourse.getUnitCreditValue());
        } else {
            BigDecimal oldExecutionCourseCLE =
                    departmentExecutionCourse.getDepartmentEffectiveLoad().multiply(
                            departmentExecutionCourse.executionCourse.getUnitCreditValue());
            newAssignedCredits = newAssignedCredits.add(oldExecutionCourseCLE);
        }
        return newAssignedCredits;
    }
}