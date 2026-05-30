package com.localhost.library.repository;

import com.localhost.library.model.BookIssue;
import com.localhost.library.model.UserInfo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookIssueRepository extends JpaRepository<BookIssue, Long> {
    List<BookIssue> findAllByUser(UserInfo user);
}
