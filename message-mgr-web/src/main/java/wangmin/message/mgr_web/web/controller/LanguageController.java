package wangmin.message.mgr_web.web.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
//import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * <p>User: Wang Min
 * <p>Date: 2016-12-19
 * <p>Version: 1.0
 */
@Controller
@RequestMapping("/")
@Api(description = "动态改变系统的语言")
public class LanguageController {
	@Autowired
	CookieLocaleResolver resolver;
	//@Autowired
	//SessionLocaleResolver resolver;

	@RequestMapping("changeLanguage/{language}")
	@ApiOperation(value = "语言切换")
	public ModelAndView language(HttpServletRequest request, HttpServletResponse response, @PathVariable("language") String language) {
		if (StringUtils.isEmpty(language)) {
			return new ModelAndView("redirect:/");
		} else {
			language = language.toLowerCase();
			
			if (language.equals("zh_cn")) {
				resolver.setLocale(request, response, Locale.CHINA);
			} else if (language.equals("en")) {
				resolver.setLocale(request, response, Locale.ENGLISH);
			} else {
				language = "zh_cn";
				resolver.setLocale(request, response, Locale.CHINA);
			}
		}

		return new ModelAndView("redirect:/");
	}
}
