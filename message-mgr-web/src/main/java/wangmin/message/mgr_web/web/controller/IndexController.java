package wangmin.message.mgr_web.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <p>User: Wang Min
 * <p>Date: 2016-12-19
 * <p>Version: 1.0
 */
@Controller
@Api(description = "首页的相关操作")
public class IndexController {

	@RequestMapping(value = "/", method = {RequestMethod.GET,RequestMethod.POST})
    @ApiOperation(value = "首页跳转")
    public String blankIndex() {
        return "index";
    }
	@RequestMapping(value = "/index", method = {RequestMethod.GET,RequestMethod.POST})
    @ApiOperation(value = "首页跳转")
    public String index() {
        return "index";
    }

}
