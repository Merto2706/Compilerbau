package de.thm.mni.compilerbau.utils;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.Supplier;

public class Try<T> {
    T value;
    Exception exception;

    private Try(T value, Exception exception) {
        this.value = value;
        this.exception = exception;
    }

    /**
     * Execute a block in a try-catch environment and wrap the result in a Try object.
     *
     * @param f   The block to execute.
     * @param <T> The type of the value calculated by the given block.
     * @return A Try object containing either the value or the thrown exception.
     */
    public static <T> Try<T> execute(Callable<T> f) {
        try {
            return new Try<>(f.call(), null);
        } catch (Exception e) {
            return new Try<>(null, e);
        }
    }

    /**
     * Whether this Try contains a value.
     *
     * @return
     */
    public boolean isDefined() {
        return this.exception == null;
    }

    /**
     * @return An empty Optional if this Try contains an exception, a filled Optional otherwise.
     */
    public Optional<T> toOptional() {
        return this.isDefined() ? Optional.of(this.value) : Optional.empty();
    }

    /**
     * @param f A supplier for an alternative value
     * @return The value of this Try if it is defined, the supplied value otherwise.
     */
    public T getOrElse(Supplier<T> f) {
        return this.isDefined() ? this.value : f.get();
    }

    /**
     * @param t An alternative value
     * @return The value of this Try if it is defined, the supplied value otherwise.
     */
    public T getOrElse(T t) {
        return this.isDefined() ? this.value : t;
    }

    /**
     * Maps the content of this Try with the given function and returns a new try.
     *
     * @param f   The mapping function.
     * @param <U> The resulting type of the mapping function.
     * @return The mapped Try.
     */
    public <U> Try<U> map(Function<T, U> f) {
        return this.isDefined() ? new Try<>(f.apply(this.value), null) : new Try<>(null, this.exception);
    }
}
