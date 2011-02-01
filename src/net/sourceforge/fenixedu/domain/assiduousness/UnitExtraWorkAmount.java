package net.sourceforge.fenixedu.domain.assiduousness;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

import net.sourceforge.fenixedu.domain.Employee;
import net.sourceforge.fenixedu.domain.RootDomainObject;
import net.sourceforge.fenixedu.domain.exceptions.DomainException;
import net.sourceforge.fenixedu.domain.organizationalStructure.Unit;
import net.sourceforge.fenixedu.util.Month;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.Region;
import org.joda.time.DateTimeFieldType;

import pt.utl.ist.fenix.tools.util.excel.StyledExcelSpreadsheet;

public class UnitExtraWorkAmount extends UnitExtraWorkAmount_Base {

    public UnitExtraWorkAmount(Integer year, Unit unit) {
	super();
	if (!isUniqueYearUnit(year, unit)) {
	    throw new DomainException("error.extraWorkAmount.alreadyExistsUnitYear");
	}
	setRootDomainObject(RootDomainObject.getInstance());
	setYear(year);
	setUnit(unit);
	setSpent(0.0);
    }

    private boolean isUniqueYearUnit(int year, Unit unit) {
	for (UnitExtraWorkAmount unitExtraWorkAmount : RootDomainObject.getInstance().getUnitsExtraWorkAmounts()) {
	    if (unitExtraWorkAmount != this && unitExtraWorkAmount.getYear().intValue() == year
		    && unitExtraWorkAmount.getUnit() == unit) {
		return false;
	    }
	}
	return true;
    }

    public Double getTotal() {
	Double total = 0.0;
	for (UnitExtraWorkMovement unitExtraWorkMovement : getUnitExtraWorkMovements()) {
	    total += unitExtraWorkMovement.getAmount();
	}
	return total;
    }

    public Double getInitial() {
	UnitExtraWorkMovement initial = null;
	for (UnitExtraWorkMovement unitExtraWorkMovement : getUnitExtraWorkMovements()) {
	    if (initial == null || initial.getDate().isAfter(unitExtraWorkMovement.getDate())) {
		initial = unitExtraWorkMovement;
	    }
	}
	return initial == null ? 0.0 : initial.getAmount();
    }

    public Double getBalance() {
	return getTotal() - getSpent();
    }

    public void sumSpent(Double amount) {
	setSpent(getSpent() + amount);
    }

    public void subtractSpent(Double amount) {
	setSpent(getSpent() - amount);
    }

    public void updateValue(Double oldValue, Double newValue) {
	subtractSpent(oldValue);
	sumSpent(newValue);
    }

    public static void getExcelHeader(StyledExcelSpreadsheet spreadsheet, ResourceBundle bundle, ResourceBundle enumBundle,
	    String title) {
	spreadsheet.newHeaderRow();
	spreadsheet.addCell(title, spreadsheet.getExcelStyle().getTitleStyle());
	spreadsheet.newHeaderRow();
	spreadsheet.newHeaderRow();
	spreadsheet.addHeader(bundle.getString("label.workingUnit"), 1500);
	spreadsheet.addHeader("", 15000);
	spreadsheet.addHeader(bundle.getString("label.initial"), 2000);
	spreadsheet.addHeader(bundle.getString("label.actual"), 2000);
	spreadsheet.addHeader(bundle.getString("label.balance"), 2000);
	for (Month month : Month.values()) {
	    spreadsheet.addHeader(enumBundle.getString(month.getName()), 2000);
	}
	spreadsheet.getSheet().addMergedRegion(new Region(2, (short) 0, 2, (short) 1));
	spreadsheet.getSheet().addMergedRegion(new Region(0, (short) 0, 0, (short) (spreadsheet.getMaxiumColumnNumber() - 1)));
    }

    public static void getExcelFooter(StyledExcelSpreadsheet spreadsheet, ResourceBundle bundle) {
	int lastRow = spreadsheet.getSheet().getLastRowNum();
	int lastColumn = spreadsheet.getMaxiumColumnNumber() - 1;
	int firstRow = 3;
	spreadsheet.newRow();
	spreadsheet.newRow();
	spreadsheet.addCell(bundle.getString("label.total").toUpperCase());
	spreadsheet.sumColumn(firstRow, lastRow, 2, lastColumn, spreadsheet.getExcelStyle().getDoubleStyle());
	spreadsheet.setRegionBorder(firstRow, spreadsheet.getSheet().getLastRowNum() + 1, 0,
		spreadsheet.getMaxiumColumnNumber() - 1);
    }

