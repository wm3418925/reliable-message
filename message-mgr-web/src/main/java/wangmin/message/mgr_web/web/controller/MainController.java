package wangmin.message.mgr_web.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>User: Wang Min
 * <p>Date: 2016-8-5
 * <p>Version: 1.0
 * 主页处理, 对session树形做获取和设置(测试)
 */
@Controller
public class MainController {

	@RequestMapping("/")
    public String blankIndex() {
        return "indexPage";
    }
	@RequestMapping("/index")
    public String index() {
        return "indexPage";
    }
	@RequestMapping("/indexPage")
    public String indexPage() {
        return "indexPage";
    }
    @RequestMapping("/main")
    public String hello() {
        return "main";
    }

}
