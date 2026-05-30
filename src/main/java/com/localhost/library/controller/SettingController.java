package com.localhost.library.controller;

import com.localhost.library.DTO.SettingsForm;
import com.localhost.library.model.BookIssue;
import com.localhost.library.model.Setting;
import com.localhost.library.service.SettingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/setting")
public class SettingController {
	
	private static final Logger logger = Logger.getLogger(SettingController.class.getName());

	@Autowired
	private SettingService settingService;
	
	
	@GetMapping({"" , "/"})
	public String showSettings(Model model) {
	    List<Setting> settings = settingService.getAllSettings();
	    SettingsForm form = new SettingsForm();
	    form.setSettings(settings);
	    logger.log(Level.SEVERE, "-----------------unable to update Setting null : "
	    		  + form );
	    model.addAttribute("settingsForm", form);
	    return "setting"; // your template name
	}



	/*
	 * // Load form with all settings
	 * 
	 * @GetMapping({"", "/"}) public String ListSetting(Model model) { List<Setting>
	 * allSettings = settingService.getAllSettings();
	 * model.addAttribute("settings",allSettings); return "setting"; // Thymeleaf
	 * template name }
	 */

    // Save updated settings
	/*
	 * @PostMapping("/save") public String saveSettings(@ModelAttribute("settings")
	 * List<Setting> settings) { //settingService.saveAll(settings);
	 * logger.log(Level.SEVERE, "-----------------unable to update Setting null : "
	 * + settings );
	 * 
	 * if (settings == null || settings.isEmpty()) { logger.log(Level.SEVERE,
	 * "-----------------unable to update Setting null : " );
	 * 
	 * }else {
	 * 
	 * for (Setting setting : settings) { logger.log(Level.SEVERE,
	 * "--------unable to update Setting id : " + setting.getSettingId());
	 * logger.log(Level.SEVERE,
	 * "-----------unable to update Setting Display Nmae : " +
	 * setting.getSettingDisplayName()); logger.log(Level.SEVERE,
	 * "-------------unable to update Setting Name: " + setting.getSettingName());
	 * logger.log(Level.SEVERE, "-----------------unable to update Setting Value : "
	 * + setting.getSettingValue()); try {
	 * settingService.updateSetting(setting.getSettingId(),setting);
	 * }catch(Exception ex) { logger.log(Level.SEVERE,
	 * "unable to update Setting id : " + setting.getSettingId() , ex); } } } return
	 * "redirect:/setting?success"; }
	 */
    
    @PostMapping("/save")
    public String saveSettings(@ModelAttribute("settingsForm") SettingsForm settingsForm) {
        List<Setting> settings = settingsForm.getSettings();
        if (settings != null) {
            for (Setting s : settings) {
                settingService.updateSetting(s.getSettingId(), s);
            }
        }
        return "redirect:/setting?success=true";
    }

}
