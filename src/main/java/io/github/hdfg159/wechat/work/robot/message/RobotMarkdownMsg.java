package io.github.hdfg159.wechat.work.robot.message;

import io.vertx.core.buffer.Buffer;

import java.nio.charset.StandardCharsets;
import java.util.StringJoiner;

/**
 * Markdown 消息
 *
 * @author hdfg159
 * @date 2020/4/6 18:58
 */
public class RobotMarkdownMsg extends AbstractRobotMsgBody<RobotMarkdownMsg> {
	private static final long serialVersionUID = 7156510233057926357L;
	private static final int MAX_LENGTH = 4096;

	/**
	 * markdown 内容
	 */
	private String content;

	public RobotMarkdownMsg() {
		super(MessageType.MARKDOWN.name().toLowerCase());
	}

	public String getContent() {
		return content;
	}

	public RobotMarkdownMsg content(String content) {
		this.content = content;
		return this;
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", RobotMarkdownMsg.class.getSimpleName() + "[", "]")
				.add("content='" + content + "'")
				.toString();
	}

	@Override
	public void valid() {
		if (content == null || content.isEmpty()) {
			throw new IllegalArgumentException("content empty");
		}

		byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
		if (bytes.length > MAX_LENGTH) {
			System.err.println("content too long:" + bytes.length);
		}

		content = Buffer.buffer(content)
				.slice(0, Math.min(bytes.length, MAX_LENGTH))
				.toString(StandardCharsets.UTF_8);
	}
}
