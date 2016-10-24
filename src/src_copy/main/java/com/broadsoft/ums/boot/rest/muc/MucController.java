package com.broadsoft.ums.boot.rest.muc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.broadsoft.ums.boot.Application;
import com.broadsoft.ums.boot.rest.UmsController;
import com.broadsoft.ums.boot.rest.exception.InvalidUdidException;
import com.broadsoft.ums.boot.rest.exception.UmsException;
import com.broadsoft.ums.boot.rest.muc.create.CreateBody;
import com.broadsoft.ums.boot.rest.muc.create.CreateResponse;
import com.broadsoft.ums.boot.rest.muc.exception.CheckIfUserExistsInRoomException;
import com.broadsoft.ums.boot.rest.muc.exception.DeviceUnauthenticatedException;
import com.broadsoft.ums.boot.rest.muc.exception.InvalidFirstNameException;
import com.broadsoft.ums.boot.rest.muc.exception.InvalidLastNameException;
import com.broadsoft.ums.boot.rest.muc.exception.InvalidOrMissingFieldException;
import com.broadsoft.ums.boot.rest.muc.exception.InvalidRoomJidException;
import com.broadsoft.ums.boot.rest.muc.exception.InvalidUserException;
import com.broadsoft.ums.boot.rest.muc.exception.MissingOrEmptyFieldException;
import com.broadsoft.ums.boot.rest.muc.exception.RoomDoesNotExistException;
import com.broadsoft.ums.boot.rest.muc.exception.RoomOwnerException;
import com.broadsoft.ums.boot.rest.muc.guestclient.AcceptResponse;
import com.broadsoft.ums.boot.rest.muc.guestclient.GuestClientBody;
import com.broadsoft.ums.boot.rest.muc.guestclient.RejectResponse;
import com.broadsoft.ums.boot.rest.muc.invite.InviteBody;
import com.broadsoft.ums.boot.rest.muc.invite.InviteResponse;
import com.broadsoft.ums.boot.rest.muc.join.JoinBody;
import com.broadsoft.ums.boot.rest.muc.join.JoinResponse;
import com.broadsoft.ums.boot.rest.muc.kick.KickBody;
import com.broadsoft.ums.boot.rest.muc.kick.KickResponse;
import com.broadsoft.ums.boot.rest.muc.leave.LeaveResponse;
import com.broadsoft.ums.boot.rest.muc.mucqueue.GetMucQueueResponse;
import com.broadsoft.ums.boot.rest.muc.participants.ViewParticipantsResponse;
import com.broadsoft.ums.boot.rest.muc.roomlist.GetRoomListResponse;
import com.broadsoft.ums.boot.rest.muc.userexists.UserExistsResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 
 * MucController<br/>
 * 
 *
 * @author mgeorgiev
 *
 */
@Controller
@EnableAutoConfiguration
public class MucController extends UmsController {

	final static List<UmsException> joinExceptions = new ArrayList<UmsException>();
	static {
		joinExceptions.add(new DeviceUnauthenticatedException());
		joinExceptions.add(new InvalidOrMissingFieldException());
		joinExceptions.add(new MissingOrEmptyFieldException());
		
		joinExceptions.add(new InvalidUdidException());
		joinExceptions.add(new InvalidRoomJidException());
		joinExceptions.add(new RoomDoesNotExistException());
		joinExceptions.add(new InvalidFirstNameException());
		joinExceptions.add(new InvalidLastNameException());
	}
	
	final static List<UmsException> leaveExceptions = new ArrayList<UmsException>();
	static {
		leaveExceptions.add(new DeviceUnauthenticatedException());
		
		leaveExceptions.add(new InvalidUdidException());
		leaveExceptions.add(new InvalidRoomJidException());
		leaveExceptions.add(new RoomDoesNotExistException());
	}
	
	final static List<UmsException> inviteExceptions = new ArrayList<UmsException>();
	static {
		inviteExceptions.add(new DeviceUnauthenticatedException());
		inviteExceptions.add(new InvalidOrMissingFieldException());
		inviteExceptions.add(new MissingOrEmptyFieldException());
		
		inviteExceptions.add(new InvalidUdidException());
		inviteExceptions.add(new RoomOwnerException());
		inviteExceptions.add(new InvalidRoomJidException());
		inviteExceptions.add(new RoomDoesNotExistException());
		inviteExceptions.add(new InvalidUserException());
	}
	
