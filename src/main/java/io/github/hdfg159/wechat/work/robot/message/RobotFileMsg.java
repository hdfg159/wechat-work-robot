package io.github.hdfg159.wechat.work.robot.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.reactivex.Maybe;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.web.multipart.MultipartForm;

import java.io.File;
import java.util.Optional;
import java.util.StringJoiner;

/**
 * 文件消息
 *
 * @author hdfg159
 * @date 2020/8/28 14:18
 */
public class RobotFileMsg extends AbstractRobotMsgBody<RobotTextMsg> {
	private static final long serialVersionUID = -2585417572015045906L;
	/**
	 * 最大 20 MB
	 */
	public static final int MB_20 = 20 * 1024;
	/**
	 * 最小 5 B
	 */
	public static final int BYTE_5 = 5;

	/**
	 * 待上传文件
	 */
	private transient File file;

	/**
	 * 文件id，通过文件上传接口获取
	 */
	@JsonProperty("media_id")
	protected String mediaId;

	public RobotFileMsg() {
		super(MessageType.FILE.name().toLowerCase());
	}

	public RobotFileMsg file(File file) {
		this.file = file;
		return this;
	}

	public String getMediaId() {
		return mediaId;
	}

	public RobotFileMsg mediaId(String mediaId) {
		this.mediaId = mediaId;
		return this;
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", RobotFileMsg.class.getSimpleName() + "[", "]")
				.add("file=" + file)
				.add("mediaId='" + mediaId + "'")
				.toString();
	}

	@Override
	public void valid() {
		if (file == null) {
			throw new IllegalArgumentException("file not allow null");
		}

		if (!file.exists() || !file.isFile()) {
			throw new IllegalArgumentException("file illegal");
		}

		if (file.length() < BYTE_5 || file.length() > MB_20) {
			throw new IllegalArgumentException("file size illegal,file size > 5 Byte and < 20 MB,current size:" + file.length());
		}
	}

	@Override
	public Maybe<JsonObject> rxSend(String key) {
		valid();
		// 重写父类 send 方法以上传文件
		MultipartForm form = MultipartForm.create()
				.binaryFileUpload("media", file.getName(), file.getAbsolutePath(), "");
		// 上传文件获取 Media Id
		return CLIENT.postAbs(UPLOAD_MEDIA_URL)
				.addQueryParam("key", key)
				.addQueryParam("type", "file")
				.rxSendMultipartForm(form)
				.flatMapMaybe(response -> Optional.ofNullable(response.bodyAsJsonObject())
						.map(entries -> entries.getString("media_id"))
						.map(mediaId -> {
							this.mediaId = mediaId;
							return super.rxSend(key);
						})
						.orElse(Maybe.empty()));
	}

}
