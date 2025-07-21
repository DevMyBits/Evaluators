import java.util.Arrays;

/**
 * Created on : 27/03/2022
 * Author     : Yoann Meclot (Msay2)
 * Email      : yoannmeclot@hotmail.com
 */
final class Items
{
    private static final int MAX_SIZE = Integer.MAX_VALUE - 8;
    private static final int DEFAULT_CAPACITY = 10;

    public static <T> T[] ensureCapacity(T[] items, int minCapacity)
    {
        if (items.length == 0) minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
        if (minCapacity - items.length > 0)
        {
            int oldCapacity = items.length;
            int newCapacity = oldCapacity + (oldCapacity >> 1);

            if (newCapacity - minCapacity < 0) newCapacity = minCapacity;
            if (newCapacity - MAX_SIZE > 0) newCapacity = maxCapacity(minCapacity);

            //noinspection unchecked
            return (T[]) Arrays.copyOf(items, newCapacity, items.getClass());
        }
        return items;
    }

    private static int maxCapacity(int minCapacity)
    {
        if (minCapacity < 0) throw new OutOfMemoryError();
        return (minCapacity > MAX_SIZE) ? Integer.MAX_VALUE : MAX_SIZE;
    }
}
