package com.github.sbouclier.intellij.plugin.designpatterns.builder.service;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Builder preferences which are saved to xml
 */
@State(name="BuilderPrefs", storages = {@Storage("BuilderPrefs.xml")})
public class BuilderPrefs implements PersistentStateComponent<BuilderPrefs> {

    private BuilderPrefs.BuilderType builderType = BuilderType.CLASSIC;
    private boolean usePrefix = false;
    private String prefix = "with";

    public enum BuilderType {
        CLASSIC, INTERFACES
    }

    @Nullable
    @Override
    public BuilderPrefs getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull BuilderPrefs builderConfig) {
        XmlSerializerUtil.copyBean(builderConfig, this);
    }

    public static BuilderPrefs getInstance() {
        return ServiceManager.getService(BuilderPrefs.class);
    }

    public BuilderType getBuilderType() {
        return builderType;
    }

    public void setBuilderType(BuilderType builderType) {
        this.builderType = builderType;
    }

    public boolean isUsePrefix() {
        return usePrefix;
    }

    public void setUsePrefix(boolean usePrefix) {
        this.usePrefix = usePrefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
