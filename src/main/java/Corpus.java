public interface Corpus {

	/**
	 * Gets all of the positions where the given token appears.
	 *
	 * @param token The token to look for.
	 * @return A {@link Cursor} that iterates over all {@link DPT}s
	 * where the token appears.
	 */
	Cursor find(String token);
}
