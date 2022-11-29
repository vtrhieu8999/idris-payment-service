package com.example.demo.service.factory.testEnum.MonadicPrimitive.Kind2.prototype;

import io.vavr.*;
import lombok.AllArgsConstructor;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class Do<M extends Monad<M>> {
    private final Monad<M> monad;

    private Do(Monad<M> monad) {
        this.monad = monad;
    }

    public static <T extends Monad<T>> Do<T> forEach(Monad<T> monad) {
        return new Do<>(monad);
    }

    public <T> Do1<T> __(Supplier<? extends $_<M>._$<? extends T>> t1) {
        return new Do1<>(t1);
    }

    @AllArgsConstructor
    public class Do1<T1> {
        private final Supplier<? extends $_<M>._$<? extends T1>> f1;

        public <T> Do2<T> __(Function<Supplier<? super T1>, ? extends $_<M>._$<? extends T>> f) {
            return new Do2<>(f);
        }

        public <R> Do1<R> yield(Function<? super T1, ? extends R> f) {
            return new Do1<>(() -> Functor.map(f1.get(), f));
        }

        @AllArgsConstructor
        public class Do2<T2> {
            private final Function<Supplier<? super T1>, ? extends $_<M>._$<? extends T2>> f2;

            public <T> Do3<T> __(Function<Supplier<Tuple2<? super T1, ? super T2>>, ? extends $_<M>._$<? extends T>> f) {
                return new Do3<>(f);
            }

            public <R> Do1<R> yield(BiFunction<? super T1, ? super T2, ? extends R> f) {
                return new Do1<>(() ->
                        Monad.flatMap(f1.get(),
                                _1 -> Functor.map(f2.apply(() -> _1),
                                        _2 -> f.apply(_1, _2)
                                )
                        )
                );
            }

            @AllArgsConstructor
            public class Do3<T3> {
                private final Function<Supplier<Tuple2<? super T1, ? super T2>>, ? extends $_<M>._$<? extends T3>> f3;

                public <T> Do4<T> __(Function<Supplier<Tuple3<? super T1, ? super T2, ? super T3>>, ? extends $_<M>._$<? extends T>> f) {
                    return new Do4<>(f);
                }

                public <R> Do1<R> yield(Function3<? super T1, ? super T2, ? super T3, ? extends R> f) {
                    return new Do1<>(() ->
                            Monad.flatMap(f1.get(),
                                    _1 -> Monad.flatMap(f2.apply(() -> _1),
                                            _2 -> Functor.map(f3.apply(() -> Tuple.of(_1, _2)),
                                                    _3 -> f.apply(_1, _2, _3)
                                            )
                                    )
                            )
                    );
                }

                @AllArgsConstructor
                public class Do4<T4> {
                    private final Function<Supplier<Tuple3<? super T1, ? super T2, ? super T3>>, ? extends $_<M>._$<? extends T4>> f4;

                    public <T> Do5<T> __(Function<Supplier<Tuple4<? super T1, ? super T2, ? super T3, ? super T4>>, ? extends $_<M>._$<? extends T>> f) {
                        return new Do5<>(f);
                    }

                    public <R> Do1<R> yield(Function4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> f) {
                        return new Do1<>(() ->
                                Monad.flatMap(f1.get(),
                                        _1 -> Monad.flatMap(f2.apply(() -> _1),
                                                _2 -> Monad.flatMap(f3.apply(() -> Tuple.of(_1, _2)),
                                                        _3 -> Functor.map(f4.apply(() -> Tuple.of(_1, _2, _3)),
                                                                _4 -> f.apply(_1, _2, _3, _4)
                                                        )
                                                )
                                        )
                                )
                        );
                    }

                    @AllArgsConstructor
                    public class Do5<T5> {
                        private final Function<Supplier<Tuple4<? super T1, ? super T2, ? super T3, ? super T4>>, ? extends $_<M>._$<? extends T5>> f5;

                        public <T> Do6<T> __(Function<Supplier<Tuple5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5>>, ? extends $_<M>._$<? extends T>> f) {
                            return new Do6<>(f);
                        }

                        public <R> Do1<R> yield
                                (Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> f) {
                            return new Do1<>(() ->
                                    Monad.flatMap(f1.get(),
                                            _1 -> Monad.flatMap(f2.apply(() -> _1),
                                                    _2 -> Monad.flatMap(f3.apply(() -> Tuple.of(_1, _2)),
                                                            _3 -> Monad.flatMap(f4.apply(() -> Tuple.of(_1, _2, _3)),
                                                                    _4 -> Functor.map(f5.apply(() -> Tuple.of(_1, _2, _3, _4)),
                                                                            _5 -> f.apply(_1, _2, _3, _4, _5)
                                                                    )
                                                            )
                                                    )
                                            )
                                    )
                            );
                        }

                        @AllArgsConstructor
                        public class Do6<T6> {
                            private final Function<Supplier<Tuple5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5>>, ? extends $_<M>._$<? extends T6>> f6;

                            public <T> Do7<T> __(Function<Supplier<Tuple6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6>>, ? extends $_<M>._$<? extends T>> f) {
                                return new Do7<>(f);
                            }

                            public <R> Do1<R> yield
                                    (Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? extends R> f) {
                                return new Do1<>(() ->
                                        Monad.flatMap(f1.get(),
                                                _1 -> Monad.flatMap(f2.apply(() -> _1),
                                                        _2 -> Monad.flatMap(f3.apply(() -> Tuple.of(_1, _2)),
                                                                _3 -> Monad.flatMap(f4.apply(() -> Tuple.of(_1, _2, _3)),
                                                                        _4 -> Monad.flatMap(f5.apply(() -> Tuple.of(_1, _2, _3, _4)),
                                                                                _5 -> Functor.map(f6.apply(() -> Tuple.of(_1, _2, _3, _4, _5)),
                                                                                        _6 -> f.apply(_1, _2, _3, _4, _5, _6)
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                );
                            }

                            @AllArgsConstructor
                            public class Do7<T7> {
                                private final Function<Supplier<Tuple6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6>>, ? extends $_<M>._$<? extends T7>> f7;

                                public <T> Do8<T> __(Function<Supplier<Tuple7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7>>, ? extends $_<M>._$<? extends T>> f) {
                                    return new Do8<>(f);
                                }

                                public <R> Do1<R> yield
                                        (Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? extends R> f) {
                                    return new Do1<>(() ->
                                            Monad.flatMap(f1.get(),
                                                    _1 -> Monad.flatMap(f2.apply(() -> _1),
                                                            _2 -> Monad.flatMap(f3.apply(() -> Tuple.of(_1, _2)),
                                                                    _3 -> Monad.flatMap(f4.apply(() -> Tuple.of(_1, _2, _3)),
                                                                            _4 -> Monad.flatMap(f5.apply(() -> Tuple.of(_1, _2, _3, _4)),
                                                                                    _5 -> Monad.flatMap(f6.apply(() -> Tuple.of(_1, _2, _3, _4, _5)),
                                                                                            _6 -> Functor.map(f7.apply(() -> Tuple.of(_1, _2, _3, _4, _5, _6)),
                                                                                                    _7 -> f.apply(_1, _2, _3, _4, _5, _6, _7)
                                                                                            )
                                                                                    )
                                                                            )
                                                                    )
                                                            )
                                                    )
                                            )
                                    );
                                }

                                @AllArgsConstructor
                                public class Do8<T8> {
                                    private final Function<Supplier<Tuple7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7>>, ? extends $_<M>._$<? extends T8>> f8;

                                    public <R> Do1<R> yield
                                            (Function8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends R> f) {
                                        return new Do1<>(() ->
                                                Monad.flatMap(f1.get(),
                                                        _1 -> Monad.flatMap(f2.apply(() -> _1),
                                                                _2 -> Monad.flatMap(f3.apply(() -> Tuple.of(_1, _2)),
                                                                        _3 -> Monad.flatMap(f4.apply(() -> Tuple.of(_1, _2, _3)),
                                                                                _4 -> Monad.flatMap(f5.apply(() -> Tuple.of(_1, _2, _3, _4)),
                                                                                        _5 -> Monad.flatMap(f6.apply(() -> Tuple.of(_1, _2, _3, _4, _5)),
                                                                                                _6 -> Monad.flatMap(f7.apply(() -> Tuple.of(_1, _2, _3, _4, _5, _6)),
                                                                                                        _7 -> Functor.map(f8.apply(() -> Tuple.of(_1, _2, _3, _4, _5, _6, _7)),
                                                                                                                _8 -> f.apply(_1, _2, _3, _4, _5, _6, _7, _8)
                                                                                                        )
                                                                                                )
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        );
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
