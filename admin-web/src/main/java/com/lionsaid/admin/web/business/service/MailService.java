package com.lionsaid.admin.web.business.service;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;

public interface MailService {

    void send(SimpleMailMessage simpleMessage) throws MailException;

    void sendHtmlMail(String mailTo, String subject, String mailContent);

    void sendVerificationCodeMail(String mailTo, String code);
}
