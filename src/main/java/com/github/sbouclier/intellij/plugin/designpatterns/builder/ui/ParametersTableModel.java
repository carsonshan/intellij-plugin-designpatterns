package com.github.sbouclier.intellij.plugin.designpatterns.builder.ui;

import com.github.sbouclier.intellij.plugin.designpatterns.builder.model.BuilderParameter;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Table model for parameters to Builder dialog
 */
public class ParametersTableModel extends AbstractTableModel {

    private static final int COLUMN_PARAM_NAME_INDEX = 0;
    private static final int COLUMN_SETTER_NAME_INDEX = 1;
    private static final int COLUMN_MANDATORY_INDEX = 2;
    private static final int COLUMN_CONSTRUCTOR_INDEX = 3;

    private final String[] headers = {"Parameter name", "Setter name", "Mandatory", "Constructor"};
    private List<BuilderParameter> parameters = new ArrayList<>();

    @Override
    public int getRowCount() {
        return parameters.size();
    }

    @Override
    public int getColumnCount() {
        return headers.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return headers[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case COLUMN_PARAM_NAME_INDEX:
                return String.class;
            case COLUMN_SETTER_NAME_INDEX:
                return String.class;
            case COLUMN_MANDATORY_INDEX:
                return Boolean.class;
            case COLUMN_CONSTRUCTOR_INDEX:
                return Boolean.class;
            default:
                return Object.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case COLUMN_PARAM_NAME_INDEX:
                return false;
            case COLUMN_SETTER_NAME_INDEX:
                return false;
            case COLUMN_MANDATORY_INDEX:
                return true;
            case COLUMN_CONSTRUCTOR_INDEX:
                return true;
            default:
                return false;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case COLUMN_PARAM_NAME_INDEX:
                return parameters.get(rowIndex).getParameterName();
            case COLUMN_SETTER_NAME_INDEX:
                return parameters.get(rowIndex).getSetterName();
            case COLUMN_MANDATORY_INDEX:
                return parameters.get(rowIndex).isMandatory();
            case COLUMN_CONSTRUCTOR_INDEX:
                return parameters.get(rowIndex).isConstructor();
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        BuilderParameter param = parameters.get(rowIndex);
        if (columnIndex == COLUMN_MANDATORY_INDEX) {
            param.setMandatory(!param.isMandatory());
            if (!param.isMandatory() && param.isConstructor()) {
                param.setConstructor(false);
                fireTableRowsUpdated(rowIndex, COLUMN_CONSTRUCTOR_INDEX);
            }
            fireTableCellUpdated(rowIndex, columnIndex);
        } else if (columnIndex == COLUMN_CONSTRUCTOR_INDEX) {
            if (param.isMandatory()) {
                param.setConstructor(!param.isConstructor());
                fireTableCellUpdated(rowIndex, columnIndex);
            }
        }
    }

    public void setParameters(List<BuilderParameter> params) {
        parameters = params;
    }

    public List<BuilderParameter> getParameters() {
        return parameters;
    }
}