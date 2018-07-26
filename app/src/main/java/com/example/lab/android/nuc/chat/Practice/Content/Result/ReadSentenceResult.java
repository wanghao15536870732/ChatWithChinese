/**
 * 
 */
package com.example.lab.android.nuc.chat.Practice.Content.Result;



/**
 * <p>Title: ReadSentenceResult</p>
 * <p>Description: </p>
 * <p>Company: www.iflytek.com</p>
 * @author iflytek
 * @date 2015年1月12日 下午5:04:14
 */
public class ReadSentenceResult extends Result {
	
	public ReadSentenceResult() {
		category = "read_sentence";
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		
		if ("cn".equals(language)) {
//			buffer.append("[总体结果]\n")
//				.append("评测内容：" + content + "\n")
//				.append("朗读时长：" + time_len + "\n")
				buffer.append( total_score );
//				.append("[朗读详情]").append(ResultFormatUtil.formatDetails_CN(sentences));
		}
		
		return buffer.toString();
	}
}
