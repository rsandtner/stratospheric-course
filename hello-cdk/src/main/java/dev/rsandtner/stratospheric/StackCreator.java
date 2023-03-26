package dev.rsandtner.stratospheric;

import software.amazon.awscdk.*;

public class StackCreator {

    public static Stack createStack(String name, Environment env, App app) {

        var props = StackProps.builder()
                .synthesizer(new DefaultStackSynthesizer(DefaultStackSynthesizerProps.builder()
                        .fileAssetsBucketName("rsandtner.stratospheric.cdk")
                        .build()))
                .env(env)
                .build();

        return new Stack(app, name, props);
    }
}
