package com.bejlka.notificationservice.service;

import com.bejlka.notificationservice.config.TemplateConfig;
import com.bejlka.notificationservice.domain.NotificationDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

@Service
@AllArgsConstructor
public class FoodMessageHandler {
    private final JavaMailSender javaMailSender;
    private final Configuration configuration;
    private final TemplateConfig templateConfig;

    @RabbitListener(queues = "${rabbitmq.queue-food}")
    public void receive(Message message) throws IOException, TemplateException, MessagingException {
        ObjectMapper objectMapper = new ObjectMapper();
        NotificationDTO notificationDTO = objectMapper.readValue(message.getBody(), NotificationDTO.class);
        System.out.println(notificationDTO);

        TemplateConfig.Template emailTemplate = templateConfig.getTemplates().get(notificationDTO.getStatus().toLowerCase());
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        Template template = configuration.getTemplate(emailTemplate.getPath());
        String email = FreeMarkerTemplateUtils.processTemplateIntoString(template, notificationDTO);
        mimeMessageHelper.setTo(notificationDTO.getEmail());
        mimeMessageHelper.setFrom("ya@ya.com");
        mimeMessageHelper.setText(email, true);
        mimeMessageHelper.setSubject(emailTemplate.getSubject());
//        javaMailSender.send(mimeMessage);
    }
}
