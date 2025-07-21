import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;

/**
 * Created on : 27/03/2022
 * Author     : Yoann Meclot (Msay2)
 * Email      : yoannmeclot@hotmail.com
 */
public final class ImmutableEvaluators<Eval, Item extends Evaluator.Evaluable<Eval>> extends AbstractEvaluators<Eval, Item> implements Serializable
{
    @Serial
    private static final long serialVersionUID = 662517613443663033L;
    private static final Object[] EMPTY_ITEMS = {};

    private Object[] mItems;
    private final int mLength;

    ImmutableEvaluators(Item[] items)
    {
        mItems = items;

        if ((mLength = mItems.length) != 0) mItems = Arrays.copyOf(mItems, mLength, mItems.getClass());
        else mItems = EMPTY_ITEMS;
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
    public Item peek()
    {
        if (mLength > 0)
        {
            //noinspection unchecked
            return (Item)mItems[mLength - 1];
        }
        return null;
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
        if (!(object instanceof ImmutableEvaluators)) return false;

        //noinspection unchecked
        ImmutableEvaluators<Eval, Item> that = (ImmutableEvaluators<Eval, Item>)object;
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
        //noinspection unchecked
        return new ImmutableEvaluators<>((Item[])mItems);
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
