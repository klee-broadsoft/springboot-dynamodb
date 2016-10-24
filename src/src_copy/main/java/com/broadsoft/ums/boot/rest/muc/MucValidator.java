package com.broadsoft.ums.boot.rest.muc;

import java.util.List;

import com.broadsoft.ums.boot.rest.BaseValidator;
import com.broadsoft.ums.boot.rest.exception.UmsException;
import com.broadsoft.ums.boot.rest.muc.exception.CheckIfUserExistsInRoomException;
import com.broadsoft.ums.boot.rest.muc.exception.DeviceUnauthenticatedException;
import com.broadsoft.ums.boot.rest.muc.exception.EmptyPersistentException;
import com.broadsoft.ums.boot.rest.muc.exception.InvalidFirstNameException;
import com.broadsoft.ums.boot.rest.muc.exception.InvalidLastNameException;
import com.broadsoft.ums.boot.rest.muc.exception.InvalidOrMissingFieldException;
import com.broadsoft.ums.boot.rest.muc.exception.InvalidRoomJidException;
import com.broadsoft.ums.boot.rest.muc.exception.InvalidUserException;
import com.broadsoft.ums.boot.rest.muc.exception.MissingOrEmptyFieldException;
import com.broadsoft.ums.boot.rest.muc.exception.NoResultFoundException;
import com.broadsoft.ums.boot.rest.muc.exception.ParticipantListUnavailableException;
import com.broadsoft.ums.boot.rest.muc.exception.RoomAlreadyExistsException;
import com.broadsoft.ums.boot.rest.muc.exception.RoomDoesNotExistException;
import com.broadsoft.ums.boot.rest.muc.exception.RoomOwnerException;

/**
 * Validator for input data
 * 
 * @author mgeorgiev@broadsoft.com
 *
 */
public class MucValidator extends BaseValidator {
	
	/**
	 * Check if udid is authenticated and belongs to logged in user.
	 * 
	 * @param udid
	 * 			- udid of the user device
	 * 
	 * @throws UmsException
	 * 			-if udid is invalid
	 */
	public static void validateIfAuthenticatedDevice(String udid) throws UmsException {
		if(udid.indexOf(DeviceUnauthenticatedException.CODE) > -1)	throw new DeviceUnauthenticatedException();
	}
	
	/**
	 * Check if room jid valid.
	 * 
	 * @param rjid
	 * 			- jid of the room
	 * 
	 * @throws UmsException
	 * 			-if jid is invalid
	 */
	public static void validateRoomJid(String rjid) throws UmsException {
		if(rjid.indexOf(InvalidRoomJidException.CODE) > -1)	throw new InvalidRoomJidException();
	}
	
	/**
	 * Check if room does NOT exist.
	 * 
	 * @param rjid
	 * 			- jid of the room
	 * 
	 * @throws UmsException
	 * 			- if room does not exist
	 */
	public static void validateIfRoomDoesNotExists(String rjid) throws UmsException {
		if(rjid.indexOf(RoomDoesNotExistException.CODE) > -1) throw new RoomDoesNotExistException();
	}
	
	/**
	 * Check if room exists.
	 * 
	 * @param rjid
	 * 			- jid of the room
	 * @throws UmsException
	 * 				- if room with this jid already exists
	 */
	public static void validateIfRoomExists(String rjid) throws UmsException {
		if(rjid.indexOf(RoomAlreadyExistsException.CODE) > -1) throw new RoomAlreadyExistsException();
	}
	
	/**
	 * Check if JID is owner of the room.
	 * 
	 * @param udid
	 * 			- udid
	 * 
	 * @throws UmsException
	 * 			- if udid is not an owner
	 */
	public static void validateRoomOwner(String udid) throws UmsException {
		if (udid.indexOf(RoomOwnerException.CODE) > -1) throw new RoomOwnerException();
	}
	
