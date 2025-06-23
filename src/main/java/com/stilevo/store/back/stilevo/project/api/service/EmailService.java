package com.stilevo.store.back.stilevo.project.api.service;

import com.stilevo.store.back.stilevo.project.api.domain.dto.request.EmailRequestDTO;
import com.stilevo.store.back.stilevo.project.api.exception.MailSendFailedException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final UserService userService;
    private final JavaMailSender mailSender;

    public EmailService(UserService userService, JavaMailSender mailSender) {
        this.userService = userService;
        this.mailSender = mailSender;
    }

    public void sendSimpleEmail(EmailRequestDTO emailData) {
        userService.loadUserByUsername(emailData.getUserEmail()); // se mandar um email nao existente no banco, ele ja lanca a excessao.

        SimpleMailMessage simpleMail = new SimpleMailMessage();

        simpleMail.setTo(emailData.getUserEmail());
        simpleMail.setSubject(emailData.getTitle());
        simpleMail.setText(emailData.getContent());

        mailSender.send(simpleMail); // envia o email
    }

    public void sendEmail(EmailRequestDTO emailData) throws MailSendFailedException {
        try {
            userService.loadUserByUsername(emailData.getUserEmail()); // se mandar um email nao existente no banco, ele ja lanca a excessao.

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(emailData.getUserEmail());
            helper.setSubject(emailData.getTitle());
            helper.setText(emailData.getContent(), true);

            mailSender.send(message); // envia o email

        } catch (MessagingException exception) {
            throw new MailSendFailedException("erro ao enviar email.");
        }
    }
}
