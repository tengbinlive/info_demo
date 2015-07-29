package com.touyan.investment.helper;

import com.dao.GroupDetalDO;
import com.dao.InviteMessageDO;
import com.dao.UserInfoDO;
import com.touyan.investment.bean.message.GroupDetail;
import com.touyan.investment.bean.message.InviteMessage;
import com.touyan.investment.bean.user.UserInfo;

public class BeanCopyHelper {

    public static UserInfo cast2UserInfo(UserInfoDO source) {
        if (source == null) return null;
        UserInfo dist = new UserInfo();
        dist.setServno(source.getServno());
        dist.setAucode(source.getAucode());
        dist.setCompny(source.getCompny());
        dist.setInrank(source.getInrank());
        dist.setIvcode(source.getIvcode());
        dist.setLocatn(source.getLocatn());
        dist.setPostin(source.getPostin());
        dist.setRscope(source.getRscope());
        dist.setTags(source.getTags());
        dist.setTeleph(source.getTeleph());
        dist.setUalias(source.getUalias());
        dist.setUisvip(source.getUisvip());
        dist.setUphoto(source.getUphoto());
        dist.setUavail(source.getUavail());
        dist.setNameSort(source.getNameSort());
        return dist;
    }

    public static UserInfoDO cast2UserInfoDO(UserInfo source) {
        if (source == null) return null;
        UserInfoDO dist = new UserInfoDO();
        dist.setServno(source.getServno());
        dist.setAucode(source.getAucode());
        dist.setCompny(source.getCompny());
        dist.setInrank(source.getInrank());
        dist.setIvcode(source.getIvcode());
        dist.setLocatn(source.getLocatn());
        dist.setPostin(source.getPostin());
        dist.setRscope(source.getRscope());
        dist.setTags(source.getTags());
        dist.setTeleph(source.getTeleph());
        dist.setUalias(source.getUalias());
        dist.setUisvip(source.getUisvip());
        dist.setUphoto(source.getUphoto());
        dist.setUavail(source.getUavail());
        dist.setNameSort(source.getNameSort());
        return dist;
    }

    public static GroupDetail cast2GroupDetal(GroupDetalDO source) {
        if (source == null) return null;
        GroupDetail dist = new GroupDetail();
        dist.setApproval(source.getApproval());
        dist.setCanadd(source.getCanadd());
        dist.setDesc(source.getDesc());
        dist.setGphoto(source.getGphoto());
        dist.setGroupid(source.getGroupid());
        dist.setGroupname(source.getGroupname());
        dist.setMaxusers(source.getMaxusers());
        dist.setMemnum(source.getMemnum());
        dist.setOwner(source.getOwner());
        dist.setPayfor(source.getPayfor());
        dist.setValidt(source.getValidt());
        dist.setVisble(source.getVisble());
        dist.setIstoll(source.getIstoll());
        return dist;
    }

    public static GroupDetalDO cast2GroupDetalDO(GroupDetail source) {
        if (source == null) return null;
        GroupDetalDO dist = new GroupDetalDO();
        dist.setApproval(source.getApproval());
        dist.setCanadd(source.getCanadd());
        dist.setDesc(source.getDesc());
        dist.setGphoto(source.getGphoto());
        dist.setGroupid(source.getGroupid());
        dist.setGroupname(source.getGroupname());
        dist.setMaxusers(source.getMaxusers());
        dist.setMemnum(source.getMemnum());
        dist.setOwner(source.getOwner());
        dist.setPayfor(source.getPayfor());
        dist.setValidt(source.getValidt());
        dist.setVisble(source.getVisble());
        dist.setIstoll(source.getIstoll());
        return dist;
    }

    public static InviteMessageDO cast2InviteMessageDO(InviteMessage source) {
        if (source == null) return null;
        InviteMessageDO dist = new InviteMessageDO();
        dist.setFrom(source.getFrom());
        dist.setTime(source.getTime());
        dist.setReason(source.getReason());
        dist.setStatus(source.getStatus().ordinal());
        dist.setGroupId(source.getGroupId());
        dist.setUnreadCount(source.getUnreadCount());
        dist.setGroupName(source.getGroupName());
        return dist;
    }

    public static InviteMessage cast2InviteMessage(InviteMessageDO source) {
        if (source == null) return null;
        InviteMessage dist = new InviteMessage();
        dist.setFrom(source.getFrom());
        dist.setTime(source.getTime());
        dist.setReason(source.getReason());
        int status = source.getStatus();
        if (status == InviteMessage.InviteMesageStatus.BEINVITEED.ordinal())
            dist.setStatus(InviteMessage.InviteMesageStatus.BEINVITEED);
        else if (status == InviteMessage.InviteMesageStatus.BEAGREED.ordinal())
            dist.setStatus(InviteMessage.InviteMesageStatus.BEAGREED);
        else if (status == InviteMessage.InviteMesageStatus.BEREFUSED.ordinal())
            dist.setStatus(InviteMessage.InviteMesageStatus.BEREFUSED);
        else if (status == InviteMessage.InviteMesageStatus.AGREED.ordinal())
            dist.setStatus(InviteMessage.InviteMesageStatus.AGREED);
        else if (status == InviteMessage.InviteMesageStatus.REFUSED.ordinal())
            dist.setStatus(InviteMessage.InviteMesageStatus.REFUSED);
        else if (status == InviteMessage.InviteMesageStatus.BEAPPLYED.ordinal()) {
            dist.setStatus(InviteMessage.InviteMesageStatus.BEAPPLYED);
        }
        dist.setGroupId(source.getGroupId());
        dist.setUnreadCount(source.getUnreadCount());
        dist.setGroupName(source.getGroupName());
        return dist;
    }

}
