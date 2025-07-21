import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created on : 27/03/2022
 * Author     : Yoann Meclot (Msay2)
 * Email      : yoannmeclot@hotmail.com
 */
public class Evaluators<Eval, Item extends Evaluator.Evaluable<Eval>> extends AbstractEvaluators<Eval, Item> implements Serializable
{
    @SafeVarargs
    public static <Eval, Item extends Evaluable<Eval>> Evaluator<Eval, Item> asImmutable(Item... items)
    {
        return new ImmutableEvaluators<>(items);
    }

    @Serial
    private static final long serialVersionUID = 6203352090132307890L;
    private static final Object[] EMPTY_ITEMS = {};

    private Object[] mItems;
    private int mLength;

    public Evaluators()
    {
        mItems = EMPTY_ITEMS;
    }

    public Evaluators(Collection<? extends Item> collection)
    {
        mItems = collection.toArray();

        if ((mLength = mItems.length) != 0) mItems = Arrays.copyOf(mItems, mLength, mItems.getClass());
        else mItems = EMPTY_ITEMS;
    }

    public Evaluators(Evaluator<? extends Eval, ? extends Item> evaluator)
    {
        mItems = evaluator.toArray();

        if ((mLength = mItems.length) != 0) mItems = Arrays.copyOf(mItems, mLength, mItems.getClass());
        else mItems = EMPTY_ITEMS;
    }

    public Evaluators(Item[] array)
    {
        mItems = array;

        if ((mLength = mItems.length) != 0) mItems = Arrays.copyOf(mItems, mLength, mItems.getClass());
        else mItems = EMPTY_ITEMS;
    }

    public Evaluators(int initialCapacity)
    {
        if (initialCapacity > 0) mItems = new Object[initialCapacity];
        else if (initialCapacity == 0) mItems = EMPTY_ITEMS;
        else throw new IllegalArgumentException("Illegal initial capacity " + initialCapacity);
    }

    @Override
    public void push(Item item)
    {
        if (item == null) return;

        mItems = Items.ensureCapacity(mItems, mLength + 1);
        mItems[mLength++] = item;
    }

    @Override
    public void push(Item item, int index)
    {
        if (index < 0 || index > mLength) throw new IndexOutOfBoundsException("Array index out of bounds: index="+index+" size="+mLength);
        if (item == null) return;

        mItems = Items.ensureCapacity(mItems, mLength + 1);

        System.arraycopy(mItems, index, mItems, index + 1, mLength - index);

        mItems[index] = item;
        mLength++;
    }

    @Override
    public void pushIfAbsent(Item item, Eval eval)
    {
        if (item == null) return;
        if (evaluateOf(eval) >= 0) return;

        push(item);
    }

    @Override
    public void set(Item item, int index)
    {
        if (index < 0 || index > mLength) throw new IndexOutOfBoundsException("Array index out of bounds: index="+index+" size="+mLength);
        mItems[index] = item;
    }

    @Override
    public void set(Item item, Eval eval)
    {
        set(item, evaluateOf(eval));
    }

    @Override
    public void pushAll(Collection<? extends Item> collection)
    {
        Object[] items = collection.toArray();
        int length = items.length;

        mItems = Items.ensureCapacity(mItems, mLength + length);

        System.arraycopy(items, 0, mItems, mLength, length);

        mLength += length;
    }

    @Override
    public void pushAll(Collection<? extends Item> collection, int index)
    {
        if (index < 0 || index > mLength) throw new IndexOutOfBoundsException("Array index out of bounds: index="+index+" size="+mLength);

        Object[] items = collection.toArray();
        int length = items.length;

        mItems = Items.ensureCapacity(mItems, mLength + length);

        System.arraycopy(mItems, index, mItems, index + length, mLength - index);
        System.arraycopy(items, 0, mItems, index, length);

        mLength += length;
    }

