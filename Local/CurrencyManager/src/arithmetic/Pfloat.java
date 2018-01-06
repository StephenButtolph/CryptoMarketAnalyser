package arithmetic;

import java.util.function.Function;

import org.apfloat.Apfloat;

import constants.Numeric;

public class Pfloat {
	public static final Pfloat ZERO;
	public static final Pfloat ONE;
	public static final Pfloat UNDEFINED;

	static {
		ZERO = new Pfloat(0);
		ONE = new Pfloat(1);
		UNDEFINED = new Pfloat(Type.UNDEFINED);
	}

	private final Apfloat value;
	private final Type type;

	public Pfloat(long val) {
		this(new Apfloat(val, Numeric.APFLOAT_PRECISION));
	}

	public Pfloat(String val) {
		this(new Apfloat(val, Numeric.APFLOAT_PRECISION));
	}

	private Pfloat(Apfloat val) {
		value = val;
		type = Type.NUMBER;
	}

	private Pfloat(Type type) {
		value = null;
		this.type = type;
	}

	public boolean isDefined() {
		return type != Type.UNDEFINED;
	}

	public Pfloat add(Pfloat snd) {
		return operation(value::add, snd);
	}

	public Pfloat subtract(Pfloat snd) {
		return operation(value::subtract, snd);
	}

	public Pfloat multiply(Pfloat snd) {
		Pfloat result = operation(value::multiply, snd);

		if (!result.isDefined() && (equals(ZERO) || snd.equals(ZERO))) {
			return ZERO;
		}
		return result;
	}

	public Pfloat divide(Pfloat snd) {
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
		return value.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Pfloat)) {
			return false;
		}

		Pfloat other = (Pfloat) o;
		return type == other.type && (type == Type.UNDEFINED || value.equals(other.value));
	}

	@Override
	public String toString() {
		if (type == Type.UNDEFINED) {
			return type.name();
		}
		return value.toString(true);
	}

	private enum Type {
		NUMBER, UNDEFINED;
	}
}
