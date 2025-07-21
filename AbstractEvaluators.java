import java.util.Collection;

/**
 * Created on : 27/03/2022
 * Author     : Yoann Meclot (Msay2)
 * Email      : yoannmeclot@hotmail.com
 */
public abstract class AbstractEvaluators<Eval, Item extends Evaluator.Evaluable<Eval>> implements Evaluator<Eval, Item>
{
    protected AbstractEvaluators() {}

    @Override
    public void push(Item item) {}

    @Override
    public void push(Item item, int index) {}

    @Override
    public void pushIfAbsent(Item item, Eval eval) {}

    @Override
    public void set(Item item, Eval eval) {}

    @Override
    public void set(Item item, int index) {}

    @Override
    public void clear() {}

    @Override
    public void pushAll(Collection<? extends Item> collection) {}

    @Override
    public void pushAll(Collection<? extends Item> collection, int index) {}

    @Override
    public void pushAll(Evaluator<? extends Eval, ? extends Item> evaluator) {}

    @Override
    public void pushAll(Evaluator<? extends Eval, ? extends Item> evaluator, int index) {}

    @Override
    public void trim() {}

    @Override
    public void reverse() {}

    @Override
    public void pop() {}

    @Override
    public Item acquire()
    {
        return null;
    }

    @Override
    public Item acquireFirst()
    {
        return null;
    }

    @Override
    public Item[] toArray(Class<Item> type)
    {
        return null;
    }

    @Override
    public Item[] toArray(Item[] items)
    {
        return null;
    }

    @Override
    public Object[] toArray()
    {
        return new Object[0];
    }

    @Override
    public boolean remove(Eval eval)
    {
        return false;
    }

    @Override
    public boolean remove(int index)
    {
        return false;
    }

    @Override
    public Evaluator<Eval, Item> clone()
    {
        return this;
    }
}
