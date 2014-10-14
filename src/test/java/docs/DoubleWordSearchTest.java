package docs;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

public class DoubleWordSearchTest {
	private final Search search = new Search(TestCorpus.load("people"));

	@Test
	public void testFindTwoWords() {
		final List<Integer> jimmiClay = search.findTwoWords("jimmi", "clay");
		assertThat(jimmiClay, contains(4));

		final List<Integer> isA = search.findTwoWords("is", "a");
		assertThat(isA, containsInAnyOrder(0, 1, 3, 4, 5, 6, 7, 8));
	}
}
