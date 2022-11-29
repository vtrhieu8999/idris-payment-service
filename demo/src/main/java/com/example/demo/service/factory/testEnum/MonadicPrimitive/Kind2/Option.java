package com.example.demo.service.factory.testEnum.MonadicPrimitive.Kind2;

import com.example.demo.service.factory.testEnum.MonadicPrimitive.Kind2.prototype.$_;
import com.example.demo.service.factory.testEnum.MonadicPrimitive.Kind2.prototype.Monad;
import lombok.var;

import java.util.function.Function;
import java.util.function.Supplier;

public class Option extends Monad<Option> {
    public static final Option instance = new Option();
    @Override
    public <T> Value<T> unit(Supplier<? extends T> value) {
        var val = value.get();
        return val != null ? new Some<>(val) : none();
    }

    public <T> Some<T> some(T value){
        return new Some<>(value);
    }

    private <T> Value<T> narrowVal($_<Option>._$<? extends T> value){
        return (Value<T>) Monad.<T, Option>narrow(value);
    }

    public <T> boolean isEmpty($_<Option>._$<? extends T> value){
        return narrowVal(value).isEmpty();
    }

    public <T> T getOrElse($_<Option>._$<? extends T> value, Supplier<? extends T> alter){
        return this.<T>narrowVal(value).getOrElse(alter);
    }

    public <T, R> R fold($_<Option>._$<? extends T> value, Supplier<? extends R> alter, Function<? super T, ? extends R> mapper){
        return narrowVal(value).fold(alter, mapper);
    }

    public abstract class Value<V> extends Monad<Option>.Val<V>{
        @Override
        public abstract <R> Value<R> fMap(Function<? super V, ? extends R> mapper);
        @Override
        public abstract <R> Value<R> bind(Function<? super V, ? extends $_<Option>._$<? extends R>> mapper);
        public abstract boolean isEmpty();
        public abstract V getOrElse(Supplier<? extends V> alter);
        public abstract <R> R fold(Supplier<? extends R> alter, Function<? super V, ? extends R> mapper);
    }

    static final class Some<T> extends Value<T>{
        final T value;
        private Some(T value){
            Option.instance.super();
            this.value = value;
        }

        @Override
        public <R> Some<R> fMap(Function<? super T, ? extends R> mapper) {
            return new Some<>(mapper.apply(value));
        }

        @Override
        public <R> Value<R> bind(Function<? super T, ? extends $_<Option>._$<? extends R>> mapper) {
            return instance.narrowVal(mapper.apply(value));
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public T getOrElse(Supplier<? extends T> alter) {
            return value;
        }

        @Override
        public <R> R fold(Supplier<? extends R> alter, Function<? super T, ? extends R> mapper) {
            return mapper.apply(value);
        }
    }

    public final class None<T> extends Value<T>{
        private None(){}
        @Override
        public <R> None<R> fMap(Function<? super T, ? extends R> mapper) {
            return none();
        }

        @Override
        public <R> None<R> bind(Function<? super T, ? extends $_<Option>._$<? extends R>> mapper) {
            return none();
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public T getOrElse(Supplier<? extends T> alter) {
            return alter.get();
        }

        @Override
        public <R> R fold(Supplier<? extends R> alter, Function<? super T, ? extends R> mapper) {
            return alter.get();
        }
    }

    @SuppressWarnings("rawtypes")
    private final None none;
    @SuppressWarnings("rawtypes")
    private Option(){this.none = new None();}
    @SuppressWarnings("unchecked")
    public <T> None<T> none(){return (None<T>) none;}
}
