package com.example.demo.service.factory.testEnum.MonadicPrimitive.Kind2.free;

import com.example.demo.service.factory.testEnum.MonadicPrimitive.Kind2.prototype.$_;
import com.example.demo.service.factory.testEnum.MonadicPrimitive.Kind2.prototype.Applicative;
import com.example.demo.service.factory.testEnum.MonadicPrimitive.Kind2.prototype.Functor;
import lombok.AllArgsConstructor;

import java.util.function.Function;
import java.util.function.Supplier;

@AllArgsConstructor
public abstract class FreeAp<F extends Functor<F>> extends Applicative<FreeAp<F>> {
    private final Functor<F> functor;

    @Override
    public <T> $_<FreeAp<F>>._$<T> unit(Supplier<? extends T> value) {
        return new Pure<>(value.get());
    }

    private <T> FreeAp<F>.__<T> narrow($_<FreeAp<F>>._$<T> value) {return (FreeAp<F>.__<T>) value;}

    abstract class __<T> extends Applicative<FreeAp<F>>.Val<T> {
        public abstract <R> R fold(Function<? super T, ? extends R> pure, Function<? super Ap<?, T>, ? extends R> ap);
    }

    @AllArgsConstructor
    class Pure<A> extends FreeAp<F>.__<A> {
        private final A a;

        @Override
        public <R> Functor<FreeAp<F>>.Val<R> fMap(Function<? super A, ? extends R> mapper) {
            return new Pure<>(mapper.apply(a));
        }

        @Override
        public <R> R fold(Function<? super A, ? extends R> pure, Function<? super Ap<?, A>, ? extends R> ap ) {
            return pure.apply(a);
        }

        @Override
        public <R> $_<FreeAp<F>>._$<R> ap($_<FreeAp<F>>._$<? extends Function<? super A, ? extends R>> fn) {
            return narrow(fn).fMap(f -> f.apply(a));
        }
    }

    @AllArgsConstructor
    class Ap<P, A> extends FreeAp<F>.__<A> {
        public final Functor<F>.Val<P> pivot;
        public final __<Function<? super P, A>> fn;

        private Ap($_<F>._$<P> pivot, $_<FreeAp<F>>._$<Function<? super P, A>> fn) {
            this.pivot = (Functor<F>.Val<P>) pivot;
            this.fn = narrow(fn);
        }

        @Override
        public <R> Functor<FreeAp<F>>.Val<R> fMap(Function<? super A, ? extends R> mapper) {
            return new Ap<>(pivot, fn.fMap(f -> f.andThen(mapper)));
        }

        @Override
        public <R> R fold(Function<? super A, ? extends R> pure, Function<? super Ap<?, A>, ? extends R> ap) {
            return ap.apply(this);
        }

        @Override
        public <R> $_<FreeAp<F>>._$<R> ap($_<FreeAp<F>>._$<? extends Function<? super A, ? extends R>> fn) {
            return new Ap<>(pivot, this.fn.ap(
                    narrow(fn).fMap(
                            f -> p -> p.andThen(f)
                    )
            ));
        }
    }
}
