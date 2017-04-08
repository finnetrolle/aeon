package ru.finnetrolle.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import ru.finnetrolle.Engine

@Component
@RequestMapping('/lib')
class LibController {

    @Autowired
    private Engine engine

    @RequestMapping
    String all(Model model) {
        model.addAttribute('libs', engine.libs())
        "libs"
    }

    @RequestMapping("/{name:.+}")
    String lib(Model model, @PathVariable("name") String name) {
        model.addAttribute('source', engine.lib(name))
        model.addAttribute('name', name)
        "lib"
    }

}
