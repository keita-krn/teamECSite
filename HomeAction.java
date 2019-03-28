package com.internousdev.neptune.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.neptune.dao.MCategoryDAO;
import com.internousdev.neptune.dto.MCategoryDTO;
import com.internousdev.neptune.util.CommonUtility;
import com.opensymphony.xwork2.ActionSupport;

public class HomeAction extends ActionSupport implements SessionAware{

	private Map<String,Object> session;
	private int categoryId;
	private List<MCategoryDTO> mCategoryDTOList = new ArrayList<MCategoryDTO>();
	
	public String execute(){

		if(!(session.containsKey("tempUserId"))){
			CommonUtility commonUtility = new CommonUtility();
			session.put("tempUserId",commonUtility.getRamdomValue());
		}
		if(!(session.containsKey("logined"))){
			session.put("logined", 0);
		}
		if(!(session.containsKey("mCategoryDTOList"))){
			MCategoryDAO mCategoryDAO = new MCategoryDAO();
			session.put("mCategoryDTOList",mCategoryDAO.getMCategoryList());
		}
		return SUCCESS;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public List<MCategoryDTO> getmCategoryDTOList() {
		return mCategoryDTOList;
	}
	public void setmCategoryDTOList(List<MCategoryDTO> mCategoryDTOList) {
		this.mCategoryDTOList = mCategoryDTOList;
	}
	public Map<String, Object> getSession() {
		return session;
	}
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}