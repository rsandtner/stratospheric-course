package dev.rsandtner.stratospheric;

import dev.rsandtner.stratospheric.construct.DockerRepository;
import software.amazon.awscdk.App;

public class DockerRepositoryApp {

    public static void main(String[] args) {

        var app = new App();

        var stack = StackCreator.createStack("DockerRepositoryStack", Env.of(app), app);
        DockerRepository.create("hello-world-app", Env.of(app).getAccount(), false, stack);

        app.synth();
    }

}
