package org.protege.swrlapi.drools.owl;

public abstract class DroolsUnaryObject<T1>
{
	private final T1 t1;

	public DroolsUnaryObject(T1 t1)
	{
		this.t1 = t1;
	}

	public T1 getT1()
	{
		return this.t1;
	}

	@Override
	public String toString()
	{
		return "(" + this.t1 + ")";
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if ((obj == null) || (obj.getClass() != this.getClass()))
			return false;
		@SuppressWarnings("unchecked")
		DroolsUnaryObject<T1> ua = (DroolsUnaryObject<T1>)obj;
		return (getT1() == ua.getT1() || (getT1() != null && getT1().equals(ua.getT1())));
	}

	@Override
	public int hashCode()
	{
		int hash = 44;

		hash = hash + (null == getT1() ? 0 : getT1().hashCode());

		return hash;
	}
}
