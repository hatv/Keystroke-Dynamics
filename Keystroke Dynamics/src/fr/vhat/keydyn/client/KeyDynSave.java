	/**
	 * Load the member area page which contain the training module and
	 * information about the account.
	 * @param login Login of the current user.
	 */
	private void loadUserPage (final String login) {

		this.trainJSNI();

		RootPanel.get("content").clear();
		RootPanel.get("errors").clear();
		RootPanel.get("infos").clear();
		chartsPanel.clear();
		
		final VerticalPanel container = new VerticalPanel();
		container.addStyleName("container");
		container.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		HorizontalPanel header = new HorizontalPanel();
		Button logoutButton = new Button("Logout");
		Button aboutButton = new Button("About");
		header.setSpacing(5);
		header.add(logoutButton);
		header.add(aboutButton);
		container.add(header);
		
		Label welcomeLabel = new Label(
				"Welcome to your member area " + login + "! " +
			    "You can train the system on this page.");
		container.add(welcomeLabel);
		container.setSpacing(5);

		Button testButton = new Button("Test authentication");
		container.add(testButton);

		HTML explanations = new HTML();
		explanations.setHTML("Type your password and press Enter.<br/>" +
				"If you typed the correct password, your keystroke dynamics " +
				"will be saved and the authentication system will learn.<br/>" +
				"<br/>At any time, if you consider the current keystroke " +
				"dynamics does not match your usual,<br/>feel free to press " +
				"Enter or Backspace to automatically reset the application.");
		container.add(explanations);

		// TODO: A box which contain remaining passwords to enter in order to
		// reach the next step

		// TODO: A PasswordTextBox which display the password

		HTML applet = new HTML();
		String installJava = "<a href=\"http://www.java.com/en/download/help" +
				"/windows_manual_download.xml\">Install Java now.</a>";
		String testJava = "<a href=\"http://www.java.com/en/download/" +
				"testjava.jsp\">Test Java.</a>";
		// TODO hide applet + auto focus
		String HTML5Applet = new String("<object id=\"KeyboardApplet\" " +
			"type=\"application/x-java-applet\" height=\"100\" width=\"650\">" +
			"<param name=\"mayscript\" value=\"yes\">" +
			"<param name=\"scriptable\" value=\"true\">" +
	        "<param name=\"codebase\" value=\"resources/\">" +
	        "<param name=\"code\" value=\"KeyboardApplet.class\">" +
	        "<!--  <param name=\"archive\" value=\"KeyboardApplet.jar\"> -->" +
	        "This application is designed to securely authenticate users " +
	        "according to their keystroke dynamics. In order to do that, " +
	        "Java must be installed on your computer. " + installJava + " " +
	        testJava + "</object>");
	    applet.setHTML(HTML5Applet);
	    container.add(applet);

	    // TODO: l'applet doit libérer le focus une fois terminé
	    HTML focusButton = new HTML();
		focusButton.setHTML("<button " +
				"onClick=\"startFocus('KeyboardApplet')\">" +
				"Permanent focus</button>");
	    container.add(focusButton);
	    container.add(chartsPanel);

		// Load the Google Visualization API which draw the line charts.
		VisualizationUtils.loadVisualizationApi(onLoadCallback,
				LineChart.PACKAGE);

		RootPanel.get("content").add(container);

		logoutButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				authenticationService.logout(new AsyncCallback<Void>() {
					@Override
                    public void onFailure(Throwable caught) {
						displayErrorMessage("Logout", caught.getMessage());
                    }
                    @Override
                    public void onSuccess(Void result) {
                    	loadHomePage();
                    }
				});
			}
		});

		testButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				loadTestPage(login);
			}
		});
	}

	/**
	 * Load the member area page which contain the authentication test module.
	 * @param login Login of the current user.
	 */
	private void loadTestPage (final String login) {

		this.testJSNI();

		RootPanel.get("content").clear();
		RootPanel.get("errors").clear();
		RootPanel.get("infos").clear();
		chartsPanel.clear();
		
		final VerticalPanel container = new VerticalPanel();
		container.addStyleName("container");
		container.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		HorizontalPanel header = new HorizontalPanel();
		Button logoutButton = new Button("Logout");
		Button aboutButton = new Button("About");
		header.setSpacing(5);
		header.add(logoutButton);
		header.add(aboutButton);
		container.add(header);
		
		Label welcomeLabel = new Label(
				"Welcome to your member area " + login + "! " +
			    "You can test the system on this page.");
		container.add(welcomeLabel);
		container.setSpacing(5);

		Button trainButton = new Button("Train the system");
		container.add(trainButton);

		HTML explanations = new HTML();
		explanations.setHTML("Type your password and press Enter.<br/>" +
				"If you typed the correct password, your keystroke dynamics " +
				"will be analyzed<br/> and the authentication system will " +
				"tell you if it matches your usual one.<br/><br/>" +
				"You can also try to log in as an other user by entering a " +
				"login in the textbox.<br/>A list of existing login and " +
				"pasword is given so you can try.");
		container.add(explanations);

		// TODO: A PasswordTextBox which display the password

		HTML applet = new HTML();
		String installJava = "<a href=\"http://www.java.com/en/download/help" +
				"/windows_manual_download.xml\">Install Java now.</a>";
		String testJava = "<a href=\"http://www.java.com/en/download/" +
				"testjava.jsp\">Test Java.</a>";
		// TODO hide applet + auto focus
		String HTML5Applet = new String("<object id=\"KeyboardApplet\" " +
			"type=\"application/x-java-applet\" height=\"100\" width=\"650\">" +
			"<param name=\"mayscript\" value=\"yes\">" +
			"<param name=\"scriptable\" value=\"true\">" +
	        "<param name=\"codebase\" value=\"resources/\">" +
	        "<param name=\"code\" value=\"KeyboardApplet.class\">" +
	        "<!--  <param name=\"archive\" value=\"KeyboardApplet.jar\"> -->" +
	        "This application is designed to securely authenticate users " +
	        "according to their keystroke dynamics. In order to do that, " +
	        "Java must be installed on your computer. " + installJava + " " +
	        testJava + "</object>");
	    applet.setHTML(HTML5Applet);
	    container.add(applet);

	    // TODO: l'applet doit libérer le focus une fois terminé
	    /*HTML focusButton = new HTML();
		focusButton.setHTML("<button " +
				"onClick=\"startFocus('KeyboardApplet')\">" +
				"Permanent focus</button>");
	    container.add(focusButton);*/
	    container.add(chartsPanel);

	    HorizontalPanel testLoginPanel = new HorizontalPanel();
	    Label testLoginLabel = new Label("Login");
	    TextBox testLogin = new TextBox();
	    testLogin.setText(login);
	    testLoginPanel.add(testLoginLabel);
	    testLoginPanel.add(testLogin);
	    HorizontalPanel testPasswordPanel = new HorizontalPanel();
	    Label testPasswordLabel = new Label("Password");
	    PasswordTextBox testPassword = new PasswordTextBox();
	    testPasswordPanel.add(testPasswordLabel);
	    testPasswordPanel.add(testPassword);
	    container.add(testLoginPanel);
	    container.add(testPassword);

	    testLogin.addFocusHandler(new FocusHandler() {
			@Override
			public native void onFocus(FocusEvent event) /*-{
			    $wnd.stopFocus();
			}-*/;
		});

	    testLogin.addBlurHandler(new BlurHandler() {
			@Override
			public native void onBlur(BlurEvent event) /*-{
			    $wnd.startFocus('KeyboardApplet');
			}-*/;
		});

	    testPassword.addFocusHandler(new FocusHandler() {
			@Override
			public native void onFocus(FocusEvent event) /*-{
			    $wnd.startFocus('KeyboardApplet');
			}-*/;
		});

		RootPanel.get("content").add(container);

		logoutButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				authenticationService.logout(new AsyncCallback<Void>() {
					@Override
                    public void onFailure(Throwable caught) {
						displayErrorMessage("Logout", caught.getMessage());
                    }
                    @Override
                    public void onSuccess(Void result) {
                    	loadHomePage();
                    }
				});
			}
		});

		trainButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				loadUserPage(login);
			}
		});
	}

	// Create a callback to be called when the visualization API has been
	// loaded. Retrieve KDData information from the data store and display
	// them on a chart.
	private static Runnable onLoadCallback = new Runnable() {
		public void run() {
			// TODO: if null, display nothing.
			transmissionService.getKDData(
					new AsyncCallback<List<KDPassword>>() {
						@Override
				public void onFailure(Throwable caught) {
					displayErrorMessage("KDDataRetrieval", caught.getMessage());
				}
				@Override
				public void onSuccess(List<KDPassword> kdData) {
					if (kdData != null) {
						int kdDataNumber = kdData.size();
						if (kdDataNumber > 0) {
							TimeSequence[] pressedData =
									new TimeSequence[kdDataNumber];
							TimeSequence[] releasedData =
									new TimeSequence[kdDataNumber];
							TimeSequence[] pressedToReleasedData =
									new TimeSequence[kdDataNumber];
							TimeSequence[] releasedToPressedData =
									new TimeSequence[kdDataNumber];
							KeystrokeSequence keystrokeSequence;
							for (int i = 0 ; i < kdDataNumber ; ++i) {
								keystrokeSequence =
										kdData.get(i).getKeystrokeSequence();
								pressedData[i] =
										keystrokeSequence
										.getPressedToPressedSequence();
								releasedData[i] =
										keystrokeSequence
										.getReleasedToReleasedSequence();
								pressedToReleasedData[i] =
										keystrokeSequence
										.getPressedToReleasedSequence();
								releasedToPressedData[i] =
										keystrokeSequence
										.getReleasedToPressedSequence();
							}
							LineChart pressedChart =
									MemberAreaCharts.getChart(
											"pressed", pressedData);
							chartsPanel.setWidget(3, 0, pressedChart);
							LineChart releasedChart =
									MemberAreaCharts.getChart(
											"released", releasedData);
							chartsPanel.setWidget(6, 0, releasedChart);
							LineChart pressedToReleasedChart =
									MemberAreaCharts.getChart(
											"pressedToReleased",
											pressedToReleasedData);
							chartsPanel.setWidget(9, 0, pressedToReleasedChart);
							LineChart releasedToPressedChart =
									MemberAreaCharts.getChart(
											"releasedToPressed",
											releasedToPressedData);
							chartsPanel.setWidget(
									12, 0, releasedToPressedChart);
						}
					}
				}
			});

			transmissionService.getMeans(
					new AsyncCallback<StatisticsUnit>() {
						@Override
				public void onFailure(Throwable caught) {
					displayErrorMessage("MeansRetrieval", caught.getMessage());
				}
				@Override
				public void onSuccess(StatisticsUnit means) {
					if (means != null) {
						Label pressedMeans = new Label("Means: " +
								means.getPressedToPressedStatistics()
								.toString());
						chartsPanel.setWidget(1, 0, pressedMeans);
						Label releasedMeans = new Label("Means: " +
								means.getReleasedToReleasedStatistics()
								.toString());
						chartsPanel.setWidget(4, 0, releasedMeans);
						Label pressedToReleasedMeans = new Label("Means: " +
								means.getPressedToReleasedStatistics()
								.toString());
						chartsPanel.setWidget(7, 0, pressedToReleasedMeans);
						Label releasedToPressedMeans = new Label("Means: " +
								means.getReleasedToPressedStatistics()
								.toString());
						chartsPanel.setWidget(10, 0, releasedToPressedMeans);
					}
				}
			});

			transmissionService.getSd(
					new AsyncCallback<StatisticsUnit>() {
						@Override
				public void onFailure(Throwable caught) {
					displayErrorMessage("SdRetrieval", caught.getMessage());
				}
				@Override
				public void onSuccess(StatisticsUnit sd) {
					if (sd != null) {
						Label pressedSd = new Label("Sd: " +
								sd.getPressedToPressedStatistics().toString());
						chartsPanel.setWidget(2, 0, pressedSd);
						Label releasedSd = new Label("Sd: " +
								sd.getReleasedToReleasedStatistics().toString());
						chartsPanel.setWidget(5, 0, releasedSd);
						Label pressedToReleasedSd = new Label("Sd: " +
								sd.getPressedToReleasedStatistics()
								.toString());
						chartsPanel.setWidget(8, 0, pressedToReleasedSd);
						Label releasedToPressedSd = new Label("Sd: " +
								sd.getReleasedToPressedStatistics()
								.toString());
						chartsPanel.setWidget(11, 0, releasedToPressedSd);
					}
				}
			});
		}
	};

	/**
	 * Define the JavaScript Native functions to be used in the web application.
	 * appletCallback:
	 *  	Called from Keyboard Applet to send Keystroke Dynamics data.
	 * appletCallbackChar:
	 * 		Called from Keyboard Applet to send last typed character.
	 */
	public native void JSNI() /*-{
		$wnd.requestFocus = function() {
			$wnd.startFocus('KeyboardApplet');
		}
    	$wnd.appletCallback = function(x) {
           @fr.vhat.keydyn.client.KeyDyn::appletCallback(Ljava/lang/String;)(x);
    	}
    	$wnd.appletCallbackChar = function(x) {
           @fr.vhat.keydyn.client.KeyDyn::appletCallbackChar(Ljava/lang/String;)(x);
    	}
    }-*/;

	public native void testJSNI() /*-{
		$wnd.appletCallback = function(x) {
	    @fr.vhat.keydyn.client.KeyDyn::appletCallbackTest(Ljava/lang/String;)(x);
		}
	}-*/;

	public native void trainJSNI() /*-{
		$wnd.appletCallback = function(x) {
	    @fr.vhat.keydyn.client.KeyDyn::appletCallback(Ljava/lang/String;)(x);
		}
	}-*/;

	private static void authenticateUser(String login, int mode, String kdData,
			boolean giveInfo) {
		authenticationService.authenticateUser(login, mode, kdData, giveInfo,
				new AsyncCallback<Float[]>() {
			@Override
            public void onFailure(Throwable caught) {
				displayErrorMessage("authenticateUserTrainMode",
						caught.getMessage());
            }
            @Override
            public void onSuccess(Float[] info) {
            	String information = new String();
            	if (info[0] == -2) {
            		information = "Le mot de passe saisi n'est pas valide.";
            	} else if (info[0] == -3) {
            		information = "Cet utilisateur n'est pas reconnu.";
            	} else {
	            	if (info[1] == 1) {
	            		// New data have been saved
	            		updateKDData();
	            	}
	            	if (info[2] != -1) {
	            		String distance = info[2].toString();
	            		information += "Distance : " + distance + " ; ";
	            	}
	            	String authenticationSuccess;
	            	if (info[0] == 1) {
	            		authenticationSuccess = "AUTHENTICATED";
	            	} else {
	            		authenticationSuccess = "FAILED";
	            	}
	            	information += "Authentification : "
	            			+ authenticationSuccess;
	            	if (info[3] != -1) {
	            		String threshold = info[3].toString();
	            		information += " (Seuil = " + threshold.toString()
	            				+ ")";
	            	}
            	}
            	final Label informationLabel = new Label(information);
            	chartsPanel.setWidget(0, 0, informationLabel);
            	RootPanel.get("infos").clear();
        		RootPanel.get("errors").clear();
            }
		});
	}
	/**
	 * Check Keystroke Dynamics data and store them in the data store.
	 * This function is called from the Keyboard Applet via JSNI.
	 * @param kdData Keystroke Dynamics data.
	 */
	public static void appletCallback(final String kdData) {
		authenticateUser("", 1, kdData, true);
	};

	/**
	 * Check Keystroke Dynamics data and test them.
	 * This function is called from the Keyboard Applet via JSNI.
	 * @param kdData Keystroke Dynamics data.
	 */
	public static void appletCallbackTest(final String kdData) {
		authenticateUser("", 0, kdData, true);
	};

	/**
	 * Display last typed character on screen.
	 * This function is called from the Keyboard Applet via JSNI.
	 * @param char Last typed character.
	 */
	public static void appletCallbackChar(final String c) {
		// TODO: display in a field
		// TODO: when appletCallback is called, clear the field
		
	}

	/**
	 * Display information about KDData saved in the data store : data,
	 * statistics and chart
	 */
	private static void updateKDData () {
		// Draw or refresh charts
		chartsPanel.clear();
		onLoadCallback.run();
		// TODO: show user that 1 KDData saved, 1 less to train
	}
}