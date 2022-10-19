package my.jes.web;

import my.jes.web.service.BankService;
import my.jes.web.vo.KB_VO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BankController {
    @Autowired
    BankService bankService;

    @RequestMapping(value = "remit.jes",
            method = {RequestMethod.POST},
            produces = "application/text; charset=utf8")
    @ResponseBody
    public String remit(KB_VO vo) {
        try {
            return bankService.remit(vo);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "fail";
        }
    }


}
