package my.jes.web.advices;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestController
public class ExceptionControllerAdvice {
    private Logger logger = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        logger.error("Exception 발생 : {}", ex.getMessage());
        JSONObject json = new JSONObject();
        json.put("msg", ex.getMessage());
        return json.toJSONString();
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String handle404(NoHandlerFoundException ex, Model model) {
        logger.error("404 발생 : {}", "404 page not found");
        JSONObject json = new JSONObject();
        json.put("msg", "해당 페이지를 찾을 수 없습니다!!!");
        return json.toJSONString();
    }

}
