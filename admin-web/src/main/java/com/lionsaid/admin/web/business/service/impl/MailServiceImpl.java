package com.lionsaid.admin.web.business.service.impl;

import com.lionsaid.admin.web.business.service.COSService;
import com.lionsaid.admin.web.business.service.MailService;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class MailServiceImpl implements MailService {
    private  final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final COSService cosService;


    @Override
    public void send(SimpleMailMessage simpleMessage) throws MailException {
        javaMailSender.send(simpleMessage);
    }

    @SneakyThrows
    @Override
    public void sendHtmlMail(String mailTo, String subject, String mailContent) {
        // 通过 Context 构造模版中变量需要的值
        MimeMessage mimeMailMessage = this.javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMailMessage, true, "utf-8");
        messageHelper.setTo(mailTo);
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        javaMailSender.send(mimeMailMessage);
    }


    @SneakyThrows
    @Override
    public void sendVerificationCodeMail(String mailTo, String code) {
        // 通过 Context 构造模版中变量需要的值
        Context ctx = new Context();
        ctx.setVariable("code", code);
        ctx.setVariable("logoUrl", cosService.getUrl("logo180Url"));
        // 使用TemplateEngine 对模版进行渲染
        String mailContent = templateEngine.process("VerificationCodeMail.html", ctx);
        sendHtmlMail(mailTo, "LionSiad 狮语·验证码", mailContent);
    }
}
