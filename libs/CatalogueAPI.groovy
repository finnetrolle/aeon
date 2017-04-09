@Grab(group='io.github.openfeign', module='feign-core', version='9.4.0')
@Grab(group='io.github.openfeign', module='feign-gson', version='9.4.0')

import feign.*
import feign.gson.*

interface CatalogueAPI {
    @RequestLine("GET ")
    abstract String ask(@QueryMap Map queryParams)
}

class CatalogueAPIBuilder {
    static CatalogueAPI build(String host) {
        return Feign.builder().target(CatalogueAPI.class, host)
    }
}
