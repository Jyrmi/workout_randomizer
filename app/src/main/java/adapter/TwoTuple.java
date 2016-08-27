package adapter;

/**
 * Created by jeremy on 8/19/16.
 */
public class TwoTuple<A, B> {
    public A a;
    public B b;

    public TwoTuple() {
        this.a = null;
        this.b = null;
    }

    public TwoTuple(A a, B b) {
        this.a = a;
        this.b = b;
    }
}
