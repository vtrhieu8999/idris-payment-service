package com.example.demo.service.factory.testEnum.MonadicPrimitive.Kind2.prototype;

import lombok.experimental.ExtensionMethod;

import java.util.function.Function;

@ExtensionMethod({Functor.class, Applicative.class})
public abstract class Monad<M extends Monad<M>> extends Applicative<M>{

    public static <T, M extends Monad<M>> $_<M>._$<T> join($_<M>._$<? extends $_<M>._$<? extends T>> nested){
        return narrow(nested).bind(Function.identity());
    }

    public static <T, R, M extends Monad<M>> $_<M>._$<R> flatMap($_<M>._$<T> a, Function<? super T, $_<M>._$<? extends R>> fa){
        return narrow(a).bind(fa);
    }

    protected static <T, M extends Monad<M>> Monad<M>.Val<T> narrow($_<M>._$<? extends T> value){
        return (Monad<M>.Val<T>) Functor.<T, M>narrowed(value);
    }

    protected abstract class Val<V> extends Applicative<M>.Val<V>{
        public abstract <R> $_<M>._$<R> bind(Function<? super V, ? extends $_<M>._$<? extends R>> mapper);

        @Override
        public <R> $_<M>._$<R> ap($_<M>._$<? extends Function<? super V, ? extends R>> fn) {
            return bind(v -> narrow(fn).fMap(f -> f.apply(v)));
        }
    }
}
