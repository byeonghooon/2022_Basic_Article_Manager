package com.KoreaIT.java.BAM.dao;

import java.util.ArrayList;
import java.util.List;

import com.KoreaIT.java.BAM.dto.Member;

public class MemberDao extends Dao {
	public List<Member> members;

	public MemberDao() {
		members = new ArrayList<>();
	}

	public void add(Member member) {
		members.add(member);
		lastId++;
	}

	public boolean isJoinableLoginId(String loginId) {
		int index = getMemberIndexbyLoginId(loginId);

		if (index == -1) {
			return true;
		}

		return false;
	}

	public Member getMemberByLoginId(String loginId) {
		int index = getMemberIndexbyLoginId(loginId);

		if (index == -1) {
			return null;
		}

		return members.get(index);
	}

	public int getMemberIndexbyLoginId(String loginId) {
		int i = 0;
		for (Member member : members) {
			if (member.loginId.equals(loginId)) {
				return i;
			}
			i++;
		}
		return -1;
	}

	public List<Member> getMembers() {
		return members;
	}

	public String getMemberNameById(int memberid) {
		for (Member member : members) {
			if (memberid == member.id) {
				return member.name;
			}
		}
		return null;
	}
}