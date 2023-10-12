package com.sajits.sajer.wssb.presentation;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.sajits.sajer.core.auth.User;
import com.sajits.sajer.core.auth.Visitor;
import com.sajits.sajer.core.auth.setting.Setting;
import com.sajits.sajer.wssb.Utils;
import com.sajits.sajer.wssb.application.UseSajer;
import com.sajits.sajer.wssb.configuration.security.services.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/p/auth")
public class AuthAPI {
    @Autowired
    UseSajer useSajer;
    
    @GetMapping("/visit")
	public Visitor visit(@RequestParam("uuid") String uuid) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = uuid;
        boolean authenticated = false;
        if( authentication.getName() != "anonymousUser" ){
            UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
            String currentPrincipalName = userPrincipal.getVisitor().getId();
            id = currentPrincipalName;
            authenticated = true;
        }
		return useSajer.visit(id, authenticated, uuid);
	}

    @GetMapping("/token")
    public String token(@RequestParam("tempk") String tempk) {
        return useSajer.getAuth().tokenFromTempKey(tempk);
    }

    @GetMapping("/login")
	void login(@RequestParam("state") String state,@RequestParam("type") String type,HttpServletResponse httpServletResponse) {
        httpServletResponse.setHeader("Location", useSajer.getAuth().login(state, type));
        httpServletResponse.setStatus(302);
	}

    @GetMapping("/redirect")
	void redirect(@RequestParam("code") String code, @RequestParam("state") String state,HttpServletResponse httpServletResponse) {
        String ret1 = useSajer.getAuth().redirect(code, state, "google");
        String userId = ret1.substring(ret1.indexOf("?userId=")+8);
        String key = useSajer.getAuth().addTempLoginInfo(userId);
        String modifiedState = ret1.substring(0,ret1.indexOf("?userId="));
		httpServletResponse.setHeader("Location", modifiedState + "?tempk=" + key);
		httpServletResponse.setStatus(302);
	}

    @GetMapping("/chat")
	ResponseEntity<?> chat() {
		return ResponseEntity.ok(useSajer.getAuth().chat(Utils.getId()));
	}

    @GetMapping("/send-comment")
	ResponseEntity<?> sendComment(@RequestParam("comment") String comment) {
		return ResponseEntity.ok(useSajer.getAuth().sendComment(Utils.getId(), comment));
	}

    @GetMapping("/token-login")
	String loginWithToken(@RequestParam("token") String token,@RequestParam("email") String email,@RequestParam("name") String name,@RequestParam("fname") String fname,@RequestParam("lname") String lname, @RequestParam("picture") String picture, @RequestParam("exp") long exp)  {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setFname(fname);
        user.setLname(lname);
        user.setPicture(picture);
		return useSajer.getAuth().loginWithToken(user, token);
	}

    @GetMapping("/update-profile")
	public User updateProfile(@RequestParam("email") String email,@RequestParam("name") String name,@RequestParam("fname") String fname,@RequestParam("lname") String lname, @RequestParam("picture") String picture)  {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setFname(fname);
        user.setLname(lname);
        user.setPicture(picture);
		return useSajer.getAuth().updateProfile(Utils.getId(), user);
	}

    @GetMapping("/setting")
	public Setting setting()  {
		return useSajer.getAuth().getUserService().getSetting(Utils.getId());
	}

    @PostMapping("/update-setting")
    public ResponseEntity<Setting> updateSetting(@Valid @RequestBody Setting setting) {
        return ResponseEntity.ok(useSajer.getAuth().getUserService().updateSetting(Utils.getId(), setting));
    }

}
