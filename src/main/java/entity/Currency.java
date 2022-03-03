package entity;

public enum Currency {
    RUB(1),
    USD(113.2),
    EUR(123.2);

    private final double rate;

    Currency(final double rate) {
        this.rate = rate;
    }

    public double convert(final double amount, final Currency from,  final Currency to) {
        return amount * from.rate / to.rate;
    }
}
