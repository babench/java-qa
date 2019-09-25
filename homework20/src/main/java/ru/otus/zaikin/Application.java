package ru.otus.zaikin;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import com.github.tomakehurst.wiremock.http.RequestMethod;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import lombok.extern.log4j.Log4j2;
import ru.otus.zaikin.helpers.CalcHelper;

import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.matching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

@Log4j2
public class Application {

    public static void main(String[] args) {
        log.debug("Application.main");

        final ResponseTemplateTransformer theTemplateTransformer = new ResponseTemplateTransformer(false, "calc-function", new CalcHelper());

        WireMockServer server = new WireMockServer(options().port(8089).extensions(theTemplateTransformer));

        StubMapping stubMapping = WireMock.request(RequestMethod.GET.getName(), WireMock.urlPathEqualTo("/calculator"))
                .withQueryParam("operation", equalTo("plus"))
                .withQueryParam("left", matching("\\d+"))
                .withQueryParam("right", matching("\\d+"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{{calc-function request.query.operation request.query.left request.query.right}}")
                        .withTransformers("response-template")
                )
                .build();

        server.addStubMapping(stubMapping);
        server.start();
    }
}