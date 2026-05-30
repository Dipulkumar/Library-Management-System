package com.localhost.library.service;

import com.localhost.library.model.BookIssue;
import com.localhost.library.model.UserInfo;

import java.time.LocalDate;
import java.util.List;

public interface BookIssueService {
    List<BookIssue> getAllIssues();
    List<BookIssue> getAllIssuesByUser(UserInfo user);
    BookIssue saveIssue(BookIssue issue);
    void deleteIssue(Long id);
    void payFine(Long issueId);
	void returnBook(Long id);
	List<BookIssue> calculateLateFee(List<BookIssue> bookIssue);
}
