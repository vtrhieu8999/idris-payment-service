package com.example.demo.service.factory.testEnum.MonadicPrimitive.Kind3;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import lombok.experimental.ExtensionMethod;

import java.util.function.BiFunction;
import java.util.function.Function;

@FunctionalInterface
public interface Applicative<A extends Applicative<A>> extends Functor<A>, Kind.Pure<A> {
    @ExtensionMethod({Functor.Util.class})
    class Util{
        private static <T, A extends Applicative<A>>
        Ap<A, T> narrow(Kind.Val<A, ? extends T> instance){
            return (Ap<A, T>) Functor.Util.<T, A>narrowed(instance);
        }
        public static <T, R, A extends Applicative<A>>
        Val<A, R> ap(Val<A, T> apply, Val<A, ? extends Function<? super T, ? extends R>> fn){
            return narrow(apply).ap(narrow(fn));
        }
        public static <T1, T2, A extends Applicative<A>>
        Val<A, Tuple2<T1, T2>> zip(A applicative, Val<A, ? extends T1> fa, Val<A, ? extends T2> fb){
            return ap(fb,ap(fa, applicative.<Function<T1, Function<T2, Tuple2<T1, T2>>>>unit(() -> a -> b -> Tuple.of(a, b))));
        }
        @SuppressWarnings({"RedundantCast", "RedundantSupress"})
        public static <T1, T2, R, A extends Applicative<A>>
        Val<A, R> zip(A a, Val<A, ? extends T1> fa, Val<A, ? extends T2> fb,
                      BiFunction<? super T1, ? super T2, ? extends R> f){
            return zip(a, fa, fb).map(in -> (R) f.apply(in._1, in._2));
        }
    }

    interface Ap<A extends Applicative<A>, V> extends Transformable<A, V>{
        <R> Ap<A, R> ap(Ap<A, ? extends Function<? super V, ? extends R>> fn);
    }
}
