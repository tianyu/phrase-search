package docs;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

public class WordSearchTest {
	private final Search search = new Search(TestCorpus.load("people"));

	@Test
	public void testFindWord() {
		// "nicolaevsky" should only appear in the first document.
		final List<Integer> nicolaevsky = search.findWord("nicolaevsky");
		assertThat(nicolaevsky, contains(0));

		// "the" should appear in every document.
		final List<Integer> the = search.findWord("the");
		assertThat(the, containsInAnyOrder(0, 1, 2, 3, 4, 5, 6, 7, 8));
	}

}
