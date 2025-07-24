import java.io.Serializable;
import java.util.Collection;

/**
 * Created on : 27/03/2022
 * Author     : Yoann Meclot (DevMyBits)
 * Email      : devmybits@gmail.com
 */
public interface Evaluator<Eval, Item extends Evaluator.Evaluable<Eval>> extends Serializable
{
    void push(Item item);

    void push(Item item, int index);

    void pushIfAbsent(Item item, Eval eval);

    void set(Item item, int index);

    void set(Item item, Eval eval);

    void pushAll(Collection<? extends Item> collection);

    void pushAll(Collection<? extends Item> collection, int index);

    void pushAll(Evaluator<? extends Eval, ? extends Item> evaluator);

    void pushAll(Evaluator<? extends Eval, ? extends Item> evaluator, int index);

    void clear();

    void trim();

    void reverse();

    void pop();

    Item evaluate(Eval eval);

    Item get(int index);

    Item acquire();

    Item acquireFirst();

    Item peek();

    Item[] toArray(Class<Item> type);

    Item[] toArray(Item[] items);

    Object[] toArray();

    int length();

    int indexOf(Eval eval);

    boolean remove(Eval eval);

    boolean remove(int index);

    boolean isEmpty();

    String toString();

    Evaluator<Eval, Item> clone();

    interface Evaluable<Target>
    {
        boolean toEvaluate(Target target);
    }
}
