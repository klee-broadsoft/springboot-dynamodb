package com.broadsoft.ums.boot.rest.presence;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.broadsoft.ums.boot.rest.UmsController;
import com.broadsoft.ums.boot.rest.exception.UmsException;
import com.broadsoft.ums.boot.rest.presence.delete.DeletePresenceResponse;
import com.broadsoft.ums.boot.rest.presence.freetext.delete.DeleteFreeTextResponse;
import com.broadsoft.ums.boot.rest.presence.freetext.set.SetFreeTextBody;
import com.broadsoft.ums.boot.rest.presence.freetext.set.SetFreeTextResponse;
import com.broadsoft.ums.boot.rest.presence.location.delete.DeleteLocationResponse;
import com.broadsoft.ums.boot.rest.presence.location.set.SetLocationBody;
import com.broadsoft.ums.boot.rest.presence.location.set.SetLocationResponse;
import com.broadsoft.ums.boot.rest.presence.set.SetPresenceBody;
import com.broadsoft.ums.boot.rest.presence.set.SetPresenceResponse;

/**
 * 
 * PresenceController<br/>
 * 
 * <br/>
 * <a href="https://docs.google.com/document/d/12x1YX0oF9uYjMjuGFBvPLNYrq5l3h4fR_fDfpdz6KzE/edit#heading=h.a3uofl4zycjj">HLD</a>
 * 
 * <br/>
 * <a href="https://docs.google.com/document/d/1EeDPkx_76T1NtTF7EBhYfZN_YpwpN5nyc7MJ1SHvZCg/edit?ts=56abed95#heading=h.ceq6rtbg6076">Aggregated Presence Proposal</a>
 * 
 * 
 * @author mgeorgiev@broadsoft.com
 *
 */
@Controller
@EnableAutoConfiguration
public class PresenceController extends UmsController {

	
	/**
	 * Set Presence.
	 * 
	 * 
	 * @param udid
	 * 			udid of the user
	 * 
	 * @param body
	 * 			PresenceBody Body
	 * 
	 * @return 
	 * 			{@link SetPresenceResponse}
	 * 
	 * @throws UmsException 
	 */
	@ApiOperation(value = "manualPresenceSet", nickname = "manualPresenceSet", notes= "Manual presence - set. \nInvalidShowExceptionCode - 0200001")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = SetPresenceResponse.class)})
	@RequestMapping(method = RequestMethod.POST, path="/gateway/manual/presence/{udid}",  produces = "application/json")
	public @ResponseBody SetPresenceResponse postPresence(@PathVariable String udid, @RequestBody SetPresenceBody body) throws UmsException {

		PresenceValidator.validateUDID(udid);
		PresenceValidator.validateShow(body.getShow());
		
		return new SetPresenceResponse();
	}
	
	/**
	 * Delete Presence.
	 * 
	 * 
	 * @param udid
	 * 			udid of the user
	 * 
	 * @return 
	 * 			{@link DeletePresenceResponse}
	 * 
	 * @throws UmsException 
	 */
	@ApiOperation(value = "manualPresenceDelete", nickname = "manualPresenceDelete", notes= "Manual presence - delete.")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = DeletePresenceResponse.class)})
	@RequestMapping(method = RequestMethod.DELETE, path="/gateway/manual/presence/{udid}",  produces = "application/json")
	public @ResponseBody DeletePresenceResponse deletePresence(@PathVariable String udid) throws UmsException {

		PresenceValidator.validateUDID(udid);
		
		return new DeletePresenceResponse();
	}
	
	/**
	 * Set FreeText.
	 * 
	 * 
	 * @param udid
	 * 			udid of the user
	 * 
	 * @param body
	 * 			SetFreeTextBody Body
	 * 
	 * @return 
	 * 			{@link SetFreeTextResponse}
	 * 
	 * @throws UmsException 
	 */
	@ApiOperation(value = "manualFreeTextSet", nickname = "manualFreeTextSet", notes= "Manual FreeText - set. \nEmptyFreeTextExceptionCode - 9200005")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = SetFreeTextResponse.class)})
	@RequestMapping(method = RequestMethod.POST, path="/gateway/manual/freetext/{udid}",  produces = "application/json")
	public @ResponseBody SetFreeTextResponse postFreeText(@PathVariable String udid, @RequestBody SetFreeTextBody body) throws UmsException {

		PresenceValidator.validateUDID(udid);
		PresenceValidator.validateFreeText(body.getFreeText());
		
		return new SetFreeTextResponse();
	}

	/**
	 * Delete FreeText.
	 * 
	 * 
	 * @param udid
	 * 			udid of the user
	 * 
	 * @return 
	 * 			{@link DeleteFreeTextResponse}
	 * 
	 * @throws UmsException 
	 */
	@ApiOperation(value = "manualFreeTextDelete", nickname = "manualFreeTextDelete", notes= "Manual FreeText - delete.")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = DeleteFreeTextResponse.class)})
	@RequestMapping(method = RequestMethod.DELETE, path="/gateway/manual/freetext/{udid}",  produces = "application/json")
	public @ResponseBody DeleteFreeTextResponse deleteFreeText(@PathVariable String udid) throws UmsException {

		PresenceValidator.validateUDID(udid);
		
		return new DeleteFreeTextResponse();
	}
	
	/**
	 * Set Location.
	 * 
	 * 
	 * @param udid
	 * 			udid of the user
	 * 
	 * @param body
	 * 			SetLocationBody Body
	 * 
	 * @return 
	 * 			{@link SetLocationResponse}
	 * 
	 * @throws UmsException 
	 */
	@ApiOperation(value = "manualLocationSet", nickname = "manualLocationSet", notes= "Manual Location - set.")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = SetLocationResponse.class)})
	@RequestMapping(method = RequestMethod.POST, path="/gateway/manual/location/{udid}",  produces = "application/json")
	public @ResponseBody SetLocationResponse postLocation(@PathVariable String udid, @RequestBody SetLocationBody body) throws UmsException {

		PresenceValidator.validateUDID(udid);
		
		return new SetLocationResponse();
	}
	
	/**
	 * Delete Location.
	 * 
	 * 
	 * @param udid
	 * 			udid of the user
	 * 
	 * @return 
	 * 			{@link DeleteLocationResponse}
	 * 
	 * @throws UmsException 
	 */
	@ApiOperation(value = "manualLocationDelete", nickname = "manualLocationDelete", notes= "Manual Location - delete.")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = DeleteLocationResponse.class)})
	@RequestMapping(method = RequestMethod.DELETE, path="/gateway/manual/location/{udid}",  produces = "application/json")
	public @ResponseBody DeleteLocationResponse deleteLocation(@PathVariable String udid) throws UmsException {

		PresenceValidator.validateUDID(udid);
		
		return new DeleteLocationResponse();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Presence Controler - Implementation of Presence REST API v. 2.0";
	}
}