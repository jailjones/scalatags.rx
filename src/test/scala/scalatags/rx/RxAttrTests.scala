package scalatags.rx

import rx._
import scalatags.JsDom.all._
import utest._
import scalatags.rx.Implicits._

object RxAttrTests extends TestSuite {

  val tests = Tests {
    "bind " - {
      Rx {
        val a = Var("test")
        val el = p(cls := a).render

        assert(el.className nonEmpty)
        assert(el.className == "test")

        a() = "test2"
        assert(el.className == "test2")
      }
    }

    "bind Rx expression" - {
      Rx {
        val a = Var("a")
        val b = Var("b")
        val el = p(cls := Rx {
          a() + b()
        }).render

        assert(el.className == "ab")

        b() = "c"
        assert(el.className == "ac")
      }
    }
  }
}
