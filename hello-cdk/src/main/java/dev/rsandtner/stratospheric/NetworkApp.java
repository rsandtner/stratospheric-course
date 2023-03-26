package dev.rsandtner.stratospheric;

import dev.stratospheric.cdk.Network;
import software.amazon.awscdk.App;

public class NetworkApp {

    public static void main(String[] args) {

        var app = new App();

        var env = Env.of(app);
        var environmentName = Env.getParam("environment", app);
        
        var stack = StackCreator.createStack(environmentName + "-NetworkStack", env, app);
        
        new Network(stack,
                "Network",
                env,
                environmentName,
                new Network.NetworkInputParameters());

        app.synth();
    }
}
