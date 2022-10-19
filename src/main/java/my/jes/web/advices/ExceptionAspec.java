package my.jes.web.advices;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ExceptionAspec {
    // OrderService.orderInsert() 실행 시 에러가 나면 실행될 메소드
    @AfterThrowing(pointcut = "execution(* com.ssafy.web.service.OrderService.orderInsert(..))", throwing = "e")
    public void c(JoinPoint jp, Throwable e) {

        throw new RuntimeException(e.getMessage());
    }
}
