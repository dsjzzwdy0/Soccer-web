package com.loris.auth.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.loris.auth.log.LogManager;
import com.loris.auth.log.LogTaskFactory;
import com.loris.auth.model.User;
import com.loris.auth.model.node.MenuNode;
import com.loris.auth.security.ShiroKit;
import com.loris.auth.security.ShiroUser;
import com.loris.auth.service.MenuService;
import com.loris.auth.service.UserService;
import com.loris.auth.util.KaptchaUtil;
import com.loris.common.constant.Constants;
import com.loris.common.exception.InvalidKaptchaException;
import com.loris.common.util.ToolUtil;
import com.loris.common.web.BaseController;

import static com.loris.common.support.HttpKit.getIp;
/**
 * 登录控制器
 *
 * @author fengshuonan
 * @Date 2017年1月10日 下午8:25:24
 */
@Controller
public class LoginController extends BaseController
{
	@Autowired
	MenuService menuService;

	@Autowired
	UserService userService;

	/**
	 * 跳转到主页
	 */
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String index(Model model)
	{
		// 获取菜单列表
		ShiroUser shiroUser = ShiroKit.getUser();
		if (shiroUser == null)
		{
			ShiroKit.getSubject().logout();
			model.addAttribute("tips", "该用户没有角色，无法登陆");
			return "/login";
		}
		List<Integer> roleList = ShiroKit.getUser().getRoleList();
		if (roleList == null || roleList.size() == 0)
		{
			ShiroKit.getSubject().logout();
			model.addAttribute("tips", "该用户没有角色，无法登陆");
			return "/login";
		}
		List<MenuNode> menus = menuService.getMenusByRoleIds(roleList);
		List<MenuNode> titles = MenuNode.buildTitle(menus);
		model.addAttribute("titles", titles);

		// 获取用户头像
		Integer id = ShiroKit.getUser().getId();
		User user = userService.getById(id);
		String avatar = user.getAvatar();
		model.addAttribute("avatar", avatar);

		return "/main";
	}

	/**
	 * 跳转到登录页面
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login()
	{
		if (ShiroKit.isAuthenticated() && ShiroKit.getUser() != null)
		{
			return REDIRECT + "/main";
		}
		else
		{
			return "/login";
		}
	}

	/**
	 * 点击登录执行的动作
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginVali(String username, String password, String kaptcha, HttpServletRequest request)
	{
		// 验证验证码是否正确
		if (KaptchaUtil.getKaptchaOnOff())
		{
			String code = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
			if (ToolUtil.isEmpty(kaptcha) || !kaptcha.equals(code))
			{
				throw new InvalidKaptchaException();
			}
		}

		Subject currentUser = ShiroKit.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password.toCharArray());
		token.setRememberMe(true);

		currentUser.login(token);

		ShiroUser shiroUser = ShiroKit.getUser();
		super.getSession().setAttribute("shiroUser", shiroUser);
		super.getSession().setAttribute("username", shiroUser.getAccount());

		LogManager.me().executeLog(LogTaskFactory.loginLog(shiroUser.getId(), getIp()));

		ShiroKit.getSession().setAttribute("sessionFlag", true);

		return REDIRECT + "/";
	}

	/**
	 * 退出登录
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logOut()
	{
		LogManager.me().executeLog(LogTaskFactory.exitLog(ShiroKit.getUser().getId(), getIp()));
		ShiroKit.getSubject().logout();
		return REDIRECT + "/login";
	}
}
