package com.e114.e114_eumyuratodemo1.controller;

import com.e114.e114_eumyuratodemo1.dto.ArtistMemberDTO;
import com.e114.e114_eumyuratodemo1.dto.CommonMemberDTO;
import com.e114.e114_eumyuratodemo1.dto.EnterpriseMemberDTO;
import com.e114.e114_eumyuratodemo1.jdbc.ArtistMemberDAO;
import com.e114.e114_eumyuratodemo1.jdbc.CommonMemberDAO;
import com.e114.e114_eumyuratodemo1.jdbc.EnterpriseMemberDAO;
import com.e114.e114_eumyuratodemo1.jwt.JwtUtils;
import com.e114.e114_eumyuratodemo1.service.ArtistService;
import com.e114.e114_eumyuratodemo1.service.CommonService;
import com.e114.e114_eumyuratodemo1.service.EnterpriseService;
import com.e114.e114_eumyuratodemo1.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoginJoinController {

    @Autowired
    private CommonService commonService;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private CommonMemberDAO commonMemberDAO;

    @Autowired
    private ArtistMemberDAO artistMemberDAO;

    @Autowired
    private EnterpriseMemberDAO enterpriseMemberDAO;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private MemberService memberService;

    @GetMapping("/home")
    public ModelAndView getHomePage() {
        ModelAndView mav = new ModelAndView("html/main/home");
        mav.addObject("top5Artists", artistService.selectTop5Artists());
        return mav;
    }

    @PostMapping("/top5artists")
    @ResponseBody
    public List<ArtistMemberDTO> getTop5Artists() {
        return artistService.selectTop5Artists();
    }


    @PostMapping("/profile")
    @ResponseBody
    public Map<String, String> profile(HttpServletRequest request) {
        String commonURI = "/profile/common/account";
        String artistURI = "/profile/artist/account";
        String enterURI = "/profile/ent/account";
        String adminURI = "/profile/admin/account";
        String notloginURI = "/loginjoin/common/login";

        String URI = jwtUtils.authByRole(request, commonURI, artistURI, enterURI, adminURI);

        if (URI == null) {
            Map<String, String> result = new HashMap<>();
            result.put("URI", notloginURI);
            return result;
        } else {
            Map<String, String> result = new HashMap<>();
            result.put("URI", URI);
            return result;
        }
    }

    //일반 로그인
    @GetMapping("/loginjoin/common/login")
    public String login() {
        return "html/loginJoin/loginForm1";
    }

    @PostMapping("/loginjoin/common/login-token")
    @ResponseBody
    public Map<String, String> login(@RequestParam("id") String id,
                                     @RequestParam("pwd") String pwd, HttpServletResponse response) throws IOException {
        CommonMemberDTO commonMemberDTO = commonService.login(id, pwd);
        if (commonMemberDTO != null) {

            String accessToken =
                    jwtUtils.createAccessToken(commonMemberDTO.getAdminNum(), commonMemberDTO.getId(), commonMemberDTO.getName());
            response.setHeader("Authorization", "Bearer " + accessToken);

            Map<String, String> result = new HashMap<>();
            result.put("jwtToken", accessToken);
            return result;
        } else {
            return null;
        }
    }


    //아티스트 로그인
    @GetMapping("/loginjoin/artist/login")
    public String login_art() {
        return "html/loginJoin/loginForm2";
    }

    @PostMapping("/loginjoin/artist/login-token")
    @ResponseBody
    public Map<String, String> loginArt(@RequestParam("id") String id,
                                        @RequestParam("pwd") String pwd, HttpServletResponse response) throws IOException {
        ArtistMemberDTO artistMemberDTO = artistService.login(id, pwd);
        if (artistMemberDTO != null) {
            String jwtToken =
                    jwtUtils.createAccessToken(artistMemberDTO.getAdminNum(), artistMemberDTO.getId(), artistMemberDTO.getName());
            response.setHeader("Authorization", "Bearer " + jwtToken);


            Map<String, String> result = new HashMap<>();
            result.put("jwtToken", jwtToken);
            return result;
        } else {
            return null;
        }
    }


    //기업 로그인
    @GetMapping("/loginjoin/enterprise/login")
    public String login_enter() {
        return "html/loginJoin/loginForm3";
    }

    @PostMapping("/loginjoin/enterprise/login-token")
    @ResponseBody
    public Map<String, String> loginenter(@RequestParam("id") String id,
                                          @RequestParam("pwd") String pwd, HttpServletResponse response) throws IOException {
        EnterpriseMemberDTO enterpriseMemberDTO = enterpriseService.login(id, pwd);
        if (enterpriseMemberDTO != null) {
            String jwtToken =
                    jwtUtils.createAccessToken(enterpriseMemberDTO.getAdminNum(), enterpriseMemberDTO.getId(), enterpriseMemberDTO.getName());
            response.setHeader("Authorization", "Bearer " + jwtToken);


            Map<String, String> result = new HashMap<>();
            result.put("jwtToken", jwtToken);
            return result;
        } else {
            return null;
        }
    }

    //회원가입
    @GetMapping("/loginjoin/joinchooes")
    public String joinchooes() {
        return "html/loginJoin/joinChooes";
    }

    //일반 회원가입
    @GetMapping("/loginjoin/common/join")
    public String commonJoin() {
        return "html/loginJoin/joinForm_1";
    }

    @PostMapping("/loginjoin/common/join")
    public String commonJoinRegister(
            @RequestParam("id") String id,
            @RequestParam("pwd") String pwd,
            @RequestParam("name") String name,
            @RequestParam("nid") String nid,
            @RequestParam("sex") String sex,
            @RequestParam("birth") String birth,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("road") String road,
            @RequestParam("genre") String genre,
            Model model) {

        boolean result = commonService.register(id, pwd, name, nid, sex, birth, email, phone, road, genre);
        if (result) {
            return "redirect:/loginjoin/common/login";
        } else {
            return "redirect:/loginjoin/common/join?error";
        }
    }

    //아티스트 회원가입
    @GetMapping("/loginjoin/artist/join")
    public String artistJoin() {
        return "html/loginJoin/joinForm_2";
    }

    @PostMapping("/loginjoin/artist/join")
    public String artistregister(
            @RequestParam("id") String id,
            @RequestParam("pwd") String pwd,
            @RequestParam("name") String name,
            @RequestParam("nid") String nid,
            @RequestParam("sex") String sex,
            @RequestParam("birth") String birth,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("genre") String genre,
            Model model) {

        boolean result = artistService.register(id, pwd, name, nid, sex, birth, email, phone, genre);
        if (result) {
            return "redirect:/loginjoin/artist/login";
        } else {
            return "redirect:/loginjoin/artist/join?error";
        }
    }


    //기업 회원 가입
    @GetMapping("/loginjoin/enterprise/join")
    public String enterpriseJoin() {
        return "html/loginJoin/joinForm_3";
    }

    @PostMapping("/loginjoin/enterprise/join")
    public String enterregister(
            @RequestParam("id") String id,
            @RequestParam("pwd") String pwd,
            @RequestParam("name") String name,
            @RequestParam("num") String num,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            Model model) {

        boolean result = enterpriseService.register(id, pwd, name, num, email, phone);
        if (result) {
            return "redirect:/loginjoin/enterprise/login";
        } else {
            return "redirect:/loginjoin/enterprise/join?error";
        }
    }


    //로그 아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("token"); // 세션에서 토큰 정보 제거

        return "redirect:/home"; // 로그아웃 후 메인 홈페이지로 이동
    }


    // 아이디 찾기
    @GetMapping("/loginjoin/Idfind")
    public String idfind() {
        return "html/loginJoin/Idfind";
    }

    // 아이디 찾기 기능을 처리하는 메서드 추가
    @PostMapping("/findUserId")
    public ResponseEntity<List<String>> findUserId(@RequestBody Map<String, String> params) {
        String name = params.get("name");
        String email = params.get("email");
        List<String> userIds = commonService.findUserIdsByNameAndEmail(name, email);
        return ResponseEntity.ok(userIds);
    }

    @GetMapping("/loginjoin/Pwfind")
    public String Pwfind() {
        return "html/loginJoin/pwfind";
    }

    @PostMapping("/loginjoin/Pwfind")
    public String findPassword(@RequestParam String id, @RequestParam String name, @RequestParam String email, Model model) {
        // 입력받은 정보를 이용해 회원 정보를 조회합니다.
        CommonMemberDTO member = commonMemberDAO.findById(id);
        if (member == null || !member.getName().equals(name) || !member.getEmail().equals(email)) {
            // CommonMemberDTO로 조회한 결과가 없는 경우
            ArtistMemberDTO artistMember = artistMemberDAO.findById(id);
            if (artistMember == null || !artistMember.getName().equals(name) || !artistMember.getEmail().equals(email)) {
                // ArtistMemberDTO로 조회한 결과가 없는 경우
                EnterpriseMemberDTO enterpriseMember = enterpriseMemberDAO.findById(id);
                if (enterpriseMember == null || !enterpriseMember.getName().equals(name) || !enterpriseMember.getEmail().equals(email)) {
                    // EnterpriseMemberDTO로 조회한 결과도 없는 경우
                    model.addAttribute("errorMessage", "입력한 정보와 일치하는 회원이 존재하지 않습니다.");
                    return "loginjoin/find_password_result";
                } else {
                    // EnterpriseMemberDTO로 조회한 결과가 있는 경우
                    // 비밀번호 업데이트 및 임시 비밀번호 발급
                    String tempPassword = memberService.generateTempPassword();
                    enterpriseMemberDAO.updatePassword(enterpriseMember.getId(), tempPassword);
                    memberService.sendTempPasswordByEmail(enterpriseMember.getEmail(), tempPassword);
                    model.addAttribute("tempPasswordSent", true);
                    return "redirect:/loginjoin/enterprise/login";
                }
            } else {
                // ArtistMemberDTO로 조회한 결과가 있는 경우
                // 비밀번호 업데이트 및 임시 비밀번호 발급
                String tempPassword = memberService.generateTempPassword();
                artistMemberDAO.updatePassword(artistMember.getId(), tempPassword);
                memberService.sendTempPasswordByEmail(artistMember.getEmail(), tempPassword);
                model.addAttribute("tempPasswordSent", true);
                return "redirect:/loginjoin/artist/login";
            }
        } else {
            // CommonMemberDTO로 조회한 결과가 있는 경우
            // 비밀번호 업데이트 및 임시 비밀번호 발급
            String tempPassword = memberService.generateTempPassword();
            commonMemberDAO.updatePassword(member.getId(), tempPassword);
            memberService.sendTempPasswordByEmail(member.getEmail(), tempPassword);
            model.addAttribute("tempPasswordSent", true);
            return "redirect:/loginjoin/common/login";
        }

    }


