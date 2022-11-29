package com.example.demo.service.factory;

import io.vavr.Function1;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import lombok.val;

import java.util.function.BiFunction;
import java.util.function.Function;

@FunctionalInterface
public interface State<S, A> extends Function1<S, Tuple2<S, A>>{

    default <B> State<S, B> flatMap(Function<A, State<S, B>> g) {
        return state1 -> {
            val state2 = apply(state1);
            return g.apply(state2._2).apply(state2._1);
        };
    }

    default <B> State<S, B> flatMap(BiFunction<A, S, Tuple2<S, B>> g) {
        return state1 -> {
            val state2 = apply(state1);
            return g.apply(state2._2, state2._1);
        };
    }

    default <B> State<S, B> map(Function<A, B> mapper) {
        return state -> apply(state).map2(mapper);
    }

    static <S> State<S, S> get() {
        return s -> Tuple.of(s, s);
    }

    static <S, U> State<S, U> pure(U a) {
        return s -> Tuple.of(s, a);
    }

    static <S> State<S, Void> modify(Function<S, S> f) {
        return s -> Tuple.of(f.apply(s), null);
    }

    static <S, T> State<S, T> inspect(Function<S, T> f) {
        return s -> Tuple.of(s, f.apply(s));
    }

    static <S> State<S, Void> set(S s) {
        return _void -> Tuple.of(s, null);
    }
}
