package com.hm.hxl.order.util;

import java.util.ArrayList;
import java.util.List;
/**
 * 类名: Pager 
 * 作用: TODO(这里用一句话描述这个类的作用) 
 * 作者: yanpengjie 
 * 日期: 2015-7-17 下午12:51:43 
 * 版本: V 1.0
 *
 */
public  class Pager {
	
	public int count=0;//总记录数
	public int pages=0;//总页数
	public int page=1;//当前页
	public int from=0;//记录的起始索引
	public int size=20;//每页的记录数
	int numberCount=5;//显示的左右页码
	
	public  List<String> pagerNumbers=new ArrayList<String>();

	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getPages() {
		pages=(int) Math.ceil((float) count / size);  //向上取整  12.3  ---》  13
		if (pages == 0)
			pages = 1;
		return pages;
	}
	public void setPages(int pages) { 
		this.pages = pages;
	}
	public int getPage() {
		if(page<=0){
			page=1;
		} 
		return page;
	}
	public void setPage(int page) {
		if (page <=0) { // *当前页码数
			page = 1;
		}
		this.page = page;
	}
	public int getFrom() {
		from =(page - 1) * size;
		if(from>count||from<0){
			from=0;
		}
		 
		return from;
	}
	public void setFrom(int from) {
		 
		this.from = from;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
 
	public void setPagerNumbers(List<String> pagerNumbers) {
		this.pagerNumbers = pagerNumbers;
	}
	 /**
	   * 返回显示的列表页数 
	   * @param pages 总页数
	   * @param nowPage 当前页
	   * @param count 当前页的前后页码数量
	   * @author Administrator
	   * */
	  public List<String> showPages(int numberCount){
			 
			 int start=1;
			 int end=1;
			  
			 List<String> pageList=new ArrayList<String>();
			  if(page-numberCount<=0){ 
				   start= 1;//开始页码 
			  }else{
				  start=page-numberCount;
			  }

			  if (page + numberCount > pages) {
				  end = pages;// 结束页码
				} else{
					end = page + numberCount;
				}
			  
			  if(end-start<numberCount*2){
				  if(page<numberCount){
					  end=end+(numberCount-start);
					  if(end>pages){
						  end= pages;
					  }
				  }else{
					  start=start-(page+numberCount-end);
				  }
			  }

			  if(end<=0) end= page+numberCount;//结束页码
			  if(start<=0){
				  start=1;
			  }
			  if(numberCount*2+1>=pages){
				  start=1;
				  end=pages;
			  }
			  for(int i=start;i<=end;i++){
				  pageList.add(i+""); 
			  } 
			return pageList;
		}
	public int getNumberCount() {
		return numberCount;
	}
	public void setNumberCount(int numberCount) {
		this.numberCount = numberCount;
	}
	
	/**
	 * 获得页码串  1 2 3 4 5 六 7 8 9 10 11
	 * @param nowpage 当前页码 （六）
	 * @param totalNum 总页数（20）
	 * @param numberCount 当前页前后页码个数（5）
	 * getPagerNumbers(6,20,5 )
	 * 
	 * */
	public List<String> getPagerNumbers() {
		 
		 int start=1;
		 int end=1;
		  
		 List<String> pageList=new ArrayList<String>();
		  if(page-numberCount<=0){ 
			   start= 1;//开始页码 
		  }else{
			  start=page-numberCount;
		  }

		  if (page + numberCount > getPages()) {
			  end = getPages();// 结束页码
			} else{
				end = page + numberCount;
			}
		  
		  if(end-start<numberCount*2){
			  if(page<numberCount){
				  end=end+(numberCount-start);
				  if(end>getPages()){
					  end= getPages();
				  }
			  }else{
				  start=start-(page+numberCount-end);
			  }
		  }

		  if(end<=0) end= page+numberCount;//结束页码
		  if(start<=0){
			  start=1;
		  }
		  if(numberCount*2+1>=getPages()){
			  start=1;
			  end=getPages();
		  }
		  for(int i=start;i<=end;i++){
			  pageList.add(i+""); 
		  } 
		return pageList;
	}
}

