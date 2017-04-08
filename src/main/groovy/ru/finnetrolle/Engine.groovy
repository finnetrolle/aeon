package ru.finnetrolle

import org.springframework.stereotype.Component

import javax.annotation.PostConstruct

@Component
class Engine {

    private Map<String, Script> scripts = [:]

    void addScript(String name, String script) {
        scripts.put(name, new Script(name, script))
    }

    List<Script> all() {
        scripts.values().toList()
    }

    Script get(String name) {
        scripts.get(name)
    }

    @PostConstruct
    void init() {
        addScript("myname", "def a = 'Maks!'\na")
    }

}
