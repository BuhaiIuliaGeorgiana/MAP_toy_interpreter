package interpreter.model.exp;

public enum LogicOP {
    AND("and"), OR("or");

    private final String symbol;

    LogicOP(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}