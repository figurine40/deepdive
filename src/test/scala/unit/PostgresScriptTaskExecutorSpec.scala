package org.deepdive.test.unit

import anorm._
import org.deepdive.datastore.PostgresDataStore
import org.deepdive.extraction._
import org.deepdive.test._
import org.scalatest._
import spray.json._
import DefaultJsonProtocol._

class PostgresScriptTaskExecutorSpec extends FunSpec {

  describe("running") {    
    def prepareData() {
      TestDataStore.init()
      PostgresDataStore.withConnection { implicit conn =>
         SQL("drop schema if exists public cascade; create schema public;").execute()
         SQL("create table documents(id bigserial primary key, docid integer, text text);").execute()
         SQL(
          """
            insert into documents(docid, text) 
            VALUES (469, 'Document 1'), (470, 'Document 2')
          """).execute()
      }
    }

    it("should work with a basic query") {
      prepareData()
      TestDataStore.init()
      val extractorFile = getClass.getResource("/simple_extractor.py")
      val task = ExtractionTask("test", "output", "SELECT * FROM documents", extractorFile.getFile)
      val executor = new PostgresScriptTaskExecutor(task)
      val result = executor.run()
      assert(result.rows.map(_.compactPrint) == List(Map("document_id" -> 469), Map
        ("document_id" -> 470)).map(_.toJson.compactPrint))
    }
  }


}