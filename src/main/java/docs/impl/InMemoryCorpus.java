package docs.impl;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableListMultimap.Builder;
import docs.Corpus;
import docs.Cursor;
import docs.DPT;

public class InMemoryCorpus implements Corpus {
	private final ImmutableListMultimap<String, DPT> index;

	public InMemoryCorpus(final Stream<Stream<String>> docs) {
		final Builder<String, DPT> builder = ImmutableListMultimap.builder();
		final AtomicInteger d = new AtomicInteger(0);
		docs.forEachOrdered(doc -> {
			final AtomicInteger p = new AtomicInteger(0);
			doc.forEachOrdered(token ->
				builder.put(token, new DPT(d.get(), p.getAndIncrement())));
			d.incrementAndGet();
		});
		index = builder.build();
	}

	@Override
	public Cursor find(final String token) {
		return new InMemoryCursor(index.get(token));
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
	}
}
