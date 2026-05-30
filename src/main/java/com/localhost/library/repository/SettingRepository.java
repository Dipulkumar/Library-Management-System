package com.localhost.library.repository;

import com.localhost.library.model.Setting;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingRepository extends JpaRepository<Setting, Long> {
	Optional<Setting> findBySettingName(String settingName);
}

