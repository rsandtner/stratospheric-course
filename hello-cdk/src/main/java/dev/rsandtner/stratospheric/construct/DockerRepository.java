package dev.rsandtner.stratospheric.construct;

import org.jetbrains.annotations.NotNull;
import software.amazon.awscdk.RemovalPolicy;
import software.amazon.awscdk.services.ecr.LifecycleRule;
import software.amazon.awscdk.services.ecr.Repository;
import software.amazon.awscdk.services.ecr.RepositoryProps;
import software.amazon.awscdk.services.ecr.TagMutability;
import software.amazon.awscdk.services.iam.AccountPrincipal;
import software.constructs.Construct;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class DockerRepository extends Construct {

    private DockerRepository(String name, String account, boolean keepImages, @NotNull Construct scope) {
        super(scope, id(name));

        var props = RepositoryProps.builder()
                .repositoryName(name)
                .imageTagMutability(TagMutability.IMMUTABLE)
                .imageScanOnPush(true)
                .lifecycleRules(List.of(LifecycleRule.builder()
                        .rulePriority(1)
                        .maxImageCount(10)
                        .description("keep max 10 images")
                        .build()));

        setRemovalPolicy(keepImages, props);

        new Repository(this, id(name), props.build())
                .grantPullPush(new AccountPrincipal(requireNonNull(account)));
    }

    public static DockerRepository create(String name, String account, boolean keepImages, Construct scope) {
        return new DockerRepository(name, account, keepImages, scope);
    }

    private static void setRemovalPolicy(boolean keepImages, RepositoryProps.Builder props) {

        if (keepImages) {
            props.removalPolicy(RemovalPolicy.RETAIN);
            return;
        }
        
        props.removalPolicy(RemovalPolicy.DESTROY)
                .autoDeleteImages(true);

    }

    private static String id(String name) {
        return name + "Repository";
    }
}
