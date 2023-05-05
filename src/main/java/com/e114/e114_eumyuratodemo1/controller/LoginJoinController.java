package com.e114.e114_eumyuratodemo1.controller;

import com.e114.e114_eumyuratodemo1.dto.ArtistMemberDTO;
import com.e114.e114_eumyuratodemo1.dto.CommonMemberDTO;
import com.e114.e114_eumyuratodemo1.dto.EnterpriseMemberDTO;
import com.e114.e114_eumyuratodemo1.jdbc.CommonMemberDAO;
import com.e114.e114_eumyuratodemo1.jwt.JwtUtils;
import com.e114.e114_eumyuratodemo1.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoginJoinController {

    @Autowired
    private UserService userService;

    @Autowired
    private CommonMemberDAO commonMemberDAO;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/")
    public String main(HttpSession session) {
        System.out.println(session.getAttribute("loginUser"));

        return "html/main/home";
    }

    @GetMapping("/profile")
    public String profile(HttpServletRequest request){
        String commonURI =  "html/profile/account/profile_common_account";
        String artistURI = "html/profile/account/profile_artist_account";
        String enterURI = "html/profile/account/profile_enterprise_account";
        String adminURI = "html/profile/root/profile_admin_root";
        String notloginURI = "html/loginJoin/loginForm1";

        String URI = jwtUtils.authByRole(request, commonURI, artistURI, enterURI, adminURI);

        if(URI == null){
            return notloginURI;
        }else{
            return URI;
        }
    }

    //일반 로그인
    @GetMapping("/login-common")
    public String login() {
        return "html/loginJoin/loginForm1";
    }

    @PostMapping("/login-common/token")
    @ResponseBody
    public Map<String, String> login(@RequestParam("id") String id,
                                     @RequestParam("pwd") String pwd,
                                     @RequestParam(name = "prevUrl", required = false) String prevUrl,
                                     HttpSession session, RedirectAttributes redirectAttributes, HttpServletResponse response) throws IOException {
        CommonMemberDTO commonMemberDTO = userService.login(id, pwd);
        if (commonMemberDTO != null) {

            String accessToken =
                    jwtUtils.createAccessToken(commonMemberDTO.getAdminNum(), commonMemberDTO.getId(), commonMemberDTO.getName());
            String refreshToken =
                    jwtUtils.createRefreshToken(commonMemberDTO.getAdminNum(), commonMemberDTO.getId(), commonMemberDTO.getName());
            Cookie cookie = new Cookie("refreshToken",refreshToken);

            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.setHeader("Authorization", "Bearer " + accessToken);
            response.addCookie(cookie);

            Map<String, String> result = new HashMap<>();
            result.put("jwtToken", accessToken);
            return result;
        } else {
            return null;
        }
    }


    //아티스트 로그인
    @GetMapping("/login-art")
    public String login_art() {
        return "html/loginJoin/loginForm2";
    }

    @PostMapping("/login-art/token")
    @ResponseBody
    public Map<String, String> loginArt(@RequestParam("id") String id,
                                        @RequestParam("pwd") String pwd,
                                        HttpServletResponse response) throws IOException {
        ArtistMemberDTO artistMemberDTO = userService.loginArt(id, pwd);
        if (artistMemberDTO != null) {
            String jwtToken =
                    jwtUtils.createAccessToken(artistMemberDTO.getAdminNum(), artistMemberDTO.getId(), artistMemberDTO.getName());
            String refreshToken =
                    jwtUtils.createRefreshToken(artistMemberDTO.getAdminNum(), artistMemberDTO.getId(), artistMemberDTO.getName());
            Cookie cookie = new Cookie("refreshToken",refreshToken);

            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.setHeader("Authorization", "Bearer " + jwtToken);
            response.addCookie(cookie);

            Map<String, String> result = new HashMap<>();
            result.put("jwtToken", jwtToken);
            return result;
        } else {
            return null;
        }
    }


    //기업 로그인
    @GetMapping("/login-enter")
    public String login_enter() {
        return "html/loginJoin/loginForm3";
    }

    @PostMapping("/login-enter/token")
    @ResponseBody
    public Map<String, String> loginenter(@RequestParam("id") String id,
                                          @RequestParam("pwd") String pwd,
                                          HttpServletResponse response) throws IOException {
        EnterpriseMemberDTO enterpriseMemberDTO = userService.loginenter(id, pwd);
        if (enterpriseMemberDTO != null) {
            String jwtToken =
                    jwtUtils.createAccessToken(enterpriseMemberDTO.getAdminNum(), enterpriseMemberDTO.getId(), enterpriseMemberDTO.getName());
            String refreshToken =
                    jwtUtils.createRefreshToken(enterpriseMemberDTO.getAdminNum(), enterpriseMemberDTO.getId(), enterpriseMemberDTO.getName());
            Cookie cookie = new Cookie("refreshToken",refreshToken);

            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.setHeader("Authorization", "Bearer " + jwtToken);
            response.addCookie(cookie);

            Map<String, String> result = new HashMap<>();
            result.put("jwtToken", jwtToken);
            return result;
        } else {
            return null;
        }
    }

//로그 아웃
@GetMapping("/logout")
public String logout(HttpSession session, HttpServletResponse response) {
    session.removeAttribute("token"); // 세션에서 토큰 정보 제거

    //쿠키
    Cookie cookie = new Cookie("refreshToken","");
    cookie.setHttpOnly(true);
    cookie.setPath("/");
    cookie.setMaxAge(0);
    response.addCookie(cookie);

    return "redirect:/"; // 로그아웃 후 메인 홈페이지로 이동
}


// 아이디 찾기
    @GetMapping("/Idfind")
    public String idfind() {
        return "html/loginJoin/Idfind";
    }

    // 아이디 찾기 기능을 처리하는 메서드 추가
    @PostMapping("/findUserId")
    public ResponseEntity<List<String>> findUserId(@RequestBody Map<String, String> params) {
        String name = params.get("name");
        String email = params.get("email");
        List<String> userIds = userService.findUserIdsByNameAndEmail(name, email);
        return ResponseEntity.ok(userIds);
    }

    @GetMapping("/Pwfind")
    public String Pwfind() {
        return "html/loginJoin/pwfind";
    }

    @GetMapping("/joinchooes")
    public String joinchooes() {
        return "html/loginJoin/joinChooes";
    }

    //일반 회원가입
    @GetMapping("/common-join")
    public String commonJoin() {
        return "html/loginJoin/joinForm_1";
    }

    @PostMapping("/common-join")
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


        boolean result = userService.register(id, pwd, name, nid, sex, birth, email, phone, road, genre);
        if (result) {
            return "redirect:/login-common";
        } else {
            return "redirect:/common-join?error";
        }
    }
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


    @GetMapping("/artist-join")
    public String artist() {
        return "html/loginJoin/joinForm_2";
    }

    @GetMapping("/enterprise-join")
    public String enterprise() {
        return "html/loginJoin/joinForm_3";
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


