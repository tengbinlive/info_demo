/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.touyan.investment.bean.message;

public class InviteMessage {

	private Long id;

	private String headphoto;

	private int unreadCount; //未读消息数量

	private String from;
	//时间
	private long time;
	//添加理由
	private String reason;
	
	//未验证，已同意等状态
	private InviteMesageStatus status;
	//群id
	private String groupId;
	//群名称
	private String groupName;

	public String getHeadphoto() {
		return headphoto;
	}

	public void setHeadphoto(String headphoto) {
		this.headphoto = headphoto;
	}

	public int getUnreadCount() {
		return unreadCount;
	}

	public void setUnreadCount(int unreadCount) {
		this.unreadCount = unreadCount;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}


	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public InviteMesageStatus getStatus() {
		return status;
	}

	public void setStatus(InviteMesageStatus status) {
		this.status = status;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}



	public enum InviteMesageStatus{
		/**被邀请*/
		BEINVITEED,
		/**被拒绝*/
		BEREFUSED,
		/**对方同意*/
		BEAGREED,
		/**对方申请*/
		BEAPPLYED,
		/**我同意了对方的请求*/
		AGREED,
		/**我拒绝了对方的请求*/
		REFUSED,
		/**其他*/
		OTHER
		
	}

	@Override
	public String toString() {
		return "InviteMessage{" +
				"id=" + id +
				", headphoto='" + headphoto + '\'' +
				", unreadCount=" + unreadCount +
				", from='" + from + '\'' +
				", time=" + time +
				", reason='" + reason + '\'' +
				", status=" + status +
				", groupId='" + groupId + '\'' +
				", groupName='" + groupName + '\'' +
				'}';
	}
}



