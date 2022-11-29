package com.example.demo.service.factory.testEnum.MonadicPrimitive.Kind2.free;

import com.example.demo.service.factory.testEnum.MonadicPrimitive.Kind2.prototype.Functor;
import com.example.demo.service.factory.testEnum.MonadicPrimitive.Kind2.prototype.$_;
import com.example.demo.service.factory.testEnum.MonadicPrimitive.Kind2.prototype.Monad;
import io.vavr.control.Either;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.ExtensionMethod;

import java.util.function.Function;
import java.util.function.Supplier;

@ExtensionMethod({Functor.class})
@AllArgsConstructor
public abstract class FreeM<F extends Functor<F>>
        extends Monad<FreeM<F>>
        implements $_.NaturalTransformation<F, FreeM<F>> {
    private Functor<F> functor;

    @Override
    public <T> Free<T> unit(Supplier<? extends T> value) {return new Pure<>(value.get());}
    @Override
    public <T> Free<T> apply($_<F>._$<T> value) {return new Suspend<T>(value.map(this::done));}
    public final <T> Free<T> done(T t) {return new Pure<>(t);}
    public final <T> Free<T> suspend($_<F>._$<Free<T>> s) {
        return new Suspend<T>(s);
    }

    public abstract class Free<T> extends Monad<FreeM<F>>.Val<T> {
        @Override
        public final <R> Free<R> fMap(Function<? super T, ? extends R> mapper) {
            return bind(t -> new Pure<>(mapper.apply(t)));
        }

        public final Either<$_<F>._$<Free<T>>, T> resume() {
            return resumeInternal().fold(
                    left -> left.fold(Either::left, Either::right),
                    right -> null);
        }

        public final T go(Function<? super $_<F>._$<Free<T>>, Free<T>> fn) {
            Free<T> toUse = this;
            while (true) {
                Either<$_<F>._$<Free<T>>, T> xor = toUse.resume();
                if (xor.isRight()) return xor.get();
                else toUse = fn.apply(xor.getLeft());
            }
        }

        @Override
        public abstract <R> Free<R>
        bind(Function<? super T, ? extends $_<FreeM<F>>._$<? extends R>> mapper);

        public abstract <R> R fold(Function<? super Pure<T>, ? extends R> done,
                                   Function<? super Suspend<T>, ? extends R> suspend,
                                   Function<? super FlatMapped<?, T>, ? extends R> flatMapped);

        abstract Either<Either<$_<F>._$<Free<T>>, T>, Free<T>> resumeInternal();
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public class Pure<T> extends Free<T> {
        final T value;

        @Override
        public <R> FlatMapped<T, R>
        bind(Function<? super T,
                ? extends $_<FreeM<F>>._$<? extends R>> mapper) {
            return new FlatMapped<>(this, narrow(mapper));
        }

        @Override
        public <R> R fold(Function<? super Pure<T>, ? extends R> done,
                          Function<? super Suspend<T>, ? extends R> suspend,
                          Function<? super FlatMapped<?, T>, ? extends R> flatMapped) {
            return done.apply(this);
        }

        @Override
        Either<Either<$_<F>._$<Free<T>>, T>, Free<T>> resumeInternal() {
            return Either.left(
                    Either.right(value)
            );
        }
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public class Suspend<T> extends Free<T> {
        final $_<F>._$<Free<T>> suspended;

        @Override
        public <R> FlatMapped<T, R>
        bind(Function<? super T,
                ? extends $_<FreeM<F>>._$<? extends R>> mapper) {
            return new FlatMapped<>(this, narrow(mapper));
        }

        @Override
        public <R> R fold(Function<? super Pure<T>, ? extends R> done,
                          Function<? super Suspend<T>, ? extends R> suspend,
                          Function<? super FlatMapped<?, T>, ? extends R> flatMapped) {
            return suspend.apply(this);
        }

        @Override
        Either<Either<$_<F>._$<Free<T>>, T>, Free<T>> resumeInternal() {
            return Either.left(
                    Either.left(suspended)
            );
        }
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public class FlatMapped<IN, T> extends Free<T>{
        private final Free<IN> free;
        private final Function<? super IN, ? extends Free<T>> fn;

        @Override
        public <R> FlatMapped<IN, R>
        bind(Function<? super T,
                    ? extends $_<FreeM<F>>._$<? extends R>
                        > mapper) {
            return new FlatMapped<>(free,
                    aa -> new FlatMapped<T, R>(
                            fn.apply(aa), narrow(mapper)
                    )
            );
        }

        @Override
        public <R> R fold(Function<? super Pure<T>, ? extends R> done,
                          Function<? super Suspend<T>, ? extends R> suspend,
                          Function<? super FlatMapped<?, T>, ? extends R> flatMapped) {
            return flatMapped.apply(this);
        }

        @Override
        Either<Either<$_<F>._$<Free<T>>, T>, Free<T>> resumeInternal() {
            return resumeNestedFree().flatMap(Free::resumeInternal);
        }

        private Either<Either<$_<F>._$<Free<T>>, T>, Free<T>> resumeNestedFree() {
            return free.fold(
                    pure -> Either.right(fn.apply(pure.value)),
                    s -> Either.left(
                            Either.left(s.suspended.map(o -> o.bind(fn)))
                    ),
                    this::apply
            );
        }

        private <U> Either<Either<$_<F>._$<Free<T>>, T>, Free<T>>
        apply(FlatMapped<U, IN> flatMapped) {
            return Either.right(flatMapped.free.bind(o -> flatMapped.fn.apply(o).bind(fn)));
        }
    }

    private <T, R> Function<? super T, ? extends Free<R>>
    narrow(Function<? super T,
            ? extends $_<FreeM<F>>._$<? extends R>
            > mapper) {
        return t -> (Free<R>) Functor.<R, FreeM<F>>narrowed(mapper.apply(t));
    }
}
