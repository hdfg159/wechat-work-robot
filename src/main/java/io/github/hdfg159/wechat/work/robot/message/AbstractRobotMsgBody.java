package io.github.hdfg159.wechat.work.robot.message;


import io.reactivex.Maybe;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.client.WebClient;

import java.io.Serializable;

/**
 * 抽象机器人消息
 *
 * @author hdfg159
 * @date 2020/4/6 18:48
 */
public abstract class AbstractRobotMsgBody<E extends AbstractRobotMsgBody<E>> implements Serializable, RobotMsgBody {
	private static final long serialVersionUID = -3076309726391520049L;

	/**
	 * 发送消息地址
	 */
	protected static final String SEND_MESSAGE_URL = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send";
	/**
	 * 上传文件地址
	 */
	protected static final String UPLOAD_MEDIA_URL = "https://qyapi.weixin.qq.com/cgi-bin/webhook/upload_media";
	
	protected static final Vertx VERTX = Vertx.vertx();
	protected static final WebClient CLIENT = WebClient.create(VERTX);

	private String type;

	public AbstractRobotMsgBody(String type) {
		this.type = type;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public E type(String type) {
		this.type = type;
		return self();
	}

	public E self() {
		return (E) this;
	}

	@Override
	public JsonObject buildRequestParam() {
		valid();
		return new JsonObject()
				.put("msgtype", getType())
				.put(getType(), JsonObject.mapFrom(this));
	}

	@Override
	public Maybe<JsonObject> send(String key) {
		return CLIENT.postAbs(SEND_MESSAGE_URL)
				.addQueryParam("key", key)
				.rxSendJsonObject(buildRequestParam())
				.flatMapMaybe(res -> Maybe.create(emitter -> {
					JsonObject body = res.bodyAsJsonObject();
					if (body == null) {
						emitter.onComplete();
					} else {
						emitter.onSuccess(body);
					}
				}));
	}

	public enum MessageType {
		/**
		 * 文本
		 */
		TEXT,
		/**
		 * MARKDOWN
		 */
		MARKDOWN,
		/**
		 * 图片
		 */
		IMAGE,
		/**
		 * 图文
		 */
		NEWS,
		/**
		 * 文件
		 */
		FILE
	}
}
