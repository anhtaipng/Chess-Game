package vn.gihot.chess.master.utilclass;

import java.util.Objects;

public final class Pair<T, U> {
    public final T first;
    public final U second;

    public Pair(T first, U second) {
        this.second = second;
        this.first = first;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return first.equals(pair.first) && second.equals(pair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    public String toString() {
        return "(" + this.first + ", " + this.second + ")";
    }
}