    public void getExcelRow(StyledExcelSpreadsheet spreadsheet) {
	HSSFCellStyle doubleStyle = spreadsheet.getExcelStyle().getDoubleStyle();
	HSSFCellStyle valueStyle = spreadsheet.getExcelStyle().getValueStyle();
	if (getBalance().compareTo(50.0) <= 0) {
	    doubleStyle = spreadsheet.getExcelStyle().getDoubleNegativeStyle();
	    valueStyle = spreadsheet.getExcelStyle().getRedValueStyle();
	}
	spreadsheet.newRow();
	spreadsheet.addCell(getUnit().getCostCenterCode().toString(), valueStyle);
	spreadsheet.addCell(getUnit().getName(), valueStyle);
	DecimalFormat decimalFormat = new DecimalFormat("0.00");
	DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
	decimalFormatSymbols.setDecimalSeparator('.');
	decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);
	spreadsheet.addCell(new Double(decimalFormat.format(getInitial())), doubleStyle);
	spreadsheet.addCell(new Double(decimalFormat.format(getTotal())), doubleStyle);
	if (getBalance().compareTo(50.0) <= 0) {
	    spreadsheet.addCell(new Double(decimalFormat.format(getBalance())), doubleStyle);
	} else {
	    spreadsheet.addCell(new Double(decimalFormat.format(getBalance())), doubleStyle);
	}
	for (Month month : Month.values()) {
	    Double monthValue = getMonthValue(month);
	    if (monthValue.equals(0.0)) {
		spreadsheet.addCell("");
	    } else {
		spreadsheet.addCell(new Double(decimalFormat.format(monthValue)), spreadsheet.getExcelStyle().getDoubleStyle());
	    }
	}
    }

    private Double getMonthValue(Month month) {
	Double monthTotal = 0.0;
	for (ExtraWorkRequest extraWorkRequest : getUnit().getExtraWorkRequests()) {
	    if (((extraWorkRequest.getPartialPayingDate().get(DateTimeFieldType.year()) == getYear() - 1 && extraWorkRequest
		    .getPartialPayingDate().get(DateTimeFieldType.monthOfYear()) == 12) && month.getNumberOfMonth() == 1)
		    || (extraWorkRequest.getPartialPayingDate().get(DateTimeFieldType.year()) == getYear()
			    && extraWorkRequest.getPartialPayingDate().get(DateTimeFieldType.monthOfYear()) + 1 == month
				    .getNumberOfMonth() && extraWorkRequest.getApproved())) {
		monthTotal += extraWorkRequest.getAmount();
	    }
	}
	return monthTotal;
    }

    public void getExtraWorkAuthorizationsExcelRows(StyledExcelSpreadsheet spreadsheet, ResourceBundle bundle,
	    ResourceBundle enumBundle) {
	HashSet<Employee> inserted = new HashSet<Employee>();
	for (ExtraWorkRequest extraWorkRequest : getUnit().getExtraWorkRequestsPayingYear(getYear())) {
	    if (!spreadsheet.hasSheet(getUnit().getCostCenterCode().toString())) {
		spreadsheet.getSheet(getUnit().getCostCenterCode().toString());

		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
		decimalFormatSymbols.setDecimalSeparator('.');
		decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);
		spreadsheet.newHeaderRow();
		spreadsheet.addCell(getUnit().getCostCenterCode().toString() + " - " + getUnit().getName(), spreadsheet
			.getExcelStyle().getTitleStyle());
		spreadsheet.newHeaderRow();
		spreadsheet.newHeaderRow();
		spreadsheet.addCell(bundle.getString("label.initial"), spreadsheet.getExcelStyle().getLabelStyle());
		spreadsheet.addCell(decimalFormat.format(getInitial()));
		spreadsheet.newHeaderRow();
		spreadsheet.addCell(bundle.getString("label.actual"), spreadsheet.getExcelStyle().getLabelStyle());
		spreadsheet.addCell(decimalFormat.format(getTotal()));
		spreadsheet.newHeaderRow();
		spreadsheet.addCell(bundle.getString("label.balance"), spreadsheet.getExcelStyle().getLabelStyle());
		spreadsheet.addCell(decimalFormat.format(getBalance()));
		spreadsheet.newHeaderRow();
		spreadsheet.newHeaderRow();
		// int rowNum = spreadsheet.getRow().getRowNum();
		// spreadsheet.addCell(bundle.getString(
		// "message.extraWorkFirstYearMonthNote"));
		spreadsheet.newHeaderRow();
		Assiduousness.getExcelHeader(spreadsheet, bundle, enumBundle);
		spreadsheet.getSheet().addMergedRegion(
			new Region(0, (short) 0, 0, (short) (spreadsheet.getMaxiumColumnNumber() - 1)));
		// spreadsheet.getSheet().addMergedRegion(
		// new Region(rowNum, (short) 0, rowNum, (short) spreadsheet
		// .getMaxiumColumnNumber()));
	    } else {
		spreadsheet.getSheet(getUnit().getCostCenterCode().toString());
	    }
	    Assiduousness assiduousness = extraWorkRequest.getAssiduousness();
	    if (!inserted.contains(assiduousness.getEmployee())) {
		assiduousness.getExcelRow(spreadsheet, getUnit(), getYear());
		inserted.add(assiduousness.getEmployee());
	    }
	}
    }

    public void delete() {
	List<UnitExtraWorkMovement> unitExtraWorkMovements = new ArrayList<UnitExtraWorkMovement>(getUnitExtraWorkMovements());
	for (UnitExtraWorkMovement unitExtraWorkMovement : unitExtraWorkMovements) {
	    unitExtraWorkMovement.delete();
	}
	removeRootDomainObject();
	removeUnit();
	deleteDomainObject();
    }
}
