package restchatbot.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import restchatbot.entities.Institut;
import restchatbot.entities.InstitutRepo;

import java.util.List;

@RestController
public class chatws {
@Autowired
private InstitutRepo institutRepo;

    @RequestMapping(value = "/contacts",method = RequestMethod.GET)
    public List<Institut> getList() {

        return institutRepo.findAll();
    }
}
