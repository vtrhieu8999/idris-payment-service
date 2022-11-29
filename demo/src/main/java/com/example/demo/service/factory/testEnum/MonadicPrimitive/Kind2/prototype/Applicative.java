package com.example.demo.service.factory.testEnum.MonadicPrimitive.Kind2.prototype;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import lombok.experimental.ExtensionMethod;

import java.util.function.BiFunction;
import java.util.function.Function;

@ExtensionMethod({Functor.class})
public abstract class Applicative<A extends Applicative<A>> extends Functor<A> implements $_.Pure<A> {

    private static <T, A extends Applicative<A>> Applicative<A>.Val<T> narrow($_<A>._$<? extends T> instance){
        return (Applicative<A>.Val<T>) Functor.<T, A>narrowed(instance);
    }
    public static <T, R, A extends Applicative<A>> $_<A>._$<R> ap($_<A>._$<T> apply, $_<A>._$<? extends Function<? super T, ? extends R>> fn) {
        return narrow(apply).ap(fn);
    }

    private static <T, R, A extends Applicative<A>> $_<A>._$<Tuple2<T, R>> baseZip(A applicative, $_<A>._$<T> fa, $_<A>._$<R> fb) {
        return ap(fb, ap(fa,
                        applicative.unit(
                                () -> a -> b -> Tuple.of(a, b)
                        )
                )
        );
    }

    public static <T, R, A extends Applicative<A>> $_<A>._$<Tuple2<T, R>> zip(A applicative, $_<A>._$<? extends T> fa, $_<A>._$<? extends R> fb){
        return baseZip(applicative, narrow(fa), narrow(fb));
    }

    @SuppressWarnings("RedundantCast")
    public static <T1, T2, R, A extends Applicative<A>> $_<A>._$<R>
    zip(A applicative, $_<A>._$<T1> fa, $_<A>._$<T2> fb,
                                       BiFunction<? super T1, ? super T2, ? extends R> f) {
        return zip(applicative, fa, fb).map(in -> (R) f.apply(in._1, in._2));
    }

    protected abstract class Val<V> extends Functor<A>.Val<V> {
        public abstract <R> $_<A>._$<R> ap($_<A>._$<? extends Function<? super V, ? extends R>> fn);
    }
}
