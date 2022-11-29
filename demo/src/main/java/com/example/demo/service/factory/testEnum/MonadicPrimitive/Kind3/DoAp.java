package com.example.demo.service.factory.testEnum.MonadicPrimitive.Kind3;

import io.vavr.*;
import lombok.AllArgsConstructor;
import lombok.experimental.ExtensionMethod;

import java.util.function.Function;
import java.util.function.Supplier;

@ExtensionMethod({Functor.Util.class, Applicative.Util.class})
public class DoAp<A extends Applicative<A>> {

    @AllArgsConstructor
    public class Do1<T1>{
        private final Supplier<? extends Kind.Val<A, ? extends T1>> f1;

        @AllArgsConstructor
        public class Do2<T2>{
            private final Function<Supplier<? super T1>, ? extends Kind.Val<A, ? extends T2>> f2;

            @AllArgsConstructor
            public class Do3<T3>{
                private final Function<Supplier<Tuple2<? super T1, ? super T2>>, ? extends Kind.Val<A, ? extends T3>> f3;

                @AllArgsConstructor
                public class Do4<T4>{
                    private final Function<Supplier<Tuple3<? super T1, ? super T2, ? super T3>>, ? extends Kind.Val<A, ? extends T4>> f4;

                    @AllArgsConstructor
                    public class Do5<T5>{
                        private final Function<Supplier<Tuple4<? super T1, ? super T2, ? super T3, ? super T4>>, ? extends Kind.Val<A, ? extends T5>> f5;

                        @AllArgsConstructor
                        public class Do6<T6>{
                            private final Function<Supplier<Tuple5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5>>, ? extends Kind.Val<A, ? extends T6>> f6;

                            @AllArgsConstructor
                            public class Do7<T7>{
                                private final Function<Supplier<Tuple6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6>>, ? extends Kind.Val<A, ? extends T7>> f7;

                                @AllArgsConstructor
                                public class Do8<T8>{
                                    private final Function<Supplier<Tuple7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7>>, ? extends Kind.Val<A, ? extends T8>> f8;

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
