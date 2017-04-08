package ru.finnetrolle

import org.springframework.stereotype.Component

import javax.annotation.PostConstruct

@Component
class Engine {

    public static final String HOME = "./scripts/"
    private GroovyScriptEngine engine = new GroovyScriptEngine([HOME, "./libs/"] as String[])

    void add(String name, byte[] code) {
        def encodedName = UUID.randomUUID().toString()
        new File("${HOME}${encodedName}").setText(new String(code, 'UTF-8'),'UTF-8')
        scripts.put(name, encodedName)
    }

    private Map<String, String> scripts = [:]

    List<String> all() {
        scripts.keySet().toList()
    }

    String exec(String name) {
        def binding = new Binding()
        def script = engine.run(scripts.get(name), binding)
        script
    }
}
