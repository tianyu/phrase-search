package docs;

import java.util.ArrayList;
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
		final List<Integer> docs = new ArrayList<>();
		final Cursor cursor = corpus.find(word);
		while (cursor.isValid()) {
			docs.add(cursor.get().getDocument());
			cursor.seek(new DPT(cursor.get().getDocument() + 1, 0));
		}
		return docs;
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
