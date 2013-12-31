package org.deepdive.test.unit

import org.deepdive.extraction._
import org.deepdive.settings.Extractor
import org.scalatest._
import spray.json._
import spray.json.DefaultJsonProtocol._

class ScriptTaskExecutorSpec extends FunSpec {

  describe("Script Task Executor") {

    it("should work with one batch") {
      val task = new ExtractionTask(Extractor("testExtractor", "relation1", 
        "relation1", "/bin/cat", 4, 100, 10000, Nil.toSet))
      val data = (1 to 1000).toList.map(i => s"""{"id":$i}""".asJson.asJsObject).toStream
      val executor = new ScriptTaskExecutor(task, data)
      val result = executor.run()
      assert(result.rows.toBlockingObservable.toList.size == 1)
      assert(result.rows.toBlockingObservable.toList.flatten.size == 1000)
    }

    it("should work with multiple batches") {
      val task = new ExtractionTask(Extractor("testExtractor", "relation1", 
        "relation1", "/bin/cat", 4, 100, 100, Nil.toSet))
      val data = (1 to 1000).toList.map(i => s"""{"id":$i}""".asJson.asJsObject).toStream
            val executor = new ScriptTaskExecutor(task, data)
      val result = executor.run()
      assert(result.rows.toBlockingObservable.toList.size == 10)
      assert(result.rows.toBlockingObservable.toList.flatten.size == 1000)
    }


  }

}