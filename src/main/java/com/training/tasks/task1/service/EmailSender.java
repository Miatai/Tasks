package com.training.tasks.task1.service;

public interface EmailSender {
	public void send(String recipientEmails, String messageSubject, String messageText);
}
