package com.localhost.library.service;


import com.localhost.library.model.Setting;
import com.localhost.library.repository.SettingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class SettingService {

	@Autowired
	private SettingRepository settingRepository;
	
	
	 public Setting updateSetting(Long id, Setting newSetting) {
	        return settingRepository.findById(id).map(setting -> {
	            setting.setSettingValue(newSetting.getSettingValue());
	            return settingRepository.save(setting);
	        }).orElse(null);
	    }

	
	 

	/**
	 * Load settings from DB once after Spring has injected the repository.
	 */

	private int loadSetting(String name, int defaultValue) {
		Setting setting = settingRepository.findBySettingName(name).get(); 
			try {
				return Integer.parseInt(setting.getSettingValue());
			} catch (NumberFormatException e) {
				System.err.println("⚠️ Invalid number for " + name + ": " + setting.getSettingValue());
				return defaultValue;
			}
	}

	public List<Setting> getAllSettings() {
		return settingRepository.findAll();
	}

	public void saveAll(List<Setting> settings) {
		settingRepository.saveAll(settings);
	}

	public long overdueDays(LocalDate dueDate) {
		if (dueDate == null)
			return 0;
		long days = ChronoUnit.DAYS.between(dueDate, LocalDate.now());
		int graceDays = getGraceDays();
		days -= graceDays;
		return Math.max(days, 0);
	}

	public double getLateFee(LocalDate dueDate) {
		long od = overdueDays(dueDate);
		int lateFeePerDay = getLateFeePerDay();
		return od * lateFeePerDay;
	}

	public int getGraceDays() {
		int graceDays = loadSetting("GRACE_DAYS", 0);
		return graceDays;
	}

	public int getLateFeePerDay() {
		int lateFeePerDay = loadSetting("LATE_FEE_PER_DAY", 0);
		return lateFeePerDay;
	}

	public int getMaxIssueDays() {
		int maxIssueDays = loadSetting("MAX_ISSUE_DAYS", 14);
		return maxIssueDays;
	}
}