	/**
	 * Validate list of user's jids.
	 * 	
	 * @param users
	 * 			- list of user's jids
	 * 
	 * @throws UmsException
	 * 			- if there is an invalid jid
	 */
	public static void validateUsers(List<String> users) throws UmsException {
		for(String user : users){
			if(user.indexOf(InvalidUserException.CODE) > -1) throw new InvalidUserException();
		}
	}
	
	/**
	 * Validate firstName.
	 * 
	 * @param firstName
	 * 			- first name
	 * 
	 * @throws UmsException
	 * 			- if first name is invalid
	 */
	public static void validateFirstName(String firstName) throws UmsException {
		if(null == firstName || firstName.length() == 0) throw new InvalidFirstNameException();
		if(firstName.indexOf(InvalidFirstNameException.CODE) > -1) throw new InvalidFirstNameException();
	}
	
	/**
	 * Validate lastName.
	 * 
	 * @param lastName
	 * 			- last name of the participant
	 * 
	 * @throws UmsException
	 * 			- if last name is invalid
	 */
	public static void validateLastName(String lastName) throws UmsException {
		if(null == lastName || lastName.length() == 0) throw new InvalidLastNameException();
		if(lastName.indexOf(InvalidLastNameException.CODE) > -1) throw new InvalidLastNameException();
	}

	/**
	 * Validate persistent flag of the room.
	 * 
	 * @param persistent
	 * 
	 * @throws UmsException
	 */
	public static void validatePersistent(Boolean persistent) throws UmsException {
		if(null == persistent) throw new EmptyPersistentException();
	}
	
	/**
	 * Validate accept / reject guest client iq id.
	 * 	
	 * @param iqId
	 * 			- stanza iq id
	 * 
	 * @throws UmsException
	 * 			- if there is an invalid iq id
	 */
	public static void validateUsers(String iqId) throws UmsException {
		if(iqId.indexOf(InvalidUserException.CODE) > -1) throw new InvalidUserException();
	}
	
	
	/**
	 * Validate JSON key field if required
	 * 	
	 * @param reqBody
	 * 			- request body
	 * 
	 * @throws UmsException
	 * 			- if there is an invalid JSON key field
	 */
	public static void missingOrEmptyField(String reqBody) throws MissingOrEmptyFieldException {
		if(reqBody.indexOf(MissingOrEmptyFieldException.CODE) > -1) throw new MissingOrEmptyFieldException();
	}
	
	
	
	/**
	 * Validate JSON key value if required
	 * 	
	 * @param reqBody
	 * 			- request body
	 * 
	 * @throws UmsException
	 * 			- if there is an invalid JSON value field
	 */
	public static void invalidOrMissingField(String reqBody) throws InvalidOrMissingFieldException {
		if(reqBody.indexOf(InvalidOrMissingFieldException.CODE) > -1) throw new InvalidOrMissingFieldException();
	}
	
	
	/**
	 * Check if participant list is available.
	 * 
	 * @param rjid
	 * 			- jid of the room
	 * 
	 * @throws UmsException
	 * 			-if jid is invalid
	 */
	public static void validateIfParticipantListAvailable(String roomJid) throws UmsException {
		if(roomJid.indexOf(ParticipantListUnavailableException.CODE) > -1)	throw new ParticipantListUnavailableException();
	}
	
	/**
	 * Check if a user exists in the room.
	 * 
	 * @param rjid
	 * 			- jid of the room
	 * 
	 * @throws UmsException
	 * 			-if jid is invalid
	 */
	public static void checkIfUserExistsInRoom(String roomJid) throws UmsException {
		if(roomJid.indexOf(CheckIfUserExistsInRoomException.CODE) > -1)	throw new CheckIfUserExistsInRoomException();
	}
	
	/**
	 * Check if no results found.
	 * 
	 * @param udid
	 * 			- udid of the room
	 * 
	 * @throws UmsException
	 * 			-if udid has no results
	 */
	public static void checkIfNoReultFound(String udid) throws UmsException {
		if(udid.indexOf(NoResultFoundException.CODE) > -1)	throw new NoResultFoundException();
	}
}