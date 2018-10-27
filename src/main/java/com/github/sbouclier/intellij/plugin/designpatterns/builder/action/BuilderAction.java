package com.github.sbouclier.intellij.plugin.designpatterns.builder.action;

import com.github.sbouclier.intellij.plugin.designpatterns.builder.generator.BuilderInterfaceCodeGenerator;
import com.github.sbouclier.intellij.plugin.designpatterns.builder.model.BuilderParameter;
import com.github.sbouclier.intellij.plugin.designpatterns.builder.ui.BuilderDialog;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.*;

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
        BuilderDialog dialog = new BuilderDialog(createParamsFromClass(targetClass));

        dialog.addOKListener(l -> {
            new BuilderInterfaceCodeGenerator(targetClass, dialog.getResult()).generate();
        });

        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private List<BuilderParameter> createParamsFromClass(PsiClass psiClass) {
        final List<BuilderParameter> parameters = new ArrayList<>();
        PsiField[] fields = psiClass.getFields();
        for(PsiField field : fields) {
            parameters.add(new BuilderParameter(
                    field.getType().getCanonicalText(),
                    field.getName(),
                    field.getName(),
                    true,
                    false
            ));
        }
        return parameters;
    }
}
