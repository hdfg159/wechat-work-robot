package io.github.hdfg159.wechat.work.robot.message;

import io.reactivex.Maybe;
import io.vertx.core.json.JsonObject;

/**
 * 机器人消息接口
 *
 * @author hdfg159
 * @date 2020/4/7 9:14
 */
public interface RobotMsgBody {
	/**
	 * 获取消息类型
	 *
	 * @return 消息类型
	 */
	String getType();

	/**
	 * 设置消息类型
	 *
	 * @param type
	 * 		类型
	 *
	 * @return 消息类型
	 */
	RobotMsgBody type(String type);

	/**
	 * 校验
	 */
	void valid();

	/**
	 * 构建 数据
	 *
	 * @return 数据
	 */
	JsonObject buildRequestParam();

	/**
	 * 发送数据
	 *
	 * @param key
	 * 		机器人密钥
	 *
	 * @return String 返回结果
	 */
	Maybe<JsonObject> send(String key);
}
