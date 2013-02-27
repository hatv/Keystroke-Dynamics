package fr.vhat.keydyn.server;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Mail management class.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public class Mail {
	// TODO: update lastMailSent Field of User if it is a stepMail

	/**
	 * Function to send an email from the server.
	 * @param from Email of the sender.
	 * @param toEmail Email address of the recipient.
	 * @param toUser Name of the recipient.
	 * @param replyTo Reply email address.
	 * @param subject Subject of the mail.
	 * @param message Content of the mail.
	 * @return Error message or null if everything is good.
	 */
	public static String sendMail(String from, String toEmail, String toUser,
			String replyTo, String subject, String message) {
        String output = null;
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from, "Keystroke Dynamics Admin"));
            msg.addRecipient(Message.RecipientType.TO,
                             new InternetAddress(toEmail, toUser));
            msg.setSubject(subject);
            msg.setText(message);
            msg.setReplyTo(new InternetAddress[]{new InternetAddress(replyTo)});
            Transport.send(msg);

        } catch (Exception e) {
            output = e.toString();                
        }   
        return output;
    }

	/**
	 * Function to send a mail to a new user with his password inside.
	 * @param toEmail Email address of the recipient.
	 * @param toUser Name of the recipient.
	 * @param password Password of the new user.
	 * @return Error message or null if everything is good.
	 */
	public static String sendWelcomeMail(String toEmail, String toUser,
			String password) {
		String message = "Thanks for your registration " + toUser + ". Your " +
			"password is: " + password + "\nYou can now access your member " +
			"area and begin to train the system.";
		return sendMail("key-dyn@victorhatinguais.fr", toEmail, toUser,
				"key-dyn@victorhatinguais.fr",
				"Keystroke Dynamics Registration", message);
	}
}
