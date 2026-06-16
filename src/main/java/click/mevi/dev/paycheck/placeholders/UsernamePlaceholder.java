package click.mevi.dev.paycheck.placeholders;

import click.mevi.dev.PlaceholderAPI.api.objects.Placeholder;
import click.mevi.dev.PlaceholderAPI.api.objects.PlaceholderContext;

public class UsernamePlaceholder implements Placeholder {
    @Override
    public String identifier() {
        return "username";
    }

    @Override
    public String resolve(PlaceholderContext placeholderContext) {
        return placeholderContext.get("username").toString();
    }
}
