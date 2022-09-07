package io.kaoto.backend.api.service.deployment.generator.kamelet;

import io.kaoto.backend.model.deployment.kamelet.step.MarshalFlowStep;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.BeanAccess;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;

public class KameletConstructor extends Constructor {

    public KameletConstructor() {
        super();
        configuration();
    }

    public KameletConstructor(final Class<? extends Object> theRoot) {
        super(theRoot);
        configuration();
    }

    private void configuration() {
        this.getPropertyUtils()
                .setSkipMissingProperties(true);
        this.getPropertyUtils()
                .setAllowReadOnlyProperties(true);
        this.getPropertyUtils()
                .setBeanAccess(BeanAccess.FIELD);
    }

    @Override
    protected Object constructObject(final Node node) {
        if (node instanceof MappingNode mappingNode) {
            for (var element : mappingNode.getValue()) {
                Node tentative = element.getKeyNode();
                if (tentative instanceof ScalarNode scalarNode) {
                    if ("marshal".equalsIgnoreCase(scalarNode.getValue())) {
                        return new MarshalFlowStep(
                                mappingNode.getValue().get(0).getValueNode());
                    }
                }
            }
        }
       return super.constructObject(node);
    }
}
