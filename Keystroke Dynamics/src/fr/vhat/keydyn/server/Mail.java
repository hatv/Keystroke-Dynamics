package fr.vhat.keydyn.server;

import java.util.Properties;
import java.util.logging.Logger;

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

	private static final Logger log = Logger.getLogger(
			Mail.class.getName());

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
		String message = "Nouvel utilisateur inscrit : <" + toUser + " ; " +
			toEmail + ">";
		sendMail("key-dyn@victorhatinguais.fr", "admin@victorhatinguais.fr",
				"Administrateur KeyDyn", "key-dyn@victorhatinguais.fr",
				"Nouvel inscrit", message);
		log.info("An email is being sent to welcome a new user: <" + toUser +
				" ; " + toEmail + ">.");
		message = "Bonjour " + toUser + ".\n\nVous recevez ce message " +
			"car vous vous êtes récemment inscrit sur notre site " +
			"d'analyse de la dynamique de frappe.\n\n Votre mot de " +
			"passe est : " + password + "\n\nVous pouvez dès à présent " +
			"accéder à votre espace membre et commencer à entraîner le " +
			"système afin qu'il vous reconnaisse.\n\n" +
			"Pour un test rapide, suivez la procédure suivante :\n" +
			"1. Connectez-vous à l'aide de votre identifiant et de " +
			"votre mot de passe.\n" +
			"2. Entrainez le système en tapant de nombreuses fois " +
			"votre mot de passe sur la page approppriée. Le minimum est " +
			"de remplir la barre de fiabilité qui s'affiche mais vous " +
			"pouvez donner au système encore plus d'informations pour " +
			"accroître ses performances.\n" +
			"3. Rendez-vous sur la page de test. En sélectionnant votre " +
			"identifiant, vous devriez pouvoir vous authentifier. Si vous " +
			"tendez le clavier à un proche, en revanche, son " +
			"authentification devrait échouer.\n\n" +
			"Pour que le système fonctionne au mieux, votre frappe doit " +
			"être naturelle et la plus régulière possible. Ne vous forcez " +
			"pas à aller vite ou à adapter un rythme particulier : le " +
			"système s'adaptera automatiquement à d'éventuelles variations." +
			"Si vous sentez au cours d'une saisie que celle-ci s'éloigne de " +
			"façon importante de votre habitude, vous pouvez l'annuler en " +
			"pressant la touche Retour Arrière de votre clavier.\n\n" +
			"En vous souhaitant une bonne visite,\n" +
			"Key Dyn";
		return sendMail("key-dyn@victorhatinguais.fr", toEmail, toUser,
				"key-dyn@victorhatinguais.fr",
				"Keystroke Dynamics Registration", message);
	}
}
