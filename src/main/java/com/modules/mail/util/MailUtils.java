/**
 * 
 * @author DoubleLi
 * @time 2019年1月2日
 * 
 */
package com.modules.mail.util;

import java.io.InputStream;
import java.util.Map;

import javax.activation.DataSource;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.commons.utils.FileUtils;
import com.modules.mail.dto.EmailModelDTO;

@Component
public class MailUtils {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private SpringTemplateEngine templateEngine;

	@Value("${fromMail.address}")
	private String from;

	public String render(String template, Map<String, Object> params) {
		Context context = new Context(LocaleContextHolder.getLocale());
		context.setVariables(params);
		return templateEngine.process(template, context);
	}

	/**
	 * 普通邮件
	 * 
	 * @param to
	 * @param subject
	 * @param content
	 * @time 2019年1月2日
	 * @author DoubleLi
	 */
	public void sendSimpleMail(String to, String subject, String content) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(content);

		try {
			mailSender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 模板邮件
	 * 
	 * @param fromEmail
	 * @param toList
	 * @param subject
	 * @param html
	 * @time 2019年1月2日
	 * @author DoubleLi
	 */
	public String mimeMail(String to, String subject, String html,Map<String, Object> params) throws Exception {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(message);
		messageHelper.setFrom(from);
		/*for (String to : toList) {
			messageHelper.addTo(to);
		}*/
		messageHelper.addTo(to);
		messageHelper.setSubject(subject);
		messageHelper.setText(html, true);
		mailSender.send(message);
		return html;
	}

	/**
	 * 网页邮件
	 * 
	 * @param email
	 * @time 2019年1月2日
	 * @author DoubleLi
	 */
	public void sendHtmlMail(EmailModelDTO email)  throws Exception  {
		MimeMessage message = null;
			message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(email.getFrom());
			helper.setTo(email.getTo());
			helper.setSubject(email.getSubject());
			// 发送htmltext值需要给个true，不然不生效
			helper.setText(email.getText(), true);
			if (email.getCc() != null) {
				helper.setCc(email.getCc());
			}
			mailSender.send(message);
	}

	/**
	 * 带附件邮件
	 * 
	 * @param email
	 * @time 2019年1月2日
	 * @author DoubleLi
	 */
	public void sendAttachmentsMail(EmailModelDTO email)  throws Exception  {
		MimeMessage message = null;
			message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(email.getFrom());
			helper.setTo(email.getTo());
			helper.setSubject(email.getSubject());
			helper.setText(email.getText());
			if (email.getCc() != null) {
				helper.setCc(email.getCc());
			}
			// 用流的形式发送附件邮件
			int i = 0;
			for (InputStream in : email.getIs()) {
				DataSource source = new ByteArrayDataSource(in, FileUtils.getContentType(email.getFjName()[i]));
				helper.addAttachment(email.getFjName()[i], source);
				i++;
			}
			mailSender.send(message);
	}

}
