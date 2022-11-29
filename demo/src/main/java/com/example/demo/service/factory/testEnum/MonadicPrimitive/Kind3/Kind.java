package com.example.demo.service.factory.testEnum.MonadicPrimitive.Kind3;

import java.util.function.*;

public interface Kind<K extends Kind<K>> {
    interface Val<K extends Kind<K>, V>{}
    interface IntWrapper<K extends Kind<K>> extends Val<K, IntWrapper<K>>{}
    interface ToIntWrapper<K extends Kind<K>, T> extends Function<T, IntWrapper<K>>{}
    interface IntToIntWrapper<K extends Kind<K>> extends IntFunction<IntWrapper<K>>{}
    interface IntToDoubleWrapper<K extends Kind<K>> extends IntFunction<DoubleWrapper<K>>{}
    interface IntToLongWrapper<K extends Kind<K>> extends IntFunction<LongWrapper<K>>{}
    interface DoubleWrapper<K extends Kind<K>> extends Val<K, DoubleWrapper<K>>{}
    interface ToDoubleWrapper<K extends Kind<K>, T> extends Function<T, DoubleWrapper<K>>{}
    interface DoubleToIntWrapper<K extends Kind<K>> extends DoubleFunction<IntWrapper<K>>{}
    interface DoubleToDoubleWrapper<K extends Kind<K>> extends DoubleFunction<DoubleWrapper<K>>{}
    interface DoubleToLongWrapper<K extends Kind<K>> extends DoubleFunction<LongWrapper<K>>{}
    interface LongWrapper<K extends Kind<K>> extends Val<K, LongWrapper<K>>{}
    interface ToLongWrapper<K extends Kind<K>, T> extends Function<T, LongWrapper<K>>{}
    interface LongToIntWrapper<K extends Kind<K>> extends LongFunction<IntWrapper<K>>{}
    interface LongToDoubleWrapper<K extends Kind<K>> extends LongFunction<DoubleWrapper<K>>{}
    interface LongToLongWrapper<K extends Kind<K>> extends LongFunction<LongWrapper<K>>{}
    interface NestedVal<K1 extends Kind<K1>, K2 extends Kind<K2>, V> extends Val<K1, Val<K2, V>>{

    }
    @SuppressWarnings("unchecked")
    @Deprecated
    default K cast(){return (K) this;}
    @FunctionalInterface
    interface Pure<K extends Kind<K>&Pure<K>>{
        <T> Val<K, T> unit(Supplier<? extends T> value);
    }
    interface NaturalTransformation<K1 extends Kind<K1>, K2 extends Kind<K2>>{
        <T> Val<K2, T> apply(Val<K1, ? extends T> a);
        default <T> Function<Val<K1, ? extends T>, Val<K2, T>> asFunction(){
            return this::apply;
        }
        default <K3 extends Kind<K3>> NaturalTransformation<K1, K3> andThen(NaturalTransformation<K2, K3> after){
            return new NaturalTransformation<K1, K3>() {
                @Override
                public <T> Val<K3, T> apply(Val<K1, ? extends T> a) {
                    return after.apply(NaturalTransformation.this.apply(a));
                }
            };
        }
    }

    interface MonadTransformer<OUT extends Monad<OUT>, IN extends Monad<IN>>{
        <T, R> Val<OUT, Val<IN, R>> bind(Val<OUT, ? extends Val<IN, T>> a, Function<? super T, ? extends Val<OUT, ? extends Val<IN, ? extends R>>> f);
        default <T, R> Val<OUT, Val<IN, R>> fMap(Val<OUT, ? extends Val<IN, T>> a, Function<? super T, ? extends R> mapper){
            return Functor.Util.map(a, in -> Functor.Util.map(in, mapper));
        }
    }
    interface Kind2<K1 extends Kind<K1>, K2 extends Kind<K2>> extends Kind<Kind2<K1, K2>>{
        interface Val2<K1 extends Kind<K1>, K2 extends Kind<K2>, T> extends Val<Kind2<K1, K2>, T>{
            Val<K1, Val<K2, T>> convert();
        }
        interface NestedVal<K1 extends Kind<K1>, K2 extends Kind<K2>, T> extends Val<K1, Val<K2, T>>{
            Val<Kind2<K1, K2>, T> convert();
        }
    }
}
