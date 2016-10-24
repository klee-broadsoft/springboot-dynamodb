package com.broadsoft.ums.boot.rest.muc.roomlist;

import java.util.ArrayList;
import java.util.List;

import com.broadsoft.ums.boot.rest.BaseResponse;
import com.broadsoft.ums.boot.rest.Status.ResponseTypes;

public class GetRoomListResponse extends BaseResponse {
	
	private static final String GET_ROOM_LIST_SUCCESSFUL = "0000004";
	
	private static final String ROOM_LIST_LIST_RETRIEVED = "Room List Retrieved Successfully";
	
	private List<String> theRoomList;
	
	public GetRoomListResponse() {
		super(GET_ROOM_LIST_SUCCESSFUL, ResponseTypes.SUCCESS, ROOM_LIST_LIST_RETRIEVED);
		theRoomList = new ArrayList<String>();
		theRoomList.add("9ac5ac6f3f6b4c98807059c55f72e3ca-myroom-7a6c6f756973436f66742e636f6d@muc.ihs.broadsoft.com/zlouis@broadsoft.com");
		theRoomList.add("msproject@muc.xyz.broadsoft.com");
	}
	
	public List<String>	getRoomList() {
		return theRoomList;
	}
	
	public void setRoomList(List<String> roomList) {
		this.theRoomList = roomList;
	}
}