	final static List<UmsException> viewExceptions = new ArrayList<UmsException>();
	static {
		viewExceptions.add(new DeviceUnauthenticatedException());
		
		viewExceptions.add(new InvalidUdidException());
		viewExceptions.add(new RoomOwnerException());
		viewExceptions.add(new InvalidRoomJidException());
		viewExceptions.add(new RoomDoesNotExistException());
		viewExceptions.add(new InvalidUserException());
	}
	
	final static List<UmsException> kickExceptions = new ArrayList<UmsException>();
	static {
		kickExceptions.add(new DeviceUnauthenticatedException());
		kickExceptions.add(new InvalidOrMissingFieldException());
		kickExceptions.add(new MissingOrEmptyFieldException());
		
		kickExceptions.add(new InvalidUdidException());
		kickExceptions.add(new RoomOwnerException());
		kickExceptions.add(new InvalidRoomJidException());
		kickExceptions.add(new RoomDoesNotExistException());
		kickExceptions.add(new InvalidUserException());
	}

	final static List<UmsException> acceptExceptions = new ArrayList<UmsException>();
	static {
		acceptExceptions.add(new DeviceUnauthenticatedException());
		acceptExceptions.add(new InvalidOrMissingFieldException());
		acceptExceptions.add(new MissingOrEmptyFieldException());
		
		acceptExceptions.add(new InvalidUdidException());
		acceptExceptions.add(new RoomOwnerException());
		acceptExceptions.add(new InvalidRoomJidException());
		acceptExceptions.add(new RoomDoesNotExistException());
		acceptExceptions.add(new InvalidUserException());
	}
	
	final static List<UmsException> rejectExceptions = new ArrayList<UmsException>();
	static {
		rejectExceptions.add(new DeviceUnauthenticatedException());
		rejectExceptions.add(new InvalidOrMissingFieldException());
		rejectExceptions.add(new MissingOrEmptyFieldException());
		
		rejectExceptions.add(new InvalidUdidException());
		rejectExceptions.add(new RoomOwnerException());
		rejectExceptions.add(new InvalidRoomJidException());
		rejectExceptions.add(new RoomDoesNotExistException());
		rejectExceptions.add(new InvalidUserException());
	}
	
	final static List<UmsException> userExistsExceptions = new ArrayList<UmsException>();
	static {
		userExistsExceptions.add(new DeviceUnauthenticatedException());
		
		userExistsExceptions.add(new InvalidUdidException());
		userExistsExceptions.add(new RoomOwnerException());
		userExistsExceptions.add(new InvalidRoomJidException());
		userExistsExceptions.add(new RoomDoesNotExistException());
		userExistsExceptions.add(new InvalidUserException());
		userExistsExceptions.add(new CheckIfUserExistsInRoomException());
	}
	
	final static List<UmsException> mucQueueExceptions = new ArrayList<UmsException>();
	static {
		mucQueueExceptions.add(new DeviceUnauthenticatedException());
		
		mucQueueExceptions.add(new InvalidUdidException());
		mucQueueExceptions.add(new InvalidUserException());
	}
	
	final static List<UmsException> roomListExceptions = new ArrayList<UmsException>();
	static {
		roomListExceptions.add(new DeviceUnauthenticatedException());
		
		roomListExceptions.add(new InvalidUdidException());
		roomListExceptions.add(new InvalidUserException());
	}
	
