package utn.frc.backend.tpi.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConf {

        @Bean
        public RouteLocator configurarRutas(RouteLocatorBuilder builder,
                        @Value("${tpi-api-gw.microservicio-pedidos}") String uriPedidos,
                        @Value("${tpi-api-gw.microservicio-logistica}") String uriLogistica) {

                // Registra las rutas que usa para redireccioanr las peticiones a los
                // microservicios especificos.
                return builder.routes()
                                .route(p -> p
                                                .path("/api/pedidos/**")
                                                .filters(f -> f.stripPrefix(2))
                                                .uri(uriPedidos))
                                .route(p -> p
                                                .path("/api/logistica/**")
                                                .filters(f -> f.stripPrefix(2))
                                                .uri(uriLogistica))
                                .build();
        }
}
