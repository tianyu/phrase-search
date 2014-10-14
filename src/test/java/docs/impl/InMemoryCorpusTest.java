package docs.impl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;

import com.google.common.collect.Lists;
import docs.Corpus;
import docs.Cursor;
import docs.DPT;

public class InMemoryCorpusTest {
	private final Corpus corpus = new InMemoryCorpus(Stream.of(
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
		for (DPT dpt : dpts) {
			assertTrue(cursor.isValid());
			assertThat(cursor.get(), is(dpt));
			cursor.next();
		}
		assertFalse(cursor.isValid());
	}

	public List<DPT> toList(Cursor cursor) {
		final List<DPT> dpts = Lists.newArrayList();
		while (cursor.isValid()) dpts.add(cursor.get());
		return dpts;
	}
}
