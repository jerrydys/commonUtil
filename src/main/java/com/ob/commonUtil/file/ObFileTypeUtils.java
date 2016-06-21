package com.ob.commonUtil.file;

public class ObFileTypeUtils {
	
	/**
	 * 根据HTTP的MIME类型获取文件扩展名
	 * @param mime
	 * @return
	 */
	public static String getFileSuffixByMIME(String mime){
		
		if("image/pjpeg".equals(mime)||"image/jpeg".equals(mime))
			return "jpg";
		if("image/gif".equals(mime))
			return "gif";
		if("image/png".equals(mime))
			return "png";
		if("application/pdf".equals(mime))
			return "pdf";
		if("application/msword".equals(mime))
			return "doc";
		if("application/vnd.openxmlformats-officedocument.wordprocessingml.document".equals(mime))
			return "docx";
		if("application/vnd.ms-excel".equals(mime))
			return "xls";
		if("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(mime))
			return "xlsx";
		if("application/pdf".equals(mime))
			return "pdf";
		
		return null;
		
	}

}
