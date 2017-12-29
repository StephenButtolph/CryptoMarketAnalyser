package arithmetic;

import org.apfloat.Apfloat;

import constants.Numeric;

public class Pfloat {
	private final Apfloat value;

	public Pfloat(long val) {
		this(new Apfloat(val, Numeric.APFLOAT_PRECISION));
	}

	public Pfloat(String val) {
		this(new Apfloat(val, Numeric.APFLOAT_PRECISION));
	}

	public Pfloat(Apfloat val) {
		value = val;
	}

	public Pfloat add(Pfloat toAdd) {
		return new Pfloat(value.add(toAdd.value));
	}

	public Pfloat subtract(Pfloat toAdd) {
		return new Pfloat(value.subtract(toAdd.value));
	}

	public Pfloat multiply(Pfloat toAdd) {
		return new Pfloat(value.multiply(toAdd.value));
	}

	public Pfloat divide(Pfloat toAdd) {
		return new Pfloat(value.divide(toAdd.value));
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
		return value.equals(other.value);
	}

	@Override
	public String toString() {
		return value.toString(true);
	}
}
