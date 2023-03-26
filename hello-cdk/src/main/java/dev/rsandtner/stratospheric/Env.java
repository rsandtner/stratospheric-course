package dev.rsandtner.stratospheric;

import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;

public final class Env {

    private Env() {
    }

    public static Environment of(App app) {
        return Environment.builder()
                .account(getParam("accountId", app))
                .region(getParam("region", app))
                .build();
    }

    public static String getParam(String name, App app) {

        var value = app.getNode().tryGetContext(name);
        if (!(value instanceof String s) || s.isEmpty()) {
            throw new IllegalStateException(name + " must not be empty");
        }

        return s;
    }
}
