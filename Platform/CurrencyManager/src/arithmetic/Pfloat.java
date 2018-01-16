package arithmetic;

import java.util.function.Function;

import org.apfloat.Apfloat;

/* 
 * TODO improve precision management 
 * should use precision based on number of decimals and round up division.
 */

/**
 * A Pfloat represents a precise float. This is intended to be used for avoiding
 * floating point approximation errors.
 * 
 * @author Stephen Buttolph
 */
public class Pfloat {
	/**
	 * F A precise float representing the value 0.
	 */
	public static final Pfloat ZERO;

	/**
	 * A precise float representing the value 1.
	 */
	public static final Pfloat ONE;

	/**
	 * A precise float representing an indeterminate value.
	 */
	public static final Pfloat UNDEFINED;

	static {
		ZERO = new Pfloat(0);
		ONE = new Pfloat(1);
		UNDEFINED = new Pfloat(Type.UNDEFINED);
	}

	private final Apfloat value;
	private final Type type;

	/**
	 * Create a new precise float with the value = [val].
	 * 
	 * @param val
	 *            The number this precise float will represent.
	 */
	public Pfloat(long val) {
		this(new Apfloat(val, Constants.DEFAULT_PRECISION));
	}

	/**
	 * Create a new precise float with the value equal to the float representation
	 * of [val].
	 * 
	 * @param val
	 *            The string representing the number this precise float will
	 *            represent.
	 * @throws NumberFormatException
	 *             Thrown when the input sting does not correctly represent a
	 *             number.
	 */
	public Pfloat(String val) throws NumberFormatException {
		this(new Apfloat(val, Constants.DEFAULT_PRECISION));
	}

	private Pfloat(Apfloat val) {
		value = val;
		type = Type.NUMBER;
	}

	private Pfloat(Type type) {
		value = null;
		this.type = type;
	}

	/**
	 * @return True if this object represents a defined number, false otherwise.
	 */
	public boolean isDefined() {
		return type != Type.UNDEFINED;
	}

	/**
	 * Return a new precise float object that represents the addition of [this] and
	 * [snd]. If either [this] or [snd] is undefined, the result will be undefined.
	 * 
	 * @param snd
	 *            The number to add to this number.
	 * @return A new precise float representing the sum of [this] and [snd].
	 */
	public Pfloat add(Pfloat snd) {
		return operation(value::add, snd);
	}

	/**
	 * Return a new precise float object that represents the subtraction of [this]
	 * and [snd]. If either [this] or [snd] is undefined, the result will be
	 * undefined.
	 * 
	 * @param snd
	 *            The number to subtract from this number.
	 * @return A new precise float representing the difference of [this] and [snd].
	 */
	public Pfloat subtract(Pfloat snd) {
		return operation(value::subtract, snd);
	}

	/**
	 * Return a new precise float object that represents the multiplication of
	 * [this] and [snd]. If either [this] or [snd] is undefined, the result will be
	 * undefined.
	 * 
	 * @param snd
	 *            The number to multiply by this number.
	 * @return A new precise float representing the multiplication of [this] and
	 *         [snd].
	 */
	public Pfloat multiply(Pfloat snd) {
		Pfloat result = operation(value::multiply, snd);

		if (!result.isDefined() && (equals(ZERO) || snd.equals(ZERO))) {
			return ZERO;
		}
		return result;
	}

	/**
	 * Return a new precise float object that represents the division of [this] and
	 * [snd], [this]/[snd]. If either [this] or [snd] is undefined, or [snd] is
	 * equal to [ZERO] the result will be undefined.
	 * 
	 * @param snd
	 *            The number to multiply by this number.
	 * @return A new precise float representing the multiplication of [this] and
	 *         [snd].
	 */
	public Pfloat divide(Pfloat snd) {
		if (snd.equals(ZERO)) {
			return UNDEFINED;
		}

		return operation(value::divide, snd);
	}

	private Pfloat operation(Function<Apfloat, Apfloat> f, Pfloat snd) {
		if (!isDefined() || !snd.isDefined()) {
			return UNDEFINED;
		}
		return new Pfloat(f.apply(snd.value));
	}

	@Override
	public int hashCode() {
		if (isDefined()) {
			return value.hashCode();
		}
		return Type.UNDEFINED.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Pfloat)) {
			return false;
		}

		Pfloat other = (Pfloat) o;
		return type == other.type && (!isDefined() || value.equals(other.value));
	}

	@Override
	public String toString() {
		if (isDefined()) {
			return value.toString(true);
		}
		return type.name();
	}

	private enum Type {
		NUMBER, UNDEFINED;
	}
}
