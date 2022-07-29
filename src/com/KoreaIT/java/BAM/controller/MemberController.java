package com.KoreaIT.java.BAM.controller;

import java.util.List;
import java.util.Scanner;

import com.KoreaIT.java.BAM.container.Container;
import com.KoreaIT.java.BAM.dto.Member;
import com.KoreaIT.java.BAM.service.MemberService;
import com.KoreaIT.java.BAM.util.Util;

public class MemberController extends Controller {
	private Scanner sc;
	private List<Member> members;
	private String cmd;
	private String actinMethodName;
	private MemberService memberService;

	public MemberController(Scanner sc) {
		this.sc = sc;
		memberService = Container.memberService;
	}

	public void doAction(String cmd, String actinMethodName) {
		this.cmd = cmd;
		this.actinMethodName = actinMethodName;

		switch (actinMethodName) {
		case "join":
			doJoin();
			break;
		case "login":
			doLogin();
			break;
		case "logout":
			doLogout();
			break;
		case "profile":
			showProfile();
			break;
		default:
			System.out.println("존재하지 않는 명령어입니다.");
			break;

		}
	}

	private void doLogout() {

		loginedMember = null;
		System.out.println("로그아웃 되었습니다.");
	}

	private void doLogin() {

		Member member = null;
		String loginPw = null;
		while (true) {

			System.out.printf("로그인 아이디 : ");
			String loginId = sc.nextLine();
			if (loginId.trim().length() == 0) {
				System.out.println("로그인 아이디를 입력해주세요.");
				continue;
			}

			while (true) {

				System.out.printf("로그인 비밀번호 : ");
				loginPw = sc.nextLine();
				if (loginPw.trim().length() == 0) {
					System.out.println("로그인 아이디를 입력해주세요.");
					continue;
				}
				break;
			}

			member = memberService.getMemberByLoginId(loginId);

			if (member == null) {
				System.out.println("일치하는 회원이 없습니다.");
				return;
			}

			if (member.loginPw.equals(loginPw) == false) {
				System.out.println("비밀번호를 다시 입력해주세요.");
				return;
			}
			break;
		}

		loginedMember = member;
		System.out.printf("로그인 성공 ! %s님 환영 합니다.\n", loginedMember.name);
	}

	private void showProfile() {

		System.out.printf("로그인 아이디 : %s\n", loginedMember.loginId);
		System.out.printf("d이름 : %s\n", loginedMember.name);
	}

	public void doJoin() {
		int id = memberService.setNewId();
		;
		String regDate = Util.getNowDateStr();

		String loginId = null;

		while (true) {
			System.out.printf("로그인 아이디 : ");
			loginId = sc.nextLine();
			if (memberService.isJoinableLoginId(loginId) == false) {
				System.out.printf("%s은(는) 이미 사용중인 아이디입니다.\n", loginId);
			}
			break;
		}

		String loginPw = null;
		String loginPwConfirm = null;

		while (true) {

			System.out.printf("로그인 비밀번호 : ");
			loginPw = sc.nextLine();
			System.out.printf("로그인 비밀번호 확인 : ");
			loginPwConfirm = sc.nextLine();

			if (loginPw.equals(loginPwConfirm) == false) {
				System.out.println("비밀번호를 다시 입력해주세요.");
				continue;
			}
			break;
		}

		System.out.printf("이름 : ");
		String name = sc.nextLine();

		Member member = new Member(id, regDate, loginId, loginPw, name);
		memberService.add(member);

		System.out.printf("%d번 회원님 환영합니다.\n", id);

	}

	public void makeTestData() {
		System.out.println("테스트를 위한 회원 데이터를 생성합니다.");

		memberService.add(new Member(memberService.setNewId(), Util.getNowDateStr(), "test1", "test1", "김철수"));
		memberService.add(new Member(memberService.setNewId(), Util.getNowDateStr(), "test2", "test2", "김영희"));
		memberService.add(new Member(memberService.setNewId(), Util.getNowDateStr(), "test3", "test3", "김철구"));
	}
}
