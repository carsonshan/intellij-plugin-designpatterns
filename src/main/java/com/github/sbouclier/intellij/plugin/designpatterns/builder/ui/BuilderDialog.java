package com.github.sbouclier.intellij.plugin.designpatterns.builder.ui;

import com.github.sbouclier.intellij.plugin.designpatterns.builder.model.BuilderParameter;
import com.github.sbouclier.intellij.plugin.designpatterns.listener.UpdateDocumentListener;
import com.github.sbouclier.intellij.plugin.designpatterns.util.StringUtils;

import javax.swing.*;
import java.awt.event.*;
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
    private JRadioButton radioBtnClassic;
    private JRadioButton radioBtnInterfaces;
    private BuilderDialogResult result;

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

        buttonCancel.addActionListener(e -> onCancel());

        radioBtnClassic.addItemListener(e -> builderParamsTable.setModel(createParametersTableModel(parameters)));
        radioBtnInterfaces.addItemListener(e -> builderParamsTable.setModel(createParametersTableModel(parameters)));

        // build parameters table
        builderParamsTable.setModel(createParametersTableModel(parameters));
        builderParamsTable.setSelectionMode(MULTIPLE_INTERVAL_SELECTION);
        builderParamsTable.setRowSelectionAllowed(true);

        cbUsePrefix.addItemListener(e -> {
            if (cbUsePrefix.isSelected()) {
                txtPrefix.setEnabled(true);
                refreshParametersWithPrefix(txtPrefix.getText(), parameters);
            } else {
                txtPrefix.setEnabled(false);
                parameters.forEach(p -> p.setSetterName(p.getParameterName()));
                builderParamsTable.updateUI();
            }
        });

        txtPrefix.getDocument().addDocumentListener((UpdateDocumentListener) e -> refreshParametersWithPrefix(txtPrefix.getText(), parameters));
    }

    private AbstractBuilderTableModel createParametersTableModel(List<BuilderParameter> parameters) {
        final AbstractBuilderTableModel tableModel;
        if(radioBtnClassic.isSelected()) {
            tableModel = new ParametersClassicBuilderTableModel();
        } else {
            tableModel = new ParametersInterfaceBuilderTableModel();
        }

        tableModel.setParameters(parameters);
        return tableModel;
    }

    private void onOK() {
        AbstractBuilderTableModel paramsTableModel = (AbstractBuilderTableModel) builderParamsTable.getModel();
        int[] selectedRows = builderParamsTable.getSelectedRows();

        if(radioBtnClassic.isSelected()) {
            this.result = new BuilderDialogResult(BuilderDialogResult.BuilderType.CLASSIC);
        } else {
            this.result = new BuilderDialogResult(BuilderDialogResult.BuilderType.INTERFACES);
        }

        for (int rowIndex : selectedRows) {
            result.addSelectedParameter(paramsTableModel.getParameters().get(rowIndex));
        }

        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public void addOKListener(ActionListener listener) {
        buttonOK.addActionListener(listener);
        buttonOK.addActionListener(e -> onOK());
    }

    public BuilderDialogResult getResult() {
        return result;
    }

    private void refreshParametersWithPrefix(String prefix, List<BuilderParameter> params) {
        params.forEach(p -> p.setSetterName(prefix + StringUtils.firstUppercaseLetter(p.getParameterName())));
        builderParamsTable.updateUI();
    }
}
