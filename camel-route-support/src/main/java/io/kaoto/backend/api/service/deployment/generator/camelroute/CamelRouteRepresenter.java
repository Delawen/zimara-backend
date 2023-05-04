package io.kaoto.backend.api.service.deployment.generator.camelroute;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.kaoto.backend.model.deployment.camelroute.Bean;
import io.kaoto.backend.model.deployment.camelroute.CamelRoute;
import io.kaoto.backend.model.deployment.rest.HttpVerb;
import io.kaoto.backend.model.deployment.rest.Rest;
import io.kaoto.backend.model.deployment.rest.RestParameter;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.introspector.BeanAccess;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;

public class CamelRouteRepresenter extends IntegrationRepresenter {


    public CamelRouteRepresenter() {
        super();
        this.getPropertyUtils().setBeanAccess(BeanAccess.FIELD);

        this.multiRepresenters.put(CamelRoute.class,
                new RepresentMap() {
                    @Override
                    public Node representData(final Object data) {
                        CamelRoute route = (CamelRoute) data;
                        List<Object> properties = new ArrayList<>();
                        if (route.getFlows() != null) {
                            properties.addAll(route.getFlows());
                        }
                        if (route.getBeans() != null) {
                            Map<String, Object> beans = new LinkedHashMap<>();
                            beans.put("beans", route.getBeans());
                            properties.add(beans);
                        }
                        return representSequence(getTag(Object.class, Tag.SEQ),
                                properties,
                                DumperOptions.FlowStyle.BLOCK);
                    }
                });
        this.multiRepresenters.put(Bean.class,
                new RepresentMap() {
                    @Override
                    public Node representData(final Object data) {
                        return representMapping(getTag(data.getClass(), Tag.MAP),
                                ((Bean) data).getRepresenterProperties(),
                                DumperOptions.FlowStyle.AUTO);
                    }
                });
        this.multiRepresenters.put(Rest.class,
                new RepresentMap() {
                    @Override
                    public Node representData(final Object data) {
                        return representMapping(getTag(data.getClass(), Tag.MAP),
                                ((Rest) data).getRepresenterProperties(),
                                DumperOptions.FlowStyle.AUTO);
                    }
                });
        this.multiRepresenters.put(HttpVerb.class,
                new RepresentMap() {
                    @Override
                    public Node representData(final Object data) {
                        return representMapping(getTag(data.getClass(), Tag.MAP),
                                ((HttpVerb) data).getRepresenterProperties(),
                                DumperOptions.FlowStyle.AUTO);
                    }
                });
        this.multiRepresenters.put(RestParameter.class,
                new RepresentMap() {
                    @Override
                    public Node representData(final Object data) {
                        return representMapping(getTag(data.getClass(), Tag.MAP),
                                ((RestParameter) data).getRepresenterProperties(),
                                DumperOptions.FlowStyle.AUTO);
                    }
                });
    }
}
