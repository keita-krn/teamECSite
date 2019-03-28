package com.internousdev.neptune.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.internousdev.neptune.dto.ProductInfoDTO;
import com.internousdev.neptune.util.DBConnector;

public class ProductInfoDAO {

	public List<ProductInfoDTO> getProductInfoList(){

		DBConnector dbConnector=new DBConnector();
		Connection connection=dbConnector.getConnection();
		List<ProductInfoDTO> productInfoDTOList=new ArrayList<ProductInfoDTO>();

		String sql="select * from product_info order by product_id";
		try{
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()){
				ProductInfoDTO productInfoDTO=new ProductInfoDTO();
				productInfoDTO.setId(resultSet.getInt("id"));
				productInfoDTO.setProductId(resultSet.getInt("product_id"));
				productInfoDTO.setProductName(resultSet.getString("product_name"));
				productInfoDTO.setProductNameKana(resultSet.getString("product_name_kana"));
				productInfoDTO.setProductDescription(resultSet.getString("product_description"));
				productInfoDTO.setCategoryId(resultSet.getInt("category_id"));
				productInfoDTO.setPrice(resultSet.getInt("price"));
				productInfoDTO.setImageFilePath(resultSet.getString("image_file_path"));
				productInfoDTO.setImageFileName(resultSet.getString("image_file_name"));
				productInfoDTO.setReleaseDate(resultSet.getDate("release_date"));
				productInfoDTO.setReleaseCompany(resultSet.getString("release_company"));
				productInfoDTO.setStatus(resultSet.getInt("status"));
				productInfoDTO.setRegistDate(resultSet.getDate("regist_date"));
				productInfoDTO.setUpdateDate(resultSet.getDate("update_date"));
				productInfoDTOList.add(productInfoDTO);
			}
		} catch(SQLException e){
			e.printStackTrace();
		} finally{
			try{
				connection.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}
		return productInfoDTOList;
	}
//商品詳細画面用//
	public ProductInfoDTO getProductInfo(int productId){

		DBConnector dbConnector=new DBConnector();
		Connection connection=dbConnector.getConnection();
		ProductInfoDTO productInfoDTO=new ProductInfoDTO();

		String sql="select * from product_info where product_id=?";
		try{
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1, productId);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()){
				productInfoDTO.setId(resultSet.getInt("id"));
				productInfoDTO.setProductId(resultSet.getInt("product_id"));
				productInfoDTO.setProductName(resultSet.getString("product_name"));
				productInfoDTO.setProductNameKana(resultSet.getString("product_name_kana"));
				productInfoDTO.setProductDescription(resultSet.getString("product_description"));
				productInfoDTO.setCategoryId(resultSet.getInt("category_id"));
				productInfoDTO.setPrice(resultSet.getInt("price"));
				productInfoDTO.setImageFilePath(resultSet.getString("image_file_path"));
				productInfoDTO.setImageFileName(resultSet.getString("image_file_name"));
				productInfoDTO.setReleaseDate(resultSet.getDate("release_date"));
				productInfoDTO.setReleaseCompany(resultSet.getString("release_company"));
				productInfoDTO.setStatus(resultSet.getInt("status"));
				productInfoDTO.setRegistDate(resultSet.getDate("regist_date"));
				productInfoDTO.setUpdateDate(resultSet.getDate("update_date"));
			}
		} catch(SQLException e){
			e.printStackTrace();
		} finally{
			try{
				connection.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}
		return productInfoDTO;
	}
