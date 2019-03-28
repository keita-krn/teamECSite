package com.internousdev.neptune.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.neptune.dao.MCategoryDAO;
import com.internousdev.neptune.dao.ProductInfoDAO;
import com.internousdev.neptune.dto.MCategoryDTO;
import com.internousdev.neptune.dto.ProductInfoDTO;
import com.internousdev.neptune.util.InputChecker;
import com.opensymphony.xwork2.ActionSupport;

public class SearchItemAction extends ActionSupport implements SessionAware{

	private String categoryId;
	private String keywords;
	private List<String> keywordsErrorMessageList = new ArrayList<String>();
	private List<ProductInfoDTO> productInfoDTOList = new ArrayList<ProductInfoDTO>();
	private List<MCategoryDTO> mCategoryDTOList = new ArrayList<MCategoryDTO>();
	private Map<String,Object> session;

	public String execute(){
		if(session.isEmpty()){
			return "sessionErr";
		}
		String result = ERROR;
		String tempKeywords = null;
		if(StringUtils.isBlank(keywords)){
			tempKeywords = "";
		}else{
			tempKeywords = keywords.replaceAll("　", " ").replaceAll("\\s{2,}", " ");
		}
		if(!tempKeywords.equals("")){
			InputChecker inputChecker = new InputChecker();
			keywordsErrorMessageList = inputChecker.doCheck("検索ワード", keywords, 0, 50, true, true, true, true, false, true, true);
			Iterator<String> iterator = keywordsErrorMessageList.iterator();
			if(iterator.hasNext()) {
				return result;
			}
		}
		ProductInfoDAO productInfoDAO = new ProductInfoDAO();
		switch(categoryId){
		case "1":
			if(!tempKeywords.equals("")){
				productInfoDTOList = productInfoDAO.getProductInfoListAll(tempKeywords.split(" "));
			}else{	
				productInfoDTOList = productInfoDAO.getProductInfoList();
			}
			result = SUCCESS;
			break;
		default:
			productInfoDTOList = productInfoDAO.getProductInfoListByKeywords(tempKeywords.split(" "), categoryId);
			result = SUCCESS;
			break;
		}

		Iterator<ProductInfoDTO> iterator = productInfoDTOList.iterator();
		if(!iterator.hasNext()){
			productInfoDTOList = null;
		}

		if(!session.containsKey("mCategoryDTOList")){
			MCategoryDAO mCategoryDAO = new MCategoryDAO();
			mCategoryDTOList = mCategoryDAO.getMCategoryList();
			session.put("mCategoryDTOList",mCategoryDTOList);
		}
		return result;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public List<String> getKeywordsErrorMessageList() {
		return keywordsErrorMessageList;
	}

	public void setKeywordsErrorMessageList(List<String> keywordsErrorMessageList) {
		this.keywordsErrorMessageList = keywordsErrorMessageList;
	}

	public List<ProductInfoDTO> getProductInfoDTOList() {
		return productInfoDTOList;
	}

	public void setProductInfoDTOList(List<ProductInfoDTO> productInfoDTOList) {
		this.productInfoDTOList = productInfoDTOList;
	}

	public List<MCategoryDTO> getMCategoryDTOList() {
		return mCategoryDTOList;
	}

	public void setMCategoryDTOList(List<MCategoryDTO> mCategoryDTOList) {
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