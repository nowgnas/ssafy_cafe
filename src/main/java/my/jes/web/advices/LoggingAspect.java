package my.jes.web.advices;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {
    private static Logger logger = (Logger) LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * biz메서드 정상 결과 반환이후에 동작해야 하므로 @AfterReturning advice로 구성한다.
     * point cut의 구성은 아래와 같다.
     * 리턴 타입: * 이므로 모든 리턴 타입에 대해 적용된다.
     * 클래스명: com.ssafy.web.service 패키지로 시작하는 OrderService 클래스에 적용된다.
     * 메서드명: insert 이므로 insert 라는 이름을 가진 메서드에 적용된다.
     * 파라미터:.. 이므로 파라미터의 개수, 타입에 상관없이 적용된다.
     *
     * @param jp JoinPoint를 통해 joinpoint의 다양한 정보에 접근할 수 있다.
     */
    // OrderService.orderInsert() 메소드 결과 반환 이후 실행될 메소드
    // joinPoint는 target 클래스의 메서드?
    @AfterReturning(value = "execution(* my.jes.web.service.OrderService.ordersInsert(..))")
    public void a(JoinPoint jp) {
        //System.out.println("..."+logger);
        logger.info(jp.getSignature().toShortString() + " 메소드 수행 후 파라미터:{}", Arrays.toString(jp.getArgs()));
    }

    /**
     * biz메서드 실행 전에 동작해야 하므로 @Before advice로 구성한다.
     * point cut의 구성은 아래와 같다.
     * 리턴 타입: * 이므로 모든 리턴 타입에 대해 적용된다.
     * 클래스명: com.ssafy.web.service 패키지로 시작하고 .. 이므로 하위의 모든 경로, 클래스에 적용된다.
     * 메서드명: * 이므로 모든 메서드에 적용된다.
     * 파라미터:.. 이므로 파라미터의 개수, 타입에 상관없이 적용된다.
     *
     * @param jp JoinPoint를 통해 joinpoint의 다양한 정보에 접근할 수 있다.
     */
    // service 하위 패키지에 있는 모든 메소드 실행 전 실행될 메소드
    @Before(value = "execution(* my.jes.web.service..*.*(..))")
    public void b(JoinPoint jp) {
        logger.info(jp.getSignature().toShortString() + " 메소드 수행 전 파라미터:{}", Arrays.toString(jp.getArgs()));
    }
}
