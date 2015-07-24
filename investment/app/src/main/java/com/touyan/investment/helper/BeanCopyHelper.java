package com.touyan.investment.helper;

import com.dao.GroupDetalDO;
import com.dao.UserInfoDO;
import com.touyan.investment.bean.message.GroupDetal;
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

    public static GroupDetal cast2GroupDetal(GroupDetalDO source) {
        if (source == null) return null;
        GroupDetal dist = new GroupDetal();
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
        return dist;
    }

    public static GroupDetalDO cast2GroupDetalDO(GroupDetal source) {
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
        return dist;
    }

}
