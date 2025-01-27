package org.lessons;

import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

/**
 * Перечисление возможных невалидных аргументов
 */
class CustomArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(org.junit.jupiter.api.extension.ExtensionContext context) {
        return Stream.of(
                Arguments.of(Double.NaN, 1, 1),
                Arguments.of(1, Double.NaN, 1),
                Arguments.of(1, 1, Double.NaN),
                Arguments.of(Double.POSITIVE_INFINITY, 1, 1),
                Arguments.of(1, Double.POSITIVE_INFINITY, 1),
                Arguments.of(1, 1, Double.POSITIVE_INFINITY),
                Arguments.of(Double.NEGATIVE_INFINITY, 1, 1),
                Arguments.of(1, Double.NEGATIVE_INFINITY, 1),
                Arguments.of(1, 1, Double.NEGATIVE_INFINITY)
        );
    }
}
