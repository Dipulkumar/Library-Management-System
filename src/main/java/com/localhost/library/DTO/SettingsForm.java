package com.localhost.library.DTO;

import java.util.List;

import com.localhost.library.model.Setting;

public class SettingsForm {
    private List<Setting> settings;

    public List<Setting> getSettings() {
        return settings;
    }

    public void setSettings(List<Setting> settings) {
        this.settings = settings;
    }
}

