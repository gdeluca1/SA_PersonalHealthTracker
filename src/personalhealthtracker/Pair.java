package personalhealthtracker;

// This class represents a pair of any two values of the same type.
public class Pair<T>
{
    private final T value1, value2;

    public Pair(T t1, T t2)
    {
        value1 = t1;
        value2 = t2;
    }

    public T getValue1()
    {
        return value1;
    }

    public T getValue2()
    {
        return value2;
    }
}