    @Override
    public void pushAll(Evaluator<? extends Eval, ? extends Item> evaluator)
    {
        Object[] items = evaluator.toArray();
        int length = items.length;

        mItems = Items.ensureCapacity(mItems, mLength + length);

        System.arraycopy(items, 0, mItems, mLength, length);

        mLength += length;
    }

    @Override
    public void pushAll(Evaluator<? extends Eval, ? extends Item> evaluator, int index)
    {
        if (index < 0 || index > mLength) throw new IndexOutOfBoundsException("Array index out of bounds: index="+index+" size="+mLength);

        Object[] items = evaluator.toArray();
        int length = items.length;

        mItems = Items.ensureCapacity(mItems, mLength + length);

        System.arraycopy(mItems, index, mItems, index + length, mLength - index);
        System.arraycopy(items, 0, mItems, index, length);

        mLength += length;
    }

    @Override
    public void clear()
    {
        for (int i = 0; i < mLength; i++) mItems[i] = null;
        mLength = 0;
    }

    @Override
    public void trim()
    {
        if (mLength < mItems.length) mItems = (mLength == 0) ? EMPTY_ITEMS : Arrays.copyOf(mItems, mLength);
    }

    @Override
    public void reverse()
    {
        int left = 0;
        int right = mLength - 1;
        while (left <= right) {
            Object start = mItems[left];
            Object end = mItems[right];

            mItems[right] = start;
            mItems[left] = end;

            left++;
            right--;
        }
    }

    @Override
    public void pop()
    {
        if (mLength > 0)
        {
            mItems[mLength - 1] = null;
            mLength--;
        }
    }

    @Override
    public Item evaluate(Eval eval)
    {
        int index = evaluateOf(eval);
        if (index < 0) return null;

        //noinspection unchecked
        return (Item)mItems[index];
    }

    @Override
    public Item get(int index)
    {
        if (index < 0 || index >= mLength) throw new IndexOutOfBoundsException("Array index out of bounds: index="+index + " size="+mLength);
        //noinspection unchecked
        return (Item)mItems[index];
    }

    @Override
    public Item[] toArray(Class<Item> type)
    {
        //noinspection unchecked
        Item[] items = (Item[]) Array.newInstance(type, mLength);

        //noinspection SuspiciousSystemArraycopy
        System.arraycopy(mItems, 0, items, 0, mLength);
        return items;
    }

    @Override
    public Item[] toArray(Item[] items)
    {
        if (items.length < mLength) //noinspection unchecked
            return (Item[])Arrays.copyOf(mItems, mLength, items.getClass());

        System.arraycopy(mItems, 0, items, 0, mLength);
        if (items.length > mLength) items[mLength] = null;

        return items;
    }

    @Override
    public Object[] toArray()
    {
        Object[] items = new Object[mLength];
        System.arraycopy(mItems, 0, items, 0, mLength);

        return items;
    }

    @Override
    public Item acquire()
    {
        if (mLength <= 0) return null;

        final int i = mLength - 1;
        //noinspection unchecked
        Item item = (Item)mItems[i];
        mItems[i] = null;
        mLength--;

        return item;
    }

    @Override
    public Item acquireFirst()
    {
        if (mLength <= 0) return null;
        //noinspection unchecked
        final Item value = (Item)mItems[0];
        System.arraycopy(mItems, 1, mItems, 0, mLength);

        mItems[--mLength] = null;
        return value;
    }

    @Override
    public Item peek()
    {
        if (mLength <= 0) return null;

        //noinspection unchecked
        return (Item)mItems[mLength - 1];
    }

    @Override
    public int length()
    {
        return mLength;
    }

    @Override
    public int indexOf(Eval eval)
    {
        return evaluateOf(eval);
    }

    @Override
    public boolean remove(Eval eval)
    {
        return remove(evaluateOf(eval));
    }

