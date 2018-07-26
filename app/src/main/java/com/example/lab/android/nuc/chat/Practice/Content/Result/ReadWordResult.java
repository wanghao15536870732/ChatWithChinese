/**
 * 
 */
package com.example.lab.android.nuc.chat.Practice.Content.Result;



/**
 * <p>Title: ReadWordResult</p>
 * <p>Description: </p>
 * <p>Company: www.iflytek.com</p>
 * @author iflytek
 * @date 2015年1月12日 下午5:03:50
 */
public class ReadWordResult extends Result {
	
	public ReadWordResult() {
		category = "read_word";
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		
		if ("cn".equals(language)) {
//			buffer.append("[总体结果]\n")
//				.append("评测内容：" + content + "\n")
//				.append("朗读时长：" + time_len + "\n")
			buffer.append(  total_score );
//				.append("[朗读详情]")
//				.append(ResultFormatUtil.formatDetails_CN(sentences));
		}
		
		return buffer.toString();
	}
}
