@Grab(group='io.github.openfeign', module='feign-core', version='9.4.0')
@Grab(group='io.github.openfeign', module='feign-gson', version='9.4.0')

import feign.*
import groovy.transform.*
import feign.gson.*

interface Monitorable {
    @RequestLine("GET /monitoring/selftest")
    SelfTestResult selftest()
}

// expect a json containing array field List<String> servers

List statuses = json.servers.collect { server ->
    Monitorable service = Feign.builder().decoder(new GsonDecoder()).target(Monitorable.class, server.host)
    def status = service.selftest()
    MonitorResult r = new MonitorResult(server.name, server.host, status)
    r
}

statuses.each { println it }

FileWriter writer = new FileWriter('monitoring.html')
MarkupBuilder b = new MarkupBuilder(writer)

b.html {
    head {
        title 'Monitoring'
    }
    body {
        table {
            tr {
                th 'name'
                th 'host'
                th 'status'
            }
            statuses.each { s ->
                tr(bgcolor : defineColor(s)) {
                    td s.name
                    td s.host
                    td s.status
                }
            }
        }
    }
}

private static String defineColor(MonitorResult result) {
    switch (result.status.status) {
        case HealthCheckState.OK:
            return '#00FF00'
        case HealthCheckState.WARNING:
            return '#00FFFF'
        case HealthCheckState.CRITICAL:
            return '#FF0000'
    }
    return '#F0F0F0'
}

@Immutable class MonitorResult {
    String name
    String host
    SelfTestResult status
}

@Immutable class SelfTestResult {
    HealthCheckState status;
    List<TestStatus> failedTests;
}

@Immutable class TestStatus {
    HealthCheckState status;
    String name;
    String error;
}

enum HealthCheckState {
    OK,
    WARNING,
    CRITICAL
}