    @Override
    public boolean remove(int index)
    {
        if (index < 0 || index >= mLength) return false;

        int pos = mLength - index - 1;
        if (pos > 0) System.arraycopy(mItems, index + 1, mItems, index, pos);

        mItems[--mLength] = null;
        return true;
    }

    @Override
    public boolean isEmpty()
    {
        return mLength <= 0;
    }

    @Override
    public String toString()
    {
        if (mLength <= 0) return "[]";

        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (int i = 0; i < mLength; i++)
        {
            if (i > 0) builder.append(", ");

            //noinspection unchecked
            Item item = (Item)mItems[i];

            if (item == null) builder.append("null");
            else builder.append(item);
        }
        return builder.append("]").toString();
    }

    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof Evaluators)) return false;

        //noinspection unchecked
        Evaluators<Eval, Item> that = (Evaluators<Eval, Item>)object;
        if (mLength != that.mLength) return false;
        if (mLength < 4)
        {
            int left = 0;
            int right = mLength - 1;
            while (left <= right) {
                if (!mItems[left].equals(that.mItems[left])) return false;
                if (!mItems[right].equals(that.mItems[right])) return false;

                left++;
                right--;
            }
            return true;
        }
        int left = 0;
        int right = mLength - 1;
        int middleLeft = right / 2;
        int middleRight = middleLeft;
        while (left < right) {
            if (!mItems[left].equals(that.mItems[left])) return false;
            if (!mItems[right].equals(that.mItems[right])) return false;
            if (!mItems[middleLeft].equals(that.mItems[middleLeft])) return false;
            if (!mItems[middleRight].equals(that.mItems[middleRight])) return false;

            middleLeft--;
            middleRight++;
            left++;
            right--;

            if (middleLeft < left)
            {
                if (middleRight == right) return mItems[middleRight].equals(that.mItems[middleRight]);
                break;
            }
            if (middleRight > right)
            {
                if (middleLeft == left) return mItems[middleLeft].equals(that.mItems[middleLeft]);
                break;
            }
        }
        return true;
    }

    @Override
    public Evaluator<Eval, Item> clone()
    {
        return new Evaluators<>(this);
    }

    private int evaluateOf(Eval eval)
    {
        if (eval == null) return -1;
        if (mLength < 4)
        {
            int left = 0;
            int right = mLength - 1;
            while (left <= right) {
                //noinspection unchecked
                Evaluable<Eval> start = (Evaluable<Eval>)mItems[left];
                //noinspection unchecked
                Evaluable<Eval> end = (Evaluable<Eval>)mItems[right];

                if (end.toEvaluate(eval)) return right;
                if (start.toEvaluate(eval)) return left;

                left++;
                right--;
            }
            return -1;
        }

        int left = 0;
        int right = mLength - 1;
        int middleLeft = right / 2;
        int middleRight = middleLeft;
        while (left < right) {
            //noinspection unchecked
            if (((Evaluable<Eval>)mItems[left]).toEvaluate(eval)) return left;
            //noinspection unchecked
            if (((Evaluable<Eval>)mItems[right]).toEvaluate(eval)) return right;
            //noinspection unchecked
            if (((Evaluable<Eval>)mItems[middleLeft]).toEvaluate(eval)) return middleLeft;
            //noinspection unchecked
            if (((Evaluable<Eval>)mItems[middleRight]).toEvaluate(eval)) return middleRight;

            middleLeft--;
            middleRight++;
            left++;
            right--;

            if (middleLeft < left)
            {
                if (middleRight == right) //noinspection unchecked
                    return ((Evaluable<Eval>)mItems[middleRight]).toEvaluate(eval) ? middleRight : -1;
                break;
            }
            if (middleRight > right)
            {
                if (middleLeft == left) //noinspection unchecked
                    return ((Evaluable<Eval>)mItems[middleLeft]).toEvaluate(eval) ? middleLeft : -1;
                break;
            }
        }
        return -1;
    }
}
