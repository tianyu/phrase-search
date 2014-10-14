package docs;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.stream.Stream;

import org.junit.Test;

public class TestCorpusTest {
	private final Corpus corpus = new TestCorpus(Stream.of(
		Stream.of("aaa", "bbb", "aab", "bba", "bbb", "aaa")
		, Stream.of("aab", "bbb", "aab", "bba", "bba", "bbb")
	));

	@Test
	public void testFind() {
		assertCursor(corpus.find("aaa")
			, new DPT(0, 0)
			, new DPT(0, 5)
		);
		assertCursor(corpus.find("bbb")
			, new DPT(0, 1)
			, new DPT(0, 4)
			, new DPT(1, 1)
			, new DPT(1, 5)
		);
		assertCursor(corpus.find("aab")
			, new DPT(0, 2)
			, new DPT(1, 0)
			, new DPT(1, 2)
		);
		assertCursor(corpus.find("aba"));
	}

	@Test
	public void testSeek() {
		final Cursor bba = corpus.find("bba");
		bba.seek(new DPT(1, 0));
		assertCursor(bba, new DPT(1, 3), new DPT(1, 4));

		final Cursor bbb = corpus.find("bbb");
		bbb.seek(new DPT(2, 0));
		assertCursor(bbb);
	}

	private static void assertCursor(Cursor cursor, DPT... dpts) {
		boolean valid = cursor.isValid();
		for (DPT dpt : dpts) {
			assertTrue(valid);
			assertThat(cursor.get(), is(dpt));
			valid = cursor.next();
		}
		assertFalse(valid);
	}
}
