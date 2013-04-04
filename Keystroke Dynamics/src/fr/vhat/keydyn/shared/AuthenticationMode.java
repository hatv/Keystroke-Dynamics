package fr.vhat.keydyn.shared;

/**
 * Define the three modes which can be used with the authentication module.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public enum AuthenticationMode {
	/**
	 * In TEST_MODE :
	 * - the user can select the login of the ready user accounts he wants to 
	 * usurp in a list ;
	 * - none of the sent data are logged ;
	 * - statistics are logged in the data store upon the trials.
	 */
	TEST_MODE,

	/**
	 * In TRAIN_MODE :
	 * - the user session login is used ;
	 * - if the account is ready (enough trained) then all of the sent data are
	 * logged, even if distance > threshold ;
	 * - statistics are logged upon the fails (distance > threshold).
	 */
	TRAIN_MODE,

	/**
	 * In PRODUCTION_MODE :
	 * - the user must type its login in an input field ;
	 * - the sent data are logged only if the authentication succeed ;
	 * - the unsuccessful authentication trials are logged in a temporary table
	 * in order to ask the legitimate owner of the account if it was him later ;
	 * - if the account is ready, statistics are logged in the data store
	 * according to the previous question answer.
	 */
	PRODUCTION_MODE
}
