package com.github.sbouclier.intellij.plugin.designpatterns.builder.generator;

import com.github.sbouclier.intellij.plugin.designpatterns.builder.model.BuilderParameter;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElementFactory;

import java.util.List;

/**
 * Abstract Builder code generator
 */
public abstract class AbstractBuilderCodeGenerator {
    protected final PsiClass targetClass;
    protected final PsiElementFactory elementFactory;
    protected final List<BuilderParameter> parameters;

    public AbstractBuilderCodeGenerator(PsiClass targetClass, List<BuilderParameter> parameters) {
        this.targetClass = targetClass;
        this.parameters = parameters;
        this.elementFactory = JavaPsiFacade.getElementFactory(targetClass.getProject());
    }

    public abstract void generate();
}
