package fr.vhat.keydyn.shared;

/**
 * Define the two modes which can be used with the recognition module.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public enum RecognitionMode {
	/**
	 * In RECOGNITION_MODE :
	 * - the user type a sentence that is dictated by the system ;
	 * - the data are not logged ;
	 * - the system try to guess which user has typed this sentence.
	 */
	RECOGNITION_MODE,

	/**
	 * In TRAIN_MODE :
	 * - the user session login is used ;
	 * - the user has to type several sentences in order to train the system ;
	 * - the data are saved to compute statistics.
	 */
	TRAIN_MODE
}
