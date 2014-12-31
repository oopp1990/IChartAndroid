package net.cxd.andimclient.api;

import com.nb82.entity.Task;

import net.cxd.im.entity.FriendGroup;
import net.cxd.im.entity.UserFriend;
import net.cxd.im.entity.UserInfo;
import net.cxd.im.service.UserHttpService;
import net.cxd.im.service.impl.UserHttpServiceImpl;

public class UserService {
	private static UserHttpService httpService = new UserHttpServiceImpl();

	private static UserHttpService getHttpService() {
		return httpService == null ? new UserHttpServiceImpl() : httpService;
	}

	public Object login(Task task) throws Exception {
		return getHttpService().login((String) task.params.get("name"),
				(String) task.params.get("password"));
	}

	public Object regist(Task task) throws Exception {
		return getHttpService().regist((String) task.params.get("name"),
				(String) task.params.get("password"));
	}

	public Object changePassword(Task task) throws Exception {
		return getHttpService().changePassword(
				(Integer) task.params.get("uid"),
				(String) task.params.get("password"));
	}

	public Object updateUserInfo(Task task) throws Exception {
		return getHttpService().updateUserInfo(
				(UserInfo) task.params.get("userInfo"));
	}

	public Object addGroup(Task task) throws Exception {
		return getHttpService().addGroup(
				(FriendGroup) task.params.get("friendGroup"));
	}

	public Object deleteFriendGroup(Task task) throws Exception {
		return getHttpService().deleteFriendGroup(
				(FriendGroup) task.params.get("friendGroup"));
	}

	public Object addFriend(Task task) throws Exception {
		return getHttpService().addFriend(
				(UserFriend) task.params.get("userFriend"));
	}

	public Object moveFriendToOtherGroup(Task task) throws Exception {
		return getHttpService()
				.moveFriendToOtherGroup((String) task.params.get("id"),
						(String) task.params.get("gid"));
	}

	public Object removeFriend(Task task) throws Exception {
		return getHttpService().removeFriend((String) task.params.get("id"),
				(String) task.params.get("gid"));
	}

	public Object getAllFriend(Task task) throws Exception {
		return getHttpService().getAllFriend((String) task.params.get("uid"));
	}
}
