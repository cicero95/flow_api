package br.com.flow_api.external;

import br.com.flow_api.external.Impl.SendEmail;
import br.com.flow_api.user.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class EmailUtils implements SendEmail<User> {

    private static final String ERROR_SEND_EMAIL_USER = "Ocorreu um erro ao enviar o email";
    private static final String SUCCESS_SEND_EMAIL_USER = "E-mail enviado com sucesso!";

    @Value("${mail.smtp.host}")
    private String host;
    @Value("${mail.smtp.protocol}")
    private String protocol;
    @Value("${mail.smtp.port}")
    private int port;
    @Value("${mail.smtp.username}")
    private String username;
    @Value("${mail.smtp.password}")
    private String password;

    @Override
    public String sendEmail(User user) {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        SimpleMailMessage message = new SimpleMailMessage();

        try {

            javaMailSender.setHost(host);
            javaMailSender.setPort(port);
            javaMailSender.setProtocol(protocol);
            javaMailSender.setUsername(username);
            javaMailSender.setPassword(password);

            message.setTo(user.getEmail());
            message.setText("Usuário cadastrado com sucesso! esses são seus dados de autenticação: "
                    + "\n\nE-mail: "
                    + user.getEmail()
                    + "\nUsername: "
                    + user.getUsername()
                    + " \nPassword: "
                    + user.getPassword());

            message.setReplyTo("cicerooliveira091@gmail.com");
            message.setFrom("ciceroregis25@gmail.com");
            message.setSubject("Dados de Login");
            log.info("Envindo E-mail...");
            javaMailSender.send(message);
            log.info(SUCCESS_SEND_EMAIL_USER);

            return SUCCESS_SEND_EMAIL_USER;
        } catch (Exception e) {
            log.error(ERROR_SEND_EMAIL_USER);
            return "Erro ao enviar e-mail.";
        }
    }

    @Override
    public String checkStatus(User user) {
        return null;
    }

}
