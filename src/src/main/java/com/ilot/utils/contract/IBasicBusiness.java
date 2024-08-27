
/*
 * Created on 2021-09-10 ( Time 15:58:06 )
 * Generator tool : Telosys Tools Generator ( version 3.1.2 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package com.ilot.utils.contract;



import java.text.ParseException;
import java.util.Locale;

/**
 * IBasic Business
 * 
 * @author Geo
 *
 */
public interface IBasicBusiness<T, K> {

	/**
	 * create Object by using T as object.
	 * 
	 * @param T
	 * @return K
	 * @throws ParseException 
	 * 
	 */
	K create(T request, Locale locale) throws ParseException;

	/**
	 * update Object by using T as object.
	 * 
	 * @param T
	 * @return K
	 * 
	 */
	K update(T request, Locale locale) throws ParseException;

	/**
	 * delete Object by using T as object.
	 * 
	 * @param T
	 * @return K
	 * 
	 */
	K delete(T request, Locale locale) throws ParseException;


	/**
	 * force deletetion Object by using T as object.
	 *
	 * @param T
	 * @return K
	 */
	K forceDelete(T request, Locale locale) throws Exception;

	/**
	 * get a List of Object by using T as criteria object.
	 * 
	 * @param T
	 * @return K
	 * 
	 */
	K getByCriteria(T request, Locale locale) throws Exception;
}
