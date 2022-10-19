package my.jes.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import my.jes.web.dao.OrderDAO;
import my.jes.web.service.MemberService;
import my.jes.web.service.OrderService;
import my.jes.web.vo.MemberVO;
import my.jes.web.vo.OrderVO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
    @Autowired
    MemberService memberService;
    @Autowired
    OrderService orderService;

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping(value = "order.jes",
            method = {RequestMethod.POST},
            produces = "application/text; charset=utf8")
    @ResponseBody
    public String order(HttpServletRequest request,
                        HttpServletResponse response) throws Exception {
        JSONObject json = null;
        //try {
        BufferedReader br = request.getReader();
        JSONParser parser = new JSONParser();
        json = (JSONObject) parser.parse(br);

        //  {"product":[{"name":"아이스아메리카노","quantity":4},{"name":"카페라떼","quantity":2}]}

        JSONArray array = (JSONArray) json.get("product");
        Object[] array2 = array.toArray();

        ArrayList<OrderVO> list = new ArrayList<>();

        for (Object o : array2) {
            JSONObject j = (JSONObject) o;
            String prodname = (String) j.get("name");
            long quantity = (long) j.get("quantity");
            OrderVO orderVO = new OrderVO("web", prodname, quantity);
            HttpSession session = request.getSession();
            MemberVO memberVO = (MemberVO) session.getAttribute("member");
            if (memberVO != null) {//로그인 된 사용자라면 id를 추가해준다
                orderVO.setMemberid(memberVO.getId());
            } else {
                orderVO.setMemberid("");
            }
            list.add(orderVO);
        }//end for

        System.out.println("총 " + list.size() + "개 품목을 주문합니다");
        long order_group_no = orderService.ordersInsert(list);

        json = new JSONObject();
        json.put("order_group_no", order_group_no);



		/*} catch (IOException | org.json.simple.parser.ParseException | RuntimeException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			json.put("msg", e.getMessage());
		}*/
        return json.toJSONString();
    }


    @RequestMapping(value = "memberInsert.jes",
            method = {RequestMethod.POST},
            produces = "application/text; charset=utf8")
    @ResponseBody
    public String memberInsert(@RequestParam("id") String id,
                               @RequestParam("pw") String pw,
                               @RequestParam(value = "name", required = false) String name) throws Exception {

        System.out.println("memberInsert:" + id + "\t" + pw + "\t" + name);

        try {
            MemberVO m = new MemberVO(id, pw, name);
            memberService.memberInsert(m);
            return name + "님 회원가입 되셨습니다";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequestMapping(value = "login.jes",
            method = {RequestMethod.POST},
            produces = "application/text; charset=utf8")
    @ResponseBody
    /*@ModelAttribute("info") = info의 이름으로 MemberVO를 저장하기 위함  */
    public String login(@ModelAttribute("info") MemberVO m, HttpServletRequest request) {
        JSONObject json = new JSONObject();

        try {
            String name = memberService.login(m);
            if (name != null) {
                HttpSession session = request.getSession();
                session.setAttribute("member", m);

                json.put("name", name);
            } else {
                json.put("msg", "로그인 실패");
            }
        } catch (Exception e) {
            json.put("msg", "로그인 실패");
        }
        return json.toJSONString();
    }

    @RequestMapping(value = "logout.jes",
            method = {RequestMethod.POST},
            produces = "application/text; charset=utf8")
    @ResponseBody
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {

        HttpSession session = request.getSession(false);
        session.invalidate();

        return "";

    }


}
