/**
 * @Title AdministratorServiceImpl.java
 * @author 皮龙霖
 * @description 
 * @date 2022年7月8日下午12:33:12
 */
package com.sx.oesb.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sx.oesb.entity.Administrator;
import com.sx.oesb.mapper.AdministratorMapper;
import com.sx.oesb.service.AdministratorService;


/** 
* @ClassName AdministratorServiceImpl 
* @Description 管理员表服务
* @author 皮龙霖
* @date 2022年7月8日 下午12:33:12 
*  
*/
@Service
public class AdministratorServiceImpl implements AdministratorService {
	
	@Value("${constraint.username}")
	private String usernameConstraint;

	@Value("${constraint.password}")
	private String passwordConstraint;
	
	private final AdministratorMapper administratorMapper;
	
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public AdministratorServiceImpl(AdministratorMapper administratorMapper, PasswordEncoder passwordEncoder) {
		super();
		this.administratorMapper = administratorMapper;
		this.passwordEncoder = passwordEncoder;
	}


	/**
	 * @Title adminLogin
	   * @author 皮龙霖
	   * @description 找到名字对象，验证密码
	   * @createdate 2022年7月8日 下午12:33:12
	   * @param name
	   * @param password
	   * @return 
	 * @see com.sx.oesb.service.AdministratorService#adminLogin(java.lang.String, java.lang.String)
	   **/

	@Override
	public Administrator adminLogin(String name, String password) {
		Administrator administrator = searchAdminByAdminName(name);
		if (administrator.getPassword().equals(password) ||passwordEncoder.matches(password, administrator.getPassword())) {
			return administrator;
		}
		throw new RuntimeException();
	}


	/**
	 * @Title adminRegister
	   * @author 皮龙霖
	   * @description 在用户名密码合乎要求且没同名时administratorMapper.insert
	   * @createdate 2022年7月8日 下午12:33:12
	   * @param name
	   * @param password 
	 * @see com.sx.oesb.service.AdministratorService#adminRegister(java.lang.String, java.lang.String)
	   **/

	@Override
	public boolean adminRegister(String name, String password) {
		if (name.matches(usernameConstraint) && password.matches(passwordConstraint)) {
			try {
				searchAdminByAdminName(name);
				throw new RuntimeException();
			} catch (UsernameNotFoundException e) {
				Administrator administrator= new Administrator();
				administrator.setName(name);
				administrator.setPassword(passwordEncoder.encode(password));
				return administratorMapper.insert(administrator) > 0; 
			}
		} else
			return false;
	}

	/**
	 * @Title searchAdminByAdminName
	   * @author 皮龙霖
	   * @description 按名查找Admin用户
	   * @createdate 2022年7月8日 下午12:33:12
	   * @param name
	   * @return
	   * @throws UsernameNotFoundException 
	 * @see com.sx.oesb.service.AdministratorService#searchAdminByAdminName(java.lang.String)
	   **/

	@Override
	public Administrator searchAdminByAdminName(String name) throws UsernameNotFoundException {
		List<Administrator> administrators = administratorMapper.selectList(
				new QueryWrapper<Administrator>().eq("name", name));
		if (administrators.isEmpty())
			throw new UsernameNotFoundException("admin-name: " + name + " not found");
		else
			return administrators.get(0);
	}

}
