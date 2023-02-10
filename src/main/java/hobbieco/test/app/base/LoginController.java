package hobbieco.test.app.base;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import hobbieco.test.app.base.service.LoginService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class LoginController {

	@Resource(name="loginService")
	private LoginService service;
	
	@RequestMapping(value="/login/test")
	public String testLogin(HttpServletRequest request, HttpServletResponse response, Model model) {
		String result = service.testLogin(request, response);
		log.debug("call /login/test");
		return result;
	}
	
	@RequestMapping(value="/login/error")
	public String loginError(HttpServletRequest request, HttpServletResponse response, Model model) {
		String result = "/login";
		log.debug("call /login/error");
		model.addAttribute("result", "fail login");
		return result;
	}
	
	@RequestMapping(value="/login/sub")
	public String loginSub(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("loginType","sub");
		return "forward:/login";
	}
	
	@RequestMapping(value="/login")
	public String login(HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		//String result = "redirect:/";
		String result = "login";
		log.debug("call /login");
		
		//Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//String username = (String)principal;
		//log.debug("user name of pricipal : {}",username);
		
		//if (StringUtils.isBlank(username) || StringUtils.equals(username, "anonymousUser")) {
		//	result = "/test/login";
		//}
		
		Object loginType = request.getAttribute("loginType");
		if (loginType != null) {
			model.addAttribute("loginType", loginType.toString());
			result = "login";
		}
		
		Object rslt = request.getAttribute("loginError");
		if (rslt != null) {
			model.addAttribute("result", rslt.toString());
			result = "login";
		}
		
		Map<String,?> flash = RequestContextUtils.getInputFlashMap(request);
		if (flash == null) {
			log.debug("flash attributes is null");
		} else {
			log.debug("GET FLASH ##################################################");
			for (String key : flash.keySet()) {
				log.debug("{} : {}",key,flash.get(key));
			}
			log.debug("GET FLASH ##################################################");
		}
		
		if (request.getParameterMap().keySet().size() > 0) {
			redirectAttributes.addFlashAttribute("paramKey", "TEST FLASH");
			result = "redirect:/login";
		}
		
		for (String key : request.getParameterMap().keySet()) {
			log.debug("parameter {} : {}",key,request.getParameterMap().get(key));
		}
		
//		Enumeration<String> at = request.getAttributeNames();
//		while(at.hasMoreElements()) {
//			String key = at.nextElement();
//			log.debug("attr {} : {}",key,request.getAttribute(key));
//		}
		
		return result;
	}
	
	@RequestMapping(value="/login/fail")
	public RedirectView loginFail(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		log.debug("call /login/fail");
		for (String key : request.getParameterMap().keySet()) {
			log.debug("parameter {} : {}",key,request.getParameterMap().get(key));
		}
		redirectAttributes.addFlashAttribute("paramKey", "TEST FLASH");
		//redirectAttributes.addAttribute("attKEY", "TEST ATTR");
		return new RedirectView("/login");
	}
	
}
