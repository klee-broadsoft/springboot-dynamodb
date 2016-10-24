package com.broadsoft.ums.boot.rest.help;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.broadsoft.ums.boot.rest.UmsController;

/**
 * 
 * Help Controller<br/>
 * 
 * Used to list Spring beans
 *
 */
@Controller
@EnableAutoConfiguration
public class HelpController extends UmsController {

	static final Logger logger = Logger.getLogger(HelpController.class);

	@Autowired
	private ApplicationContext context;

	@ApiOperation(value = "help", nickname = "help")
	@RequestMapping(method = RequestMethod.GET, path="/help", produces = "application/json")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = HelpBean.class)})
	public @ResponseBody List<HelpBean> help() {

		List<HelpBean> services = new ArrayList<HelpBean>();

		String[] beanNames = context.getBeanNamesForType(UmsController.class);

		for (String beanName : beanNames) {
			HelpBean helpbean = new HelpBean();
			helpbean.setName(beanName);
			services.add(helpbean);
		}

		return services;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Help Controler - Show implemented REST APIs";
	}
}