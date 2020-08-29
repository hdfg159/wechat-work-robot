package io.github.hdfg159.wechat.work.robot

import groovy.test.GroovyTestCase
import io.github.hdfg159.wechat.work.robot.message.RobotImgArticleMsg
import io.vertx.core.json.JsonObject

/**
 * Project:wechat-work-robot
 * <p>
 * Package:io.github.hdfg159.wechat.work.robot
 * <p>
 *
 * @date 2020/8/28 18:28
 * @author zhangzhenyu
 */
class RobotMessagesTest extends GroovyTestCase {
	def key = ""
	def mobile = ""
	
	void testText() {
		def result = RobotMessages
				.text("hello world")
				.mentionedMobileList([mobile])
				.send(key)
				.blockingGet()
		
		assertSuc(result)
	}
	
	void testLongText() {
		def text = (1..2999).collect {it}.join("")
		def result = RobotMessages
				.text(text)
				.mentionedMobileList([mobile])
				.send(key)
				.blockingGet()
		
		assertSuc(result)
	}
	
	void testImg() {
		def base64 = "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"
		def md5 = "B23A6657BE07FCFCB6589C2022821B4C"
		def result = RobotMessages
				.img(base64, md5)
				.send(key)
				.blockingGet()
		assertSuc(result)
	}
	
	void testImgArticle() {
		List<RobotImgArticleMsg.Article> articles = (1..8).collect {
			new RobotImgArticleMsg.Article(
					title: "test1",
					picUrl: "https://img.ithome.com/newsuploadfiles/2020/4/20200406_193534_215.jpg",
					url: "https://m.ithome.com/html/481352.htm"
			)
		}
		
		def result = RobotMessages
				.imgArticle(articles)
				.send(key)
				.blockingGet()
		assertSuc(result)
		
		List<RobotImgArticleMsg.Article> articlesMany = (1..9).collect {
			new RobotImgArticleMsg.Article(
					title: "test1",
					picUrl: "https://img.ithome.com/newsuploadfiles/2020/4/20200406_193534_215.jpg",
					url: "https://m.ithome.com/html/481352.htm"
			)
		}
		shouldFail {
			RobotMessages
					.imgArticle(articlesMany)
					.send(key)
					.blockingGet()
		}
	}
	
	void testMarkdown() {
		def result = RobotMessages
				.markdown("> test hello world")
				.send(key)
				.blockingGet()
		assertSuc(result)
		
		def longText = RobotMessages
				.markdown((1..2999).collect {it}.join(""))
				.send(key)
				.blockingGet()
		assertSuc(longText)
	}
	
	void testFile() {
		shouldFail {
			RobotMessages
					.file(new File("C:\\Users\\ZZY2019\\Desktop\\Temp"))
					.send(key)
					.blockingGet()
		}
		
		shouldFail {
			RobotMessages
					.file(new File("C:\\Users\\ZZY2019\\Desktop\\Temp\\uuid.asdasd"))
					.send(key)
					.blockingGet()
		}
		
		def result = RobotMessages
				.file(new File("C:\\Users\\ZZY2019\\Desktop\\Temp\\1.html"))
				.send(key)
				.blockingGet()
		assertSuc(result)
	}
	
	private void assertSuc(JsonObject result) {
		assert result
		assert result.containsKey("errcode")
		assert result.getInteger("errcode") == 0
	}
}
