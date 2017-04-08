package ru.finnetrolle

import org.springframework.stereotype.Component

import javax.annotation.PostConstruct

@Component
class Engine {

    public static final String SPRIPTS = "./scripts/"
    public static final String LIBS = "./libs/"
    private GroovyScriptEngine engine = new GroovyScriptEngine([SPRIPTS, LIBS] as String[])

    List<String> libs() {
        return new File(LIBS).listFiles().collect {it.name}
    }

    String lib(String name) {
        return new File("${LIBS}${name}").text
    }

    void add(String name, byte[] code) {
        def encodedName = UUID.randomUUID().toString()
        new File("${SPRIPTS}${encodedName}").setText(new String(code, 'UTF-8'),'UTF-8')
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
