package com.cgi.nikoniko.controllers.base;

import java.util.ArrayList;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.cgi.nikoniko.dao.base.IBaseCrudRepository;
import com.cgi.nikoniko.models.tables.modelbase.DatabaseItem;

public abstract class BaseController <T extends DatabaseItem> {

	protected LocalDate todayDate = new LocalDate();
	
	@Autowired
	private IBaseCrudRepository<T> baseCrud;

	private Class<T> clazz;

	protected Class<T> getClazz(){
		return clazz;
	}

	protected BaseController (Class<T> clazz){
		this.clazz = clazz;
	}

	public String deleteItem(Long id){
		try {
			baseCrud.delete(id);
		} catch (Exception e) {
			return "Delete : FAIL";
		}
		return "Delete : SUCCESS";
	}

	public String deleteItem (@ModelAttribute T item) {
		try {
			baseCrud.delete(item);
		} catch (Exception e) {
			return "Delete : FAIL";
		}
		return "Delete : SUCCESS";
	}

	public T insertItem (@ModelAttribute T item) {
		baseCrud.save(item);
		return item;
	}

	public String updateItem (@ModelAttribute T item) {
		try {
			baseCrud.save(item);
		} catch (Exception e) {
			return "Update : FAIL";
		}
		return "Update : SUCCESS";
	}

	public T getItem (Long id) {
		T item = null;
		item = baseCrud.findOne(id);
		return item;
	}

	public ArrayList<T> getItems() {

		ArrayList<T> items = null;
		items = (ArrayList<T>) baseCrud.findAll();
		return items;
	}

}
