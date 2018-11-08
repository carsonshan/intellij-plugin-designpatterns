package com.github.sbouclier.intellij.plugin.designpatterns.builder.ui;

import com.github.sbouclier.intellij.plugin.designpatterns.builder.model.BuilderParameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder dialog result
 */
public class BuilderDialogResult {
    private final BuilderType builderType;
    private List<BuilderParameter> selectedParameters = new ArrayList<>();

    public enum BuilderType {
        CLASSIC, INTERFACES
    }

    public BuilderDialogResult(BuilderType builderType) {
        this.builderType = builderType;
    }

    public void addSelectedParameter(BuilderParameter parameter) {
        this.selectedParameters.add(parameter);
    }

    public BuilderType getBuilderType() {
        return builderType;
    }

    public List<BuilderParameter> getSelectedParameters() {
        return selectedParameters;
    }

    @Override
    public String toString() {
        return "BuilderDialogResult{" +
                "builderType=" + builderType +
                ", selectedParameters=" + selectedParameters +
                '}';
    }
}
