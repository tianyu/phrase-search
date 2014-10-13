public interface Cursor {
	/**
	 * Gets the current position of the cursor.
	 * @return the {@link DPT} of the current cursor's position.
	 */
	DPT get();

	/**
	 * Advances this cursor to the next {@link DPT}.
	 *
	 * @return true if and only if the next position is valid.
	 * @see #isValid()
	 */
	boolean next();

	/**
	 * Advances this cursor to the given target, or the next
	 * {@link DPT} if the target doesn't exist.
	 *
	 * @param target The target {@link DPT} to go to.
	 * @return true if and only if the target exists.
	 */
	boolean seek(DPT target);

	/**
	 * Determines whether this cursor is valid (not off the end)
	 * @return true if and only if this cursor is valid.
	 */
	boolean isValid();
}
