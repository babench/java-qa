package ru.otus.zaikin;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.common.ListOrSingle;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import com.github.tomakehurst.wiremock.http.RequestMethod;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import lombok.extern.log4j.Log4j2;

import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.matching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

@Log4j2
public class Application {

    public static void main(String[] args) {
        log.debug("Application.main");

        Helper<Object> stringLengthHelper = new Helper<Object>() {
            @Override
            public Object apply(final Object context, Options options) {
                Integer result =
                        Integer.valueOf(String.valueOf(((ListOrSingle) options.params[0]).getFirst())) +
                                Integer.valueOf(String.valueOf(((ListOrSingle) options.params[1]).getFirst()));
                return result;
            }
        };

        final ResponseTemplateTransformer theTemplateTransformer = new ResponseTemplateTransformer(false, "my-function", stringLengthHelper);

        WireMockServer server = new WireMockServer(options().port(8089).extensions(theTemplateTransformer));

        StubMapping stubMapping = WireMock.request(RequestMethod.GET.getName(), WireMock.urlPathEqualTo("/calculator"))
                .withQueryParam("operation", equalTo("plus"))
                .withQueryParam("left", matching("\\d+"))
                .withQueryParam("right", matching("\\d+"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{{my-function request.query.operation request.query.left request.query.right}}")
                        .withTransformers("response-template")
                )
                .build();

        server.addStubMapping(stubMapping);
        server.start();
    }
}