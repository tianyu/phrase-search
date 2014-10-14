package docs;

import java.util.List;

public class Search {
	private final Corpus corpus;

	public Search(final Corpus corpus) {
		this.corpus = corpus;
	}

	/**
	 * Find all documents in the corpus containing a word.
	 * @param word The word to search for.
	 * @return A list of all document numbers containing a word.
	 */
	public List<Integer> findWord(String word) {
		return null;
	}

	/**
	 * Find all documents in the corpus containing both words.
	 * @param word1 The first word to search for.
	 * @param word2 The second word to search for.
	 * @return A list of all document numbers containing both words.
	 */
	public List<Integer> findTwoWords(String word1, String word2) {
		return null;
	}
}
