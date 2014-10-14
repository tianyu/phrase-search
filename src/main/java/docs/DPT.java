package docs;

import static java.lang.Integer.compare;

import java.util.Objects;

/**
 * A document, position tuple.
 */
public class DPT implements Comparable<DPT> {
	private final int document;
	private final int position;

	public DPT(final int document, final int position) {
		this.document = document;
		this.position = position;
	}

	public int getDocument() {
		return document;
	}

	public int getPosition() {
		return position;
	}

	@Override
	public int compareTo(final DPT other) {
		return document == other.document
			? compare(position, other.position)
			: compare(document, other.document);
	}

	@Override
	public int hashCode() {
		return Objects.hash(document, position);
	}

	@Override
	public boolean equals(final Object obj) {
		return obj instanceof DPT && compareTo(((DPT) obj)) == 0;
	}
}
