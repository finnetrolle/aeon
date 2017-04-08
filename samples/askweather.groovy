@Grab(group='io.github.openfeign', module='feign-core', version='9.4.0')

import feign.*
import feign.gson.*

interface Weather {
    @RequestLine("GET /data/2.5/forecast")
    String ask(@QueryMap Map queryParams)
}

def weather = Feign.builder().target(Weather.class, 'http://api.openweathermap.org')

def map = ["id": "524901", "APPID": "cb48378eb0aa347364eabdcbb0fbb0d2"]

def result = weather.ask(map)

result