package net.sourceforge.fenixedu.domain.thesis;

import java.text.Collator;
import java.util.Comparator;

import net.sourceforge.fenixedu.domain.Employee;
import net.sourceforge.fenixedu.domain.Person;
import net.sourceforge.fenixedu.domain.RootDomainObject;
import net.sourceforge.fenixedu.domain.Teacher;
import net.sourceforge.fenixedu.domain.organizationalStructure.ExternalContract;
import net.sourceforge.fenixedu.domain.organizationalStructure.Unit;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;

import pt.ist.fenixframework.pstm.AbstractDomainObject;
import dml.runtime.RelationAdapter;

public class ThesisEvaluationParticipant extends ThesisEvaluationParticipant_Base {

    private static KeepParticipationNumberAdapter KEEP_PARTICIPATION_NUMBER_ADAPTER = new KeepParticipationNumberAdapter();
    static {
        ThesisHasParticipations.addListener(KEEP_PARTICIPATION_NUMBER_ADAPTER);
    }

    public final static Comparator<ThesisEvaluationParticipant> COMPARATOR_BY_PERSON_NAME = new ComparatorChain();
    static {
        ((ComparatorChain) COMPARATOR_BY_PERSON_NAME).addComparator(new BeanComparator("person.name", Collator.getInstance()));
        ((ComparatorChain) COMPARATOR_BY_PERSON_NAME).addComparator(AbstractDomainObject.COMPARATOR_BY_ID);
    }

    public final static Comparator<ThesisEvaluationParticipant> COMPARATOR_BY_STUDENT_NUMBER = new BeanComparator(
            "thesis.student.number");

    public ThesisEvaluationParticipant(Thesis thesis, Person person, ThesisParticipationType type) {
        super();

        setRootDomainObject(RootDomainObject.getInstance());

        setType(type);
        setThesis(thesis);
        setPerson(person);
        setPersonName(person.getName());
    }

    public String getPersonNameWithLogin() {
        Person person = getPerson();

        if (person == null || person.hasExternalContract()) {
            return getPersonName() + " (Externa)";
        } else {
            return getPersonName() + " (" + person.getIstUsername() + ")";
        }
    }

    @Override
    public void setPerson(Person person) {
        super.setPerson(person);

        if (person != null) { // consider remove
            updateParticipantInformation(person);
        }
    }

    protected void updateParticipantInformation(Person person) {
        Teacher teacher = person.getTeacher();

        if (teacher != null && teacher.getCurrentWorkingDepartment() != null) {
            if (teacher.getCategory() == null) {
                setCategory("-");
            } else {
                setCategory(teacher.getCategory().getName().getContent());
            }
            setAffiliation(teacher.getCurrentWorkingDepartment().getRealName());
        } else {
            Employee employee = person.getEmployee();
            if (employee != null) {
                Unit currentWorkingPlace = employee.getCurrentWorkingPlace();
                if (currentWorkingPlace != null) {
                    setAffiliation(currentWorkingPlace.getNameWithAcronym());
                }
            } else {
                ExternalContract contract = person.getExternalContract();
                if (contract != null) {
                    setAffiliation(contract.getInstitutionUnit().getName());
                }
            }
        }
    }

    public double getParticipationCredits() {
        return Thesis.getCredits() * getCreditsDistribution() / 100;
    }

    public double getCreditsDistribution() {
        Thesis thesis = getThesis();

        if (!thesis.hasCredits()) {
            return 0.0;
        }

        ThesisParticipationType type = this.getType();

        if (type.equals(ThesisParticipationType.ORIENTATOR)) {
            if (thesis.getOrientatorCreditsDistribution() != null) {
                return thesis.getOrientatorCreditsDistribution();
            }
        }

        if (type.equals(ThesisParticipationType.COORIENTATOR)) {
            if (thesis.getCoorientatorCreditsDistribution() != null) {
                return thesis.getCoorientatorCreditsDistribution();
            }
        }

        return 0.0;
    }

    public void delete() {
        setRootDomainObject(null);
        setPerson(null);
        setThesis(null);

        deleteDomainObject();
    }

    @Override
    public void setType(ThesisParticipationType type) {
        super.setType(type);

        KEEP_PARTICIPATION_NUMBER_ADAPTER.changedType(this);
    }

    public static class KeepParticipationNumberAdapter extends RelationAdapter<ThesisEvaluationParticipant, Thesis> {

        @Override
        public void beforeAdd(ThesisEvaluationParticipant o1, Thesis o2) {
            super.beforeAdd(o1, o2);

            if (o1 != null && o2 != null) {
                keepTypeCount(o1, o2);
            }
        }

        public void changedType(ThesisEvaluationParticipant participant) {
            keepTypeCount(participant, participant.getThesis());
        }

        private void keepTypeCount(ThesisEvaluationParticipant participant, Thesis thesis) {
            if (thesis == null) {
                return;
            }

            ThesisParticipationType type = participant.getType();

            if (type == null) {
                return;
            }

            if (type.isSingle()) {
                ThesisEvaluationParticipant existing = thesis.getParticipant(type);

                if (existing != null && existing != participant) {
                    existing.delete();
                }
            }
        }

    }

}
