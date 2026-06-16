package click.mevi.dev.paycheck.placeholders;

import click.mevi.dev.PlaceholderAPI.api.objects.Placeholder;
import click.mevi.dev.PlaceholderAPI.api.objects.PlaceholderContext;

public class MoneyAmountPlaceholder implements Placeholder {
    @Override
    public String identifier() {
        return "amount";
    }

    @Override
    public String resolve(PlaceholderContext placeholderContext) {
        return placeholderContext.get("amount").toString();
    }
}
