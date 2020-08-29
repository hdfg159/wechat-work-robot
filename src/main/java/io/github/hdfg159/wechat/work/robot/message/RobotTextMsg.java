package io.github.hdfg159.wechat.work.robot.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.vertx.core.buffer.Buffer;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.StringJoiner;

/**
 * 文本 消息
 *
 * @author hdfg159
 * @date 2020/4/6 18:56
 */
public class RobotTextMsg extends AbstractRobotMsgBody<RobotTextMsg> {
	private static final long serialVersionUID = 801133018881328777L;
	private static final int MAX_LENGTH = 5120;

	/**
	 * 最长不超过2048个字节
	 */
	private String content;
	/**
	 * 被@用户 @all是全部
	 */
	@JsonProperty("mentioned_list")
	private List<String> mentionedList;
	/**
	 * 被@手机号 @all是全部
	 */
	@JsonProperty("mentioned_mobile_list")
	private List<String> mentionedMobileList;

	public RobotTextMsg() {
		super(MessageType.TEXT.name().toLowerCase());
	}

	public RobotTextMsg content(String content) {
		this.content = content;
		return this;
	}

	public String getContent() {
		return content;
	}

	public List<String> getMentionedList() {
		return mentionedList;
	}

	public List<String> getMentionedMobileList() {
		return mentionedMobileList;
	}

	public RobotTextMsg mentionedList(List<String> mentionedList) {
		this.mentionedList = mentionedList;
		return this;
	}

	public RobotTextMsg mentionedMobileList(List<String> mentionedMobileList) {
		this.mentionedMobileList = mentionedMobileList;
		return this;
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", RobotTextMsg.class.getSimpleName() + "[", "]")
				.add("content='" + content + "'")
				.add("mentionedList=" + mentionedList)
				.add("mentionedMobileList=" + mentionedMobileList)
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
