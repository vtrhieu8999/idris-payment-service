package com.example.demo.service.factory.testEnum.MonadicPrimitive.Kind2.prototype;

import java.util.function.Consumer;
import java.util.function.Function;

public abstract class Functor<F extends Functor<F>> extends $_<F> {

    @SuppressWarnings("unchecked")
    public static <T, F extends Functor<F>> Functor<F>.Val<T> narrowed($_<F>._$<? extends T> instance){
        return (Functor<F>.Val<T>) instance;
    }

    @Deprecated
    static <T, F extends Functor<F>> Functor<F>.Val<T> narrowDeprecated($_<F>._$<? extends T> instance){
        return narrow(instance).fMap(Function.identity());
    }

    private static <T, F extends Functor<F>> Functor<F>.Val<T> narrow($_<F>._$<T> instance){
        return (Functor<F>.Val<T>) instance;
    }

    public static <T, R, F extends Functor<F>> $_<F>._$<R> map($_<F>._$<T> a, Function<? super T, ? extends R> fa){
        return narrowed(a).fMap(fa);
    }

    public static <T, F extends Functor<F>> $_<F>._$<T> peek($_<F>._$<T> src, Consumer<? super T> fn) {
        return map(src,
                t -> {
                    fn.accept(t);
                    return t;
                }
        );
    }

    public static <T, R, F extends Functor<F>> Function<$_<F>._$<T>, $_<F>._$<R>> lift(Function<? super T, ? extends R> func) {
        return t -> map(t, func);
    }

    protected abstract class Val<V> extends $_<F>._$<V> implements $_.Transformable<V>{
        @Override
        public abstract <R> Functor<F>.Val<R> fMap(Function<? super V, ? extends R> mapper);
    }
}
