package com.common;

import org.springframework.stereotype.Service;

@Service
public class PublicService {
	
	/**
	 * 必填参数空验证
	 * @param arg
	 * @return
	 */
	public boolean checkParam(String... arg){
		for(int i=0;i<arg.length;i++){
			if(arg[i] == null || arg[i].equals("")){
				return false;
			}
		}
		return true;
	}

}
