import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created on : 27/03/2022
 * Author     : Yoann Meclot (DevMyBits)
 * Email      : devmybits@gmail.com
 */
public class EvaluatorsSynchronized<Eval, Item extends Evaluator.Evaluable<Eval>> extends Evaluators<Eval, Item> implements Serializable
{
    @Serial
    private static final long serialVersionUID = 5182765180467492991L;

    private final Object mMonitor;

    public EvaluatorsSynchronized()
    {
        super();
        mMonitor = new Object();
    }

    public EvaluatorsSynchronized(Collection<? extends Item> collection)
    {
        super(collection);
        mMonitor = new Object();
    }

    public EvaluatorsSynchronized(Evaluator<? extends Eval, ? extends Item> evaluator)
    {
        super(evaluator);
        mMonitor = new Object();
    }

    public EvaluatorsSynchronized(Item[] array)
    {
        super(array);
        mMonitor = new Object();
    }

    public EvaluatorsSynchronized(int initialCapacity)
    {
        super(initialCapacity);
        mMonitor = new Object();
    }

    @Override
    public void push(Item item)
    {
        synchronized (mMonitor) {
            super.push(item);
        }
    }

    @Override
    public void push(Item item, int index)
    {
        synchronized (mMonitor) {
            super.push(item, index);
        }
    }

    @Override
    public void pushIfAbsent(Item item, Eval eval)
    {
        synchronized (mMonitor) {
            super.pushIfAbsent(item, eval);
        }
    }

    @Override
    public void set(Item item, int index)
    {
        synchronized (mMonitor) {
            super.set(item, index);
        }
    }

    @Override
    public void set(Item item, Eval eval)
    {
        synchronized (mMonitor) {
            super.set(item, eval);
        }
    }

    @Override
    public void pushAll(Collection<? extends Item> collection)
    {
        synchronized (mMonitor) {
            super.pushAll(collection);
        }
    }

    @Override
    public void pushAll(Collection<? extends Item> collection, int index)
    {
        synchronized (mMonitor) {
            super.pushAll(collection, index);
        }
    }

    @Override
    public void pushAll(Evaluator<? extends Eval, ? extends Item> evaluator)
    {
        synchronized (mMonitor) {
            super.pushAll(evaluator);
        }
    }

    @Override
    public void pushAll(Evaluator<? extends Eval, ? extends Item> evaluator, int index)
    {
        synchronized (mMonitor) {
            super.pushAll(evaluator, index);
        }
    }

    @Override
    public void clear()
    {
        synchronized (mMonitor) {
            super.clear();
        }
    }

    @Override
    public void trim()
    {
        synchronized (mMonitor) {
            super.trim();
        }
    }

    @Override
    public void reverse()
    {
        synchronized (mMonitor) {
            super.reverse();
        }
    }

    @Override
    public void pop()
    {
        synchronized (mMonitor) {
            super.pop();
        }
    }

    @Override
    public Item evaluate(Eval eval)
    {
        synchronized (mMonitor) {
            return super.evaluate(eval);
        }
    }

    @Override
    public Item get(int index)
    {
        synchronized (mMonitor) {
            return super.get(index);
        }
    }

    @Override
    public Item[] toArray(Class<Item> type)
    {
        synchronized (mMonitor) {
            return super.toArray(type);
        }
    }

    @Override
    public Item[] toArray(Item[] items)
    {
        synchronized (mMonitor) {
            return super.toArray(items);
        }
    }

    @Override
    public Object[] toArray()
    {
        synchronized (mMonitor) {
            return super.toArray();
        }
    }

    @Override
    public Item acquire()
    {
        synchronized (mMonitor) {
            return super.acquire();
        }
    }

    @Override
    public Item acquireFirst()
    {
        synchronized (mMonitor) {
            return super.acquireFirst();
        }
    }

    @Override
    public Item peek()
    {
        synchronized (mMonitor) {
            return super.peek();
        }
    }

    @Override
    public int length()
    {
        synchronized (mMonitor) {
            return super.length();
        }
    }

    @Override
    public int indexOf(Eval eval)
    {
        synchronized (mMonitor) {
            return super.indexOf(eval);
        }
    }

    @Override
    public boolean remove(Eval eval)
    {
        synchronized (mMonitor) {
            return super.remove(eval);
        }
    }

    @Override
    public boolean remove(int index)
    {
        synchronized (mMonitor) {
            return super.remove(index);
        }
    }

    @Override
    public boolean isEmpty()
    {
        synchronized (mMonitor) {
            return super.isEmpty();
        }
    }

    @Override
    public String toString()
    {
        synchronized (mMonitor) {
            return super.toString();
        }
    }

    @Override
    public boolean equals(Object object)
    {
        synchronized (mMonitor) {
            return super.equals(object);
        }
    }

    @Override
    public Evaluator<Eval, Item> clone()
    {
        synchronized (mMonitor) {
            return super.clone();
        }
    }
}
