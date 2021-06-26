package br.com.flow_api.external;

import br.com.flow_api.constants.Constants;
import br.com.flow_api.user.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class Utils {

	@Value("${mail.smtp.host}")
	private String host;
	@Value("${mail.smtp.protocol}")
	private String protocol;
	@Value("${mail.smtp.port}")
	private int port;
	@Value("${mail.smtp.starttls.enable}")
	private boolean starttls_enable;
	@Value("${mail.smtp.starttls.required}")
	private boolean starttls_required;
	@Value("${mail.smtp.username}")
	private String username;
	@Value("${mail.smtp.password}")
	private String password;

	public static String converteJsonEmString(BufferedReader buffereReader) throws IOException {
		String resposta, jsonEmString = "";
		while ((resposta = buffereReader.readLine()) != null) {
			jsonEmString += resposta;
		}
		return jsonEmString;
	}

	public static Date formatDate(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		dateFormat.format(date);
		return date;
	}


	private static final Logger LOG = LogManager.getLogger();


	public static String encryptPassword(String password) {
		Mac sha512HMAC = null;
		String result = null;

		LOG.info("Início da execução da criptografia da senha.");

		try {
			byte[] byteKey = Constants.KEY.getBytes("UTF-8");
			sha512HMAC = Mac.getInstance(Constants.HMAC_SHA512);
			SecretKeySpec keySpec = new SecretKeySpec(byteKey, Constants.HMAC_SHA512);
			sha512HMAC.init(keySpec);
			byte[] macData = sha512HMAC.doFinal((password).getBytes("UTF-8"));
			result = Utils.bytesToHex(macData);
		} catch (UnsupportedEncodingException e) {
			LOG.info("Erro na codificação", e);
		} catch (NoSuchAlgorithmException e) {
			LOG.info("Erro no algoritmo", e);
		} catch (InvalidKeyException e) {
			LOG.info("Erro na key", e);
		} finally {
			LOG.info("Finalizou a execução da criptografia de senha.");
		}

		return result;
	}

	private static String bytesToHex(byte[] bytes) {
		final char[] hexArray = "0123456789ABCDEF".toCharArray();
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}


	public String SendEmail(User user){
		try {
			JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
			SimpleMailMessage message = new SimpleMailMessage();
			message.setText("Usuario cadastrado com sucesso! esses são seus dados de autenticação");
			message.setTo(user.getEmail());
			message.setText(user.getUsername());
			message.setText(user.getPassword());
			message.setReplyTo("cicerooliveira091@gmail.com");
			message.setFrom("ciceroregis25@gmail.com");
			message.setSubject("Olá Teste");

			javaMailSender.setProtocol(protocol);
			javaMailSender.setHost(host);
			javaMailSender.setPort(port);
			javaMailSender.setUsername(username);
			javaMailSender.setPassword(password);

			javaMailSender.send(message);

			return "Email enviado com sucesso!";
		} catch (Exception e) {
			e.printStackTrace();
			return "Erro ao enviar email.";
		}
	}
}
