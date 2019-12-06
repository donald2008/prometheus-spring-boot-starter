package com.kuding.markdown;

import java.time.format.DateTimeFormatter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kuding.content.ExceptionNotice;
import com.kuding.properties.ExceptionNoticeProperty;
import com.kuding.text.ExceptionNoticeResolver;

public class DefaultMarkdownMessageResolver implements ExceptionNoticeResolver {

	private final ExceptionNoticeProperty exceptionNoticeProperty;

	private final Log logger = LogFactory.getLog(getClass());

	public DefaultMarkdownMessageResolver(ExceptionNoticeProperty exceptionNoticeProperty) {
		this.exceptionNoticeProperty = exceptionNoticeProperty;
	}

	@Override
	public String resolve(ExceptionNotice exceptionNotice) {
		String title = String.format("%s(%s)", exceptionNotice.getProject(),
				exceptionNoticeProperty.getProjectEnviroment().getName());
		String markdown = SimpleMarkdownBuilder.create().title(title, 1).title("路径：", 2)
				.text(exceptionNotice.getClassPath(), true).title("方法名：", 2).text(exceptionNotice.getMethodName(), true)
				.title("参数信息：", 2).point(exceptionNotice.getParames()).title("异常信息：", 2)
				.text(exceptionNotice.getExceptionMessage(), true).title("追踪信息：", 2)
				.point(exceptionNotice.getTraceInfo()).title("最后一次出现时间：", 2)
				.text(exceptionNotice.getLatestShowTime().format(DateTimeFormatter.ISO_DATE_TIME), true)
				.title("出现次数：", 2).text(exceptionNotice.getShowCount().toString(), true).build();
		logger.debug(markdown);
		return markdown;
	}
}
