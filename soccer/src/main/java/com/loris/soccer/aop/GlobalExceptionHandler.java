package com.loris.soccer.aop;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.UnknownSessionException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.loris.auth.log.LogManager;
import com.loris.auth.log.LogTaskFactory;
import com.loris.auth.security.ShiroKit;
import com.loris.auth.security.ShiroUser;
import com.loris.common.constant.tips.ErrorTip;
import com.loris.common.exception.BussinessException;
import com.loris.common.exception.InvalidKaptchaException;
import com.loris.common.exception.ParamsException;
import com.loris.common.exception.enums.BizExceptionEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.UndeclaredThrowableException;

import static com.loris.common.support.HttpKit.*;

/**
 * 全局的的异常拦截器（拦截所有的控制器）（带有@RequestMapping注解的方法上都会拦截）
 *
 * @author fengshuonan
 * @date 2016年11月12日 下午3:19:56
 */
@ControllerAdvice
public class GlobalExceptionHandler
{
	private static Logger log = Logger.getLogger(GlobalExceptionHandler.class);

	/**
	 * 拦截业务异常
	 *
	 * @author fengshuonan
	 */
	@ExceptionHandler(BussinessException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorTip notFount(BussinessException e)
	{
		LogManager.me().executeLog(LogTaskFactory.exceptionLog(ShiroKit.getUser().getId(), e));
		getRequest().setAttribute("tip", e.getMessage());
		log.error("业务异常:", e);
		return new ErrorTip(e.getCode(), e.getMessage());
	}

	/**
	 * 用户未登录
	 *
	 * @author fengshuonan
	 */
	@ExceptionHandler(AuthenticationException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public String unAuth(AuthenticationException e)
	{
		log.error("用户未登陆：", e);
		return "/test/login";
	}

	/**
	 * 账号被冻结
	 *
	 * @author fengshuonan
	 */
	@ExceptionHandler(DisabledAccountException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public String accountLocked(DisabledAccountException e, Model model)
	{
		String username = getRequest().getParameter("username");
		LogManager.me().executeLog(LogTaskFactory.loginLog(username, "账号被冻结", getIp()));
		model.addAttribute("tips", "账号被冻结");
		return "/login";
	}

	/**
	 * 账号密码错误
	 *
	 * @author fengshuonan
	 */
	@ExceptionHandler(CredentialsException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public String credentials(CredentialsException e, Model model)
	{
		String username = getRequest().getParameter("username");
		LogManager.me().executeLog(LogTaskFactory.loginLog(username, "账号密码错误", getIp()));
		model.addAttribute("tips", "账号密码错误");
		return "/login";
	}

	/**
	 * 验证码错误
	 *
	 * @author fengshuonan
	 */
	@ExceptionHandler(InvalidKaptchaException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String credentials(InvalidKaptchaException e, Model model)
	{
		String username = getRequest().getParameter("username");
		LogManager.me().executeLog(LogTaskFactory.loginLog(username, "验证码错误", getIp()));
		model.addAttribute("tips", "验证码错误");
		return "/login";
	}

	/**
	 * 无权访问该资源
	 *
	 * @author fengshuonan
	 */
	@ExceptionHandler(UndeclaredThrowableException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public ErrorTip credentials(UndeclaredThrowableException e)
	{
		getRequest().setAttribute("tip", "权限异常");
		log.error("权限异常!", e);
		return new ErrorTip(BizExceptionEnum.NO_PERMITION);
	}

	/**
	 * 拦截未知的运行时异常
	 *
	 * @author fengshuonan
	 */
	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorTip notFount(RuntimeException e)
	{
		ShiroUser shiroUser = ShiroKit.getUser();
		LogManager.me().executeLog(LogTaskFactory.exceptionLog(shiroUser != null ? shiroUser.getId() : -100, e));
		getRequest().setAttribute("tip", "服务器未知运行时异常");
		log.error("运行时异常:", e);
		return new ErrorTip(BizExceptionEnum.SERVER_ERROR);
	}

	/**
	 * session失效的异常拦截
	 *
	 * @author stylefeng
	 * @Date 2017/6/7 21:02
	 */
	@ExceptionHandler(InvalidSessionException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String sessionTimeout(InvalidSessionException e, Model model, HttpServletRequest request,
			HttpServletResponse response)
	{
		model.addAttribute("tips", "session超时");
		assertAjax(request, response);
		return "/login";
	}

	/**
	 * session异常
	 *
	 * @author stylefeng
	 * @Date 2017/6/7 21:02
	 */
	@ExceptionHandler(UnknownSessionException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String sessionTimeout(UnknownSessionException e, Model model, HttpServletRequest request,
			HttpServletResponse response)
	{
		model.addAttribute("tips", "session超时");
		assertAjax(request, response);
		return "/login";
	}
	
	@ExceptionHandler(ParamsException.class)
	public String paramsError(ParamsException e, Model model)
	{
		model.addAttribute("tips", e.getMessage());
		return "/login";
	}

	private void assertAjax(HttpServletRequest request, HttpServletResponse response)
	{
		if (request.getHeader("x-requested-with") != null
				&& request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest"))
		{
			// 如果是ajax请求响应头会有，x-requested-with
			response.setHeader("sessionstatus", "timeout");// 在响应头设置session状态
		}
	}

}
