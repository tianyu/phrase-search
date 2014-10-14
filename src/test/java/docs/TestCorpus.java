package docs;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableListMultimap.Builder;

public class TestCorpus implements Corpus {
	private final ImmutableListMultimap<String, DPT> index;

	public TestCorpus(final Stream<Stream<String>> docs) {
		final Builder<String, DPT> builder = ImmutableListMultimap.builder();
		final AtomicInteger d = new AtomicInteger(0);
		docs.forEachOrdered(tokens -> {
			final AtomicInteger p = new AtomicInteger(0);
			tokens
				.map(String::trim)
				.map(String::toLowerCase)
				.forEachOrdered(token ->
				builder.put(token, new DPT(d.get(), p.getAndIncrement())));
			d.incrementAndGet();
		});
		index = builder.build();
	}

	@Override
	public Cursor find(final String token) {
		return new InMemoryCursor(index.get(token.trim().toLowerCase()));
	}

	private static class InMemoryCursor implements Cursor {
		private final List<DPT> dpts;
		private int index = 0;

		public InMemoryCursor(List<DPT> dpts) {
			this.dpts = dpts;
		}

		@Override
		public DPT get() {
			if (!isValid()) return null;
			return dpts.get(index);
		}

		@Override
		public boolean next() {
			if (!isValid()) return false;
			index += 1;
			return isValid();
		}

		@Override
		public boolean seek(final DPT target) {
			index = Collections.binarySearch(dpts, target);
			if (index >= 0) return true;
			index = -(index + 1);
			return false;
		}

		@Override
		public boolean isValid() {
			return index < dpts.size();
		}

		@Override
		public String toString() {
			final StringBuilder builder = new StringBuilder();
			while (isValid()) {
				builder.append(get()).append(' ');
				next();
			}
			return builder.toString();
		}
	}

	public static Corpus load(String dir) {
		final Pattern nonToken = Pattern.compile("[^\\w]+");
		try {
			final URL url = TestCorpus.class.getResource("/" + dir);
			if (url == null) throw new Error("Resource \"" + dir + "\" is not available.");
			final Path path = Paths.get(url.toURI());
			final Stream<Stream<String>> docs = StreamSupport
				.stream(Files.newDirectoryStream(path, "*.txt").spliterator(), false)
				.map(doc -> {
					try {
						return Files.newBufferedReader(doc)
							.lines()
							.flatMap(nonToken::splitAsStream);
					} catch (IOException e) {
						throw new UncheckedIOException(e);
					}
				});
			return new TestCorpus(docs);
		} catch (Exception e) {
			throw new Error("Error retrieving corpus files.", e);
		}
	}
}
