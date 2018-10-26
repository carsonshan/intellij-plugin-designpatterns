package com.github.sbouclier.intellij.plugin.designpatterns.builder.model;

public class BuilderParameter {
    private final String parameterType;
    private final String parameterName;
    private String setterName;
    private boolean mandatory;
    private boolean constructor;

    public BuilderParameter(String parameterType,
                            String parameterName,
                            String setterName,
                            boolean mandatory,
                            boolean constructor) {
        this.parameterType = parameterType;
        this.parameterName = parameterName;
        this.setterName = setterName;
        this.mandatory = mandatory;
        this.constructor = constructor;
    }

    public String getParameterType() {
        return parameterType;
    }

    public String getParameterName() {
        return parameterName;
    }

    public String getSetterName() {
        return setterName;
    }

    public void setSetterName(String setterName) {
        this.setterName = setterName;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public boolean isConstructor() {
        return constructor;
    }

    public void setConstructor(boolean constructor) {
        this.constructor = constructor;
    }

    @Override
    public String toString() {
        return "BuilderParameter{" +
                "parameterType='" + parameterType + '\'' +
                ", parameterName='" + parameterName + '\'' +
                ", setterName='" + setterName + '\'' +
                ", mandatory=" + mandatory +
                ", constructor=" + constructor +
                '}';
    }
}