/*
    @PostMapping("/loginjoin/artist/join")
    public String eRegister(ArtistMemberDTO dto,Model model) {
        boolean result = userService.artistregister(dto, UserType.ARTIST);

        if (result) {
            return "redirect:/loginjoin/common/login";
        } else {
            model.addAttribute("error", "회원가입에 실패했습니다.");
            return "redirect:/loginjoin/artist/join?error";
        }
    }
*/


    //중복 확인
    @GetMapping("/checkIdDuplicate/{id}")
    public ResponseEntity<Map<String, Boolean>> checkIdDuplicate(@PathVariable String id) {
        boolean duplicate = commonMemberDAO.useById(id) != null;
        Map<String, Boolean> response = Collections.singletonMap("duplicate", duplicate);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/checkNidDuplicate/{nid}")
    public ResponseEntity<Map<String, Boolean>> checkNidDuplicate(@PathVariable String nid) {
        boolean duplicate = commonMemberDAO.useByNid(nid) != null;
        Map<String, Boolean> response = Collections.singletonMap("duplicate", duplicate);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/mypage")
    public String mypage(Model model, HttpSession session) {
        Object loginUser = session.getAttribute("loginUser");
        int adminNum = -1;
        if (loginUser instanceof CommonMemberDTO) {
            adminNum = ((CommonMemberDTO) loginUser).getAdminNum();
        } else if (loginUser instanceof ArtistMemberDTO) {
            adminNum = ((ArtistMemberDTO) loginUser).getAdminNum();
        } else if (loginUser instanceof EnterpriseMemberDTO) {
            adminNum = ((EnterpriseMemberDTO) loginUser).getAdminNum();
        }

        switch (adminNum) {
            case 0: // 관리자
                return "redirect:/profile/admin/account";
            case 1: // 일반 회원
                return "redirect:/profile/admin/modify";
            case 2: // 아티스트 회원
                return "redirect:/profile/admin/management/view";
            case 3: // 기업 회원
                return "redirect:/enterprise-page";
            default:
                return "redirect:/login-common";
        }
    }

}

/*    @GetMapping("/mypage")
    public String mypage(HttpSession session, Model model) {
        // 세션에서 현재 로그인된 사용자 정보를 가져온다
        CommonMemberDTO user = (CommonMemberDTO) session.getAttribute("user");
        if (user == null) { // 로그인되어있지 않으면 로그인 페이지로 이동
            return "redirect:/login?error";
        } else {
            // 현재 사용자가 가진 권한 정보를 가져와 사용자 객체에 추가
            List<String> roles = userService.getUserRoles(user.getId());
            user.setRoles(roles);
            // 모델에 사용자 정보를 담아 마이페이지 화면을 반환
            model.addAttribute("user", user);
            return "html/loginJoin/mypage";
        }
    }

    // 토큰 정보를 반환하는 API
    @GetMapping("/token")
    @ResponseBody
    public String getToken(HttpSession session) {
        // 세션에서 현재 로그인된 사용자 정보를 가져온다
        CommonMemberDTO user = (CommonMemberDTO) session.getAttribute("user");
        if (user == null) { // 로그인되어있지 않으면 에러 메시지 반환
            return "로그인 후 이용해주세요.";
        } else {
            // 현재 사용자가 가진 권한 정보를 가져와 JWT 토큰을 생성
            List<String> roles = userService.getUserRoles(user.getId());
            String token = jwtTokenProvider.createToken(user.getId(), roles);
            // 생성된 토큰 값 반환
            return "발행된 토큰 값: " + token;
        }
    }

        @PostMapping("/login")
    public String login(@RequestParam("id") String id, @RequestParam("pwd") String pwd, HttpSession session) {
            CommonMemberDTO commonMemberDTO = userService.login(id, pwd); // 사용자 정보 조회
            if (commonMemberDTO != null) {
                List<String> roles = userService.getUserRoles(id); // 사용자의 권한 정보 조회
                String token = jwtTokenProvider.createToken(commonMemberDTO.getId(), roles); // JWT 토큰 생성
                commonMemberDTO.setRoles(roles); // 사용자 정보에 권한 정보 추가
                session.setAttribute("user", commonMemberDTO); // HttpSession에 사용자 정보 저장
                return "redirect:/mypage"; // 마이페이지로 이동
            } else {
                return "redirect:/login?error"; // 에러 페이지로 이동
            }
        }*/


