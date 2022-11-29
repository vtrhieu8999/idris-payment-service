package com.example.demo.service.factory;

import io.vavr.Function1;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Pattern<T extends Content<T>> {
    private List<Function1<T, StringBuilder>> pattern;

    public Pattern(Class<T> dto) {
        this.pattern =
                Arrays.stream(dto.getFields())
                .filter(field -> !field.getName().contains("sign"))
                .map(field -> Function1.of(
                        (T instance) -> instance.fieldAccessor(field)
                        )
                ).collect(Collectors.toList())
        ;
    }

    public String get(T instance) {
        return this.pattern.stream()
                .map(getter -> getter.apply(instance))
                .reduce((a, b) -> a.append(b))
                .get().toString();
    }
}
