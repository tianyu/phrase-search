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
		final List<Integer> docs = new ArrayList<>();
		findTwoWords(corpus.find(word1), corpus.find(word2), docs);
		return docs;
	}

	private void findTwoWords(Cursor cursor1, Cursor cursor2, List<Integer> docs) {
		if (!cursor1.isValid() || !cursor2.isValid()) return;
		final DPT dpt1 = cursor1.get();
		final DPT dpt2 = cursor2.get();
		if (dpt1.compareTo(dpt2) > 0) {
			findTwoWords(cursor2, cursor1, docs);
		} else if (dpt1.getDocument() == dpt2.getDocument()) {
			docs.add(dpt1.getDocument());
			final DPT nextDoc = new DPT(dpt1.getDocument() + 1, 0);
			cursor1.seek(nextDoc);
			cursor2.seek(nextDoc);
			findTwoWords(cursor1, cursor2, docs);
		} else {
			cursor1.seek(new DPT(dpt1.getDocument() + 1, 0));
			findTwoWords(cursor1, cursor2, docs);
		}
	}
}
