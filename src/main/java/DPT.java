/**
 * A document, position tuple.
 */
public class DPT {
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
}
