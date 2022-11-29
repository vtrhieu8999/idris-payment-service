package com.example.demo.service.factory.testEnum.MonadicPrimitive.Kind2.prototype;

import com.example.demo.service.factory.testEnum.MonadicPrimitive.Kind2.Option;

import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public abstract class $_<K extends $_<K>> {
    @SuppressWarnings("InnerClassMayBeStatic")
    public abstract class _$<T> {
        public K getKind() {
            return cast();
        }
    }
    @SuppressWarnings("unchecked")
    public K cast() {
        return (K) this;
    }

    @FunctionalInterface
    public interface Pure<K extends $_<K> & $_.Pure<K>> {
        <T> $_<K>._$<T> unit(Supplier<? extends T> value);
    }

    @FunctionalInterface
    public interface Transformable<T> {
        <R> Transformable<R> fMap(Function<? super T, ? extends R> mapper);
    }

    @FunctionalInterface
    public interface NaturalTransformation<N1 extends $_<N1>, N2 extends $_<N2>> {
        <T> $_<N2>._$<T> apply($_<N1>._$<T> a);
        default <T> Function<$_<N1>._$<T>, $_<N2>._$<T>> asFunction() {return this::apply;}
        default <N3 extends $_<N3>> NaturalTransformation<N1, N3> andThen(NaturalTransformation<N2, N3> after) {
            return new NaturalTransformation<N1, N3>() {
                public <T> $_<N3>._$<T> apply($_<N1>._$<T> a) {
                    return after.apply(NaturalTransformation.this.apply(a));
                }
            };
        }

        default <N3 extends $_<N3>> NaturalTransformation<N3, N2> compose(NaturalTransformation<N3, N1> before) {
            return before.andThen(this);
        }

        static <N extends $_<N>> NaturalTransformation<N, N> identity() {
            return new NaturalTransformation<N, N>() {
                @Override
                public <T> $_<N>._$<T> apply($_<N>._$<T> a) {
                    return a;
                }
            };
        }
    }



    public interface MonadTransformer<I extends Monad<I>, O extends Monad<O>> {
        <T, R> $_<O>._$<$_<I>._$<R>> bind($_<O>._$<$_<I>._$<T>> a, Function<? super T, $_<O>._$<$_<I>._$<R>>> f);
        I getInnerMonad();
        O getOuterMonad();
        default <T, R> $_<O>._$<$_<I>._$<R>> fMap($_<O>._$<? extends $_<I>._$<T>> a, Function<? super T, R> mapper) {
            return Functor.map(a, it -> Functor.map(it, mapper));
        }

        @FunctionalInterface
        interface OptionT<M extends Monad<M>> extends MonadTransformer<Option, M> {
            @Override
            default Option getInnerMonad(){return Option.instance;}
            @Override
            default <T, R> $_<M>._$<$_<Option>._$<R>> bind($_<M>._$<$_<Option>._$<T>> a, Function<? super T, $_<M>._$<$_<Option>._$<R>>> f) {
                return Monad.flatMap(a, op -> getInnerMonad().fold(op, () -> getOuterMonad().unit(getInnerMonad()::none), f));
            }
        }
    }

}
