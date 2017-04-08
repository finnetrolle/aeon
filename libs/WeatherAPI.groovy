@Grab(group='io.github.openfeign', module='feign-core', version='9.4.0')

import feign.*
import feign.gson.*

interface WeatherAPI {
    @RequestLine("GET /data/2.5/forecast")
    abstract String ask(@QueryMap Map queryParams)
}

class WeatherAPIBuilder {
    static WeatherAPI build(String host) {
        return Feign.builder().target(WeatherAPI.class, host)
    }
}



