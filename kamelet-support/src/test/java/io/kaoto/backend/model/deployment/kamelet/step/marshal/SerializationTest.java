package io.kaoto.backend.model.deployment.kamelet.step.marshal;

import io.kaoto.backend.api.service.deployment.generator.kamelet.KameletConstructor;
import io.kaoto.backend.api.service.deployment.generator.kamelet.KameletRepresenter;
import io.kaoto.backend.model.deployment.kamelet.step.MarshalFlowStep;
import io.kaoto.backend.model.deployment.kamelet.step.dataFormat.DataFormat;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.Yaml;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class SerializationTest {


    @Test
    public void yaml() {
        Yaml yaml = new Yaml(
                new KameletConstructor(),
                new KameletRepresenter());

        var step = getMarshalFlowStep();
        var yamlstring = yaml.dumpAsMap(step);
        compareMarshalSteps(step, yaml.load(yamlstring));

        step = getMarshalFlowStep2();
        yamlstring = yaml.dumpAsMap(step);
        compareMarshalSteps(step, yaml.load(yamlstring));
    }

    private void compareMarshalSteps(final MarshalFlowStep step,
                                     final MarshalFlowStep other) {
        DataFormat dataFormat1 = step.getDataFormat();
        DataFormat dataFormat2 = other.getDataFormat();
        assertEquals(dataFormat1.getFormat(), dataFormat2.getFormat());

        assertTrue(dataFormat1.getProperties().keySet().stream()
                .allMatch(k -> dataFormat2.getProperties().containsKey(k)));

        assertTrue(dataFormat2.getProperties().keySet().stream()
                .allMatch(k -> dataFormat1.getProperties().containsKey(k)));
    }

    private MarshalFlowStep getMarshalFlowStep() {
        MarshalFlowStep step = new MarshalFlowStep();
        step.setDataFormat(new DataFormat());
        step.getDataFormat().setFormat("json");
        step.getDataFormat().setProperties(new HashMap<>());
        step.getDataFormat().getProperties().put("library", "Gson");
        return step;
    }

    private MarshalFlowStep getMarshalFlowStep2() {
        MarshalFlowStep step = new MarshalFlowStep();
        step.setDataFormat(new DataFormat());
        step.getDataFormat().setFormat("json");
        step.getDataFormat().setProperties(new HashMap<>());
        step.getDataFormat().getProperties().put("library", "Jackson");
        step.getDataFormat().getProperties().put("unmarshalType",
                "com.fasterxml.jackson.databind.JsonNode");
        step.getDataFormat().getProperties().put("schemaResolver", "#class"
                + ":org.apache.camel.kamelets.utils.serialization"
                + ".InflightProtobufSchemaResolver");
        return step;
    }
}
