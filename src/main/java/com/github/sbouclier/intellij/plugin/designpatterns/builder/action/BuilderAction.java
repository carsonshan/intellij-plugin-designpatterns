package com.github.sbouclier.intellij.plugin.designpatterns.builder.action;

import com.github.sbouclier.intellij.plugin.designpatterns.builder.generator.BuilderClassicCodeGenerator;
import com.github.sbouclier.intellij.plugin.designpatterns.builder.generator.BuilderInterfaceCodeGenerator;
import com.github.sbouclier.intellij.plugin.designpatterns.builder.model.BuilderParameter;
import com.github.sbouclier.intellij.plugin.designpatterns.builder.ui.BuilderDialog;
import com.github.sbouclier.intellij.plugin.designpatterns.builder.ui.BuilderDialogResult;
import com.intellij.codeInsight.hint.HintManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiFile;

import java.util.ArrayList;
import java.util.List;

import static com.intellij.psi.util.PsiTreeUtil.getParentOfType;

/**
 * Builder action to launch builder dialog
 */
public class BuilderAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        PsiFile psiFile = e.getData(CommonDataKeys.PSI_FILE);
        Editor editor = e.getData(CommonDataKeys.EDITOR);

        int offset = editor.getCaretModel().getOffset();
        PsiElement elementAt = psiFile.findElementAt(offset);

        PsiClass targetClass = getParentOfType(elementAt, PsiClass.class);
        if(targetClass != null) {
            BuilderDialog dialog = new BuilderDialog(createParamsFromClass(targetClass));

            dialog.addOKListener(l -> {
                BuilderDialogResult result = dialog.getResult();
                List<BuilderParameter> params = dialog.getResult().getSelectedParameters();
                if(result.getBuilderType() == BuilderDialogResult.BuilderType.CLASSIC) {
                    new BuilderClassicCodeGenerator(targetClass, params).generate();
                } else {
                    new BuilderInterfaceCodeGenerator(targetClass, params).generate();
                }
            });

            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        } else {
            HintManager.getInstance().showErrorHint(editor, "Builder must be generated inside class");
        }
    }

    private List<BuilderParameter> createParamsFromClass(PsiClass psiClass) {
        final List<BuilderParameter> parameters = new ArrayList<>();
        PsiField[] fields = psiClass.getFields();
        for(PsiField field : fields) {
            parameters.add(new BuilderParameter(
                    field,
                    field.getName(),
                    true,
                    false
            ));
        }
        return parameters;
    }
}
