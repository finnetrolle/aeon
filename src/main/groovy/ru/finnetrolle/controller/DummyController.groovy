package ru.finnetrolle.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import ru.finnetrolle.Engine

@Component
@RequestMapping
class DummyController {

    @Autowired
    private Engine engine

    @RequestMapping("/")
    String triggers(Model model) {
        def list = engine.all()
        model.addAttribute('scripts', list)
        "upload"
    }

    @RequestMapping(path = "/", method = RequestMethod.POST)
    String upload(
            @RequestParam("script") MultipartFile script,
            RedirectAttributes attributes) {

        engine.add(script.originalFilename, script.bytes)

        attributes.addFlashAttribute('message', "Uploaded: ${script.originalFilename}")

        "redirect:/"
    }

    @RequestMapping("/exec/{scriptname:.+}")
    String exec(@PathVariable String scriptname, Model model) {
        def result = engine.exec(scriptname)
        model.addAttribute('name', scriptname)
        model.addAttribute('result', result)
        "done"
    }

}