	final static List<UmsException> createExceptions = new ArrayList<UmsException>();
	static {
		createExceptions.add(new DeviceUnauthenticatedException());
		createExceptions.add(new InvalidUdidException());
		createExceptions.add(new RoomOwnerException());
		createExceptions.add(new InvalidRoomJidException());
		createExceptions.add(new RoomDoesNotExistException());
		createExceptions.add(new InvalidUserException());
	}
	
	
	/**
	 * Join a room.
	 * You could be joining the room as an owner or as a participant.
	 * 
	 * <ul>
	 * 	<li>When gateway gets this request, it will check if this is a local MUC or a federated MUC. If local, it will query the database to check if the user can be added to the passed in room.</li>
	 * 	<li>Gateway will return success, if the result to the above query succeeds. Note that after receiving the HTTP response, the user has not yet joined the room. This only indicates that the process for joining the room has started.</li>
	 * 	<li>Gateway will pass the request information to IMP over BCCT.</li>
	 * 	<li>Please see sequence diagram below for the sequence of events for a join room.</li>
	 * </ul>
	 * 
	 * @param rjid
	 * 			Room JID
	 * 
	 * @param udid
	 * 			UDID/Resource of the user. Must have a VC
	 * 
	 * @param body
	 * 			Request Body
	 * 
	 * @return 
	 * 			{@link JoinResponse}
	 * 
	 * @throws UmsException 
	 */
	@ApiOperation(value = "join", nickname = "join", notes= "Join a room.")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = JoinResponse.class)}) 
	@RequestMapping(method = RequestMethod.POST, path="/gateway/room/join/{rjid}/user/{udid}",  produces = "application/json")
	public @ResponseBody JoinResponse join(@PathVariable String rjid, @PathVariable String udid, @RequestBody JoinBody body) throws UmsException {
		
		if(Application.MODE) {
			Random randomizer = new Random();
			throw joinExceptions.get(randomizer.nextInt(joinExceptions.size()));
		}
		
		MucValidator.validateIfAuthenticatedDevice(udid);
		MucValidator.invalidOrMissingField(body.getFn());
		MucValidator.missingOrEmptyField(body.getFn());
		MucValidator.invalidOrMissingField(body.getLn());
		MucValidator.missingOrEmptyField(body.getLn());
		
		MucValidator.validateUDID(udid);
		MucValidator.validateRoomJid(rjid);
		MucValidator.validateIfRoomDoesNotExists(rjid);
		MucValidator.validateFirstName(body.getFn());
		MucValidator.validateLastName(body.getLn());

		return new JoinResponse();
	}

	/**
	 * Leave room.
	 * 
	 * <ul>
	 * 	<li>Gateway will pass the request information to IMP.</li>
	 * 	<li>MUC will subsequently send out a presence unavailable, which will be saved to the user’s MUC Q and then a push notification will be triggered.</li>
	 * </ul>
	 * 
	 * @param rjid
	 * 			- Room JID
	 * 
	 * @param udid
	 * 			- udid of the user
	 * 
	 * @return
	 * 			- {@link LeaveResponse}
	 * 
	 * @throws UmsException 
	 */
	@ApiOperation(value = "leave", nickname = "leave", notes = "Leave a room.")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = LeaveResponse.class)})
	@RequestMapping(method = RequestMethod.POST, path="/gateway/room/leave/{rjid}/user/{udid}", produces = "application/json")
	public @ResponseBody LeaveResponse leave(@PathVariable String rjid, @PathVariable String udid) throws UmsException {
		
		if(Application.MODE) {
			Random randomizer = new Random();
			throw leaveExceptions.get(randomizer.nextInt(leaveExceptions.size()));
		}
		
		MucValidator.validateIfAuthenticatedDevice(udid);
		MucValidator.validateUDID(udid);
		MucValidator.validateRoomJid(rjid);
		MucValidator.validateIfRoomDoesNotExists(rjid);

		return new LeaveResponse();
	}

	/**
	 * API to leave a room you are currently in.
	 * 
	 * <ul>
	 * 	<li>Gateway will pass the request information to IMP.</li>
	 * 	<li>IMP will send out a direct invitation to each of the invited users (message stanza).</li>
	 * </ul>
	 * 
	 * @param rjid
	 * 			Room JID
	 * 
	 * @param udid
	 * 			udid of the user
	 * 
	 * @param body
	 * 			Request Body
	 * 
	 * @return
	 * 			{@link InviteResponse}
	 * 
	 * @throws UmsException 
	 */
	@ApiOperation(value = "invite", nickname = "invite", notes = "Invite users to a room.")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = InviteResponse.class)})
	@RequestMapping(method = RequestMethod.POST, path="/gateway/room/invite/{rjid}/user/{udid}", produces = "application/json")
	public @ResponseBody InviteResponse invite(@PathVariable String rjid, @PathVariable String udid, @RequestBody InviteBody body) throws UmsException {

		if(Application.MODE) {
			Random randomizer = new Random();
			throw inviteExceptions.get(randomizer.nextInt(inviteExceptions.size()));
		}
		
		MucValidator.validateIfAuthenticatedDevice(udid);
		MucValidator.invalidOrMissingField(body.getUsers().get(0));
		MucValidator.missingOrEmptyField(body.getUsers().get(0));
		
		MucValidator.validateUDID(udid);
		MucValidator.validateRoomOwner(udid);
		MucValidator.validateRoomJid(rjid);
		MucValidator.validateIfRoomDoesNotExists(rjid);
		MucValidator.validateUsers(body.getUsers());

		return new InviteResponse();
	}

	/**
	 * API to get list of participants in a room. Gateway will fetch the participant list from the database.
	 * <ul>
	 * 	<li>User must be a participant of the room to retrieve the participant list.</li>
	 * 	<li>Participant list will be returned in the HTTP response.</li>
	 * </ul>
	 * 
	 * @param rjid
	 * 			room JID
	 * 
	 * @param udid
	 * 			udid of the user
	 * 
	 * @return
	 * 			{@link ViewParticipantsResponse}
	 * 
	 * @throws UmsException 
	 */
	@ApiOperation(value = "view", nickname = "view", notes="View participant list")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = ViewParticipantsResponse.class)})
	@RequestMapping(method = RequestMethod.GET, path="/gateway/room/participants/{rjid}/user/{udid}", produces = "application/json")
	public @ResponseBody ViewParticipantsResponse viewParticipants(@PathVariable String rjid, @PathVariable String udid) throws UmsException {
		
		if(Application.MODE) {
			Random randomizer = new Random();
			throw viewExceptions.get(randomizer.nextInt(viewExceptions.size()));
		}
		
		MucValidator.validateIfAuthenticatedDevice(udid);
		
		MucValidator.validateUDID(udid);
		MucValidator.validateRoomJid(rjid);
		MucValidator.validateIfRoomDoesNotExists(rjid);	
		MucValidator.validateIfParticipantListAvailable(rjid);
		
		return new ViewParticipantsResponse();
	}
	
	
	/**
	 * API to get list of Muc Queue for given resource. Gateway will fetch the MUC queue from the database.
	 * 
	 * @param udid
	 * 			udid of the user
	 * 
	 * @return
	 * 			{@link GetMucQueueResponse}
	 * 
	 * @throws UmsException 
	 */
	@ApiOperation(value = "mucQueue", nickname = "mucQueue", notes="Get MUC Queue list")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = GetMucQueueResponse.class)})
	@RequestMapping(method = RequestMethod.GET, path="/gateway/room/mucqueue/user/{udid}", produces = "application/json")
	public @ResponseBody GetMucQueueResponse getMucQueue(@PathVariable String udid) throws UmsException {
		
		if(Application.MODE) {
			Random randomizer = new Random();
			throw mucQueueExceptions.get(randomizer.nextInt(mucQueueExceptions.size()));
		}
		
		MucValidator.validateIfAuthenticatedDevice(udid);
		
		MucValidator.validateUDID(udid);
		
		//MucValidator.checkIfNoReultFound(udid);
		
		return new GetMucQueueResponse();
	}
	
	/**
	 * API to get list room list for given resource. Gateway will fetch the room list from the database.
	 * 
	 * @param udid
	 * 			udid of the user
	 * 
	 * @return
	 * 			{@link GetMucQueueResponse}
	 * 
	 * @throws UmsException 
	 */
	@ApiOperation(value = "roomList", nickname = "roomList", notes="Get room list")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = GetRoomListResponse.class)})
	@RequestMapping(method = RequestMethod.GET, path="/gateway/room/roomlist/user/{udid}", produces = "application/json")
	public @ResponseBody GetRoomListResponse getRoomList(@PathVariable String udid) throws UmsException {
		
		if(Application.MODE) {
			Random randomizer = new Random();
			throw roomListExceptions.get(randomizer.nextInt(roomListExceptions.size()));
		}
		
		MucValidator.validateIfAuthenticatedDevice(udid);
		
		MucValidator.validateUDID(udid);
		
		//MucValidator.checkIfNoReultFound(udid);
		
		return new GetRoomListResponse();
	}
	
	/**
	 * API to temporarily remove a participant or visitor from a room; the user is allowed to re-enter the room at any time. A kicked user has a role of "none".
	 * <ul>
	 * 	<li>User performing the kick must be a moderator of the room</li>
	 * 	<li>MUC will subsequently send out a presence unavailable for the user that has been kicked.</li>
	 * </ul>
	 * 
	 * @param rjid
	 * 			room JID
	 * 
	 * @param udid
	 * 			udid of the user
	 * 
	 * @return
	 * 			{@link KickResponse}
	 * 
	 * @throws UmsException 
	 */
	@ApiOperation(value = "kick", nickname = "kick", notes = "Kick a user from the room.")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = KickResponse.class)})
	@RequestMapping(method = RequestMethod.POST, path="/gateway/room/kick/{rjid}/user/{udid}", produces = "application/json")
	public @ResponseBody KickResponse kick(@PathVariable String rjid, @PathVariable String udid, @RequestBody KickBody body) throws UmsException {
		
		if(Application.MODE) {
			Random randomizer = new Random();
			throw kickExceptions.get(randomizer.nextInt(kickExceptions.size()));
		}
		
		MucValidator.validateIfAuthenticatedDevice(udid);
		MucValidator.invalidOrMissingField(body.getUsers().get(0));
		MucValidator.missingOrEmptyField(body.getUsers().get(0));
		
		MucValidator.validateUDID(udid);
		MucValidator.validateRoomOwner(udid);
		MucValidator.validateRoomJid(rjid);
		MucValidator.validateIfRoomDoesNotExists(rjid);
		MucValidator.validateUsers(body.getUsers());

		return new KickResponse();
	}

	/**
	 * API to temporarily remove all participants or visitors from a room; the users are allowed to re-enter the room at any time. A kicked user has a role of "none".
	 * <ul>
	 * 	<li>User performing the kick must be a moderator of the room</li>
	 * 	<li>MUC will subsequently send out a presence unavailable for the user that has been kicked.</li>
	 * </ul>
	 * 
	 * @param rjid
	 * 			room JID
	 * 
	 * @param udid
	 * 			udid of the user
	 * 
	 * @return
	 * 			{@link KickResponse}
	 * 
	 * @throws UmsException 
	 */
	@ApiOperation(value = "kickall", nickname = "kickall", notes = "Kick all users from the room.")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = KickResponse.class)})
	@RequestMapping(method = RequestMethod.POST, path="/gateway/room/kick/{rjid}/user/{udid}/all", produces = "application/json")
	public @ResponseBody KickResponse kickAll(@PathVariable String rjid, @PathVariable String udid) throws UmsException {
		
		if(Application.MODE) {
			Random randomizer = new Random();
			throw kickExceptions.get(randomizer.nextInt(kickExceptions.size()));
		}
		
		MucValidator.validateIfAuthenticatedDevice(udid);
		
		MucValidator.validateUDID(udid);
		MucValidator.validateRoomOwner(udid);
		MucValidator.validateRoomJid(rjid);
		MucValidator.validateIfRoomDoesNotExists(rjid);

		return new KickResponse();
	}
	
	/**
	 * API to accept a guest client user's request to join the room.
	 * <ul>
	 * 	<li>User performing the accept must be a moderator of the room</li>
	 * </ul>
	 * 
	 * @param rjid
	 * 			room JID
	 * 
	 * @param udid
	 * 			udid of the user
	 * 
	 * @param gjid
	 * 			gjid of the user
	 * 
	 * @return
	 * 			{@link AcceptResponse}
	 * 
	 * @throws UmsException 
	 */
	@ApiOperation(value = "guestClientAccept", nickname = "guestClientAccept", notes = "Accept a guest client user's request to join room.")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = AcceptResponse.class)})
	@RequestMapping(method = RequestMethod.POST, path="/gateway/room/accept/{rjid}/user/{udid}/guest/{gjid}", produces = "application/json")
	public @ResponseBody AcceptResponse acceptGuestClientUser(@PathVariable String rjid, @PathVariable String udid, @RequestBody GuestClientBody body) throws UmsException {
		
		if(Application.MODE) {
			Random randomizer = new Random();
			throw acceptExceptions.get(randomizer.nextInt(acceptExceptions.size()));
		}
		
		MucValidator.validateIfAuthenticatedDevice(udid);
		MucValidator.invalidOrMissingField(body.getIqId());
		MucValidator.missingOrEmptyField(body.getIqId());
		
		MucValidator.validateUDID(udid);
		MucValidator.validateRoomOwner(udid);
		MucValidator.validateRoomJid(rjid);
		MucValidator.validateIfRoomDoesNotExists(rjid);
		MucValidator.validateUsers(body.getIqId());

		return new AcceptResponse();
	}
	
	/**
	 * API to reject a guest client user's request to join the room.
	 * <ul>
	 * 	<li>User performing the accept must be a moderator of the room</li>
	 * </ul>
	 * 
	 * @param rjid
	 * 			room JID
	 * 
	 * @param udid
	 * 			udid of the user
	 * 
	 * @param gjid
	 * 			gjid of the user
	 * 
	 * @return
	 * 			{@link RejectResponse}
	 * 
	 * @throws UmsException 
	 */
	@ApiOperation(value = "guestClientReject", nickname = "guestClientReject", notes = "Reject a guest client user's request to join room")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = RejectResponse.class)})
	@RequestMapping(method = RequestMethod.POST, path="/gateway/room/reject/{rjid}/user/{udid}/guest/{gjid}", produces = "application/json")
	public @ResponseBody RejectResponse rejectGuestClientUser(@PathVariable String rjid, @PathVariable String udid, @RequestBody GuestClientBody body) throws UmsException {
		
		if(Application.MODE) {
			Random randomizer = new Random();
			throw rejectExceptions.get(randomizer.nextInt(rejectExceptions.size()));
		}
		
		MucValidator.validateIfAuthenticatedDevice(udid);
		MucValidator.invalidOrMissingField(body.getIqId());
		MucValidator.missingOrEmptyField(body.getIqId());
		
		MucValidator.validateUDID(udid);
		MucValidator.validateRoomOwner(udid);
		MucValidator.validateRoomJid(rjid);
		MucValidator.validateIfRoomDoesNotExists(rjid);

		return new RejectResponse();
	}
	
	/**
	 * API to check if a user is currently a participant of the room.
	 * 
	 * @param rjid
	 * 			room JID
	 * 
	 * @param udid
	 * 			udid of the user
	 * 
	 * @return
	 * 			{@link UserExistsResponse}
	 * 
	 * @throws UmsException 
	 */
	@ApiOperation(value = "userexists", nickname = "userexists", notes = "Check if a user exists in the room.")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = UserExistsResponse.class)})
	@RequestMapping(method = RequestMethod.POST, path="/gateway/room/userstatus/{rjid}/user/{udid}", produces = "application/json")
	public @ResponseBody UserExistsResponse checkIfUserExistsInRoom(@PathVariable String rjid, @PathVariable String udid) throws UmsException {
		
		if(Application.MODE) {
			Random randomizer = new Random();
			throw userExistsExceptions.get(randomizer.nextInt(userExistsExceptions.size()));
		}
		
		MucValidator.validateIfAuthenticatedDevice(udid);
		MucValidator.validateUDID(udid);
		MucValidator.validateRoomOwner(udid);
		MucValidator.validateRoomJid(rjid);
		MucValidator.validateIfRoomDoesNotExists(rjid);
		MucValidator.checkIfUserExistsInRoom(rjid);
		
		return new UserExistsResponse();
	}
	
	
	/**
	 * This API is called to create a room. Unlike standard xmpp, this API will be used to create AND configure the room.
	 * If the room already exists, an error will be returned. Creating a room is a 2 step process.
	 * First it must be created, then it must be configured.
	 * 
	 * @param rjid
	 * 			Room JID
	 * 
	 * @param udid
	 * 			udid of the user
	 * 
	 * @param body
	 * 			Request Body {@link CreateBody}
	 * 
	 * @return
	 * 			{@link CreateResponse} as JSON
	 * 
	 * @throws UmsException 
	 */
	@ApiOperation(value = "create", nickname = "create")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = CreateResponse.class)})
	@RequestMapping(method = RequestMethod.POST, path="/gateway/room/create/{rjid}/user/{udid}", produces = "application/json")
	public @ResponseBody CreateResponse create(@PathVariable String rjid, @PathVariable String udid, @RequestBody CreateBody body) throws UmsException {
		
		if(Application.MODE) {
			Random randomizer = new Random();
			throw createExceptions.get(randomizer.nextInt(createExceptions.size()));
		}
		
		MucValidator.validateIfAuthenticatedDevice(udid);
		MucValidator.validateUDID(udid);
		MucValidator.validateRoomJid(rjid);
		MucValidator.validateIfRoomExists(rjid);

		MucValidator.validateFirstName(body.getFn());
		MucValidator.validateLastName(body.getLn());
		MucValidator.validatePersistent(body.isPersistent());

		return new CreateResponse();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MUC Controler - Implementation of MUC REST API";
	}
	
}