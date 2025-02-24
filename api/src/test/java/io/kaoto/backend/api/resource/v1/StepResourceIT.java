package io.kaoto.backend.api.resource.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.kaoto.backend.model.step.Step;
import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusIntegrationTest
class StepResourceIT {
    @Test
    void checkCatalogLoaded() throws Exception {
        ValidatableResponse response = RestAssured.given().get("/v1/steps").then().statusCode(200);
        
        String json = response.extract().body().asString();
        ObjectMapper objectMapper = new ObjectMapper();
        Step[] steps = objectMapper.readValue(json, Step[].class);
        assertThat(steps).isNotEmpty();
        Condition<Step> weHaveCamelConnectors =
                new Condition<>(value -> value.getKind().equalsIgnoreCase("Camel-Connector"),
                "We have camel connectors");
        Condition<Step> weHaveKamelets =
                new Condition<>(value -> value.getKind().equalsIgnoreCase("Kamelet"),
                        "We have kamelets.");
        Condition<Step> weHaveEIPs =
                new Condition<>(value -> value.getKind().equalsIgnoreCase("EIP"),
                        "We have EIPs.");
        assertThat(steps)
                .haveAtLeast(10, weHaveCamelConnectors)
                .haveAtLeast(10, weHaveKamelets)
                .haveAtLeast(10, weHaveEIPs);
    }

    @Test
    void checkAllRequiredPropertiesInitialized() throws Exception {
        ValidatableResponse response = RestAssured.given().get("/v1/steps").then().statusCode(200);

        String json = response.extract().body().asString();
        ObjectMapper objectMapper = new ObjectMapper();
        Step[] steps = objectMapper.readValue(json, Step[].class);

        assertThat(steps)
                .allMatch(step -> step.getRequired() != null);
    }
}
