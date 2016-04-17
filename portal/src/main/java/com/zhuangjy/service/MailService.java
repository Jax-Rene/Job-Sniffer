package com.zhuangjy.service;

import com.zhuangjy.dao.BaseDao;
import com.zhuangjy.entity.Mail;
import org.apache.commons.logging.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by johnny on 16/4/16.
 */
@Service
public class MailService {
    @Autowired
    private JavaMailSenderImpl mailSender;
    @Autowired
    private BaseDao<Mail> baseDao;
    private static final Logger LOGGER = LogManager.getLogger(MailService.class);

    public void subscribe(Mail mail) {
        baseDao.save(mail);
    }

    public void unSubscribe(String mail) {
        String sql = "delete from Mail where mail=:mail";
        Map<String,Object> hs = new HashMap<>();
        hs.put("mail",mail);
        baseDao.executeUpdateSql(sql,hs);
    }

    public List<Mail> loadEmails() {
        String hql = "From Mail";
        return baseDao.query(hql);
    }

    public void sendEmails(String subject, String text) {
        try {
            MimeMessage mailMessage = mailSender.createMimeMessage();
            MimeMessageHelper smm = new MimeMessageHelper(mailMessage, true);
            smm.setFrom(mailSender.getUsername());
            smm.setSubject(subject);
            smm.setText(text, true);
            List<Mail> list = loadEmails();
            String[] receives = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                receives[i] = list.get(i).getMail();
            }
            smm.setTo(receives);
            mailSender.send(mailMessage);
        } catch (MessagingException e) {
            LOGGER.error("发送邮件失败", e);
        }
    }
}
