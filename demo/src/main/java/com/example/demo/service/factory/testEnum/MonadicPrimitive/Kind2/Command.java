package com.example.demo.service.factory.testEnum.MonadicPrimitive.Kind2;

import com.example.demo.service.factory.testEnum.MonadicPrimitive.Kind2.prototype.Functor;
import lombok.AllArgsConstructor;

import java.util.function.Function;

public class Command extends Functor<Command> {

    public class Done<T> extends Functor<Command>.Val<T>{
        @Override
        public <R> Done<R> fMap(Function<? super T, ? extends R> mapper) {
            return new Done<>();
        }
    }

    @AllArgsConstructor
    public class Bell<T> extends Functor<Command>.Val<T>{
        private final T next;
        @Override
        public <R> Bell<R> fMap(Function<? super T, ? extends R> mapper) {
            return new Bell<>(mapper.apply(next));
        }
    }

    @AllArgsConstructor
    public class Output<T> extends Functor<Command>.Val<T>{
        private final char c;
        private final T next;
        @Override
        public <R> Output<R> fMap(Function<? super T, ? extends R> mapper) {
            return new Output<R>(c, mapper.apply(next));
        }
    }
}
