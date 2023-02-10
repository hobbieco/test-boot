package hobbieco.test.app.base;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import hobbieco.test.config.security.UserInfo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BaseController {

	@RequestMapping(value="/", method= {RequestMethod.GET,RequestMethod.POST})
	public String index(@AuthenticationPrincipal UserInfo authUser) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		log.debug("########## index - auth.getName : {}", auth.getName());

		if (authUser != null) {
			log.debug("########## index - authUser.getNm : {}", authUser.getUserNm());
		} else {
			log.debug("AuthUser is null : 미인증 사용자");
		}

		return "index";
	}
}
