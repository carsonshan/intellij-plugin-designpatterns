package com.github.sbouclier.intellij.plugin.designpatterns.builder.ui;

import com.github.sbouclier.intellij.plugin.designpatterns.builder.model.BuilderParameter;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION;

public class BuilderDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTable builderParamsTable;
    private JScrollPane jscrollBuilderParams;
    private JCheckBox cbUsePrefix;
    private JTextField txtPrefix;
    private JLabel lbSelectParameters;
    private List<BuilderParameter> result = new ArrayList<>();

    public BuilderDialog(List<BuilderParameter> parameters) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        // build parameters table
        builderParamsTable.setModel(createParametersTableModel(parameters));
        builderParamsTable.setSelectionMode(MULTIPLE_INTERVAL_SELECTION);
        builderParamsTable.setRowSelectionAllowed(true);
    }

    private ParametersTableModel createParametersTableModel(List<BuilderParameter> parameters) {
        ParametersTableModel paramsTableModel = new ParametersTableModel();
        paramsTableModel.setParameters(parameters);
        return paramsTableModel;
    }

    private void onOK() {
        ParametersTableModel paramsTableModel = (ParametersTableModel) builderParamsTable.getModel();
        int[] selectedRows = builderParamsTable.getSelectedRows();

        for (int rowIndex : selectedRows) {
            result.add(paramsTableModel.getParameters().get(rowIndex));
        }


        /*
        final List<BuilderParameter> mandatoryParams = new ArrayList<>();
        final List<BuilderParameter> optionalParams = new ArrayList<>();

        for (int rowIndex : selectedRows) {
            BuilderParameter param = paramsTableModel.getParameters().get(rowIndex);
            if (param.isMandatory()) {
                mandatoryParams.add(param);
            } else {
                optionalParams.add(param);
            }
        }
        result = new BuilderDialogResult(mandatoryParams, optionalParams);
        */

        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public void addOKListener(ActionListener listener) {
        buttonOK.addActionListener(listener);
        buttonOK.addActionListener(e -> onOK());
    }

    public void addCancelListener(ActionListener listener) {
        buttonCancel.addActionListener(listener);
        buttonCancel.addActionListener(e -> onCancel());
    }

    public List<BuilderParameter> getResult() {
        return result;
    }

    public static void main(String[] args) {
        BuilderDialog dialog = new BuilderDialog(Arrays.asList(
                new BuilderParameter(
                        "String",
                        "name",
                        "name",
                        true,
                        true),
                new BuilderParameter(
                        "int",
                        "age",
                        "age",
                        true,
                        false),
                new BuilderParameter(
                        "Long",
                        "salary",
                        "salary",
                        false,
                        false)
        ));
        dialog.addOKListener(e -> {
            System.out.println("result = " + dialog.getResult());
        });
        dialog.addCancelListener(l -> System.out.println("CANCEL"));

        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
