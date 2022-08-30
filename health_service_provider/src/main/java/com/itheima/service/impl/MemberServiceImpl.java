package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.entity.MD5Utils;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberDao memberDao;

    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    public void add(Member member) {
        if(member.getPassword() != null){
            member.setPassword(MD5Utils.md5(member.getPassword()));
        }
        memberDao.add(member);
    }

    public List<Integer> findMemberCountByMonth(List<String> months) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (String month : months) {
            String date=month+".31";
            Integer memberCountBeforeDate = memberDao.findMemberCountBeforeDate(date);
            list.add(memberCountBeforeDate);
        }


        return list;
    }


}
