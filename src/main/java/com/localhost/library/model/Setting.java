package com.localhost.library.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "setting")

public class Setting {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "settingId")
	private Long settingId;
	
	@Column(name = "settingName")
	private String settingName;
	 
	@Column(name = "settingDisplayName")
	private String settingDisplayName;
	
	 @Column(name = "settingValue")
	 private String settingValue;
	 
	 public Setting() {}
	 
	 public Long getSettingId() {
        return settingId;
    }

    public void setSettingId(Long settingId) {
        this.settingId = settingId;
    }
    
    public String getSettingName() {
        return settingName;
    }

    public void setSettingName(String settingName) {
        this.settingName = settingName;
    }

    public String getSettingDisplayName() {
        return settingDisplayName;
    }

    public void setSettingDisplayName(String settingDisplayName) {
        this.settingDisplayName = settingDisplayName;
    }
    
    public String getSettingValue() {
        return settingValue;
    }

    public void setSettingValue(String settingValue) {
        this.settingValue = settingValue;
    }
	 
}
