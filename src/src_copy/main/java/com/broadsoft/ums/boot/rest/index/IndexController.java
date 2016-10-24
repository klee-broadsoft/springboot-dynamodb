package com.broadsoft.ums.boot.rest.index;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.broadsoft.ums.boot.rest.UmsController;

/**
 * Index controller<br/>
 * 
 * Used to check the status of the test server<br/>
 * 
 * @author mgeorgiev
 *
 */
@Controller
@EnableAutoConfiguration
public class IndexController extends UmsController {

	static final Logger logger = Logger.getLogger(IndexController.class);
	
    @ApiOperation(value = "index", nickname = "index")
	@RequestMapping(method = RequestMethod.GET, path="/", produces = "text/html")
	@ApiResponses(value = 
		{ 
			@ApiResponse(code = 200, message = "Success", response = String.class)
		}
	) 
    @ResponseBody String home() {
        return "UMS REST simulator is up!";
    }
    
    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Index Controler - Show emulator's status";
	}
	
}