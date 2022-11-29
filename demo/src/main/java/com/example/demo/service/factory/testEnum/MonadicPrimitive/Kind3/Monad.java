package com.example.demo.service.factory.testEnum.MonadicPrimitive.Kind3;

import lombok.experimental.ExtensionMethod;

import java.util.function.*;

public interface Monad<M extends Monad<M>> extends Applicative<M> {
    Bindable.MonadicInteger<M> unit(Integer a);
    @ExtensionMethod({Functor.Util.class})
    class Util{
        private static <T, M extends Monad<M>> Bindable<M, T> narrow(Kind.Val<M, ? extends T> instance) {
            return (Bindable<M, T>) Functor.Util.<T, M>narrowed(instance);
        }
        @SuppressWarnings({"RedundantCast", "RedundantSupress"})
        private static <T, R, M extends Monad<M>> Val<M, R> ap(Val<M, T> apply, Val<M, ? extends Function<? super T, ? extends R>> fn) {
            return flatMap(apply, t -> fn.map(f -> (R) f.apply(t)));
        }
        public static <T, R, M extends Monad<M>> Val<M, R> flatMap(Val<M, T> a, Function<? super T, ? extends Val<M, ? extends R>> f){
            return narrow(a).bind(t -> narrow(f.apply(t)));
        }
        public static <T, M extends Monad<M>> Val<M, T> join(Val<M, ? extends Val<M, ? extends T>> nested){
            return flatMap(nested, Function.identity());
        }
    }

    interface Bindable<M extends Monad<M>, T> extends Ap<M, T>{
        <R> Bindable<M, R> bind(Function<? super T, ? extends Bindable<M, ? extends R>> mapper);

        @Override
        default <R> Bindable<M, R> ap(Ap<M, ? extends Function<? super T, ? extends R>> fn) {
            return Monad.Util.narrow(Monad.Util.ap(this, fn));
        }

        interface MonadicInteger<M extends Monad<M>> extends Bindable<M, Integer>{
            <R> Bindable<M, R> fMap(IntFunction<? extends R> func);
            <R> Bindable<M, R> bind(IntFunction<? extends Bindable<M, ? extends R>> func);
            MonadicLong<M> fMap(IntToLongFunction func);
            MonadicDouble<M> fMap(IntToDoubleFunction func);
            MonadicBoolean<M> fMap(IntPredicate func);
            MonadicInteger<M> fMap(IntUnaryOperator func);
        }
        interface MonadicDouble<M extends Monad<M>> extends Bindable<M, Double>{}
        interface MonadicLong<M extends Monad<M>> extends Bindable<M, Long>{}
        interface MonadicCharacter<M extends Monad<M>> extends Bindable<M, Character>{}
        interface MonadicBoolean<M extends Monad<M>> extends Bindable<M, Boolean>{}
    }
}