//商品詳細画面での関連商品表示用//
	public List<ProductInfoDTO> getProductInfoListByCategory(int categoryId, int productId, int limitOffset, int limitRowCount ){

		DBConnector dbConnector=new DBConnector();
		Connection connection=dbConnector.getConnection();
		List<ProductInfoDTO> productInfoDTOList=new ArrayList<ProductInfoDTO>();

		String sql="select * from product_info where category_id=? and product_id not in(?) order by rand() limit ?,?";
		try{
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1, categoryId);
			preparedStatement.setInt(2, productId);
			preparedStatement.setInt(3, limitOffset);
			preparedStatement.setInt(4, limitRowCount);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()){
				ProductInfoDTO productInfoDTO=new ProductInfoDTO();
				productInfoDTO.setId(resultSet.getInt("id"));
				productInfoDTO.setProductId(resultSet.getInt("product_id"));
				productInfoDTO.setProductName(resultSet.getString("product_name"));
				productInfoDTO.setProductNameKana(resultSet.getString("product_name_kana"));
				productInfoDTO.setProductDescription(resultSet.getString("product_description"));
				productInfoDTO.setCategoryId(resultSet.getInt("category_id"));
				productInfoDTO.setPrice(resultSet.getInt("price"));
				productInfoDTO.setImageFilePath(resultSet.getString("image_file_path"));
				productInfoDTO.setImageFileName(resultSet.getString("image_file_name"));
				productInfoDTO.setReleaseDate(resultSet.getDate("release_date"));
				productInfoDTO.setReleaseCompany(resultSet.getString("release_company"));
				productInfoDTO.setStatus(resultSet.getInt("status"));
				productInfoDTO.setRegistDate(resultSet.getDate("regist_date"));
				productInfoDTO.setUpdateDate(resultSet.getDate("update_date"));
				productInfoDTOList.add(productInfoDTO);
			}
		} catch(SQLException e){
			e.printStackTrace();
		} finally{
			try{
				connection.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}
		return productInfoDTOList;
	}

	/*---------------------------------------------------------------------------------------
	  SearchItemActionで呼び出すメソッド
	  入力されたキーワードに部分一致する「商品名」または「商品名かな」を持つ
	  商品の詳細情報が入ったProductInfoDTO型のリストを取得する
	---------------------------------------------------------------------------------------*/
	public List<ProductInfoDTO> getProductInfoListAll(String[] keywordsList){
		DBConnector dbConnector = new DBConnector();
		Connection connection = dbConnector.getConnection();
		List<ProductInfoDTO> productInfoDTOList = new ArrayList<ProductInfoDTO>();

		String sql = "SELECT * FROM product_info WHERE";
		for(int i = 0; i < keywordsList.length; i++){
			if(i == 0){
				sql += " (product_name like ? or product_name_kana like ?)";
			}else{
				sql += " or (product_name like ? or product_name_kana like ?)";
			}
		}

		try{
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			for(int i = 0; i < keywordsList.length; i++){
				preparedStatement.setString(i * 2 + 1, '%' + keywordsList[i] + '%');
				preparedStatement.setString(i * 2 + 2, '%' + keywordsList[i] + '%');
			}
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()){
				ProductInfoDTO productInfoDTO = new ProductInfoDTO();
				productInfoDTO.setId(resultSet.getInt("id"));
				productInfoDTO.setProductId(resultSet.getInt("product_id"));
				productInfoDTO.setProductName(resultSet.getString("product_name"));
				productInfoDTO.setProductNameKana(resultSet.getString("product_name_kana"));
				productInfoDTO.setProductDescription(resultSet.getString("product_description"));
				productInfoDTO.setCategoryId(resultSet.getInt("category_id"));
				productInfoDTO.setPrice(resultSet.getInt("price"));
				productInfoDTO.setImageFilePath(resultSet.getString("image_file_path"));
				productInfoDTO.setImageFileName(resultSet.getString("image_file_name"));
				productInfoDTO.setReleaseDate(resultSet.getDate("release_date"));
				productInfoDTO.setReleaseCompany(resultSet.getString("release_company"));
				productInfoDTO.setStatus(resultSet.getInt("status"));
				productInfoDTO.setRegistDate(resultSet.getDate("regist_date"));
				productInfoDTO.setUpdateDate(resultSet.getDate("update_date"));
				productInfoDTOList.add(productInfoDTO);
			}
		}catch (SQLException e){
			e.printStackTrace();
		}finally{
			try{
				connection.close();
			}catch (SQLException e){
				e.printStackTrace();
			}
		}
		return productInfoDTOList;
	}

	/*---------------------------------------------------------------------------------------
	  SearchItemActionで呼び出すメソッド
	  キーワードに部分一致する「商品名」または「商品名かな」を持ち、かつ選択された
	  カテゴリーに属する商品の詳細情報が入ったProductInfoDTO型のリストを取得する
	---------------------------------------------------------------------------------------*/
	public List<ProductInfoDTO> getProductInfoListByKeywords(String[] keywordsList,String categoryId){
		DBConnector dbConnector = new DBConnector();
		Connection connection = dbConnector.getConnection();
		List<ProductInfoDTO> productInfoDTOList = new ArrayList<ProductInfoDTO>();

		String sql = "SELECT * FROM product_info WHERE";
		for(int i = 0; i < keywordsList.length; i++){
			if(i == 0){
				sql += " category_id = ? and ((product_name LIKE ? or product_name_kana LIKE ?)";
			}else{
				sql += " or (product_name LIKE ? or product_name_kana LIKE ?)";
			}
		}
		sql += ")";

		try{
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, categoryId);
			for(int i = 0; i < keywordsList.length; i++){
				preparedStatement.setString(i * 2 + 2, '%' + keywordsList[i] + '%');
				preparedStatement.setString(i * 2 + 3, '%' + keywordsList[i] + '%');
			}
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				ProductInfoDTO productInfoDTO = new ProductInfoDTO();
				productInfoDTO.setId(resultSet.getInt("id"));
				productInfoDTO.setProductId(resultSet.getInt("product_id"));
				productInfoDTO.setProductName(resultSet.getString("product_name"));
				productInfoDTO.setProductNameKana(resultSet.getString("product_name_kana"));
				productInfoDTO.setProductDescription(resultSet.getString("product_description"));
				productInfoDTO.setCategoryId(resultSet.getInt("category_id"));
				productInfoDTO.setPrice(resultSet.getInt("price"));
				productInfoDTO.setImageFilePath(resultSet.getString("image_file_path"));
				productInfoDTO.setImageFileName(resultSet.getString("image_file_name"));
				productInfoDTO.setReleaseDate(resultSet.getDate("release_date"));
				productInfoDTO.setReleaseCompany(resultSet.getString("release_company"));
				productInfoDTO.setStatus(resultSet.getInt("status"));
				productInfoDTO.setRegistDate(resultSet.getDate("regist_date"));
				productInfoDTO.setUpdateDate(resultSet.getDate("update_date"));
				productInfoDTOList.add(productInfoDTO);
			}
		}catch (SQLException e){
			e.printStackTrace();
		}finally{
			try{
				connection.close();
			}catch (SQLException e){
				e.printStackTrace();
			}
		}
		return productInfoDTOList;
	}
}
