package fr.vhat.keydyn.client.pages;

import com.github.gwtbootstrap.client.ui.Image;
import com.github.gwtbootstrap.client.ui.Paragraph;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.DecoratedStackPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import fr.vhat.keydyn.client.widgets.GroupTabPanel;
import fr.vhat.keydyn.client.widgets.Page;
import fr.vhat.keydyn.shared.FAQResourcesBundle;
import fr.vhat.keydyn.shared.XMLReader;

/**
 * Represent the Frequently Asked Questions page.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public class FAQPage extends Page {

	/**
	 * Constructor of the Frequently Asked Questions page.
	 */
	public FAQPage(GroupTabPanel owner) {
		super("F.A.Q.", IconType.QUESTION_SIGN, owner);
	}

	@Override
	protected Widget getContent() {

		VerticalPanel panel = new VerticalPanel();
		Paragraph introduction = new Paragraph("Vous trouverez ci-dessous une" +
				" liste de questions que vous pourriez vous poser à propos de" +
				" l'application.");
		panel.add(introduction);

		DecoratedStackPanel stackPanel = new DecoratedStackPanel();
		panel.add(stackPanel);

		String question;
		Paragraph answer;
		String header;
		FAQResourcesBundle resources = GWT.create(FAQResourcesBundle.class);

		question = new String("Qu'est-ce que la dynamique de frappe ?");
		answer = new Paragraph();
		answer.addStyleName("justify");
		answer.setText("Vous vous en êtes déjà rendu compte : tout le monde " +
				"n'a pas la même écriture, chacun ayant ses spécificités au " +
				"niveau de la taille des caractères, de l'épaisseur des " +
				"lettres, des espacements, etc. Il se trouve que de la même " +
				"façon, chaque individu a une façon bien particulière de " +
				"taper sur un clavier d'ordinateur : on appelle cela la " +
				"dynamique de frappe qui constitue alors une sorte de " +
				"caractéristique biométrique propre à l'individu permettant " +
				"éventuellement de le reconnaître de la même façon qu'on " +
				"pourrait le faire à partir d'une empreinte digitale ou " +
				"d'un iris.");
		header = new String(getHeaderString(question, resources.keyboard()));
		stackPanel.add(answer, header, true);

		question = new String("Quel est le but de ce site ?");
		answer = new Paragraph();
		answer.addStyleName("justify");
		answer.setText("L'objectif de ce site est de présenter à un public " +
				"large les possibilités méconnues offertes par l'analyse de " +
				"la dynamique de frappe, dans un premier temps via la mise en" +
				" place d'un module d'authentification biométriquement " +
				"sécurisé, puis par une tentative de reconnaissance complète " +
				"d'un individu inconnu à partir de séquences frappées sur un " +
				"clavier.");
		header = new String(getHeaderString(question, resources.globe()));
		stackPanel.add(answer, header, true);

		question = new String("Pourquoi m'inscrire ?");
		answer = new Paragraph();
		answer.addStyleName("justify");
		answer.setText("La création d'un compte sur ce site ne vous engage " +
				"à rien. Votre adresse de courriel ne sera jamais utilisée " +
				"que pour vous renvoyer votre mot de passe sur votre demande " +
				"dans le cas où vous l'auriez oublié et libre à vous " +
				"d'utiliser une adresse de courriel temporaire. Une fois " +
				"inscrit, vous pourrez au fil des connexions ou directement " +
				"depuis la page d'entraînement, apprendre au système à vous " +
				"connaître. Peu à peu, celui-ci s'habituera à votre dynamique" +
				" de frappe et sera capable de la reconnaître de plus en plus" +
				" facilement. Il vous sera alors possible d'explorer la page " +
				"de test pour tenter d'usurper l'identité d'autres membres en" +
				" saisissant leurs mots de passe. Une page de statistiques " +
				"regroupe toutes les informations sur les tentatives échouées" +
				" et réussies donnant une idée de la fiabilité du système.");
		header = new String(getHeaderString(question, resources.clipboard()));
		stackPanel.add(answer, header, true);

		question = new String("Pourquoi des renseignements personnels me " +
				"sont-ils demandés lors de l'inscription ?");
		answer = new Paragraph();
		answer.addStyleName("justify");
		answer.setText("Ces informations resteront strictement " +
				"confidentielles et pourront éventuellement servir à " +
				"développer de nouvelles fonctionnalités permettant par " +
				"exemple de déterminer le sexe ou l'âge d'une personne à " +
				"partir de sa dynamique de frappe. Il vous est demandé de " +
				"renseigner ces informations le plus honnêtement possible " +
				"afin de ne pas compromettre une éventuelle étude en ce sens.");
		header = new String(getHeaderString(question, resources.user()));
		stackPanel.add(answer, header, true);

		question = new String("Pourquoi dois-je obligatoirement posséder " +
				"Java sur mon ordinateur ?");
		answer = new Paragraph();
		answer.addStyleName("justify");
		answer.setText("L'application d'authentification par analyse de la " +
				"dynamique de frappe se base sur l'exploitation de mesures " +
				"de temps entre la pression et le relachement des touches de " +
				"votre clavier lors de la frappe. Pour accéder à de telles " +
				"informations de façon précise, l'utilisation d'une Applet " +
				"Java est largement recommandée. Sachez que ces informations " +
				"temporelles, ainsi que des informations très générales sur " +
				"votre système d'exploitation ou votre navigateur, sont les " +
				"seules données recueillies par l'application. Si vous ne " +
				"possédez pas Java, vous pouvez vous connecter au site " +
				"depuis un autre ordinateur équipé de Java ou installer Java " +
				"sur votre ordinateur personnel en suivant ces explications :" +
				" LIEN. Notez que suivant les navigateurs, l'installation " +
				"d'un plug-in peut également vous être proposée. Afin de " +
				"profiter pleinement de l'expérience d'authentification par " +
				"analyse de la dynamique de frappe, il vous est recommandé de" +
				" choisir l'option \"Toujours exécuter\" ou \"Toujours " +
				"accepter\" lors de la demande d'autorisation faite par Java.");
		header = new String(getHeaderString(question, resources.java()));
		stackPanel.add(answer, header, true);

		question = new String("De quelle façon dois-je entraîner le système ?");
		answer = new Paragraph();
		answer.addStyleName("justify");
		answer.setText("En vous connectant régulièrement à votre espace " +
				"membre, le système apprendra à connaître vos habitudes de " +
				"frappe. Il est cependant conseillé de passer par la page " +
				"d'entraînement lors de vos premières connexions pour donner " +
				"rapidement une idée de votre dynamique de frappe au système." +
				" Il est également recommandé d'espacer un peu vos frappes " +
				"afin de ne pas bénéficier outre mesure de la rapidité de " +
				"votre habitude immédiate. Vous pouvez, par exemple, entrer " +
				"votre mot de passe à 5 reprises, puis patienter quelques " +
				"minutes avant de recommencer. Passés les 30 enregistrements," +
				" le système est généralement proche de ses performances " +
				"maximales ; de nouvelles frappes ne feront alors que le " +
				"renforcer et l'adapter en cas d'évolution de vos habitudes " +
				"de frappe.");
		header = new String(getHeaderString(question, resources.learn()));
		stackPanel.add(answer, header, true);

		XMLReader test = new XMLReader(resources.FAQ());

		return panel;
	}
	
	private String getHeaderString(String text, ImageResource image) {
	    // Add the image and text to a horizontal panel
	    HorizontalPanel hPanel = new HorizontalPanel();
	    hPanel.setSpacing(0);
	    hPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
	    hPanel.add(new Image(image));
	    HTML headerText = new HTML(text);
	    headerText.setStyleName("cw-StackPanelHeader");
	    hPanel.add(headerText);

	    // Return the HTML string for the panel
	    return hPanel.getElement().getString();
	  }
}
