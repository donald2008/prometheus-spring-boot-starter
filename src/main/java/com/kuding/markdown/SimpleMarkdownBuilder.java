package com.kuding.markdown;

import java.util.List;

public class SimpleMarkdownBuilder {

	private StringBuilder stringBuilder = new StringBuilder();

	public static SimpleMarkdownBuilder create() {
		SimpleMarkdownBuilder markdownBuilder = new SimpleMarkdownBuilder();
		return markdownBuilder;
	}

	public SimpleMarkdownBuilder title(String content, int level) {
		if (level > 0 && level < 6) {
			for (int i = 0; i < level; i++)
				stringBuilder.append("#");
			stringBuilder.append(" ").append(content).append("\n\n");
		}
		return this;
	}

	public SimpleMarkdownBuilder bold(String content) {
		stringBuilder.append(String.format("**%s**", content));
		return this;
	}

	public SimpleMarkdownBuilder text(String content, boolean lineFeed) {
		stringBuilder.append(content);
		if (lineFeed)
			stringBuilder.append("\n\n");
		return this;
	}

	public SimpleMarkdownBuilder point(List<String> contentList) {
		contentList.forEach(x -> stringBuilder.append("- ").append(x).append("\n"));
		stringBuilder.append("\n");
		return this;
	}

	public SimpleMarkdownBuilder orderPoint(List<String> list) {
		for (int i = 0; i < list.size(); i++)
			stringBuilder.append(i + 1).append(". ").append(list.get(i)).append("\n");
		stringBuilder.append("\n");
		return this;
	}

	public SimpleMarkdownBuilder code(String content, int level) {
		if (level > 0 && level < 4) {
			String str = "`````````".substring(0, level);
			if (level != 3)
				stringBuilder.append(String.format("%s%s%s", str, content, str));
			else
				stringBuilder.append(String.format("%s\n%s\n%s\n", str, content, str));
		}
		return this;
	}

	public SimpleMarkdownBuilder linked(String explain, String url) {
		stringBuilder.append("![").append(explain).append("](").append(url).append(")");
		return this;
	}

	public SimpleMarkdownBuilder nextLine() {
		stringBuilder.append("\n");
		return this;
	}

	public String build() {
		return stringBuilder.toString();
	}
}
