package dev.rsandtner.stratospheric;

import dev.stratospheric.cdk.ApplicationEnvironment;
import dev.stratospheric.cdk.Network;
import dev.stratospheric.cdk.Service;
import software.amazon.awscdk.App;

import java.util.Map;

public class ServiceApp {

    public static void main(String[] args) {

        var app = new App();
        var env = Env.of(app);

        var envName = Env.getParam("environment", app);
        var appName = Env.getParam("applicationName", app);

        var stack = StackCreator.createStack(envName + "-ServiceStack", env, app);

        new Service(stack,
                "Service",
                env,
                new ApplicationEnvironment(appName, envName),
                new Service.ServiceInputParameters(
                        new Service.DockerImageSource(Env.getParam("imageName", app), Env.getParam("imageTag", app)),
                        Map.of())
                        .withDesiredInstances(1),
                Network.getOutputParametersFromParameterStore(stack, envName));

        app.synth();
    }
}
