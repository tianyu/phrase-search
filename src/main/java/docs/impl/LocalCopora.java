package docs.impl;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import docs.Corpus;

public enum LocalCopora {;
	public static Corpus load(final String dir) {
		final Pattern nonToken = Pattern.compile("[^\\w]+");
		try {
			final URL url = LocalCopora.class.getResource("/" + dir);
			if (url == null) throw new Error("Resource \"" + dir + "\" is not available.");
			final Path docs = Paths.get(url.toURI());
			final Stream<Stream<String>> tokens = StreamSupport.stream(Files.newDirectoryStream(docs, "*.txt").spliterator(), false)
				.map(doc -> {
					try {
						return Files.newBufferedReader(doc)
							.lines()
							.flatMap(nonToken::splitAsStream);
					} catch (IOException e) {
						throw new UncheckedIOException(e);
					}
				});
			return new InMemoryCorpus(tokens);
		} catch (Exception e) {
			throw new Error("Error retrieving corpus files.", e);
		}
	}
}
