package com.example.demo.service.factory.testEnum.MonadicPrimitive.Kind3;

import java.util.function.*;

@SuppressWarnings("unused")
public interface Functor<F extends Functor<F>> extends Kind<F> {
    class Util{
        @SuppressWarnings("unchecked")
        static <T, F extends Functor<F>> Transformable<F, T> narrowed(Kind.Val<F, ? extends T> a){
            return (Transformable<F, T>) a;
        }
        private static <T, F extends Functor<F>> Transformable<F, T> narrow(Kind.Val<F, T> a){
            return (Transformable<F, T>) a;
        }
        @Deprecated
        private static <T, F extends Functor<F>> Transformable<F, T> narrowDeprecated(Kind.Val<F, ? extends T> a){
            return narrow(a).fMap(Function.identity());
        }
        public static <T, R, F extends Functor<F>> Val<F, R> map(Val<F, T> a, Function<? super T, ? extends R> mapper){
            return narrowed(a).fMap(mapper);
        }
        public static <T, F extends Functor<F>> Val<F, T> peek(Val<F, ? extends T> src, Consumer<? super T> fn){
            return map(src, t -> {fn.accept(t); return t;});
        }
        public static <T, R, F extends Functor<F>> Function<Val<F, T>, Val<F, R>> lift(Function<? super T, ? extends R> f){
            return t -> map(t, f);
        }
    }

    interface Transformable<F extends Functor<F>, T> extends Val<F, T>{
        <R> Transformable<F, R> fMap(Function<? super T, ? extends R> mapper);
        IntF<F> fMap(ToIntFunction<? super T> mapper);
        LongF<F> fMap(ToLongFunction<? super T> mapper);
        DoubleF<F> fMap(ToDoubleFunction<? super T> mapper);
    }

    interface IntF<F extends Functor<F>> extends Transformable<F, IntWrapper<F>>, IntWrapper<F>{
        <R> Transformable<F, R> fMap(IntFunction<? extends R> mapper);
        IntF<F> fMap(IntUnaryOperator mapper);
        LongF<F> fMap(IntToLongFunction mapper);
        DoubleF<F> fMap(IntToDoubleFunction mapper);
    }

    interface LongF<F extends Functor<F>> extends Transformable<F, LongWrapper<F>>, LongWrapper<F>{
        <R> Transformable<F, R> fMap(LongFunction<? extends R> mapper);
        IntF<F> fMap(LongToIntFunction mapper);
        LongF<F> fMap(LongUnaryOperator mapper);
        DoubleF<F> fMap(LongToDoubleFunction mapper);
    }

    interface DoubleF<F extends Functor<F>> extends Transformable<F, DoubleWrapper<F>>, DoubleWrapper<F>{
        <R> Transformable<F, R> fMap(DoubleFunction<? extends R> mapper);
        IntF<F> fMap(DoubleToIntFunction mapper);
        LongF<F> fMap(DoubleToLongFunction mapper);
        DoubleF<F> fMap(DoubleUnaryOperator mapper);
    }
}
