package life.qyh.community.controller;

import life.qyh.community.dto.AccessTokenDTO;
import life.qyh.community.dto.GithubUser;
import life.qyh.community.mapper.UserMapper;
import life.qyh.community.model.User;
import life.qyh.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Value("${github.client.id}")
    private String clientID;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectURI;
    @Resource
    private GithubProvider githubProvider;
    @Resource
    private UserMapper userMapper;
    @GetMapping("/callback")
    public String callback (@RequestParam(name = "code") String code,
                            @RequestParam(name = "state") String state,
                            HttpServletRequest request,
                            HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientID);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectURI);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser != null) {
            //登录成功 写session
            User user = new User();
            user.setAccount_id(String.valueOf(githubUser.getId()));
            user.setName(githubUser.getName());
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setGmt_create(System.currentTimeMillis());
            user.setGmt_modified(user.getGmt_create());
            user.setAvatar_url(githubUser.getAvatar_url());
            userMapper.insertUser(user);
            response.addCookie(new Cookie("token", token));
//            request.getSession().setAttribute("user", user);
        } else {
            //登录失败，重新登录
        }
        return "redirect:/";
    }

}
