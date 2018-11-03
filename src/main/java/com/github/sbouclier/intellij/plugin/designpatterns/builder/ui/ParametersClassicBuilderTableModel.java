package com.github.sbouclier.intellij.plugin.designpatterns.builder.ui;

import com.github.sbouclier.intellij.plugin.designpatterns.builder.model.BuilderParameter;

/**
 * Classic builder table model for parameters to Builder dialog
 */
public class ParametersClassicBuilderTableModel extends AbstractBuilderTableModel {

    private static final int COLUMN_PARAM_NAME_INDEX = 0;
    private static final int COLUMN_SETTER_NAME_INDEX = 1;
    private static final int COLUMN_CONSTRUCTOR_INDEX = 2;

    @Override
    String[] getHeaders() {
        return new String[]{"Parameter name", "Setter name", "Constructor"};
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case COLUMN_PARAM_NAME_INDEX:
                return String.class;
            case COLUMN_SETTER_NAME_INDEX:
                return String.class;
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
            case COLUMN_CONSTRUCTOR_INDEX:
                return parameters.get(rowIndex).isConstructor();
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        BuilderParameter param = parameters.get(rowIndex);
        if (columnIndex == COLUMN_CONSTRUCTOR_INDEX) {
            param.setConstructor(!param.isConstructor());
            fireTableCellUpdated(rowIndex, columnIndex);
        }
    }
}
